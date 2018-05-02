package com.github.ralphgj.bot;

import com.github.ralphgj.bot.model.receive.*;
import com.github.ralphgj.bot.model.send.Text;
import com.github.ralphgj.bot.model.send.Text.TextBuilder;
import com.github.ralphgj.coin.CoinClient;
import com.github.ralphgj.coin.model.send.LatestPrice;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ralph on 2018/4/25.
 * Email: ralphgejie@gmail.com
 *
 * @author ralph
 */
@Component
public class BotClient {
    private static final Logger logger = LoggerFactory.getLogger(BotClient.class);

    @Value("${telegram_bot_url}")
    private String botUrl;

    private OkHttpClient client = new OkHttpClient();

    private Gson gson = new Gson();

    private AtomicBoolean started = new AtomicBoolean(false);
    private AtomicInteger offset = new AtomicInteger();

    private ConcurrentHashMap<Integer, String> watchTasks = new ConcurrentHashMap<>();

    @Autowired
    private CoinClient coinClient;

    @Scheduled(fixedDelay = 10000)
    public void watchTasks() {
        watchTasks.entrySet().parallelStream()
                .forEach(entry -> {
                    String text = handleCoinCommand(entry.getValue());
                    TextBuilder builder = Text.builder()
                            .chatId(entry.getKey())
                            .text(text);
                    sendMessage(builder.build());
                });
    }

    @Scheduled(fixedDelay = 100)
    public void watchUpdates() {
        if (started.compareAndSet(false, true)) {

            HttpUrl url = HttpUrl.parse(botUrl + "/getUpdates")
                    .newBuilder()
                    .addQueryParameter("offset", String.valueOf(offset.get()))
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String updatesText = response.body().string();
                logger.info("------------" + updatesText);
                Type resultType = new TypeToken<BotResult<List<Update>>>(){}.getType();
                BotResult<List<Update>> result = gson.fromJson(updatesText, resultType);
                List<Update> updates = result.getResult();
                updates.parallelStream()
                        .forEach(update -> {
                            Message message = update.getMessage();
                            Text text = handleMessage(message);
                            sendMessage(text);
                        });
                if (updates.size() > 0) {
                    // offset need plus 1
                    offset.set(updates.get(updates.size() - 1).getUpdateId() + 1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                started.set(false);
            }
        }
    }

    private Text handleMessage(Message message) {
        Text text = null;
        String plainText = "";
        if (CollectionUtils.isNotEmpty(message.getEntities())) {
            MessageEntity messageEntity = message.getEntities().get(0);
            if (messageEntity.getType().equals(MessageEntityType.BOT_COMMAND.getType())) {
                String command = message.getText().substring(messageEntity.getOffset(), messageEntity.getLength());
                String content = message.getText().substring(command.length());
                command = command.trim();
                if (command.equals("/coin")) {
                    plainText = handleCoinCommand(content);
                } else if(command.equals("/watch")) {
                    watchTasks.put(message.getFrom().getId(), content);
                } else {
                    plainText = "Not support the command!";
                }
            }
        } else {
            plainText = message.getText();
        }
        TextBuilder builder = Text.builder()
                .chatId(message.getFrom().getId())
                .text(plainText)
                .originMessageId(message.getMessageId());
        text = builder.build();
        return text;
    }

    private String handleCoinCommand(String content) {
        List<String> symbols = Arrays.asList(content.split(","));
        StringBuilder builder = new StringBuilder();
        symbols.parallelStream()
                .forEach(symbol -> {
                    try {
                        LatestPrice latestPrice = coinClient.getLatestPrice(symbol);
                        builder.append(latestPrice.toString());
                    } catch (Exception e) {
                        builder.append(symbol.toUpperCase())
                                .append(":\n")
                                .append("Invalid pair, please input again!\n")
                                .append("===============================\n");
                        e.printStackTrace();
                    }
                });
        return builder.toString();
    }

    public void sendMessage(Text text) {
        HttpUrl url = HttpUrl.parse(botUrl + "/sendMessage")
                .newBuilder()
                .addQueryParameter("chat_id", String.valueOf(text.getChatId()))
                .addQueryParameter("text", text.getText())
//                .addQueryParameter("reply_to_message_id", String.valueOf(text.getOriginMessageId()))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            logger.info(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
