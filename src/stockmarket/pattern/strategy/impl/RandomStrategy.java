package stockmarket.pattern.strategy.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import stockmarket.external.Stock;
import stockmarket.pattern.strategy.Strategy;

/**
 * ThirdStrategy.java
 * @author Nghia Tran
 * @date Apr 4, 2016
 */
public class RandomStrategy implements Strategy{

	/**
	 * Is buy
	 * @param stock
	 * @return
	 */
	private boolean isBuy(Stock stock) {
		return stock.getSymbol().length() == 4;
	}
	
	/**
	 * Is sell
	 * @return
	 */
	private boolean isSell() {
		int end = 1500;
		int begin = 1200;
		
		DateFormat dateFormat = new SimpleDateFormat("HHmm");
		int now = Integer.parseInt(dateFormat.format(new Date()));		
		if (begin < now && now < end) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Analyze to see what we should do with the stock
	 */
	@Override
	public String analyze(Stock stock) {
		if (isSell() && isBuy(stock)) {
			return "hold";
		} else if(isSell()){
			return "sell";
		} else if(isBuy(stock)){
			return "buy";
		}	
		return "hold";
	}

}
