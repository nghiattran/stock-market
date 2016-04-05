package stockmarket.pattern.strategy;

import stockmarket.external.Stock;

/**
 * StockStrategy.java
 * @author Nghia Tran
 * @date Apr 4, 2016
 */
public interface Strategy {
	
	/**
	 * Analyzes the stock
	 * @param stock
	 * @return
	 */
	public String analyze(Stock stock);
}
