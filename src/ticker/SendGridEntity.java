package ticker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public class SendGridEntity {

	private String SUBJECT = "Arbitrax Cex Zebpay";
	private String BODY = "";
	private String FROM = "profitmaker@cextozebpay.com";
	
	private String[] toEmails = {"sanketsahu9394@gmail.com",
			"ashish.chelikani@capillarytech.com",
			"arjit.malviya@capillarytech.com",
			"amol.patil@capillarytech.com"};
	
	
	public SendGridEntity(Double profit, Double breakEven, Double zp, Double cp, Double usdToInr) {
		
		this.BODY = "Hi! "
				+"<br> <b>Possible Profit on 1000$: INR "+round(profit,2)
				+ "</b> <br><br> Cex Price : INR "+round((cp*usdToInr),2)
				+"<br> Zebpay Price : INR "+round(zp,2)
				+"<br><br> Maximum Zebpay Price Drop for no loss : INR "+round((zp-breakEven),2)
				+"<br> Zebpay BreakEven Value : INR "+round(breakEven,2); 

		this.SUBJECT = "Arbitrax Profit : "+round(profit,2);
		
		if(profit > 10000) {
			this.FROM = "1000plusprofit@ticker.com";
		}

		if(profit < 4500) {
			this.BODY = this.BODY + " <br> <b> NOTE: </b> You will be notified next only when profit crosses 4500+";
		}
		
		System.out.println(this.BODY);
	}

	public String makeJson() {
		// creating JSONObject
		JSONObject jo = new JSONObject();

		// personalizations

		// adding EMAILS
		JSONArray ja2 = new JSONArray();
		
		for(int i = 0 ; i < toEmails.length ; i++) {
			Map mEmail = new LinkedHashMap(1);
			mEmail.put("email",toEmails[i]);
			ja2.add(mEmail);
		}
		
		//set email over
		
		JSONArray ja1 = new JSONArray();
		Map m1 = new LinkedHashMap(1);
		m1.put("to", ja2);
		
		
		// adding map to list
		ja1.add(m1);
		// putting phoneNumbers to JSONObject
		jo.put("personalizations", ja1);

		// SUBJECT
		jo.put("subject", SUBJECT);

		// FROM
		JSONObject fromJo = new JSONObject();
		fromJo.put("email", FROM);
		jo.put("from", fromJo);

		// CONTENT
		JSONArray ja = new JSONArray();
		Map m = new LinkedHashMap(2);
		m.put("type", "text/html");
		m.put("value", BODY);
		// adding map to list
		ja.add(m);
		// putting phoneNumbers to JSONObject
		jo.put("content", ja);

		System.out.println(jo);
		return jo.toJSONString();
	}
	

	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	

}
