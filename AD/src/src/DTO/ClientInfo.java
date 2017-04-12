package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientInfo {
	protected long clientCode;
	protected String clientID;
	protected boolean isConnected;
	
	public long getClientCode() {
		return clientCode;
	}
	public ClientInfo setClientCode(long clientCode) {
		this.clientCode = clientCode;
		return this;
	}
	public String getClientID() {
		return clientID;
	}
	public ClientInfo setClientID(String clientID) {
		this.clientID = clientID;
		return this;
	}
	public boolean isConnected() {
		return isConnected;
	}
	public ClientInfo setConnected(boolean isConnected) {
		this.isConnected = isConnected;
		return this;
	}
	
	public ClientInfo setAll(ResultSet rs) throws SQLException	{
		this.clientCode = rs.getLong("CLIENT_CODE");
		this.clientID = rs.getString("CLIENT_ID");
		this.isConnected = rs.getString("IS_CONN").equals("T") ? true : false;
		return this;
	}
	
}
