package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;

public class ADTarget {
	protected long ADCode;
	protected long clientCode;
	protected String targetType;
	protected String targetValue;
	
	public long getADCode() {
		return ADCode;
	}
	public ADTarget setADCode(long ADCode) {
		this.ADCode = ADCode;
		return this;
	}
	public long getClientCode() {
		return clientCode;
	}
	public ADTarget setClientCode(long clientCode) {
		this.clientCode = clientCode;
		return this;
	}
	public String getTargetType() {
		return targetType;
	}
	public ADTarget setTargetType(String targetType) {
		this.targetType = targetType;
		return this;
	}
	public String getTargetValue() {
		return targetValue;
	}
	public ADTarget setTargetValue(String targetValue) {
		this.targetValue = targetValue;
		return this;
	}
	public ADTarget setAll(ResultSet rs) throws SQLException {
		this.ADCode = rs.getLong("AD_CODE");
		this.clientCode = rs.getLong("CLIENT_CODE");
		this.targetType = rs.getString("TARGET_TYPE");
		this.targetValue = rs.getString("TARGET_VALUE");
		return this;
	}
	

}
