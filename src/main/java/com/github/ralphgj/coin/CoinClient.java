package com.github.ralphgj.coin;

import com.github.ralphgj.coin.model.recieve.BinanceLatest;
import com.github.ralphgj.coin.model.recieve.GateIOLatest;
import com.github.ralphgj.coin.model.send.LatestPrice;
import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Created by ralph on 2018/4/25.
 * Email: ralphgejie@gmail.com
 *
 * @author ralph
 */
@Component
public class CoinClient {

    private static final Logger logger = LoggerFactory.getLogger(CoinClient.class);

    private static final String DEFAULT_PAIR_SUFFIX = "_usdt";

    @Value("${huobi_url}")
    private String huobiUrl;

    @Value("${gateio_url}")
    private String gateioUrl;

    @Value("${binance_url}")
    private String binanceUrl;

    private OkHttpClient client = new OkHttpClient();

    private Gson gson = new Gson();

    public LatestPrice getLatestPrice(String symbol) {
        symbol = symbol.trim();
        GateIOLatest gateIOLatest = getFromGateio(symbol);
        BinanceLatest binanceLatest = getFromBinance(symbol);
        return new LatestPrice(symbol, gateIOLatest, binanceLatest);
    }

    private String getFromHuobi(String symbol) {
        return null;
    }

    private GateIOLatest getFromGateio(String symbol) {
        if (symbol.toLowerCase().equals("bcc") || symbol.toLowerCase().startsWith("bcc_")) {
            symbol = symbol.replace("bcc", "bch");
        }
        if (!symbol.endsWith(DEFAULT_PAIR_SUFFIX) && !symbol.contains("_")) {
            symbol = symbol + DEFAULT_PAIR_SUFFIX;
        }
        HttpUrl url = HttpUrl.parse(gateioUrl + "/api2/1/ticker/" + symbol)
                .newBuilder()
                .build();
        String body = excute(url);
        if (StringUtils.isEmpty(body)) {
            return null;
        }
        try {
            GateIOLatest latest = gson.fromJson(body, GateIOLatest.class);
            return latest;
        } catch (Exception e) {
            logger.error("Serialized failed, response body: " + body, e);
            return null;
        }
    }

    private BinanceLatest getFromBinance(String symbol) {
        if (symbol.toLowerCase().equals("bch") || symbol.toLowerCase().startsWith("bch_")) {
            symbol = symbol.replace("bch", "bcc");
        }
        if (!symbol.endsWith(DEFAULT_PAIR_SUFFIX) && !symbol.contains("_")) {
            symbol = symbol + DEFAULT_PAIR_SUFFIX;
        }
        HttpUrl url = HttpUrl.parse(binanceUrl + "/api/v1/ticker/24hr")
                .newBuilder()
                .addQueryParameter("symbol", symbol.replace("_", "").toUpperCase())
                .build();
        String body = excute(url);
        if (StringUtils.isEmpty(body)) {
            return null;
        }
        try {
            BinanceLatest latest = gson.fromJson(body, BinanceLatest.class);
            return latest;
        } catch (Exception e) {
            logger.error("Serialized failed, response body: " + body, e);
            return null;
        }
    }

    private String excute(HttpUrl url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String body = response.body().string();
            logger.info(body);
            return body;
        } catch (IOException e) {
            logger.error("Request failed: ", e);
        }
        return null;
    }
}
