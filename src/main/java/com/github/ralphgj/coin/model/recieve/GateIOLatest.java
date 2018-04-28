package com.github.ralphgj.coin.model.recieve;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ralph on 2018/4/28.
 * Email: ralphgejie@gmail.com
 *
 * @author ralph
 */
@Getter
@Setter
public class GateIOLatest {
    // 交易量
    private double baseVolume;

    // 24小时最高价
    private double high24hr;

    // 买方最高价
    private double highestBid;

    // 最新成交价
    private double last;

    // 24小时最低价
    private double low24hr;

    // 卖方最低价
    private double lowestAsk;

    // 涨跌百分比
    private double percentChange;

    // 兑换货币交易量
    private double quoteVolume;

}
