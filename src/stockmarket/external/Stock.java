package stockmarket.external;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents an external stock
 * Stock.java
 * @author Clayton Williams
 * @date Apr 4, 2016
 */
public class Stock {
	
	/**
	 * The stock data
	 */
	private JSONObject data;
	
	/**
	 * The stock symbol
	 */
	private final String symbol;
	
	/**
	 * The company's fully qualified name
	 */
	private String companyName;
	
	/**
	 * Various price-related data of the stock
	 */
	private double highPrice, lowPrice, openPrice, closePrice, currentPrice;
	
	/**
	 * The current days trade volume
	 */
	private int volume;
	
	/**
	 * The last trade date
	 */
	private String lastTradeDate;
	
	/**
	 * Stock
	 * @param symbol - the stock symbol (4 letters)
	 */
	public Stock(String symbol, JSONObject data) {
		this.symbol = symbol.toUpperCase();
		this.data = data;
		parseStock();
	}
	
	/**
	 * Parses the stock json data
	 */
	private void parseStock() {
		try {
			this.companyName = data.getString("Name");
			this.currentPrice = data.getDouble("LastTradePriceOnly");
			this.volume = data.getInt("Volume");
			if (!data.isNull("DaysLow"))
				this.lowPrice = data.getDouble("DaysLow");		
			if (!data.isNull("DaysHigh"))
				this.highPrice = data.getDouble("DaysHigh");
			this.closePrice = data.getDouble("PreviousClose");
			this.openPrice = data.getDouble("Open");
			this.lastTradeDate = data.getString("LastTradeDate");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the last trade date
	 */
	public String getLastTradeDate() {
		return lastTradeDate;
	}

	/**
	 * @return the highPrice
	 */
	public double getHighPrice() {
		return highPrice;
	}

	/**
	 * @return the lowPrice
	 */
	public double getLowPrice() {
		return lowPrice;
	}

	/**
	 * @return the openPrice
	 */
	public double getOpenPrice() {
		return openPrice;
	}

	/**
	 * @return the closePrice
	 */
	public double getClosePrice() {
		return closePrice;
	}

	/**
	 * @return the currentPrice
	 */
	public double getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * @return the Volume
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	
	

}
