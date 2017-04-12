package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpenedChatGroup {
	protected long chatGrpCode;
	protected long userCode;
	protected long ADSendTime;
	
	public long getChatGrpCode() {
		return chatGrpCode;
	}
	public OpenedChatGroup setChatGrpCode(long chatGrpCode) {
		this.chatGrpCode = chatGrpCode;
		return this;
	}
	public long getUserCode() {
		return userCode;
	}
	public OpenedChatGroup setUserCode(long userCode) {
		this.userCode = userCode;
		return this;
	}
	public long getADSendTime() {
		return ADSendTime;
	}
	public OpenedChatGroup setADSendTime(long aDSendTime) {
		ADSendTime = aDSendTime;
		return this;
	}
	
	public OpenedChatGroup setAll(ResultSet rs) throws SQLException {
		this.chatGrpCode = rs.getLong("CHAT_GRP_CODE");
		this.userCode = rs.getLong("USER_CODE");
		this.ADSendTime = rs.getLong("AD_SEND_TIME");
		return this;
		
	}
	
}
