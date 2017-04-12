package DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;

import DTO.ADHistory;
import DTO.ADInfo;
import DTO.ADSended;
import DTO.ADTarget;
import util.ASTKLogManager;
import util.DBConnectionPool;
import util.TimeManager;

public class ADManager {
	DBConnectionPool connPool;
	
	final int BASE_FEE = 10;
	final int OPTION_FEE_SEX = 10;
	final int OPTION_FEE_LOCAL = 10;
	final int OPTION_FEE_AGE_SOME = 10;
	final int OPTION_FEE_AGE_BETWEEN = 10;
	final long AD_INTERVAL_TIME = 1000 * 60 * 5 ; // millis * sec * min
	
	public ADManager(DBConnectionPool connPool) {
		this.connPool = connPool;
	}

	public HashMap<String, Object> selectADHistory_today(long clientCode) {
		final long todayStart = TimeManager.getMillis_todayStart();
		String sql_where
			= " WHERE CLIENT_CODE="+ clientCode + " AND HIST_DATE > "+ todayStart;
		return this.base_selectADHistory(sql_where, 0);
	}
	
	public HashMap<String, Object> selectADHistory_all(long clientCode) {
		String sql_where
			= " WHERE CLIENT_CODE="+ clientCode + " ORDER BY HIST_DATE DESC";
		return this.base_selectADHistory(sql_where, 0);
	}
	
	public HashMap<String, Object> selectAD_forStatics(long clientCode) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql_countADHistory = "";
		
		HashMap<String ,Object> resultSet = new HashMap<String, Object>();
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_countADHistory
				+= " SELECT AD_CODE, COUNT(AD_CODE) AS A_CNT, HIST_TYPE, COUNT(HIST_TYPE) AS H_CNT FROM ASTK_AD_HISTORY ";
			sql_countADHistory
				+= " WHERE CLIENT_CODE="+ clientCode;
			sql_countADHistory
				+= " GROUP BY AD_CODE, HIST_TYPE ";
			System.out.println(sql_countADHistory);
			rs = st.executeQuery(sql_countADHistory);
			rs.last();
			if (rs.getRow() == 0) {
				resultSet.put("result", "N");
				return resultSet;
			}
			rs.beforeFirst();
			while (rs.next()) { 
				long ADCode = rs.getLong("AD_CODE");
				long ADCnt = rs.getLong("A_CNT");
				String histType = rs.getString("HIST_TYPE");
				long histCnt = rs.getLong("H_CNT");
				
				HashMap<String, Object> histMap = new HashMap<String ,Object>();

				if (resultSet.get(""+ADCode) == null) {
					histMap.put(histType, histCnt);
				} else {
					histMap = (HashMap<String, Object>)resultSet.get(""+ADCode);
					histMap.put(histType, histCnt);
				}
				resultSet.put(""+ADCode, histMap);
				
			}
			resultSet.put("result", "T");
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_now() +"\n"+ex);
			resultSet.put("result", "E");
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
			return resultSet;
		}
	}
	
	public HashMap<String, Object> selectAD_forStatics(long ADCode, long clientCode) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql_countADHistory = "";
		
		HashMap<String ,Object> resultSet = new HashMap<String, Object>();
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_countADHistory
				+= " SELECT HIST_TYPE, COUNT(HIST_TYPE) AS COUNT FROM ASTK_AD_HISTORY ";
			sql_countADHistory
				+= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode;
			sql_countADHistory
				+= " GROUP BY HIST_TYPE ";
			rs = st.executeQuery(sql_countADHistory);
			rs.last();
			if (rs.getRow() == 0) {
				resultSet.put("result", "N");
				return resultSet;
			}
			rs.beforeFirst();
			while (rs.next()) { resultSet.put(rs.getString("HIST_TYPE"), rs.getLong("COUNT")); }
			resultSet.put("result", "T");
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_now() +"\n"+ex);
			resultSet.put("result", "E");
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
			return resultSet;
		}
		
	}
	
	
	public HashMap<String, Object> selectADSended_someSendedCode(long sendedCode) {
		String sql_where
			= " WHERE SENDED_CODE="+ sendedCode;
		return this.base_selectADSended(sql_where, 1);
	}
	public long sendAD_database(long ADCode, long clientCode, long userCode, long chatGrpCode) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int rs2 = 0;
		
		final long NOW = System.currentTimeMillis();
		final long SEND_POINT = this.calcADPoint_send(ADCode, clientCode);
		final long SPEND_POINT = SEND_POINT + BASE_FEE;
		long sendedCode = 0;
		
		String sql_getSQ_sendedCode_nextval = ""; 
		String sql_insertSendedAD = "";
		String sql_insertADHistory = "";
		String sql_updateADInfo = "";
		String sql_selectADInfo = "";
		
		long remainCount = 0;
		long remainPoint = 0;
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			/** Step 0. check.. */
			sql_selectADInfo
				= " SELECT * FROM ASTK_AD_INFO ";
			sql_selectADInfo
				+= " WHERE AD_CODE="+ ADCode + " AND CLIENT_CODE="+ clientCode + " ";
			rs = st.executeQuery(sql_selectADInfo);
			rs.last();
			if (rs.getRow() != 1) { conn.rollback(); return -1; }
			rs.beforeFirst();
			rs.next();
			
			remainCount = rs.getLong("AD_REMAIN_COUNT");
			remainPoint = rs.getLong("AD_REMAIN_POINT");
			rs.close();
			
			if (remainCount-1 < 0 || remainPoint-SPEND_POINT < 0) {
				/** Step 0 - case ERROR. update ADInfo */
				sql_updateADInfo
					= " UPDATE ASTK_AD_INFO SET IS_ADING='N' ";
				sql_updateADInfo
					+= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode +" ";
				
				rs2 = st.executeUpdate(sql_updateADInfo);
				if (rs2 != 1) { conn.rollback(); return -1; }
				
				return 0; 
			}
			
			/** Step1. get SendedCode */
			sql_getSQ_sendedCode_nextval
				= " SELECT SQ_SENDED_CODE.NEXTVAL FROM DUAL ";
			rs = st.executeQuery(sql_getSQ_sendedCode_nextval);
			rs.next();
			sendedCode = rs.getLong("NEXTVAL");
			
			/** Step2. insert SendedAD Info */
			sql_insertSendedAD
				= " INSERT INTO ASTK_AD_SENDED (";
			sql_insertSendedAD
				+= " SENDED_CODE, AD_CODE, CLIENT_CODE, USER_CODE, CHAT_GRP_CODE, AD_SEND_TIME ";
			sql_insertSendedAD
				+= " ) VALUES ( ";
			sql_insertSendedAD
				+= " "+ sendedCode +","+ ADCode +","+ clientCode +","+ userCode +","+ chatGrpCode +","+ NOW +" ";
			sql_insertSendedAD
				+= " ) ";
			rs2 = st.executeUpdate(sql_insertSendedAD);
			if (rs2 != 1) { conn.rollback(); return -1; }
			
			/** Step3. insert AD History */
			sql_insertADHistory
				= " INSERT INTO ASTK_AD_HISTORY	(";
			sql_insertADHistory
				+= " AD_CODE, CLIENT_CODE, USER_CODE, HIST_TYPE, HIST_POINT, HIST_DATE ";
			sql_insertADHistory
				+= " ) VALUES ( ";
			sql_insertADHistory
				+= " "+ ADCode +","+ clientCode +","+ userCode +",'S',"+ SPEND_POINT +","+ NOW +" ";
			sql_insertADHistory
				+= " ) ";
			rs2 = st.executeUpdate(sql_insertADHistory);
			if (rs2 != 1) { conn.rollback(); return -1; }

			/** Step4. update ADInfo - Point and Count */
			sql_updateADInfo
				= " UPDATE ASTK_AD_INFO ";
			sql_updateADInfo
				+= " SET AD_REMAIN_POINT=AD_REMAIN_POINT-("+ SPEND_POINT +") "; //기본 차감 + send에 필요한 point 차감
			sql_updateADInfo
				+= ", AD_REMAIN_COUNT=AD_REMAIN_COUNT-1 ";
		  if (remainCount-1 == 0 || remainPoint-SPEND_POINT == 0) { sql_updateADInfo += ", IS_ADING='N' "; } //remain 결과에 따라 추가 조작
			sql_updateADInfo
				+= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode +" ";
			rs2 = st.executeUpdate(sql_updateADInfo);
			if (rs2 != 1) { conn.rollback(); return -1; }
			
			conn.commit();
			return sendedCode;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName()+"\n"+ex);
			return -1;
			
		} finally {
			try { if (rs!=null) rs.close(); } catch (Exception ex) { }
			try { if (st!=null) st.close(); } catch (Exception ex) { }
			try { if (conn!=null) conn.close(); } catch (Exception ex) { }
			
		}
		
		
	}
		
	private boolean isInterval(long targetTime) {
		final long NOW = System.currentTimeMillis();
		final long INTERVAL = 1000 * 60 * 5;
		if (targetTime > NOW-INTERVAL && targetTime <= NOW) { return true; }
		return false; 		
	}
	
	public boolean isChargeable_touch(long ADCode, long clientCode, long userCode) {
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		String sql_where = "";
		final long NOW = System.currentTimeMillis();
		final long INTERVAL = 1000 * 60 * 5;
		
 		//Step1. get AD Information .. INTERVAL and TOUCH = 'T' ; Equals('N') 라는 건 getRow()==0 이므로..
		sql_where //광고 타겟
			= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode;
		sql_where //받았을 유저와 터치 유무
			+= " AND USER_CODE="+ userCode +" AND WAS_TOUCH='T' ";
		sql_where //5분 이내의 광고
			+= " AND AD_SEND_TIME BETWEEN " + (NOW-INTERVAL) +" AND "+ NOW;
		tmp = this.base_selectADSended(sql_where, 0);
		if (!tmp.get("result").equals("N")) { return false; }		
		return true;
	}
	
	public boolean isChargeable_touch(long sendedCode) {
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		long ADCode = 0;
		long clientCode = 0;
		long userCode = 0;
		
		//Step 1. get ADCode, clientCode, userCode
		tmp = this.selectADSended_someSendedCode(sendedCode);
		if (!tmp.get("result").equals("T")) { return false; }
 		ADSended a = new ADSended();
 		a = (ADSended) tmp.get("ADSended");
 		ADCode = a.getADCode();
 		clientCode = a.getClinetCode();
 		userCode = a.getUserCode();
 		
 		return this.isChargeable_touch(ADCode, clientCode, userCode);
	}
	
	/* 기존 목표 기능은 한번 View 한 광고는 5분 이내에 동일 광고 수신시에도 View 안되게 하려 했으나
	 * 지금은 전달된 특정 광고에 한해 재적립 불가능하게 한다.
	 * 즉, 이미 본 광고를 또 보는 오류가 발생하지 않도록 하는 것.
	 * */
	public boolean isChargeable_view(long sendedCode) {
		HashMap<String, Object> tmp = new HashMap<String ,Object>();
		
		String sql_where = " WHERE SENDED_CODE="+sendedCode;
		tmp = this.base_selectADSended(sql_where, 1);
		if (!tmp.get("result").equals("T")) { return false; }
		ADSended s = (ADSended) tmp.get("ADSended");
		if (!s.itWasView()) { return true; }
		return false;
	}

//	public boolean isChargeable_view(long ADCode, long clientCode) {
//		HashMap<String, Object> tmp = new HashMap<String ,Object>();
//		final long NOW = System.currentTimeMillis();
//		
//		tmp = this.selectADHistory_someAD(ADCode, clientCode);
//		if (!tmp.get("result").equals("T")) { return false; } //기록 없거나 에러일때 불가
//		ADHistory h = (ADHistory) tmp.get("ADHistory");
//		return false;
//	}
		
	
	public HashMap<String, Object> isChargeable_base_latest(long ADCode, long clientCode, long userCode, String histType) {
		String sql_where = "";
		
		sql_where
			= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode +" ";
		sql_where
			+= " AND USER_CODE="+ userCode +" AND HIST_TYPE='"+ histType +"' ";
		sql_where
			+= " ORDER BY HIST_DATE DESC";
		
		return this.base_selectADHistory(sql_where, -1);
	}
	
	public boolean alreadyViewAD_sended(long ADCode, long clientCode, long userCode, long chatGrpCode) {
		//포인트와 관련되어 있는 관계로 모든 애매모호한 상황은 전부 본 것으로  ; 적립과 차감이 안되는 방향으로 처리한다.
		HashMap<String, Object> tmp = this.selectADSended_some_interval(ADCode, clientCode, userCode, chatGrpCode);
		if (tmp.get("result").equals("N")) { return false; } // result == N ; 결과값 없음...즉, 최근 해당 광고 데이터 없음을 의미.
		if (tmp.get("result").equals("F")) { return true; }
		if (tmp.get("result").equals("T")) {
			ADSended s = (ADSended) tmp.get("sendedAD");
			return s.itWasView();
		}
		return true;
		
	}
	
//	public boolean alreadyTouchAD(long ADCode, long clientCode, long userCode, long chatGrpCode) {
//		HashMap<String, Object> tmp = this.selectADSended_some_interval(ADCode, clientCode, userCode, chatGrpCode);
//		if (tmp.get("result").equals("N")) { return false; }
//		if (tmp.get("result").equals("F")) { return true; }
//		if (tmp.get("result").equals("T")) {
//			ADSended s = (ADSended) tmp.get("sendedAD");
//			return s.wasTouch();
//		}
//		return true;
//		
//	}
	
	public HashMap<String, Object> selectADSended_some_interval(long ADCode, long clientCode, long userCode, long chatGrpCode) {
		final long LIMITED_TIME = System.currentTimeMillis() - AD_INTERVAL_TIME;
		final long NOW = System.currentTimeMillis();
		
		String sql_where = "";
		
		sql_where
			+= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode +" ";
		sql_where
			+= " AND USER_CODE="+ userCode +" AND CHAT_GRP_CODE="+ chatGrpCode +" ";
		sql_where
			+= " AND AD_SEND_TIME BETWEEN "+ LIMITED_TIME +" AND "+ NOW +" ";
		
		return this.base_selectADSended(sql_where, 1);
	}
	
	public boolean insertSendedAD(ADInfo AD, long userCode, long chatGrpCode) {
		long ADCode = AD.getADCode();
		long clientCode = AD.getClientCode();
		
		return this.insertSendedAD(ADCode, clientCode, userCode, chatGrpCode);
	}
	
	public boolean insertSendedAD(long ADCode, long clientCode, long userCode, long chatGrpCode) {
		final long NOW = System.currentTimeMillis();
		String sql_table = " ASTK_AD_SENDED ";
		String sql_targetColumn = " AD_CODE, CLIENT_CODE, USER_CODE, CHAT_GRP_CODE, AD_SEND_TIME ";
		String sql_values = " "+ ADCode +","+ clientCode +","+ userCode +","+ chatGrpCode +","+ NOW +" ";
		
		return new DBManager(connPool).insertSQL(sql_table, sql_targetColumn, sql_values);
		
	}
	
	public boolean deleteSendedAD_old() {
		final long TARGET_TIME = System.currentTimeMillis() - AD_INTERVAL_TIME;
		String sql_table = " ASTK_AD_SENDED ";
		String sql_where = " WHERE AD_SEND_TIME < "+ TARGET_TIME;
		return new DBManager(connPool).deleteSQL(sql_table, sql_where, 0);
	}
	
		//Deprecated
//	public boolean touchAD(long ADCode, long clientCode, long userCode, long chatGrpCode, long ADBonus) {
//		Connection conn = null;
//		Statement st = null;
//		int rs = 0;
//		
////		long ADBonus = 0;
//		final long NOW = System.currentTimeMillis();
//		
//		String sql_updateADInfo = "";
//		String sql_insertADHistory = "";
////		String sql_updateSendedAD = "";
//
//		//Step 0. alreadyTouchAD .. return false; it will be Error
//		if (this.alreadyTouchAD(ADCode, clientCode, userCode, chatGrpCode)) { return false; }
//		
//		try {
//			conn = connPool.getConn();
//			conn.setAutoCommit(false);
//			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			
////			//Step 1. get Bonus  //5분 이내 중복 클릭된 광고 보너스 처리로 인해 제거
////			HashMap<String, Object> tmp = this.selectAD_someAD(ADCode, clientCode);
////			if (!tmp.get("result").equals("T")) { return false; }
////			ADInfo adInfo = (ADInfo)tmp.get("ADInfo");
////			ADBonus = adInfo.getADBonus();
////			if (ADBonus == 0) { return false; }
//			
//			//Step 2. update ADInfo .. Sub Point
//			if (ADBonus != 0) { //포인트가 0이 아닐 때 ; 수정사항 및 기록사항이 있을 때만 실시한다.
//				sql_updateADInfo
//					+= " UPDATE ASTK_AD_INFO ";
//				sql_updateADInfo
//					+= " SET AD_REMAIN_POINT = AD_REMAIN_POINT-"+ ADBonus + " ";
//				sql_updateADInfo
//					+= " WHERE AD_CODE="+ ADCode + " AND CLIENT_CODE="+ clientCode;
//				rs = st.executeUpdate(sql_updateADInfo);
//				if (rs != 1) { conn.rollback(); return false; }
//			
//				//Step 3. insertADHistory //적립 포인트가 0일때는 기록하지 아니한다.
//				sql_insertADHistory
//					+= " INSERT INTO ASTK_AD_HISTORY ";
//				sql_insertADHistory
//					+= " (AD_CODE, CLIENT_CODE, USER_CODE, HIST_POINT, HIST_DATE, HIST_TYPE) ";
//				sql_insertADHistory
//					+= " VALUES ("+ ADCode +","+ clientCode +","+ userCode +","+ ADBonus +","+ NOW +",'T') ";
//				rs = st.executeUpdate(sql_insertADHistory);
//				if (rs != 1) { conn.rollback(); return false; }
//			}
//
////			//Step 4. updateSendedAD // 정책 변화로 더이상 사용하지 않음.
////			sql_updateSendedAD
////				+= " UPDATE ASTK_TEMP_SENDED_AD ";
////			sql_updateSendedAD
////				+= " SET WAS_TOUCH='T' ";
////			sql_updateSendedAD
////				+= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode +" ";
////			sql_updateSendedAD
////				+= " AND USER_CODE="+ userCode +" AND CHAT_GRP_CODE="+ chatGrpCode +" ";
////			rs = st.executeUpdate(sql_updateSendedAD);
////			if (rs != 1) { conn.rollback(); return false; }
//			
//			conn.commit();
//			return true;
//			
//		} catch (Exception ex) {
//			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName() +"\n" + ex);
//			return false;
//			
//		} finally {
//			try { if (st != null) st.close(); } catch (Exception ex) { }
//			try { if (conn != null) conn.close(); } catch (Exception ex) { }
//		}
//		
//	}
	
	//Deprecated
//	public boolean viewAD(long ADCode, long clientCode, long userCode, long chatGrpCode) 
//		{Connection conn = null;
//		Statement st = null;
//		int rs = 0;
//		
//		long ADView = 0;
//		final long NOW = System.currentTimeMillis();
//		
//		String sql_updateADInfo = "";
//		String sql_insertADHistory = "";
//		String sql_updateSendedAD = "";
//		
//		//Step 0. alreadyViewAD .. return false; it will be Error
//		if (this.alreadyViewAD(ADCode, clientCode, userCode, chatGrpCode)) { return false; }
//		
//		try {
//			conn = connPool.getConn();
//			conn.setAutoCommit(false);
//			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			
//			//Step 1. get ViewPoint ; AD's Sub Point Value
//			ADView = this.calcADPoint_view(ADCode, clientCode);
//			if (ADView == -1) { return false; } //case - Error or Failed
//			ADView += BASE_FEE;
//			
//			//Step 2. update ADInfo ; Sub Point
//			sql_updateADInfo
//				+= " UPDATE ASTK_AD_INFO ";
//			sql_updateADInfo
//				+= " SET AD_REMAIN_POINT = AD_REMAIN_POINT-"+ ADView + " ";
//			sql_updateADInfo
//				+=" , AD_REMAIN_COUNT = AD_REMAIN_COUNT-1";
//			sql_updateADInfo
//				+= " WHERE AD_CODE="+ ADCode + " AND CLIENT_CODE="+ clientCode;
//			rs = st.executeUpdate(sql_updateADInfo);
//			if (rs != 1) { conn.rollback(); return false; }
//			
//			//Step 3. insert ADHistory
//			sql_insertADHistory
//				+= " INSERT INTO ASTK_AD_HISTORY ";
//			sql_insertADHistory
//				+= " (AD_CODE, CLIENT_CODE, USER_CODE, HIST_POINT, HIST_DATE, HIST_TYPE) ";
//			sql_insertADHistory
//				+= " VALUES ("+ ADCode +","+ clientCode +","+ userCode +","+ ADView +","+ NOW +",'V') ";
//			rs = st.executeUpdate(sql_insertADHistory);
//			if (rs != 1) { conn.rollback(); return false; }
//			
//			//Step 4. insert SendedAD
//			sql_updateSendedAD
//			+= " UPDATE ASTK_TEMP_SENDED_AD ";
//			sql_updateSendedAD
//				+= " SET WAS_VIEW='T' ";
//			sql_updateSendedAD
//				+= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode +" ";
//			sql_updateSendedAD
//				+= " AND USER_CODE="+ userCode +" AND CHAT_GRP_CODE="+ chatGrpCode +" ";
//			rs = st.executeUpdate(sql_updateSendedAD);
//			if (rs != 1) { conn.rollback(); return false; }
//			
//			
//			
//			conn.commit();
//			return true;
//			
//		} catch (Exception ex) {
//			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName() +"\n" + ex);
//			return false;
//			
//		} finally {
//			try { if (st != null) st.close(); } catch (Exception ex) { }
//			try { if (conn != null) conn.close(); } catch (Exception ex) { }
//		}
//	
//	}
	
	public boolean collectADPoint(long clientCode, long ADCode) {
		/** include collect Method */
		Connection conn = null;
		Statement st = null;
		int rs = 0;
		
		String sql_collectADPoint = "";
		String sql_stopAD = "";
		String sql_insertADHistory = "";
		String sql_insertClientHistory = ""; 
		
		final long NOW = System.currentTimeMillis();
		final long REMAIN_POINT = this.getRemainPoint(ADCode, clientCode);
		if (REMAIN_POINT <= 0) { return false; }
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			/* STEP1. Stop AD */
			sql_stopAD
				+= " UPDATE ASTK_AD_INFO ";
			sql_stopAD
				+= " SET IS_ADING='N', AD_REMAIN_POINT=0, AD_REMAIN_COUNT=0";
			sql_stopAD
				+= " WHERE AD_CODE="+ADCode + " AND CLIENT_CODE="+clientCode;
			rs = st.executeUpdate(sql_stopAD);
			if (rs != 1) { conn.rollback(); return false; }
			
			/* STEP2. collect POINT */
			sql_collectADPoint
				+= " UPDATE ASTK_CLIENT_POINT ";
			sql_collectADPoint
				+= " SET NOW_POINT=NOW_POINT+"+ REMAIN_POINT +" ";
			sql_collectADPoint
				+= " WHERE CLIENT_CODE="+ clientCode +" ";
			rs = st.executeUpdate(sql_collectADPoint);
			if (rs != 1) { conn.rollback(); return false; }
			
			/* STEP3. insert into HISTORY_AD*/
			sql_insertADHistory
				+= " INSERT INTO ASTK_HISTORY_AD ";
			sql_insertADHistory
				+= " (AD_CODE, CLIENT_CODE, HIST_TYPE, HIST_POINT, HIST_DATE) ";
			sql_insertADHistory
				+= " VALUES ";
			sql_insertADHistory
				+= " ("+ ADCode +","+ clientCode +",'C',"+ REMAIN_POINT +","+ NOW +")";
			rs = st.executeUpdate(sql_insertADHistory);
			if (rs != 1) { conn.rollback(); return false; }
			
			/* STEP4. insert into HISTORY_CLIENT */
			sql_insertClientHistory
				+= " INSERT INTO ASTK_HISTORY_CLIENT ";
			sql_insertClientHistory
				+= " (CLIENT_CODE, AD_CODE, HIST_TYPE, HIST_POINT, HIST_DATE) ";
			sql_insertClientHistory
				+= " VALUES ";
			sql_insertClientHistory
				+= " ("+ clientCode +","+ ADCode +",'C',"+ REMAIN_POINT +","+ NOW +")";
			rs = st.executeUpdate(sql_insertClientHistory);
			if (rs != 1) { conn.rollback(); return false; }
			
			
			conn.commit();
			return true;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.." + ASTKLogManager.getMethodName_withClassName() +"\n" + ex);
			return false;
			
		} finally {
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
			
		}
		
	}
	
	public boolean isADReceivable(long userCode ) {
		String sql_where
			= " WHERE USER_CODE ="+ userCode +" AND ROWNUM=1 ORDER BY HIST_DATE DESC;";
		HashMap<String, Object> tmp = this.base_selectADHistory(sql_where, 1);
		if (tmp.get("result").equals("T")) { 
			ADHistory a = (ADHistory) tmp.get("ADHistory");
			
			final long TARGET_DATE = System.currentTimeMillis() - AD_INTERVAL_TIME;
			final long HISTORY_DATE = a.getHistDate();
			
			return HISTORY_DATE <= TARGET_DATE;
		}
		return true;
		
	}
	
	public boolean isSendable(long ADCode, long clientCode, long userCode) {
			HashMap<String, Object> tmp = new HashMap<String, Object>();
			final long NOW = System.currentTimeMillis();
			String sql_where = "";
			
			sql_where
				+= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode;
			sql_where
				+= " AND USER_CODE="+ userCode + " AND HIST_DATE >"+ (NOW - AD_INTERVAL_TIME); //5분 이내의 기록 중에서...
			sql_where
				+= " AND HIST_TYPE='V' ";
			tmp = this.base_selectADHistory(sql_where, 0);
			if (tmp.get("result").equals("T")) { return false; } //있으면 발송불가
			return true;
			
	}
	
	public HashMap<String, Object> selectAD_all_sendable(){
		String sql_where
			= " WHERE IS_CONN='T' AND IS_ADING='T' AND AD_REMAIN_POINT > 0 AND AD_REMAIN_COUNT > 0 ";
		return this.base_selectAD(sql_where, 0);
	}
	
	// L : Low, F : false ; failed, T : true ; success
	public String addAD(long clientCode, String ADCtt, String ADURL, String ADImgAddr, long ADCount, long ADBonus, ArrayList<ADTarget> ADTargetList) {
		/** include spend Method */
		Connection conn = null;
		Statement st = null;
		ResultSet rs2 = null;
		int rs = 0;
		
		String sql_insertADTarget_base = " INSERT INTO ASTK_AD_TARGET (AD_CODE, CLIENT_CODE, TARGET_TYPE, TARGET_VALUE) VALUES";
		String sql_getADCode = "";
		String sql_insertAD = "";
		String sql_insertADTarget = "";
		String sql_updatePoint = "";
		String sql_selectClientPoint = "";
		
		final long AD_CODE;
		String tmp = "";		
		long needPoint = 0;
		
		PointManager pm = new PointManager(connPool);

		needPoint = this.calcADPoint_need(ADCount, ADBonus, ADTargetList);
		
		//Point Check 1단계
		tmp = pm.checkClientPoint(clientCode, needPoint);
		if (!tmp.equals("T")) { return tmp; }

		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			//Step1. get AD_CODE
			sql_getADCode
				= " SELECT SQ_AD_CODE.NEXTVAL FROM DUAL";
			rs2 = st.executeQuery(sql_getADCode);
			rs2.next();
			AD_CODE = rs2.getLong("NEXTVAL");
			rs2.close();
			
			//Step2. insert AD
			sql_insertAD 
				+= "INSERT INTO ASTK_AD_INFO";
			sql_insertAD
				+= "(AD_CODE, CLIENT_CODE, AD_CTT, AD_URL, AD_IMG_ADDR, AD_REMAIN_POINT, AD_BONUS, AD_REMAIN_COUNT)";
			sql_insertAD
				+= " VALUES";
			sql_insertAD
				+= "("+ AD_CODE +","+ clientCode +",'"+ ADCtt +"','"+ ADURL +"','"+ ADImgAddr +"'";
			sql_insertAD
				+= ","+ needPoint +","+ ADBonus +","+ ADCount +")";
			rs = st.executeUpdate(sql_insertAD);
			if (rs != 1) { conn.rollback(); return "F"; }
			
			//Step3. insert ADTarget
			for (ADTarget target : ADTargetList) {
				String type = target.getTargetType();
				String value = target.getTargetValue();
//				System.out.println("log : " + ASTKLogManager.getMethodName_withClassName() + ".."+ type + "-"+ value);
				
				sql_insertADTarget 
					= sql_insertADTarget_base;
				sql_insertADTarget
					+= " ("+ AD_CODE +","+ clientCode +",'"+ type +"','"+ value +"')";
				rs = st.executeUpdate(sql_insertADTarget);
				if (rs != 1) { conn.rollback(); return "F"; }
			}
			
			// PointCheck 2단계 -  연속입력 방어용
			tmp = pm.checkClientPoint(clientCode, needPoint);
			if (!tmp.equals("T")) { return tmp; }
			
			// update POINT
			sql_updatePoint
				+= " UPDATE ASTK_CLIENT_POINT ";
			sql_updatePoint
				+= " SET NOW_POINT = NOW_POINT-"+ needPoint;
			sql_updatePoint
				+= " WHERE CLIENT_CODE ="+ clientCode;
			rs = st.executeUpdate(sql_updatePoint);
			if (rs != 1) { conn.rollback(); return "F"; }
			
			// PointCheck 3단계 - 결과값 기준 방어 ; 결과가 음수면 rollback;
			sql_selectClientPoint
				+= " SELECT NOW_POINT FROM ASTK_CLIENT_POINT ";
			sql_selectClientPoint
				+= " WHERE CLIENT_CODE ="+ clientCode;
			rs2 = st.executeQuery(sql_selectClientPoint);
			rs2.next();
			if (rs2.getLong("NOW_POINT") < 0) { conn.rollback(); return "L"; }
			
			conn.commit();
			return "T";
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName() +"\n" + ex);
			return "E";
			
		} finally {
			try { if (rs2 != null) rs2.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
	
	public String editAD(long ADCode, long clientCode, String newCtt, String oldURL, String newURL, String newImgAddr, 
			long newBonus, long oldBonus, long remainCount, long newCount, HashMap<String ,Object> newTarget) {
		/** include collect or spend Method */
		Connection conn = null;
		Statement st = null;
		ResultSet rs2 = null;
		int rs = 0;
		
		int ADTargetCount = 0;
		
		long remainPoint = this.getRemainPoint(ADCode, clientCode);
		long newPoint = this.calcADPoint(newCount, newBonus, newTarget);
		long diffPoint = remainPoint - newPoint;
		
		boolean useSex = false;
		boolean useAge = false;
		boolean useLocal = false;
		String ageType = "";
		
		String sql_updateClientPoint = "";
		String sql_updateADInfo = "";
		String sql_countADTarget = "";
		String sql_deleteADTarget = "";
		String sql_insertADTarget = "";
		String sql_insertADTarget_base = "INSERT INTO ASTK_AD_TARGET (AD_CODE, CLIENT_CODE, TARGET_TYPE, TARGET_VALUE) VALUES ";
		
		useSex = (Boolean)newTarget.get("useSex");
		useAge = (Boolean)newTarget.get("useAge");
		useLocal = (Boolean)newTarget.get("useLocal");
		if (useAge) { ageType = (String)newTarget.get("ageType"); }
		
		/* Point 부족 */
		if (diffPoint < 0) { 
			String tmp = new PointManager(connPool).checkClientPoint(clientCode, diffPoint);
			if (!tmp.equals("T")) { return tmp; }
		}

		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			/* Step1. Point..case : need more point */
			if(diffPoint < 0) {
				sql_updateClientPoint
					+= " UPDATE ASTK_CLIENT_POINT ";
				sql_updateClientPoint
					+= " SET NOW_POINT = NOW_POINT+"+ diffPoint;
				sql_updateClientPoint
					+= " WHERE CLIENT_CODE =" + clientCode;
				rs = st.executeUpdate(sql_updateClientPoint);
				if (rs != 1) { conn.rollback(); return "F"; }
			}
			
			/* Step2. update AD */
			sql_updateADInfo
				+= " UPDATE ASTK_AD_INFO ";
			sql_updateADInfo
				+= " SET AD_CTT='" + newCtt +"' ";
			if (!newURL.equals("oldURL")) { sql_updateADInfo+= ", AD_URL='" + newURL +"'"; }
			if (!newImgAddr.equals("stay")) { sql_updateADInfo += ", AD_IMG_ADDR ='" + newImgAddr +"' "; }
			if (newBonus != oldBonus) {sql_updateADInfo += ", AD_BONUS=" + newBonus +" "; }
			if (newCount != remainCount) { sql_updateADInfo += ", AD_REMAIN_COUNT="+ newCount +" "; }
			if (diffPoint != 0) { sql_updateADInfo += ", AD_REMAIN_POINT="+ newPoint +" "; }
			sql_updateADInfo
				+= " WHERE AD_CODE="+ ADCode + " AND CLIENT_CODE="+ clientCode;			
			rs = st.executeUpdate(sql_updateADInfo);
			if (rs != 1) { conn.rollback(); return "F"; }
			
			/* Step3-1. get AD_TARGET count */
			sql_countADTarget
				+= " SELECT COUNT(*) AS CNT FROM ASTK_AD_TARGET ";
			sql_countADTarget
				+= " WHERE AD_CODE="+ ADCode + " AND CLIENT_CODE=" + clientCode;
			sql_countADTarget
				+= " GROUP BY AD_CODE ";
			rs2 = st.executeQuery(sql_countADTarget);
			rs2.next();
			ADTargetCount = rs2.getInt("CNT");
			rs2.close();
			
			/* Step3-2. delete AD_TARGET */
			sql_deleteADTarget
				+= " DELETE ASTK_AD_TARGET ";
			sql_deleteADTarget
				+= " WHERE AD_CODE="+ ADCode + " AND CLIENT_CODE="+ clientCode;
			rs = st.executeUpdate(sql_deleteADTarget);
			if (rs != ADTargetCount) { conn.rollback(); return "F"; } 
			
			
			/* Step4-1. insert AD_Target .. Sex */
			if (useSex) {
				String sexValue = (String)newTarget.get("sexValue");
				sql_insertADTarget = sql_insertADTarget_base;
				sql_insertADTarget
					+= "("+ ADCode +","+ clientCode +",'G','"+ sexValue +"')";
				rs = st.executeUpdate(sql_insertADTarget);
				if (rs != 1) { conn.rollback(); return "F"; }
			}
			
			/* Step4-2. insert AD_Target .. Age */
			if (useAge) {
				ageType = (String)newTarget.get("ageType");
				sql_insertADTarget = sql_insertADTarget_base;
				if (ageType.equals("some")) {
					int ageValue = (Integer)newTarget.get("ageValue");
					sql_insertADTarget
						+= "("+ ADCode +","+ clientCode +",'S','"+ ageValue +"')";
				} else if (ageType.equals("between")) {
					int ageValue1 = (Integer)newTarget.get("ageValue1");
					int ageValue2 = (Integer)newTarget.get("ageValue2");
					sql_insertADTarget
						+= "("+ ADCode +","+ clientCode +",'B','"+ ageValue1 +"')";
					rs = st.executeUpdate(sql_insertADTarget);
					if (rs != 1) { conn.rollback(); return "F"; }
					
					sql_insertADTarget = sql_insertADTarget_base;
					sql_insertADTarget
						+= "("+ ADCode +","+ clientCode +",'B','"+ ageValue2 +"')";
				}
				rs = st.executeUpdate(sql_insertADTarget);
				if (rs != 1) { conn.rollback(); return "F"; }
			}
			
			/* Step4-3. insert AD_Target .. Local */
			if (useLocal) {
				String[] localValues = (String[])newTarget.get("localValues");
				for (String localValue : localValues) {
					sql_insertADTarget = sql_insertADTarget_base;
					sql_insertADTarget
						+="("+ ADCode +","+ clientCode +",'L','"+ localValue +"')";
					rs = st.executeUpdate(sql_insertADTarget);
					if (rs != 1) { conn.rollback(); return "F"; }
				}
			}
			
			/* Step5. Point..case : collect point */
			if(diffPoint > 0) {
				sql_updateClientPoint
					+= " UPDATE ASTK_CLIENT_POINT ";
				sql_updateClientPoint
					+= " SET NOW_POINT = NOW_POINT+"+ diffPoint;
				sql_updateClientPoint
					+= " WHERE CLIENT_CODE =" + clientCode;
				rs = st.executeUpdate(sql_updateClientPoint);
				if (rs != 1) { conn.rollback(); return "F"; }
			}
			
			conn.commit();
			return "T";
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.." + ASTKLogManager.getMethodName_withClassName() +"\n" + ex);
			return "E";
			
		} finally {
			try { if (rs2 != null) rs2.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
		
	}
	
	public boolean disconnectAD(long ADCode, long clientCode) {
		/** include collect Method */
		Connection conn = null;
		Statement st = null;
		int rs = 0;
		
		long remainPoint = 0;
		String sql_disconnAD = "";
		String sql_collectADPoint = "";
		
		HashMap<String, Object> tmp = this.selectAD_someAD(ADCode, clientCode);
		if (!tmp.get("result").equals("T")) { return false; }
		remainPoint = ((ADInfo)tmp.get("ADInfo")).getADRemainPoint();
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			/* STEP1. disconnect AD - CLIENT */
			sql_disconnAD
				+= " UPDATE ASTK_AD_INFO ";
			sql_disconnAD
				+= " SET IS_CONNECT='F', IS_ADING='N'";
			sql_disconnAD //기존 잔여 Point 들은 음수로 돌려서 최종 상태 보존한다.
				+= ", AD_BONUS=AD_BONUS*-1, AD_REMAIN_POINT=AD_REMAIN_POINT*-1, AD_REMAIN_COUNT=AD_REMAIN_COUNT*-1 ";
			sql_disconnAD
				+= " WHERE AD_CODE="+ADCode + " AND CLIENT_CODE="+clientCode;
			rs = st.executeUpdate(sql_disconnAD);
			if (rs != 1) { conn.rollback(); return false; }
			
			/* STEP2. collect POINT */
			sql_collectADPoint
				+= " UPDATE ASTK_CLIENT_POINT ";
			sql_collectADPoint
				+= " SET NOW_POINT=NOW_POINT+"+ remainPoint +" ";
			sql_collectADPoint
				+= " WHERE CLIENT_CODE="+ clientCode +" ";
			rs = st.executeUpdate(sql_collectADPoint);
			if (rs != 1) { conn.rollback(); return false; }
			
			conn.commit();
			return true;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.." + ASTKLogManager.getMethodName_withClassName() +"\n" + ex);
			return false;
			
		} finally {
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
			
		}
			
		
	}
	
	public boolean stopAD(long ADCode, long clientCode) {
		/** include collect Method */
		Connection conn = null;
		Statement st = null;
		int rs = 0;
		
		String sql_stopAD = "";
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			/* STEP1. Stop AD */
			sql_stopAD
				+= " UPDATE ASTK_AD_INFO ";
			sql_stopAD
				+= " SET IS_ADING='N'";
			sql_stopAD
				+= " WHERE AD_CODE="+ADCode + " AND CLIENT_CODE="+clientCode;
			rs = st.executeUpdate(sql_stopAD);
			if (rs != 1) { conn.rollback(); return false; }
			
			conn.commit();
			return true;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.." + ASTKLogManager.getMethodName_withClassName() +"\n" + ex);
			return false;
			
		} finally {
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
			
		}
			
	}
	
	public long getADBonus(long ADCode, long clientCode) {
		HashMap<String, Object> tmp = this.selectAD_someAD(ADCode, clientCode);
		if (!tmp.get("result").equals("T")) { return -1; }
		return ((ADInfo)tmp.get("ADInfo")).getADBonus();
	}
	
	public long calcADPoint_send(long ADCode, long clientCode) {
		return this.calcADPoint_send(this.selectADTarget_someAD_allTarget(ADCode, clientCode));
	}
	
	public long calcADPoint_send(ArrayList<ADTarget> ADTargetList) {
		long calcPoint = 0;
		boolean useAge_some = false;
		boolean useAge_between = false;
		boolean useSex = false;
		boolean useLocal = false;
		
		for (ADTarget target : ADTargetList) {
			String type = target.getTargetType();
			
			if (type.equals("G")) { useSex = true; }
			else if (type.equals("B")) { useAge_between = true; }
			else if (type.equals("S")) { useAge_some = true; }
			else if (type.equals("L")) { useLocal = true; }
		}
		
		if (useSex) { calcPoint += OPTION_FEE_SEX;}
		if (useAge_between) { calcPoint += OPTION_FEE_AGE_BETWEEN; } 
		if (useAge_some) { calcPoint += OPTION_FEE_AGE_SOME; }
		if (useLocal) {	calcPoint += OPTION_FEE_LOCAL; }
		
		return calcPoint;
	}
	
	public long calcADPoint_send(HashMap<String, Object> map) {
		if (map.get("result").equals("N")) { return 0; }
		else if (!map.get("result").equals("T")) { return -1; }
		
		ArrayList<ADTarget> ADTargetList = (ArrayList<ADTarget>) map.get("ADTargetList");
		
		return this.calcADPoint_send(ADTargetList);
	}
	

	private long calcADPoint_need(long count, long bonus, ArrayList<ADTarget> ADTargetList) {
		long viewPoint = 0;
		long calcPoint = 0;
		
		viewPoint = this.calcADPoint_send(ADTargetList);
		if (viewPoint == -1) { return -1; }
		
		calcPoint += count * BASE_FEE;
		calcPoint += count * bonus;
		calcPoint += count * viewPoint;
		
		return calcPoint;
	}
	
	private long calcADPoint(long count, long bonus, HashMap<String, Object> map) {
		if (map.get("result").equals("N")) { return this.calcADPoint_need(count, bonus, new ArrayList<ADTarget>()); }
		else if (!map.get("result").equals("T")) { return -1; }
		else { return this.calcADPoint_need(count, bonus, (ArrayList<ADTarget>)map.get("ADTargetList")); }
	}
	
	public long getRemainPoint(long ADCode, long clientCode) {
		HashMap<String, Object> tmp = this.selectAD_someAD(ADCode, clientCode);
		if(!tmp.get("result").equals("T")) { return -1; }
		long remainPoint = ((ADInfo)tmp.get("ADInfo")).getADRemainPoint();
		return remainPoint;
	}
	
	public HashMap<String, Object> selectAD_condition (JSONObject userProfile) {
		JSONObject userInfo = (JSONObject)userProfile.get("userInfo");
		long tmp = (Long)userInfo.get("userSex");
		
		String userSex = tmp==1 ? "M" : tmp==2 ? "F" : "N";
		String userLocal = (String)userInfo.get("userCity");
		long userAge = (Long)userInfo.get("userBirthYear");
		
		return this.selectAD_condition(userSex, userAge, userLocal);
	}
	

	public HashMap<String, Object> selectAD_condition(String userSex, long userAge, String userLocal) {
		HashMap<String, Object> resultSet = new HashMap<String, Object>();
		ArrayList<ADInfo> ADInfoList = new ArrayList<ADInfo>();

		HashMap<String, Object> tmp = new HashMap<String, Object>();
		ArrayList<ADInfo> list = new ArrayList<ADInfo>();
		
		tmp = this.selectAD_all_sendable();
		if (!tmp.get("result").equals("T")) { return tmp; }
		list = (ArrayList<ADInfo>)tmp.get("ADInfoList");
		
		for (ADInfo a : list) {
			ArrayList<ADTarget> ADTargetList = new ArrayList<ADTarget>();
			long ADCode = a.getADCode();
			long clientCode = a.getClientCode();

			long age_low = 0;
			long age_high = 0;
			int age_between_cnt = 0;
			
			boolean useSex = false;
			boolean useAge = false;
			boolean useLocal = false;
			
			boolean isMatch_sex = false;
			boolean isMatch_age = false;
			boolean isMatch_local = false;
			
			tmp = this.selectADTarget_someAD_allTarget(ADCode, clientCode);
			
			if (tmp.get("result").equals("F")) { return tmp; }
			else if (tmp.get("result").equals("T")) { 
				ADTargetList = (ArrayList<ADTarget>)tmp.get("ADTargetList");
				
				for (ADTarget t : ADTargetList) {
					String type = t.getTargetType();
					String value = t.getTargetValue();
					
					if (type.equals("G")) { useSex = true; if (value.equals(userSex)) { isMatch_sex = true; } } 
					
					else if (type.equals("L")) { useLocal = true; if (value.equals(userLocal)) { isMatch_local = true; } } 
					
					else if (type.equals("S")) { useAge = true; if (Long.parseLong(value) == userAge) { isMatch_age = true; } } 
					
					else if (type.equals("B")) { 
						useAge = true;
						long age_tmp = Long.parseLong(value);
						if (age_tmp > age_high) { age_low = age_high; age_high = age_tmp; } 
						else { age_low = age_tmp; }
						age_between_cnt++;
						
						if (age_between_cnt == 2) { if (age_low<=userAge && age_high>= userAge){ isMatch_age = true; } }
					}
				}
			} 
			
			if (!useSex) { isMatch_sex = true; }
			if (!useAge) { isMatch_age = true; }
			if (!useLocal) { isMatch_local = true; }
			
			if (!isMatch_sex || !isMatch_age || !isMatch_local ) { continue; }
			
			a.setADTargetList(ADTargetList);
			ADInfoList.add(a);
		}
		
		if (ADInfoList.size() == 0) { resultSet.put("result", "N"); return resultSet; }
		
		resultSet.put("result", "T");
		resultSet.put("ADInfoList", ADInfoList);
		
		return resultSet;
		
	}
	
	
	public ArrayList<ADTarget> getADTargetList_request(HttpServletRequest req) throws Exception {
		ArrayList<ADTarget> ADTargetList = new ArrayList<ADTarget>();
		
		boolean useSex = false;
		boolean useAge_between = false;
		boolean useAge_some = false;
		boolean useLocal = false;
		
		String[] targetList = req.getParameterValues("ADTarget");
		
		for (String targetValue : targetList) {
			if (targetValue.equals("useSex")) { useSex = true; continue; }
			if (targetValue.equals("uesLocal")) { useLocal = true; continue; }
			if (targetValue.equals("useAge")) { 
				String ageType = req.getParameter("ageType");
				if (ageType.equals("some")) { useAge_some = true; }
				else if (ageType.equals("between")) { useAge_between = true; }
				continue; 
			}
		}
		
		if (useSex) { ADTargetList.add(new ADTarget().setTargetType("G").setTargetValue(req.getParameter("sexValue"))); }
		
		if (useLocal) {
			String[] localValues = req.getParameterValues("localValue");
			for (String localValue : localValues) { ADTargetList.add(new ADTarget().setTargetType("L").setTargetValue(localValue));  }
		}
		
		if (useAge_some) { 
			ADTargetList.add(new ADTarget().setTargetType("S").setTargetValue(req.getParameter("ageValue"))); }
		
		if (useAge_between) {
			ADTargetList.add(new ADTarget().setTargetType("B").setTargetValue(req.getParameter("ageValue1")) );
			ADTargetList.add(new ADTarget().setTargetType("B").setTargetValue(req.getParameter("ageValue2")) );
		}
		
		return ADTargetList;
	}

	
	public ArrayList<ADTarget> getADTargetList_multipart(MultipartRequest multi) throws Exception {
		boolean hasTarget = false;
		boolean useSex = false;
		boolean useAge_between = false;
		boolean useAge_some = false;
		boolean useLocal = false;
		
		ArrayList<ADTarget> ADTargetList = new ArrayList<ADTarget>();
		
		Enumeration e = multi.getParameterNames();
		while (e.hasMoreElements()) {
			String s = (String)e.nextElement();
			if (s.equals("ADTarget")) { hasTarget = true; break; }
		}
		
		String[] targetList = multi.getParameterValues("ADTarget");
		
		for (String targetValue : targetList) {
			if (targetValue.equals("useSex")) { useSex = true; continue; }
			if (targetValue.equals("useLocal")) { useLocal = true; continue; }
			if (targetValue.equals("useAge")) { 
				String ageType = multi.getParameter("ageType");
				if (ageType.equals("some")) { useAge_some = true; }
				else if (ageType.equals("between")) { useAge_between = true; }
				continue; 
			}
		}
		
		if (useSex) { ADTargetList.add(new ADTarget().setTargetType("G").setTargetValue(multi.getParameter("sexValue"))); }
		
		if (useLocal) {
			String[] localValues = multi.getParameterValues("localValue");
			for (String localValue : localValues) { ADTargetList.add(new ADTarget().setTargetType("L").setTargetValue(localValue));  }
		}
		
		if (useAge_some) { 
			ADTargetList.add(new ADTarget().setTargetType("S").setTargetValue(multi.getParameter("ageValue"))); }
		
		if (useAge_between) {
			ADTargetList.add(new ADTarget().setTargetType("B").setTargetValue(multi.getParameter("ageValue1")) );
			ADTargetList.add(new ADTarget().setTargetType("B").setTargetValue(multi.getParameter("ageValue2")) );
		}
		
		return ADTargetList;
	}
	
	
	public HashMap<String, Object> selectADTarget_someAD_allTarget(long ADCode, long clientCode) {
		String sql_where
		= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode;
		return this.base_selectADTarget_someAD_allTarget(sql_where);
	}
	
	/*
	public HashMap<String, Object> selectADTarget_someAD_allADTarget(long ADCode, long clientCode) {
		String sql_where
			= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode;
		return this.base_selectADTarget_someAD_allADTarget(sql_where);
	}
	*/

	public HashMap<String, Object> selectAD_allAD(long clientCode) {
		String sql_where = " WHERE CLIENT_CODE=" + clientCode;
		return this.base_selectAD(sql_where, 0);
	}
	
	public HashMap<String, Object> selectAD_someAD(long ADCode, long clientCode) {
		String sql_where = " WHERE AD_CODE=" + ADCode + " AND CLIENT_CODE=" + clientCode;
		return this.base_selectAD(sql_where, 1);
	}
	
	public HashMap<String, Object> selectAD_someAD_withTarget(long ADCode, long clientCode) {
		HashMap<String, Object> resultSet = new HashMap<String, Object>();
		
		resultSet = this.selectAD_someAD(ADCode, clientCode);
		resultSet = this.addADTargetToResultSet(ADCode, clientCode, resultSet);
		
		return resultSet;
	}
	
	public HashMap<String, Object> addADTargetToResultSet(long ADCode, long clientCode, HashMap<String, Object> resultSet) {
		HashMap<String, Object> tmp = this.selectADTarget_someAD_allTarget(ADCode, clientCode);
		String ADTargetResult = (String)tmp.get("result");
		resultSet.put("ADTargetResult", ADTargetResult);
		
		if (ADTargetResult.equals("T")) {
			ArrayList<ADTarget> ADTargetList = (ArrayList<ADTarget>) tmp.get("ADTargetList");
			resultSet.put("ADTargetList", ADTargetList);
		}
		return resultSet;
	}
	
	public HashMap<String, Object> selectADHistory_someAD(long ADCode, long clientCode) {
		String sql_where = " WHERE AD_CODE=" + ADCode + " AND CLIENT_CODE=" + clientCode + " ORDER BY HIST_DATE DESC ";
		return this.base_selectADHistory(sql_where, 0);
	}
	
	private HashMap<String, Object> base_selectADHistory(String sql_where, int result) {
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
				= " SELECT * FROM ASTK_AD_HISTORY " + sql_where;
			rs = st.executeQuery(sql_select);
			rs.last();
			switch (result) {
			case -1 :
				if (rs.getRow() == 0) {
					resultSet.put("result", "N");
					return resultSet;
				} else if (rs.getRow() < 0) {
					resultSet.put("result", "F");
					return resultSet;
				}
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
			if ( result == 1 || result == -1 ) {
				rs.next();
				resultSet.put("ADHistory", new ADHistory().setAll(rs));
			} else {
				ArrayList<ADHistory> ADHistoryList = new ArrayList<ADHistory>();
				while (rs.next()) { ADHistoryList.add(new ADHistory().setAll(rs)); }
				resultSet.put("ADHistoryList", ADHistoryList);
			}
			resultSet.put("result", "T");
			return resultSet;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_now() +"\n"+ex);
			resultSet.put("result", "E");
			return resultSet;
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
	
	private HashMap<String ,Object> base_selectAD(String sql_where, int result) {
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
				= " SELECT * FROM ASTK_AD_INFO " + sql_where;
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
				resultSet.put("ADInfo", new ADInfo().setAll(rs));
			} else {
				ArrayList<ADInfo> ADInfoList = new ArrayList<ADInfo>();
				while (rs.next()) { ADInfoList.add(new ADInfo().setAll(rs)); }
				resultSet.put("ADInfoList", ADInfoList);
			}
			resultSet.put("result", "T");
			return resultSet;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_now() +"\n"+ex);
			resultSet.put("result", "E");
			return resultSet;
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
	
	private HashMap<String ,Object> base_selectADTarget_someAD_allTarget(String sql_where) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql_select = "";
		
		HashMap<String, Object> resultSet = new HashMap<String, Object>();
		ArrayList<ADTarget> ADTargetList = new ArrayList<ADTarget>();
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_select
				+= " SELECT * FROM ASTK_AD_TARGET "+ sql_where;
			rs = st.executeQuery(sql_select);
			rs.last();
			if (rs.getRow() == 0) { resultSet.put("result", "N"); return resultSet; }
			rs.beforeFirst();
			
			while (rs.next()) { ADTargetList.add(new ADTarget().setAll(rs)); }
			
			resultSet.put("result", "T");
			resultSet.put("ADTargetList", ADTargetList);
			return resultSet;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_now() +"\n"+ex);
			resultSet.put("result", "E");
			return resultSet;
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
	
	private HashMap<String, Object> base_selectADSended(String sql_where, int result) {
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
				+= " SELECT * FROM ASTK_AD_SENDED" + sql_where;
			
			rs = st.executeQuery(sql_select);
			rs.last();
			if (rs.getRow() == 0) { resultSet.put("result", "N"); return resultSet; }
			switch (result) {
			case 0 :
				if (rs.getRow() < 0) { resultSet.put("result", "F"); return resultSet; }
				break;
			default :
				if (rs.getRow() != result) { resultSet.put("result", "F"); return resultSet; }
				break;
			}
			rs.beforeFirst();

			resultSet.put("result", "T");
			if (result == 1) {
				rs.next();
				resultSet.put("ADSended", new ADSended().setAll(rs));
			} else {
				ArrayList<ADSended> sendedADList = new ArrayList<ADSended>();
				while (rs.next()) { sendedADList.add(new ADSended().setAll(rs)); }
				resultSet.put("ADSendedList", sendedADList);
			}
			
			return resultSet;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName(2) +"\n"+ex);
			resultSet.put("result", "E");
			return resultSet;
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
		
}
