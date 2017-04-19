package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordQuestion {
	protected long qNo;
	protected String question;
	
	public long getqNo() {
		return qNo;
	}
	public PasswordQuestion setqNo(long qNo) {
		this.qNo = qNo;
		return this;
	}
	public String getQuestion() {
		return question;
	}
	public PasswordQuestion setQuestion(String question) {
		this.question = question;
		return this;
	}
	
	public PasswordQuestion setAll(ResultSet rs) throws SQLException {
		this.qNo = rs.getLong("Q_NO");
		this.question = rs.getString("QUESTION");
		return this;
	}
	
	

}
