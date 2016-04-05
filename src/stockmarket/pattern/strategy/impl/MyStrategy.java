package stockmarket.pattern.strategy.impl;

import java.util.Arrays;
import java.util.List;

import stockmarket.external.Stock;
import stockmarket.pattern.strategy.Strategy;

public class MyStrategy implements Strategy{
	
	private String[] goodStocks = {"GOOG", "LNKD", "AAPL"};
    List<String> goodStocksList = Arrays.asList(goodStocks);
    
    private String[] badStocks = {"AMZN", "ROST", "ERTS"};
    List<String> badStocksList = Arrays.asList(badStocks);
	
	@Override
	public String analyze(Stock stock) {
		if (goodStocksList.indexOf(stock.getSymbol()) != -1) {
			return "buy";
		} else if(badStocksList.indexOf(stock.getSymbol()) != -1){
			return "sell";
		}
		return "hold";
	}
	
}
