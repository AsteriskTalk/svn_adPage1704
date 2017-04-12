package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientProfile {
	protected long clientCode;
	protected String clientName;
	protected String clientEmail;
	protected String clientPhone;
	protected String clientLogoAddr;
	protected String clientNumber;
	protected String clientCtt;
	protected long clientPoint;
	
	public String getClientCtt() {
		return clientCtt;
	}
	public ClientProfile setClientCtt(String clientCtt) {
		this.clientCtt = clientCtt;
		return this;
	}
	public String getClientNumber() {
		return clientNumber;
	}
	public ClientProfile setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
		return this;
	}
	public String getClientEmail() {
		return clientEmail;
	}
	public ClientProfile setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
		return this;
	}
	public String getClientPhone() {
		return clientPhone;
	}
	public ClientProfile setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
		return this;
	}
	public long getClientCode() {
		return clientCode;
	}
	public ClientProfile setClientCode(long clientCode) {
		this.clientCode = clientCode;
		return this;
	}
	public String getClientName() {
		return clientName;
	}
	public ClientProfile setClientName(String clientName) {
		this.clientName = clientName;
		return this;
	}
	public String getClientLogoAddr() {
		return clientLogoAddr;
	}
	public ClientProfile setClientLogoAddr(String clientLogoAddr) {
		this.clientLogoAddr = clientLogoAddr;
		return this;
	}
	public long getClientPoint() {
		return clientPoint;
	}
	public ClientProfile setClientPoint(long clientPoint) {
		this.clientPoint = clientPoint;
		return this;
	}
	
	public ClientProfile setAll(ResultSet rs) throws SQLException {
		this.clientCode = rs.getLong("CLIENT_CODE");
		this.clientName = rs.getString("CLIENT_NAME");
		this.clientEmail = rs.getString("CLIENT_EMAIL");
		this.clientPhone = rs.getString("CLIENT_PHONE");
		this.clientCtt = rs.getString("CLIENT_CTT");
		this.clientLogoAddr = rs.getString("CLIENT_LOGO_ADDR");
		this.clientNumber = rs.getString("CLIENT_NUM")==null ? "NOPE" : rs.getString("CLIENT_NUM");
		return this;
	}
	
}
