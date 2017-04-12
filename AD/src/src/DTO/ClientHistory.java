package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientHistory {
	protected long clientCode;
	protected long ADCode;
	protected String histType;
	protected long histPoint;
	protected long histDate;
	public long getClientCode() {
		return clientCode;
	}
	public ClientHistory setClientCode(long clientCode) {
		this.clientCode = clientCode;
		return this;
	}
	public long getADCode() {
		return ADCode;
	}
	public ClientHistory setADCode(long aDCode) {
		ADCode = aDCode;
		return this;
	}
	public String getHistType() {
		return histType;
	}
	public ClientHistory setHistType(String histType) {
		this.histType = histType;
		return this;
	}
	public long getHistPoint() {
		return histPoint;
	}
	public ClientHistory setHistPoint(long histPoint) {
		this.histPoint = histPoint;
		return this;
	}
	public long getHistDate() {
		return histDate;
	}
	public ClientHistory setHistDate(long histDate) {
		this.histDate = histDate;
		return this;
	}
	
	public ClientHistory setAll(ResultSet rs) throws SQLException {
		this.clientCode = rs.getLong("CLIENT_CODE");
		this.ADCode = rs.getLong("AD_CODE");
		this.histType = rs.getString("HIST_TYPE");
		this.histDate = rs.getLong("HIST_DATE");
		this.histPoint = rs.getLong("HIST_POINT");
		return this;
		
	}
	

}
