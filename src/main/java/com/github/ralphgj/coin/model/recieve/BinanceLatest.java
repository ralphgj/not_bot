package com.github.ralphgj.coin.model.recieve;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ralph on 2018/5/2.
 * Email: ralphgejie@gmail.com
 *
 * @author ralph
 */
@Getter
@Setter
public class BinanceLatest {
    // 最新成交价
    private double lastPrice;

    // 24小时最高价
    private double highPrice;

    // 24小时最低价
    private double lowPrice;

    // 涨跌百分比
    private double priceChangePercent;
}
