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
public class BotResult<T> {

    private boolean ok;

    private T result;
}
