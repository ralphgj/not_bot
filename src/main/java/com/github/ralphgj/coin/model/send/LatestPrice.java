package com.github.ralphgj.coin.model.send;

import com.github.ralphgj.coin.model.recieve.GateIOLatest;
import lombok.Getter;
import lombok.Setter;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by ralph on 2018/4/28.
 * Email: ralphgejie@gmail.com
 *
 * @author ralph
 */
@Getter
@Setter
public class LatestPrice {
    // 交易对
    private String symbol;

    // 最新成交价
    private double latestPrice;

    // 涨跌百分比
    private double percentChange;

    // 24小时最高价
    private double high24Price;

    // 24小时最低价
    private double low24Price;

    public LatestPrice(String symbol, GateIOLatest latest) {
        this.symbol = symbol;
        this.latestPrice = latest.getLast();
        this.percentChange = latest.getPercentChange();
        this.high24Price = latest.getHigh24hr();
        this.low24Price = latest.getLow24hr();
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(12);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
//        numberFormat.setGroupingUsed(false);
        StringBuilder builder = new StringBuilder();
        builder.append(symbol.toUpperCase())
                .append(":\n")
                .append("最新成交价：")
                .append(numberFormat.format(latestPrice))
                .append("\n")
                .append("涨跌百分比: ")
                .append(numberFormat.format(percentChange))
                .append("%")
                .append("\n")
                .append("24小时最高价: ")
                .append(numberFormat.format(high24Price))
                .append("\n")
                .append("24小时最低价: ")
                .append(numberFormat.format(low24Price))
                .append("\n")
                .append("==================");
        return builder.toString();
    }
}