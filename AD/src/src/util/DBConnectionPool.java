package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnectionPool {
	protected String url;
	protected String oid;
	protected String opw;
	
	ArrayList<Connection> connList = new ArrayList<Connection>();
	
	public DBConnectionPool(String driver, String url, String oid, String opw) throws ClassNotFoundException {
		this.url = url;
		this.oid = oid;
		this.opw = opw;		
		Class.forName(driver);
	}
	
	public String getID() {
		return oid;
	}
	
	public String getPW() {
		return opw;
	}
	
	public String getURL() {
		return url;
	}
	
	public String toString() {
		String result = "";
		result
			+= "URL : " + url;
		result
			+= "\nID : " + oid;
		
		return result;
	}
	
	public Connection getConn() throws Exception {
		if (connList.size() > 15) {
			for (int i=10 ; i<connList.size() ; i++) {
				Connection conn = connList.get(i);
				try { if (conn != null) conn.close(); } catch (Exception ex) { }				
			}
		}
		if (connList.size() > 0	) {
			Connection conn = connList.get(0);
			return conn;
		}
		return DriverManager.getConnection(url, oid, opw);
	}
	
	public void returnConn(Connection conn) throws Exception {
		connList.add(conn);
	}
	
	public void closeAll() {
		for (Connection conn : connList) {
			try { conn.close(); } catch (Exception ex) { }
		}
	}

}
