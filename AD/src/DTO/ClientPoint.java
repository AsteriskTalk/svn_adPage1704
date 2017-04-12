package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientPoint {
	protected long clientCode;
	protected long sumPurPrice;
	protected long sumPurPoint;
	protected long sumPBPrice;
	protected long sumPBPoint;
	protected long nowPoint;
	
	
	public long getClientCode() {
		return clientCode;
	}
	public ClientPoint setClientCode(long clientCode) {
		this.clientCode = clientCode;
		return this;
	}
	public long getSumPurPrice() {
		return sumPurPrice;
	}
	public ClientPoint setSumPurPrice(long sumPurPrice) {
		this.sumPurPrice = sumPurPrice;
		return this;
	}
	public long getSumPurPoint() {
		return sumPurPoint;
	}
	public ClientPoint setSumPurPoint(long sumPurPoint) {
		this.sumPurPoint = sumPurPoint;
		return this;
	}
	public long getSumPBPrice() {
		return sumPBPrice;
	}
	public ClientPoint setSumPBPrice(long sumPBPrice) {
		this.sumPBPrice = sumPBPrice;
		return this;
	}
	public long getSumPBPoint() {
		return sumPBPoint;
	}
	public ClientPoint setSumPBPoint(long sumPBPoint) {
		this.sumPBPoint = sumPBPoint;
		return this;
	}
	public long getNowPoint() {
		return nowPoint;
	}
	public ClientPoint setNowPoint(long nowPoint) {
		this.nowPoint = nowPoint;
		return this;
	}
	
	public ClientPoint setAll(ResultSet rs) throws SQLException {
		this.clientCode = rs.getLong("CLIENT_CODE");
		this.sumPurPrice = rs.getLong("SUM_PUR_PRICE");
		this.sumPurPoint = rs.getLong("SUM_PUR_POINT");
		this.sumPBPrice = rs.getLong("SUM_PB_PRICE");
		this.sumPBPoint = rs.getLong("SUM_PB_POINT");
		this.nowPoint = rs.getLong("NOW_POINT");
		return this;
	}
	

}
