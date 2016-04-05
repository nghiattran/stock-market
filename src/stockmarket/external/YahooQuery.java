package stockmarket.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Get stock information
 * @author nghia
 *
 */
public class YahooQuery {
	
	public static JSONObject getStock(String symbol) {
    	String end = "%22)%0A%09%09&format=json&diagnostics=true&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=";
		String begin = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22";
    	String url = begin + symbol + end;
    	JSONObject json = null;
		try {
			json = readJsonFromUrl(url);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}     
		return json.getJSONObject("query").getJSONObject("results").getJSONObject("quote");
    }
	
	/**
	 * Get all stock information of symbols in `quotes` array
	 * Example url: https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22YHOO%22%2C%22ALL%22)%0A%09%09&format=json&diagnostics=true&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=
	 */
	public static JSONArray getQuotes(ArrayList<String> quotes) {
	
    	String quoteQuery = "";
    	for(int i = 0; i < quotes.size(); i++) {
    		if (i >= 1) {
    			quoteQuery += "%2C";
    		}
    		quoteQuery += "\"" + quotes.get(i).replace("\"", "") + "\"";
    	}   	
    	String end = ")%0A%09%09&format=json&diagnostics=true&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=";
		String begin = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(";
    	String url = begin + quoteQuery + end;
    	System.out.println(url);
//    	String url = "http://finance.yahoo.com/webservice/v1/symbols/" 
//    			+ quoteQuery + "/quote?format=json&view=detail";
//    	url = url.replace("\"", "");
//    	System.out.println(url);
    	JSONObject json = null;
		try {
			json = readJsonFromUrl(url);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}     
		return json.getJSONObject("query").getJSONObject("results").getJSONArray("quote");
    }
	
	/**
	 * Get all stock symbol from companylist.csv
	 */
	public static ArrayList<HashMap<String, String>> getAllSymbols() {
		Scanner scanner = null;
		ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String, String>>();
		try {
			scanner = new Scanner(new File("companylist.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        scanner.useDelimiter("\n");
        while(scanner.hasNext()){
        	HashMap<String, String> sub = new HashMap<String, String>();
        	String line = scanner.next();
        	String[] fields = line.split(",");
        	for(int i = 0; i < fields.length; i++) {
        		switch(i) {
        		case 0:
        			sub.put("symbol", fields[i]);
        			break;
        		case 1:
        			sub.put("name", fields[i]);
        			break;
        		case 2:
        			sub.put("price", fields[i]);
        			break;
        		case 3:
        			sub.put("capital", fields[i]);
        			break;
        		case 4:
        			sub.put("OPIYear", fields[i]);
        			break;
        		case 6:
        			sub.put("sector", fields[i]);
        			break;
        		case 7:
        			sub.put("industry", fields[i]);
        			break;
        		case 8:
        			sub.put("summary", fields[i]);
        			break;
        		}
        	}
        	
        	al.add(sub);
        }
        scanner.close();
        
        return al;
	}

	public static JSONObject getResourceArray(JSONArray object, int index) {
		return object.getJSONObject(index);
	}
	
	
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    
    
}