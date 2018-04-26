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
public class User {
    private int id;

    private boolean isBot;

    private String firstName;

    private String lastName;

    private String username;

    private String languageCode;
}
