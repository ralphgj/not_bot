package com.github.ralphgj.bot.model.send;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ralph on 2018/4/25.
 * Email: ralphgejie@gmail.com
 *
 * @author ralph
 */
@Getter
@Setter
@Builder
public class Text {
    private int chatId;

    private String text;

    private int originMessageId;
}
