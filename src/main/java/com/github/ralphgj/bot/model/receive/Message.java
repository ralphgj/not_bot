package com.github.ralphgj.bot.model.receive;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by ralph on 2018/4/25.
 * Email: ralphgejie@gmail.com
 *
 * @author ralph
 */
@Getter
@Setter
public class Message {
    @SerializedName("message_id")
    private int messageId;

    private User from;

    private int date;

    private String text;

    private List<MessageEntity> entities;
}
