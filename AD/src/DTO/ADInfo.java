package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ADInfo {
	protected long ADCode;
	protected long clientCode;
	protected long ADRemainPoint;
	protected long ADRemainCount;
	protected long ADBonus;
	protected long ADViewPoint;
	protected String ADCtt;
	protected String ADURL;
	protected String ADImgAddr;
	protected boolean isConn;
	protected boolean isADing;
	protected ArrayList<ADTarget> ADTargetList;
	
		
	public boolean isConn() {
		return isConn;
	}
	public ADInfo setConn(boolean isConn) {
		this.isConn = isConn;
		return this;
	}
	public boolean isADing() {
		return isADing;
	}
	public ADInfo setADing(boolean isADing) {
		this.isADing = isADing;
		return this;
	}
	public ArrayList<ADTarget> getADTargetList() {
		return ADTargetList;
	}
	public ADInfo setADTargetList(ArrayList<ADTarget> ADTargetList) {
		this.ADTargetList = ADTargetList;
		return this;
	}
	public long getADViewPoint() {
		return ADViewPoint;
	}
	public ADInfo setADViewPoint(long ADViewPoint) {
		this.ADViewPoint= ADViewPoint;
		return this;
	}	
	public long getADCode() {
		return ADCode;
	}
	public ADInfo setADCode(long ADCode) {
		this.ADCode = ADCode;
		return this;
	}
	public long getClientCode() {
		return clientCode;
	}
	public ADInfo setClientCode(long clientCode) {
		this.clientCode = clientCode;
		return this;
	}
	public long getADRemainPoint() {
		return ADRemainPoint;
	}
	public ADInfo setADRemainPoint(long ADRemainPoint) {
		this.ADRemainPoint = ADRemainPoint;
		return this;
	}
	public long getADRemainCount() {
		return ADRemainCount;
	}
	public ADInfo setADRemainCount(long ADRemainCount) {
		this.ADRemainCount = ADRemainCount;
		return this;
	}
	public long getADBonus() {
		return ADBonus;
	}
	public ADInfo setADBonus(long ADBonus) {
		this.ADBonus = ADBonus;
		return this;
	}
	public String getADCtt() {
		return ADCtt;
	}
	public ADInfo setADCtt(String ADCtt) {
		this.ADCtt = ADCtt;
		return this;
	}
	public String getADURL() {
		return ADURL;
	}
	public ADInfo setADURL(String ADURL) {
		this.ADURL = ADURL;
		return this;
	}
	public String getADImgAddr() {
		return ADImgAddr;
	}
	public ADInfo setADImgAddr(String ADImgAddr) {
		this.ADImgAddr = ADImgAddr;
		return this;
	}
	
	public ADInfo setAll(ResultSet rs) throws SQLException {
		this.ADCode = rs.getLong("AD_CODE");
		this.clientCode = rs.getLong("CLIENT_CODE");
		this.ADCtt = rs.getString("AD_CTT");
		this.ADURL = rs.getString("AD_URL");
		this.ADImgAddr = rs.getString("AD_IMG_ADDR");
		this.ADBonus = rs.getLong("AD_BONUS");
		this.ADRemainPoint = rs.getLong("AD_REMAIN_POINT");
		this.ADRemainCount = rs.getLong("AD_REMAIN_COUNT");
		this.ADViewPoint = rs.getLong("AD_VIEW");
		this.isADing = rs.getString("IS_ADING").equals("T") ? true : false;
		this.isConn = rs.getString("IS_CONN").equals("T") ? true : false;
		return this;
	}
	
	public ADInfo setExam() {
		this.ADCode = 0;
		this.clientCode = 0;
		this.ADCtt = "샘플";
		this.ADURL = "sample";
		this.ADImgAddr = "sample";
		this.ADBonus = 0;
		this.ADRemainPoint = 0;
		this.ADRemainCount = 0;
		this.ADViewPoint  = 0;
		this.isADing = false;
		this.isConn = false;
		return this;
	}
	
}
