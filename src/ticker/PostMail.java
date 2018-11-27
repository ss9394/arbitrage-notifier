package ticker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostMail {

	String URL1 = "https://api.sendgrid.com/v3/mail/send";
	String SENDGRID_API_KEY = "---";
	

	public void sendGet(String profit, SendGridEntity gridBody) {

		try {

			URL url = new URL(URL1);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer "+SENDGRID_API_KEY);
			conn.setRequestProperty("Content-Type", "application/json");

			String input = gridBody.makeJson();

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (Exception e) {
			System.out.println(" ERROR get" + e.toString());
		}


	}
}
