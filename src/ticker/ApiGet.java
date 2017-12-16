package ticker;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class ApiGet {

	String ZEBPAY = "https://www.zebapi.com/api/v1/market/ticker/btc/inr";
	String CEXUSD = "https://cex.io/api/ticker/BTC/USD";
	String CEXEUR = "https://cex.io/api/ticker/BTC/EUR";
	String USD = "https://api.fixer.io/latest?base=USD";
	String EUR = "https://api.fixer.io/latest?base=EUR";

	String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";

	private String sendGet(String host) {

		try {

			URL obj = new URL(host);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
//			System.out.println("\nSending 'GET' request to URL : " + host);
//			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return response.toString();
		} catch (Exception e) {
			System.out.println(" ERROR get" + e.toString());
		}

		return "" ;
	}

	private Double jsonParse(String responseJson, String key1) {

		Object obj;
		try {
			obj = new JSONParser().parse(responseJson);
			// typecasting obj to JSONObject
			JSONObject jo = (JSONObject) obj;

			Double sell = Double.valueOf(jo.get(key1).toString());

			return sell;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(" ERROR json " + e.toString());
			e.printStackTrace();
		}

		return 0.0;

		/*
		 * // iterating address Map Iterator<Map.Entry> itr1 =
		 * address.entrySet().iterator(); while (itr1.hasNext()) { Map.Entry pair =
		 * itr1.next(); System.out.println(pair.getKey() + " : " + pair.getValue()); }
		 * 
		 * // getting phoneNumbers JSONArray ja = (JSONArray) jo.get("phoneNumbers");
		 * 
		 * // iterating phoneNumbers Iterator itr2 = ja.iterator();
		 * 
		 * while (itr2.hasNext()) { itr1 = ((Map) itr2.next()).entrySet().iterator();
		 * while (itr1.hasNext()) { Map.Entry pair = itr1.next();
		 * System.out.println(pair.getKey() + " : " + pair.getValue()); } }
		 * 
		 */
	}
	
	private Double jsonParseString(String responseJson, String key1) {

		Object obj;
		try {
			obj = new JSONParser().parse(responseJson);
			// typecasting obj to JSONObject
			JSONObject jo = (JSONObject) obj;
			
			JSONObject jo1 = (JSONObject)  jo.get(key1);

			Double str = (Double)jo1.get("INR");

			return str;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(" ERROR json " + e.toString());
			e.printStackTrace();
		}

		return 0.0;
	}

	public Double getZebpayPrice() {
		return round(jsonParse(sendGet(ZEBPAY) , "sell"),2);
	}

	public Double getCexPrice() {
		return round(jsonParse(sendGet(CEXUSD) , "last"),2);
	}
	
	public Double getUsdForex() {
		return round(jsonParseString(sendGet(USD) , "rates"),2);
	}
	

	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
}
