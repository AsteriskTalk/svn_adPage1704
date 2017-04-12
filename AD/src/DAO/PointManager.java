package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import DTO.ClientPoint;
import util.ASTKLogManager;
import util.DBConnectionPool;

public class PointManager {
	DBConnectionPool connPool;
	final int USER_VIEW_POINT = 1;
	
	public PointManager(DBConnectionPool connPool) {
		this.connPool = connPool;
	}
	
	public boolean userViewAD(long userCode, long ADCode, long clientCode) {
		Connection conn = null;
		Statement st = null;
		int rs = 0;
		
		final long NOW = System.currentTimeMillis();
		final long AD_VIEW_POINT;
		String sql_updateADPoint = "";
		String sql_updateUserPoint = "";
		String sql_insertADHistory = "";
		String sql_insertUserHistory = "";
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			AD_VIEW_POINT = new ADManager(connPool).calcADPoint_view(ADCode, clientCode);
			
			sql_updateADPoint
				= " UPDATE ASTK_AD_INFO ";
			sql_updateADPoint
				+= " SET AD_REMAIN_POINT=AD_REMAIN_POINT-"+ AD_VIEW_POINT +" ";
			sql_updateADPoint
				+= " , AD_REMAIN_COUNT=AD_REMAIN_COUNT-1";
			sql_updateADPoint
				+= " WHERE AD_CODE="+ ADCode + " AND CLIENT_CODE="+ clientCode;
			rs = st.executeUpdate(sql_updateADPoint);
			if (rs != 1) { conn.rollback(); return false; }
			
			sql_updateUserPoint
				= " UPDATE ASTK_USER_POINT ";
			sql_updateUserPoint
				+= " SET SUM_SAVED=SUM_SAVED+"+ USER_VIEW_POINT;
			sql_updateUserPoint
				+= " , NOW_SUM_POINT=NOW_SUM_POINT+"+ USER_VIEW_POINT;
			sql_updateUserPoint
				+= " , NOW_SAVED_POINT=NOW_SAVED_POINT+"+ USER_VIEW_POINT;
			sql_updateUserPoint
				+= " WHERE USER_CODE="+ userCode;
			rs = st.executeUpdate(sql_updateUserPoint);
			if (rs != 1) { conn.rollback(); return false; }
			
			sql_insertADHistory
				= " ISNERT INTO ASTK_HISTORY_AD ";
			sql_insertADHistory
				+= " (AD_CODE, CLIENT_CODE, USER_CODE, HIST_TYPE, HIST_POINT, HIST_DATE) ";
			sql_insertADHistory
				+= " VALUES";
			sql_insertADHistory
				+= " ("+ ADCode +","+ clientCode +","+ userCode +",'V',"+ AD_VIEW_POINT +","+ NOW +")";
			rs = st.executeUpdate(sql_insertADHistory);
			if (rs != 1) { conn.rollback(); return false; }
			
			sql_insertUserHistory
				= " INSERT INTO ASTK_HISTORY_USER ";
			sql_insertUserHistory	
				+= " (USER_CODE, CLIENT_CODE, AD_CODE, HIST_TYPE, HIST_DATE, HIST_POINT) ";
			sql_insertUserHistory
				+= " VALUES ";
			sql_insertUserHistory
				+= " ("+ userCode +","+ clientCode +","+ ADCode +",'V',"+ NOW +","+ AD_VIEW_POINT +")";
			rs = st.executeUpdate(sql_insertUserHistory);
			if (rs != 1) { conn.rollback(); return false; }
			
			conn.commit();
			return true;
			
		} catch (Exception ex) { 
			System.out.println("try-catch.."+ ASTKLogManager.getMethodName_withClassName() +"\n"+ ex);
			return false;
			
		} finally {
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
			
		}
		
	}
	
	public boolean userClickAD(long userCode, long ADCode, long clientCode) {
		Connection conn = null;
		Statement st = null;
		int rs = 0;
		
		final long NOW = System.currentTimeMillis();
		final long AD_BONUS_POINT;
		String sql_updateADPoint = "";
		String sql_updateUserPoint = "";
		String sql_insertADHistory = "";
		String sql_insertUserHistory = "";
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			AD_BONUS_POINT = new ADManager(connPool).getADBonus(ADCode, clientCode);
			
			sql_updateADPoint
				= " UPDATE ASTK_AD_INFO ";
			sql_updateADPoint
				+= " SET AD_REMAIN_POINT=AD_REMAIN_POINT-"+ AD_BONUS_POINT +" ";
			sql_updateADPoint
				+= " WHERE AD_CODE="+ ADCode + " AND CLIENT_CODE="+ clientCode;
			rs = st.executeUpdate(sql_updateADPoint);
			if (rs != 1) { conn.rollback(); return false; }
			
			sql_updateUserPoint
				= " UPDATE ASTK_USER_POINT ";
			sql_updateUserPoint
				+= " SET SUM_SAVED=SUM_SAVED+"+ AD_BONUS_POINT;
			sql_updateUserPoint
				+= " , NOW_SUM_POINT=NOW_SUM_POINT+"+ AD_BONUS_POINT;
			sql_updateUserPoint
				+= " , NOW_SAVED_POINT=NOW_SAVED_POINT+"+ AD_BONUS_POINT;
			sql_updateUserPoint
				+= " WHERE USER_CODE="+ userCode;
			rs = st.executeUpdate(sql_updateUserPoint);
			if (rs != 1) { conn.rollback(); return false; }
			
			sql_insertADHistory
				= " ISNERT INTO ASTK_HISTORY_AD ";
			sql_insertADHistory
				+= " (AD_CODE, CLIENT_CODE, USER_CODE, HIST_TYPE, HIST_POINT, HIST_DATE) ";
			sql_insertADHistory
				+= " VALUES";
			sql_insertADHistory
				+= " ("+ ADCode +","+ clientCode +","+ userCode +",'T',"+ AD_BONUS_POINT +","+ NOW +")";
			rs = st.executeUpdate(sql_insertADHistory);
			if (rs != 1) { conn.rollback(); return false; }
			
			sql_insertUserHistory
				= " INSERT INTO ASTK_HISTORY_USER ";
			sql_insertUserHistory	
				+= " (USER_CODE, CLIENT_CODE, AD_CODE, HIST_TYPE, HIST_DATE, HIST_POINT) ";
			sql_insertUserHistory
				+= " VALUES ";
			sql_insertUserHistory
				+= " ("+ userCode +","+ clientCode +","+ ADCode +",'T',"+ NOW +","+ AD_BONUS_POINT +")";
			rs = st.executeUpdate(sql_insertUserHistory);
			if (rs != 1) { conn.rollback(); return false; }
			
			conn.commit();
			return true;
			
		} catch (Exception ex) { 
			System.out.println("try-catch.."+ ASTKLogManager.getMethodName_withClassName() +"\n"+ ex);
			return false;
			
		} finally {
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
			
		}
	}
	
	public boolean rechargeClientPoint(long clientCode, long point) {
		
	}
	
	
	//L : Low, F : false, T : true
	public String checkClientPoint(long clientCode, long targetPoint) {
		long cp = this.getClientPoint(clientCode);
		
		if (cp == -1) { return "F"; }
		else if ( cp <= targetPoint ) { return "L"; }
		else if ( cp >= targetPoint ) {	return "T"; }
		else { return "F"; }
		
	}
	
	public long getClientPoint(long clientCode) {
		long point = 0;
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		tmp = new ClientManager(connPool).getClientProfile_someClient_all(clientCode);
		if (!tmp.get("result").equals("T")) { return -1; }
		point = ((ClientPoint)tmp.get("clientPoint")).getNowPoint();
		return point;
	}
	
	
	
}
