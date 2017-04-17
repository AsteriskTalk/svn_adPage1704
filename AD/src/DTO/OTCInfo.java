package DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OTCInfo {
	protected String OTC;
	protected String OTCQuery;
	protected long OTCDate;
	protected long PKCode;
	protected boolean isUsed;
	
	public String getOTC() {
		return OTC;
	}
	public OTCInfo setOTC(String oTC) {
		OTC = oTC;
		return this;
	}
	public String getOTCQuery() {
		return OTCQuery;
	}
	public OTCInfo setOTCQuery(String oTCQuery) {
		OTCQuery = oTCQuery;
		return this;
	}
	public long getOTCDate() {
		return OTCDate;
	}
	public OTCInfo setOTCDate(long oTCDate) {
		OTCDate = oTCDate;
		return this;
	}
	public long getPKCode() {
		return PKCode;
	}
	public OTCInfo setPKCode(long pKCode) {
		PKCode = pKCode;
		return this;
	}
	public boolean isUsed() {
		return isUsed;
	}
	public OTCInfo setUsed(boolean isUsed) {
		this.isUsed = isUsed;
		return this;
	}
	public OTCInfo setAll(ResultSet rs) throws SQLException {
		this.OTC  = rs.getString("OTC");
		this.OTCQuery = rs.getString("OTC_QUERY");
		this.OTCDate = rs.getLong("OTC_DATE");
		this.PKCode = rs.getLong("PK_CODE");
		this.isUsed = rs.getString("IS_USED").equals("T") ? true : false; 
		return this;
	}
	
}
