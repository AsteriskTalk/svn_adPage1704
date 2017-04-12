package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import DTO.ADInfo;
import DTO.ClientInfo;
import DTO.ClientPoint;
import DTO.ClientProfile;
import util.ASTKLogManager;
import util.DBConnectionPool;
import util.EmailManager;

public class ClientManager {
	DBConnectionPool connPool;
	
	public ClientManager(DBConnectionPool connPool) {
		this.connPool = connPool;
	}
	
	public HashMap<String, Object> getClientProfile_Point(long clientCode){
		HashMap<String, Object> resultSet;
		
		resultSet = this.selectClientInfo_some(clientCode);
		resultSet = this.addADInfoListToResultSet(resultSet, clientCode);
		resultSet = this.addClientProfileToResultSet(resultSet, clientCode);
		
		return resultSet;
		
	}
	
	public boolean removeSignUp(long clientCode) {
		HashMap<String, Object> tmp = new HashMap<String ,Object>();
		DBManager dm = new DBManager(connPool);
		String sql_table = "";
		String sql_where = "";
		
		sql_where = " WHERE CLIENT_CODE="+ clientCode;
		
		/* Step1. Remove point */
		sql_table = " ASTK_CLIENT_POINT ";
		tmp = this.selectClientPoint_some(clientCode);
		if (tmp.get("result").equals("T")) { if (!dm.deleteSQL(sql_table, sql_where, 1)) { return false; } }
		
		/* Step2. Remove profile */
		sql_table = " ASTK_CLIENT_PROFILE ";
		tmp = this.selectClientProfile_some(clientCode);
		if ( tmp.get("result").equals("T")) { if (!dm.deleteSQL(sql_table, sql_where, 1)) { return false; } }
		
		/* Step3. Remove Info */
		sql_table = " ASTK_CLIENT_INFO ";
		tmp = this.selectClientInfo_some(clientCode);
		if ( tmp.get("result").equals("T")) { if (!dm.deleteSQL(sql_table, sql_where, 1)) { return false; } }
		
		return true;
	}
	
	public boolean checkClientID (String clientID) {
		String sql_where
			 = " WHERE CLIENT_ID='" + clientID +"'";
		HashMap<String, Object> tmp = this.base_selectClientInfo(sql_where, 1);
		if (!tmp.get("result").equals("T")) { return false; }
		return true;
	}
	
	public boolean checkClientPassword (long clientCode, String clientPW) {
		String sql_where
			= " WHERE CLIENT_CODE="+ clientCode + " AND CLIENT_PW='"+ clientPW +"' ";
		HashMap<String, Object> tmp = this.base_selectClientInfo(sql_where, 1);
		if (!tmp.get("result").equals("T")) { return false; }
		return true;
	}
	
	public boolean changeClientPassword (long clientCode, String newPW) {
		String sql_table = " ASTK_CLIENT_INFO ";
		String sql_set = " SET CLIENT_PW='"+ newPW +"' ";
		String sql_where = " WHERE CLIENT_CODE="+ clientCode;
		return new DBManager(connPool).updateSQL(sql_table, sql_set, sql_where, 1);
	}
	
	{ }
	
	public boolean updateClientProfile_logo(long clientCode, String clientlogoAddr) {
		String sql_table
			= " ASTK_CLIENT_PROFILE ";
		String sql_set
			= " SET CLIENT_LOGO_ADDR='"+ clientlogoAddr +"' " ;
		String sql_where 
			= " WHERE CLIENT_CODE="+ clientCode;
		
		return new DBManager(connPool).updateSQL(sql_table, sql_set, sql_where, 1);
	}
	
	public boolean exitAccount(long clientCode, String clientID, String clientPW) {
		/** 회원 탈퇴이긴 하지만 실제로는 Connect를 종료시키는 것이다.
		 * 왜냐하면 AD는 돈이 오고가는 곳이기 때문에 정보를 삭제한다는 것은 우리 측에 해가 될 수 있기 때문이다.
		 * 최대한 많은 정보를 보유하고 있다가, 우리 측으로 들어오는 일부 컴플레인을 방어하는 용도로써 사용하기 위해
		 * 당 정보들을 남겨두었다가, 이용약관에 의거하여 광고주가 책임을 올바르게 지도록 하는 것이 그 목적이다. */
		Connection conn = null;
		Statement st = null;
		ResultSet rs2 = null;
		int rs = 0;
		
		int ADCount = 0;
		String sql_disconnClient = "";
		String sql_getClientADCount = "";
		String sql_stopClientAD = "";

		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_disconnClient
				= " UPDATE ASTK_CLIENT_Profile ";
			sql_disconnClient
				+= " SET IS_CONN='F' ";
			sql_disconnClient
				+= " WHERE CLIENT_ID='"+ clientID +"' AND CLIENT_PW='"+ clientPW +"' AND CLIENT_CODE="+ clientCode;
			rs = st.executeUpdate(sql_disconnClient);
			if (rs != 1) { conn.rollback(); return false; }
			
			sql_getClientADCount
				= " SELECT COUNT(*) AS CNT FROM ASTK_AD_Profile ";
			sql_getClientADCount
				+= " WHERE CLIENT_CODE="+ clientCode;
			sql_getClientADCount
				+= " GROUP BY CLIENT_CODE";
			rs2 = st.executeQuery(sql_getClientADCount);
			rs2.next();
			ADCount = rs2.getInt("CNT");
			rs2.close();
			
			sql_stopClientAD
				= " UPDATE ASTK_AD_Profile ";
			sql_stopClientAD
				+= " SET AD_STATUS='N' ";
			sql_stopClientAD
				= " WHERE CLIENT_CODE="+ clientCode;
			rs = st.executeUpdate(sql_stopClientAD);
			if (rs != ADCount) { conn.rollback(); return false; }
			
			
			
			conn.commit();
			return true;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch..\n" + ex);
			return false;
			
		} finally {
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
		
	}
	
	public boolean changeEmail_ready(long clientCode, String newEmail){
		String sql_OTPQuery_SQL = "";
		
		sql_OTPQuery_SQL
			+= " UPDATE ASTK_CLIENT_PROFILE ";
		sql_OTPQuery_SQL
			+= " SET CLIENT_EMAIL='"+ newEmail +"' ";
		sql_OTPQuery_SQL
			+= " WHERE CLIENT_CODE="+ clientCode +" ";
		
		final long OTP = new DBManager(connPool).insertOTPQuery(sql_OTPQuery_SQL);
		
		return EmailManager.sendEmail_changeEmail(OTP, newEmail);
		
	}
	
	public boolean changeEmail_link(long OTP) {
		return new DBManager(connPool).doOTPSQL_update(OTP);
	}
	
	public boolean doSignIn(String clientID, String clientPW) {
		String sql_where
			= " WHERE CLIENT_ID='"+ clientID + "' AND CLIENT_PW='"+ clientPW +"' AND IS_CONN='T' ";
		HashMap<String, Object> tmp = this.base_selectClientInfo(sql_where, 1);
		if (!tmp.get("result").equals("T")) { return false; }
		return true;
	}
	
	public boolean doSignUp(String clientID, String clientPW, String clientName, String clientEmail, String clientPhone, String clientCtt) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int rs2 = 0;
		
		String sql_getNextClientCode = "";
		String sql_insertClientProfile = "";
		String sql_insertClientProf = "";
		String sql_insertClientPoint = "";
		
		long clientCode = 0;
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			sql_getNextClientCode
				= " SELECT SQ_CLIENT_CODE.NEXTVAL FROM DUAL";
			rs = st.executeQuery(sql_getNextClientCode);
			rs.next();
			clientCode = rs.getLong("NEXTVAL");
			rs.close();
			
			sql_insertClientProfile
				= " INSERT INTO ASTK_CLIENT_INFO(CLIENT_CODE, CLIENT_ID, CLIENT_PW) VALUES";
			sql_insertClientProfile
				+= " ("+ clientCode +",'"+ clientID +"','"+ clientPW +"')";
			rs2 = st.executeUpdate(sql_insertClientProfile);
			if (rs2 != 1) { conn.rollback(); return false; }
			
			sql_insertClientProf
				= " INSERT INTO ASTK_CLIENT_PROFILE(CLIENT_CODE, CLIENT_EMAIL, CLIENT_PHONE, CLIENT_NAME, CLIENT_CTT) VALUES ";
			sql_insertClientProf
				+= " ("+ clientCode +",'"+ clientEmail +"','"+ clientPhone +"','"+ clientName +"','"+ clientCtt +"')";
			rs2 = st.executeUpdate(sql_insertClientProf);
			if (rs2 != 1) { conn.rollback(); return false; }
			
			sql_insertClientPoint
				= " INSERT INTO ASTK_CLIENT_POINT(CLIENT_CODE) VALUES ";
			sql_insertClientPoint
				+= " ("+ clientCode +") ";
			rs2 = st.executeUpdate(sql_insertClientPoint);
			if (rs2 != 1) { conn.rollback(); return false; }
			
			conn.commit();
			return true;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName() +"\n" + ex);
			return false;
			
		} finally {
			try { if(rs != null) rs.close(); } catch (Exception ex) { }
			try { if(st != null) st.close(); } catch (Exception ex) { }
			try { if(conn != null) conn.close(); } catch (Exception ex) { }
		}
		
	}
	
	public long getClientCode(String clientID) {
		String sql_where
			= " WHERE CLIENT_ID='" + clientID +"'";
		HashMap<String, Object> tmp = this.base_selectClientInfo(sql_where, 1);
		if (!tmp.get("result").equals("T")) { return -1; }
		long clientCode = ((ClientInfo)tmp.get("clientInfo")).getClientCode();
		return clientCode;
	}
	
	public HashMap<String, Object> addADInfoListToResultSet(HashMap<String, Object> resultSet, String clientID) {
		long clientCode = this.getClientCode(clientID);
		return this.addADInfoListToResultSet(resultSet, clientCode);
	}
	
	public HashMap<String, Object> addADInfoListToResultSet(HashMap<String, Object> resultSet, long clientCode) {
		HashMap<String, Object> tmp = new ADManager(connPool).selectAD_allAD(clientCode);
		String ADResult = (String)tmp.get("result");
		resultSet.put("ADResult", ADResult);
		if (!ADResult.equals("T")) { return resultSet; }
		ArrayList<ADInfo> ADInfoList = (ArrayList<ADInfo>)tmp.get("ADInfoList");
		resultSet.put("ADInfoList", ADInfoList);
		return resultSet;
	}
	
	public HashMap<String, Object> addClientProfileToResultSet(HashMap<String, Object> resultSet, String clientID) {
		long clientCode = this.getClientCode(clientID);
		return this.addClientProfileToResultSet(resultSet, clientCode);
	}
	
	public HashMap<String, Object> addClientProfileToResultSet(HashMap<String, Object> resultSet, long clientCode) {
		HashMap<String, Object>tmp = this.selectClientProfile_some(clientCode);
		String profileResult = (String) tmp.get("result");
		resultSet.put("profileResult", profileResult);
		if (!profileResult.equals("T")) { return resultSet; }
		ClientProfile cp = (ClientProfile)tmp.get("clientProfile");
		resultSet.put("clientProfile", cp);
		return resultSet;
	}
	
	public HashMap<String, Object> addClientPointToResultSet(HashMap<String, Object> resultSet, long clientCode) {
		HashMap<String, Object> tmp = this.selectClientPoint_some(clientCode);
		String pointResult = (String) tmp.get("result");
		resultSet.put("pointResult", pointResult);
		if (tmp.get("result").equals("N")) { return resultSet; }
		ClientPoint cp = (ClientPoint)tmp.get("clientPoint");
		resultSet.put("clientPoint", cp);
		return resultSet;
		
	}
	
	public HashMap<String, Object> selectClientPoint_some(long clientCode) {
		String sql_where
			= " WHERE CLIENT_CODE="+ clientCode;
		return this.base_selectClientPoint(sql_where, 1);
	}
	
	public HashMap<String, Object> selectClientInfo_some(long clientCode) {
		String sql_where
			= " WHERE CLIENT_CODE="+ clientCode;
		return this.base_selectClientInfo(sql_where, 1);
		
	}
	
	public HashMap<String, Object> selectClientProfile_some(long clientCode) {
		String sql_where
			= " WHERE CLIENT_CODE="+ clientCode;
		return this.base_selectClientProfile(sql_where, 1);
	}
	
	public HashMap<String, Object> getClientProfile_someClient_all(long clientCode) {
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		
		tmp = this.selectClientInfo_some(clientCode);
		tmp = this.addClientProfileToResultSet(tmp, clientCode); // profileResult , clientProfile
		tmp = this.addClientPointToResultSet(tmp, clientCode); // pointResult , clientPoint
		tmp = this.addADInfoListToResultSet(tmp, clientCode); // ADResult , ADInfoList
		
		return tmp;
	}

	private HashMap<String, Object> base_selectClientProfile(String sql_where, int result) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql_select = "";
		
		HashMap<String, Object> resultSet = new HashMap<String, Object>();

		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_select
				= " SELECT * FROM ASTK_CLIENT_PROFILE " + sql_where;
			rs = st.executeQuery(sql_select);
			rs.last();
			switch (result) {
			case 0 :
				if (rs.getRow() == 0) {
					resultSet.put("result", "N");
					return resultSet;
				} else if (rs.getRow() < 0 ) {
					resultSet.put("result", "F");
					return resultSet;
				}
				break;
			default :
				if (rs.getRow() == 0) {
					resultSet.put("result", "N");
					return resultSet;
				} else if (rs.getRow() != result) {
					resultSet.put("result", "F");
					return resultSet;
				}
				break;
			}
			rs.beforeFirst();
			if ( result == 1) {
				rs.next();
				resultSet.put("clientProfile", new ClientProfile().setAll(rs));
			} else {
				ArrayList<ClientProfile> clientProfileList = new ArrayList<ClientProfile>();
				while (rs.next()) { clientProfileList.add(new ClientProfile().setAll(rs)); }
				resultSet.put("clientProfileList", clientProfileList);
			}
			resultSet.put("result", "T");
			return resultSet;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName(1) + "\n"+ ex);
			resultSet.put("result", "E");
			return resultSet;
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
	
	private HashMap<String, Object> base_selectClientInfo(String sql_where, int result) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql_select = "";
		
		HashMap<String, Object> resultSet = new HashMap<String, Object>();

		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_select
				= " SELECT CLIENT_CODE, CLIENT_ID, IS_CONN FROM ASTK_CLIENT_INFO "+ sql_where;
			rs = st.executeQuery(sql_select);
			rs.last();
			switch (result) {
			case 0 :
				if (rs.getRow() == 0) {
					resultSet.put("result", "N");
					return resultSet;
				} else if (rs.getRow() < 0 ) {
					resultSet.put("result", "F");
					return resultSet;
				}
				break;
			default :
				if (rs.getRow() == 0) {
					resultSet.put("result", "N");
					return resultSet;
				} else if (rs.getRow() != result) {
					resultSet.put("result", "F");
					return resultSet;
				}
				break;
			}
			rs.beforeFirst();
			if ( result == 1) {
				rs.next();
				resultSet.put("clientInfo", new ClientInfo().setAll(rs));
			} else {
				ArrayList<ClientInfo> clientInfoList = new ArrayList<ClientInfo>();
				while (rs.next()) { clientInfoList.add(new ClientInfo().setAll(rs)); }
				resultSet.put("clientInfoList", clientInfoList);
			}
			resultSet.put("result", "T");
			return resultSet;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getMethodName_setLength(1)+"\n"+ ex);
			resultSet.put("result", "E");
			return resultSet;
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
	
	private HashMap<String, Object> base_selectClientPoint(String sql_where, int result) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql_select = "";
		
		HashMap<String, Object> resultSet = new HashMap<String, Object>();

		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_select
				= " SELECT * FROM ASTK_CLIENT_POINT "+ sql_where;
			rs = st.executeQuery(sql_select);
			rs.last();
			switch (result) {
			case 0 :
				if (rs.getRow() == 0) {
					resultSet.put("result", "N");
					return resultSet;
				} else if (rs.getRow() < 0 ) {
					resultSet.put("result", "F");
					return resultSet;
				}
				break;
			default :
				if (rs.getRow() == 0) {
					resultSet.put("result", "N");
					return resultSet;
				} else if (rs.getRow() != result) {
					resultSet.put("result", "F");
					return resultSet;
				}
				break;
			}
			rs.beforeFirst();
			if ( result == 1) {
				rs.next();
				resultSet.put("clientPoint", new ClientPoint().setAll(rs));
			} else {
				ArrayList<ClientPoint> clientPointList = new ArrayList<ClientPoint>();
				while (rs.next()) { clientPointList.add(new ClientPoint().setAll(rs)); }
				resultSet.put("clientPointList", clientPointList);
			}
			resultSet.put("result", "T");
			return resultSet;
			
		} catch (Exception ex) {
			resultSet.put("result", "E");
			return resultSet;
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
}
