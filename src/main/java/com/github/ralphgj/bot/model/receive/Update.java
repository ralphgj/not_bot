package com.github.ralphgj.bot.model.receive;

import com.google.gson.annotations.SerializedName;
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
public class Update {
    @SerializedName("update_id")
    private int updateId;

    private Message message;
}
