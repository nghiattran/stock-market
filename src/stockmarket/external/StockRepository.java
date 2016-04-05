package stockmarket.external;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * The stock repository (holding information about various companies as needed)
 * StockRepository.java
 * @author Nghia Tran
 * @author Clayton Williams
 * @date Apr 4, 2016
 */
public class StockRepository {
	
	/**
	 * The {@code StockRepository}
	 */
	private static StockRepository INSTANCE = new StockRepository();
	
	/**
	 * The current stock information we have in our repository
	 */
	private Map<String, Stock> stocks = new HashMap<String, Stock>();
	
	/**
	 * StockRepository
	 */
	private StockRepository() {
		/**
		 * Empty
		 */
	}
	
	/**
	 * Gets a stock from the repository from the given 4 letter symbol
	 * @param symbol - the stock symbol (must be 4 letters)
	 * @return {Stock} or null
	 */
	public Stock getStock(String symbol) {
		symbol = symbol.toUpperCase();		
		if (stocks.containsKey(symbol)) {
			return stocks.get(symbol);
		} else {
			JSONObject obj = YahooQuery.getStock(symbol.toLowerCase());
			if (obj != null) {
				Stock stock = new Stock(symbol, obj);
				stocks.put(symbol, stock);
				return stocks.get(symbol);
			} else {
				System.out.println("No data found!");
			}
		}
		return null;
	}
	
	/**
	 * Gets the stock repository instance
	 * @return
	 */
	public static StockRepository getInstance() {
		return INSTANCE;
	}

}
