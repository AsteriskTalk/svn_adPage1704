package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;

import DTO.ADInfo;

public class FCMPushManager extends Thread {
	StringBuilder output = new StringBuilder();
	protected final String API_URL;
	protected final String AUTH_KEY;
	protected final String SPEED;
	protected final int TIME_TO_LIVE;
	JSONObject json = new JSONObject();
	JSONObject data = new JSONObject();
	
	public FCMPushManager() {
		this.AUTH_KEY = "AAAA23H8G_I:APA91bH5eSEEoqJatnWxTW7chWi3SfD179E_R0ALz4IbRTawJtBiQiSEH-3QYTsNPn-6HKoV8_F195xbWAkCRSPdjHKcc7b2-w301bN3niIdPTX3tCuDABWcgO1PfzweSUyeG2L6rlG_"; 
		this.API_URL = "https://fcm.googleapis.com/fcm/send";
		this.SPEED = "high"; /** high OR normal */
		this.TIME_TO_LIVE = 60 * 60 * 24 * 3; /** SEND LIMIT TIME(sec) */
		
	}
	
	public FCMPushManager(DBConnectionPool connPool) {
		this.AUTH_KEY = "AAAA23H8G_I:APA91bH5eSEEoqJatnWxTW7chWi3SfD179E_R0ALz4IbRTawJtBiQiSEH-3QYTsNPn-6HKoV8_F195xbWAkCRSPdjHKcc7b2-w301bN3niIdPTX3tCuDABWcgO1PfzweSUyeG2L6rlG_"; 
		this.API_URL = "https://fcm.googleapis.com/fcm/send";
		this.SPEED = "high"; /** high OR normal */
		this.TIME_TO_LIVE = 60 * 60 * 24 * 3; /** SEND LIMIT TIME(sec) */
	}

	public void run() {
		HttpURLConnection conn = null;
		
		try {
			URL url = new URL(this.API_URL);
			conn = (HttpURLConnection) url.openConnection();		
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + this.AUTH_KEY);
			conn.setRequestProperty("Content-Type", "application/json");
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			System.out.println(json.toJSONString());
			wr.write(json.toString());
			wr.flush();
			
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("FAILED : HTTP error Code : " + conn.getResponseCode());
			}			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String output;
			System.out.println("Output from server...\n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				if (output.substring(0, 5).equals("error")) {
					System.out.println("Error");
				}
			}
			
		} catch (Exception ex) {
			System.out.println("log : try-catch..FCMPush.push\n" + ex);
			
		} finally {
			this.resetStatus();
			conn.disconnect();
			this.interrupt();
		}
	}

	public void pushInit() {
		data.clear();
		json.clear();
	}
	
	public void noticePush(String noticeCtt, String noticeSbj, String noticeImg) {
		
	}
	
	public void noticePush(String noticeCtt, String noticeSbj) {
		
	}
	
	/* Destroy ChatGroup */
	public void ADPush(long targetUserCode, long chatGrpCode, ADInfo AD, long sendedCode, boolean chargeable) {
		this.pushInit();
		
		String message = "ADTest";
		
		
		String ADCtt = AD.getADCtt();
		String ADImgAddr = AD.getADImgAddr();
		String ADURL = AD.getADURL();
		long ADCode = AD.getADCode();
		long clientCode = AD.getClientCode();
		long ADBonus = chargeable ? AD.getADBonus() : 0; //충전가능하면 ADBonus로, 아니라면 0으로 보낸다.
		
		data.put("userCode", targetUserCode); // user match check
		data.put("chatGrpCode", chatGrpCode);
		data.put("message", message); //Message Title
		data.put("notiType", "AD");
		
		data.put("ADSbj", "광고 타이틀 샘플");
		data.put("sendedCode", sendedCode);
		data.put("ADCode", ADCode);
		data.put("clientCode", clientCode);
		data.put("ADBonus", ADBonus);
		data.put("ADCtt", ADCtt);
//		data.put("ADImgAddr", "http://news.naver.com/main/read.nhn?mode=LSD&mid=sec&sid1=004&oid=417&aid=0000231614");
		data.put("ADImgAddr", ADImgAddr);
		data.put("ADURL", ADURL);
		
		
		json.put("to", "/topics/" + targetUserCode); //targetUser;
		json.put("data", data);

		this.start();
	}
	
	private void resetStatus() {
		data.clear();
		json.clear();
				
		json.put("priority", SPEED); // message's speed
		json.put("time_to_live", TIME_TO_LIVE); // message push limit time(second)
		
	}
	
}
