package com.github.ralphgj.coin;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by ralph on 2018/4/25.
 * Email: ralphgejie@gmail.com
 *
 * @author ralph
 */
public class CoinClient {

    @Value("huobi_url")
    private String huobiUrl;

    @Value("gateio_url")
    private String gateioUrl;

    @Value("binance_url")
    private String binanceUrl;

    public String getLatestPrice(String symbol) {
        return null;
    }

    private String getFromHuobi(String symbol) {
        return null;
    }

    private String getFromGateio(String symbol) {
        return null;
    }

    private String getFromBinance(String symbol) {
        return null;
    }
}
