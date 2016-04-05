package stockmarket.pattern.strategy.impl;

import stockmarket.external.Stock;
import stockmarket.pattern.strategy.Strategy;

/**
 * FirstStrategy.java
 * @author Nghia Tran
 * @date Apr 4, 2016
 */
public class FirstStrategy implements Strategy{

	@Override
	public String analyze(Stock stock) {
		double last = stock.getCurrentPrice();
		double close = stock.getClosePrice();
		double diff = (last - close)/close;
		if (diff > 0.05) {
			return "buy";
		} else if(diff < -0.05){
			return "sell";
		}
		return "hold";
	}

}
