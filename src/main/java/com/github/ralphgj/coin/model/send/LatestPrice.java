package com.github.ralphgj.coin.model.send;

import com.github.ralphgj.coin.model.recieve.BinanceLatest;
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
    private double gateIOLatestPrice;
    private double binanceLatestPrice;

    // 涨跌百分比
    private double gateIOPercentChange;
    private double binancePercentChange;

    // 24小时最高价
    private double gateIOHigh24Price;
    private double binanceHigh24Price;

    // 24小时最低价
    private double gateIOLow24Price;
    private double binanceLow24Price;

    public LatestPrice(String symbol, GateIOLatest gateIOLatest, BinanceLatest binanceLatest) {
        this.symbol = symbol;
        if (gateIOLatest != null) {
            this.gateIOLatestPrice = gateIOLatest.getLast();
            this.gateIOPercentChange = gateIOLatest.getPercentChange();
            this.gateIOHigh24Price = gateIOLatest.getHigh24hr();
            this.gateIOLow24Price = gateIOLatest.getLow24hr();
        }
        if (binanceLatest != null) {
            this.binanceLatestPrice = binanceLatest.getLastPrice();
            this.binancePercentChange = binanceLatest.getPriceChangePercent();
            this.binanceHigh24Price = binanceLatest.getHighPrice();
            this.binanceLow24Price = binanceLatest.getLowPrice();
        }
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
                .append("最新成交价: ")
                .append("Gate -> " + numberFormat.format(gateIOLatestPrice) + ", ")
                .append("币安 -> " + numberFormat.format(binanceLatestPrice))
                .append("\n")
                .append("涨跌百分比: ")
                .append("Gate -> " + numberFormat.format(gateIOPercentChange) + "%, ")
                .append("币安 -> " + numberFormat.format(binancePercentChange) + "%")
                .append("\n")
                .append("24小时最高价: ")
                .append("Gate -> " + numberFormat.format(gateIOHigh24Price) + ", ")
                .append("币安 -> " + numberFormat.format(binanceHigh24Price))
                .append("\n")
                .append("24小时最低价: ")
                .append("Gate -> " + numberFormat.format(gateIOLow24Price) + ", ")
                .append("币安 -> " + numberFormat.format(binanceLow24Price))
                .append("\n")
                .append("===============================\n");
        return builder.toString();
    }
}