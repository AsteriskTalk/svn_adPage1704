package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import util.TimeManager;

public class ADHistory {
	protected long ADCode;
	protected long clientCode;
	protected long histDate;
	protected long userCode;
	protected String histType;
	protected long histPoint;
//	protected boolean wasView;
//	protected boolean wasTouch;
	
	public long getADCode() {
		return ADCode;
	}
	public ADHistory setADCode(long ADCode) {
		this.ADCode = ADCode;
		return this;
	}
	public long getClientCode() {
		return clientCode;
	}
	public ADHistory setClientCode(long clientCode) {
		this.clientCode = clientCode;
		return this;
	}
	public long getHistDate() {
		return histDate;
	}
	public ADHistory setHistDate(long histDate) {
		this.histDate = histDate;
		return this;
	}
	public long getUserCode() {
		return userCode;
	}
	public ADHistory setUserCode(long userCode) {
		this.userCode = userCode;
		return this;
	}
	public String getHistType() {
		return histType;
	}
	public ADHistory setHistType(String histType) {
		this.histType = histType;
		return this;
	}
	public long getHistPoint() {
		return histPoint;
	}
	public ADHistory setHistPoint(long histPoint) {
		this.histPoint = histPoint;
		return this;
	}
//	public boolean wasView() {
//		return wasView;
//	}
//	public ADHistory setWasView(String wasView) {
//		this.wasView = wasView.equals("T") ? true : false;
//		return this;
//	}
//	public boolean wasTouch() {
//		return wasTouch;
//	}
//	public ADHistory setWasTouch(String wasTouch) {
//		this.wasTouch = wasTouch.equals("T") ? true : false;
//		return this;
//	}
	
	public ADHistory setAll(ResultSet rs) throws SQLException {
		this.ADCode = rs.getLong("AD_CODE");
		this.clientCode = rs.getLong("CLIENT_CODE");
		this.userCode = rs.getLong("USER_CODE");
		this.histType = rs.getString("HIST_TYPE");
		this.histPoint = rs.getLong("HIST_POINT");
		this.histDate = rs.getLong("HIST_DATE");
//		this.wasTouch = rs.getString("WAS_TOUCH").equals("T") ? true : false;
//		this.wasView = rs.getString("WAS_VIEW").equals("T") ? true : false;
		return this;
	}
	
	public String toString() {
		String str = "ADCode - "+ ADCode +" | CCode - "+ clientCode +" | UCode - "+ userCode 
				+" | Type - "+ histType +" | Point - "+ histPoint +" | Date - "+ TimeManager.getTime_toLocalString(histDate);
		return str;
	}
}
