package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.simple.JSONObject;

public class ADParser {
	//final String URL_PATH = "http://www.asterisktalk.com:8080/ASTK100/appLoadMain.astk?userCode=";
	final  String URL_BASE = "http://117.52.31.199:3030";
	final  String URL_GET_USERINFO = "/userInfoAD";
	final  String URL_POST_ADDPOINT = "/addPoint";

	public  JSONObject parsingUserInfo(long userCode) throws Exception {
		String paramTrgUserId = "trgUserId=" + userCode;
		final String URL_PATH = URL_BASE + URL_GET_USERINFO + "?" + paramTrgUserId;
		return parsingBase(URL_PATH);
	}
	
	public  JSONObject parsingUpdateUserPoint(long userCode, long chatGrpCode, long ADCode, long clientCode, long point) throws Exception {
		final int MB = 8 * 1024;
		final String URL_PATH= URL_BASE + URL_POST_ADDPOINT;
		final String ENC_TYPE = "UTF-8";
		
		String line = "";
		String result = "";
		
		StringBuilder sbParam = new StringBuilder();
		sbParam.append("userId="+URLEncoder.encode("" + userCode, ENC_TYPE));
		sbParam.append("&chatRoomId="+URLEncoder.encode("" + chatGrpCode, ENC_TYPE));
		sbParam.append("&ADCode="+URLEncoder.encode("" + ADCode, ENC_TYPE));
		sbParam.append("&clientCode="+URLEncoder.encode("" + clientCode, ENC_TYPE));
		sbParam.append("&point="+URLEncoder.encode("" + point, ENC_TYPE));
		
		URL url = new URL(URL_PATH);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		
		OutputStream os = conn.getOutputStream();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, ENC_TYPE));
		bw.write(sbParam.toString());
		bw.flush();
		bw.close();
		
		InputStream is = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is), MB);
		StringBuffer sb = new StringBuffer();
		
		while ((line = br.readLine()) != null) { sb.append(line +"\n"); }
		result = sb.toString().trim();

		is.close();
		br.close();
		os.close();
		System.out.println("log : parsing - "+ result);
		
		return ADTools.toJSONObject(result);
	}
	
	private  JSONObject parsingBase(String urlPath) throws Exception {
		String result = "";
		String line = "";
		URL url = new URL(urlPath);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestProperty("CONTENT-TYPE", "text/xml");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
		while ( (line = in.readLine()) != null ) { result += line.trim(); line = ""; }
		in.close();
		System.out.println("plag 1 : "+ result);

		return ADTools.toJSONObject(result);
	}
	
}
