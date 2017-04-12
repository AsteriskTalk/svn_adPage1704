package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ADSended {
	protected long ADCode;
	protected long clinetCode;
	protected long userCode;
	protected long chatGrpCode;
	protected long ADSendedTime;
	protected boolean wasView;
	protected boolean wasTouch; 
	//본 테이블의 역할이 전송된 AD가 제대로 take 되었는지만을 확인하도록 변화하였기에 Touch는 기록하지 아니함.
	
	
	public long getADCode() {
		return ADCode;
	}
	public ADSended setADCode(long ADCode) {
		this.ADCode = ADCode;
		return this;
	}
	public long getClinetCode() {
		return clinetCode;
	}
	public ADSended setClinetCode(long clinetCode) {
		this.clinetCode = clinetCode;
		return this;
	}
	public long getUserCode() {
		return userCode;
	}
	public ADSended setUserCode(long userCode) {
		this.userCode = userCode;
		return this;
	}
	public long getChatGrpCode() {
		return chatGrpCode;
	}
	public ADSended setChatGrpCode(long chatGrpCode) {
		this.chatGrpCode = chatGrpCode;
		return this;
	}
	public boolean itWasView() {
		return wasView;
	}
	public ADSended setWasView(boolean wasView) {
		this.wasView = wasView;
		return this;
	}
	public long getADSendedTime() {
		return ADSendedTime;
	}
	public ADSended setADSendedTime(long ADSendedTime) {
		this.ADSendedTime = ADSendedTime;
		return this;
	}
	
	public boolean itWasTouch() {
		return wasTouch;
	}
	public ADSended setWasTouch(boolean wasTouch) {
		this.wasTouch = wasTouch;
		return this;
	}
	
	public ADSended setAll(ResultSet rs) throws SQLException {
		this.ADCode = rs.getLong("AD_CODE");
		this.clinetCode = rs.getLong("CLIENT_CODE");
		this.userCode = rs.getLong("USER_CODE");
		this.chatGrpCode = rs.getLong("CHAT_GRP_CODE");
		this.ADSendedTime = rs.getLong("AD_SEND_TIME");
		this.wasView = rs.getString("WAS_VIEW").equals("T") ? true : false; 
		this.wasTouch = (rs.getString("WAS_TOUCH").equals("T") || rs.getString("WAS_TOUCH").equals("N") ) ? true : false;
		return this;
	}
	
	

}
