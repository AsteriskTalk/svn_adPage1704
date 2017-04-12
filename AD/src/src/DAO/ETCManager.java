package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import DTO.OpenedChatGroup;
import util.ASTKLogManager;
import util.DBConnectionPool;

public class ETCManager {
	DBConnectionPool connPool;
	
	public ETCManager(DBConnectionPool connPool) {
		this.connPool = connPool;
	}
	
	public boolean insertNotice(String noticeSbj, String noticeCtt, String noticeImgAddr) {
		long noticeCode = 0;
		this.getNoticeCode_nextval();
		String sql_table = " ASTK_AD_NOTICE ";
		String sql_targetColumn = " NOTICE_CODE, NOTICE_SBJ, NOTICE_CTT, NOTICE_IMG_ADDR ";
		String sql_values = " "+ noticeCode +","+ noticeSbj +","+ noticeCtt +","+ noticeImgAddr;
		
	}
	
	public long getNoticeCode_nextval() {
		return this.getSQ_nextval("SQ_NOTICE_CODE");
	}
	
	public long getSQ_nextval(String sqeunceName) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
		} catch (Exception ex) {
			
		} finally {
			try { if (rs!=null) rs.close(); } catch (Exception ex) { }
		}
		
	}
	
	public HashMap<String ,Object> selectOCG_all() {
		String sql_where = " ";
		return this.base_selectOCG(sql_where, 0);		
	}
	
	public HashMap<String, Object> selectOCG_someUser_someChatGrp(long userCode, long chatGrpCode) {
		String sql_where
			= " WHERE USER_CODE="+userCode + " AND CHAT_GRP_CODE="+ chatGrpCode;
		return this.base_selectOCG(sql_where, 1);
	}
	
	public boolean hasData(long userCode, long chatGrpCode) {
		HashMap<String, Object> tmp = this.selectOCG_someUser_someChatGrp(userCode, chatGrpCode);
		if (!tmp.get("result").equals("T")) { return false; }
		return true;
	}
	
	public boolean insertOCG(long userCode, long chatGrpCode) {
		//TODO 최초 방이 열리는 시점..
		/**
		 * Auto Send를 위하여 필요하다. hasData는 이미 열린 방의 호출에 대한 예외 처리.
		 */
		boolean hasData = this.hasData(userCode, chatGrpCode);
		if (hasData) { return true; } //이미 열림 상태라면 굳이 새로 insert 할필요 없이 true 반환
		
		//TODO 추가사항
		/**
		 * 이 방에 FCM Push를 전송해서 !!!view에 성공하는 시점!!! 에 AD_SEND_TIME 갱신 필요 => actionAD.java에서 처리 
		 */
		String sql_table = " ASTK_OPENED_ROOM ";
		String sql_targetColumn = " USER_CODE, CHAT_GRP_CODE ";
		String sql_values = " "+ userCode +","+ chatGrpCode + " ";
		return new DBManager(connPool).insertSQL(sql_table, sql_targetColumn, sql_values);
	}
	
	public boolean deleteOCG(long userCode, long chatGrpCode) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int rs2 = 0;
		
		final long NOW = System.currentTimeMillis();
		
		String sql_selectADSended = "";
		String sql_updateADSended = "";
		String sql_updateADInfo = "";
		String sql_insertADHistory = "";
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			
//			//Step 1. select ADSended 중에서 View 와 Touch 둘다 F인 부분
//			sql_selectADSended
//				= " SELECT AD_CODE, CLIENT_CODE FROM ASTK_AD_SENDED ";
//			sql_selectADSended
//				+= " WHERE USER_CODE="+ userCode + " AND CHAT_GRP_CODE="+ chatGrpCode;
//			sql_selectADSended
//				+= " AND WAS_VIEW='F' AND WAS_TOUCH='F' "	; //둘다 F이어야만 한다. 하나라도 F가 아니라면 View로 간주한다.
//			rs = st.executeQuery(sql_selectADSended);
//			
//			rs.last();
//			if (rs.getRow() == 0) { return true; } //실패한게 하나도 없을 경우 바로 종료
//			
//			rs.beforeFirst();			
//			while (rs.next()) {
//				long ADCode = rs.getLong("AD_CODE");
//				long clientCode = rs.getLong("CLIENT_CODE");
//				long paybackPoint = new ADManager(connPool).calcADPoint_view(ADCode, clientCode);
//				
//				//Step 2. UPDATE INFO .. AD_REMAIN_POINT & AD_REMAIN_COUNT
//				sql_updateADInfo
//					= " UPDATE ASTK_AD_INFO ";
//				sql_updateADInfo
//					+= " SET AD_REMAIN_POINT = AD_REMAIN_POINT+"+ paybackPoint;
//				sql_updateADInfo
//					+= " , AD_REMAIN_COUNT = AD_REMAIN_COUNT+1 ";
//				rs2 = st.executeUpdate(sql_updateADInfo);
//				if (rs2 != 1) { conn.rollback(); return false; }
//				
//				//Step 3. INSERT HISTORY
//				sql_insertADHistory
//					= " INSERT INTO ASTK_AD_HISTORY( ";
//				sql_insertADHistory
//					+= " AD_CODE, CLIENT_CODE, HIST_DATE, HIST_POINT, HIST_TYPE ";
//				sql_insertADHistory
//					+= " ) VALUES ( ";
//				sql_insertADHistory
//					+= " "+ ADCode +","+ clientCode +","+ NOW +","+ paybackPoint +"'B' ";
//				sql_insertADHistory
//					+= " ) ";
//				rs2 = st.executeUpdate(sql_insertADHistory);
//				if (rs2 != 1) { conn.rollback(); return false; }
//			}
//			
//			//Step 4. update ADSended 중에서 View 와 Touch 둘다 F인 부분
//			sql_updateADSended
//				= " UPDATE ASTK_AD_SENDED ";
//			sql_updateADSended
//				+= " SET WAS_VIEW='B' ";
//			sql_updateADSended
//				+= " WHERE WAS_VIEW='F AND WAS_TOUCH='F' ";
//			sql_updateADSended
//				+= " AND USER_CODE="+ userCode + " AND CHAT_GRP_CODE="+ chatGrpCode;
//			rs2 = st.executeUpdate(sql_updateADSended);
//			if (rs2 != 1) { conn.rollback(); return false; }
//			
//			conn.commit();
			return true;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName() + "\n"+ex);
			return false;
			
		} finally {
			try { if ( rs != null ) rs.close(); } catch (Exception ex) { }
			try { if ( st != null ) st.close(); } catch (Exception ex) { }
			try { if ( conn != null ) conn.close(); } catch (Exception ex) { }
			
		}
		
	}
	
	public boolean updateOCG_ADSendTimeToNow(long userCode, long chatGrpCode) {
		final long NOW = System.currentTimeMillis();
		String sql_table = " ASTK_OPENED_ROOM ";
		String sql_set = " SET AD_SEND_TIME="+ NOW;
		String sql_where = " WHERE USER_CODE="+ userCode + " AND CHAT_GRP_CODE="+ chatGrpCode;
		return new DBManager(connPool).updateSQL(sql_table, sql_set, sql_where, 1);
	}
	
	private HashMap<String, Object> base_selectOCG(String sql_where, int result) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql_select = "";
		
		HashMap<String ,Object> resultSet = new HashMap<String, Object>();
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_select
				+= " SELECT * FROM ASTK_OPENED_ROOM " + sql_where;
			rs = st.executeQuery(sql_select);
			rs.last();
			if (rs.getRow() == 0) { resultSet.put("result", "N"); return resultSet; }
			switch (result) {
			case 0 : 
				if (rs.getRow() < 0 ) { resultSet.put("result", "F"); return resultSet; } break;
			default :
				if (rs.getRow() != result	) { resultSet.put("result", "F"); return resultSet; } break; 
			}
			
			rs.beforeFirst();
			if (result == 1) {
				rs.next();
				resultSet.put("OCG", new OpenedChatGroup().setAll(rs));
			} else {
				ArrayList<OpenedChatGroup> OCGList = new ArrayList<OpenedChatGroup>();
				while (rs.next()) { OCGList.add(new OpenedChatGroup().setAll(rs)); }
				resultSet.put("OCGList", OCGList);
			}
			resultSet.put("result", "T");
			return resultSet;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName(2) +"\n"+ ex);
			resultSet.put("result", "E");
			return resultSet;
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
	
}
