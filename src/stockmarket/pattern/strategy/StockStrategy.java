package stockmarket.pattern.strategy;

import stockmarket.external.Stock;

/**
 * StockStrategy.java
 * @author Nghia Tran
 * @author Clayton Williams
 * @date Apr 4, 2016
 */
public class StockStrategy implements Strategy {
	
	/**
	 * The stock strate
	 */
	private Strategy strategy;
	
	/**
	 * StockStrategy
	 */
	private StockStrategy() {
		/**
		 * Empty
		 */
	}
	
	/**
	 * Stock strategy
	 * @param object
	 */
	public StockStrategy(Object object) {
		this.strategy = (Strategy) object;
	}
	
	/**
	 * Setting different strategy
	 * @param strat
	 */
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public String analyze(Stock stock) {
		return strategy.getClass().getSimpleName() + ": You should " + strategy.analyze(stock) + " " + stock.getSymbol() + ".";
	}

}
