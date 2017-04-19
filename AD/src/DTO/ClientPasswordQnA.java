package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientPasswordQnA {
	protected long clientCode;
	protected long question;
	protected String answer;
	
	public long getClientCode() {
		return clientCode;
	}
	public ClientPasswordQnA setClientCode(long clientCode) {
		this.clientCode = clientCode;
		return this;
	}
	public long getQuestion() {
		return question;
	}
	public ClientPasswordQnA setQuestion(long question) {
		this.question = question;
		return this;
	}
	public String getAnswer() {
		return answer;
	}
	public ClientPasswordQnA setAnswer(String answer) {
		this.answer = answer;
		return this;
	}
	
	public ClientPasswordQnA setAll(ResultSet rs) throws SQLException {
		this.clientCode = rs.getLong("CLIENT_CODE");
		this.question = rs.getLong("QUESTION");
		this.answer = rs.getString("ANSWER");
		return this;
	}
	
	

}
