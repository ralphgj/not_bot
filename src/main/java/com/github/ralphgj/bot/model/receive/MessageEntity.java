package com.github.ralphgj.bot.model.receive;

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
public class MessageEntity {
    private String type;

    private int offset;

    private int length;

    private String url;

    private User user;
}
