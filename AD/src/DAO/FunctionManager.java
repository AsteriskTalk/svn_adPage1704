package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import DTO.ADInfo;
import DTO.ADSended;
import DTO.OTCInfo;
import util.ADParser;
import util.ASTKLogManager;
import util.DBConnectionPool;
import util.FCMPushManager;

public class FunctionManager {
	DBConnectionPool connPool;

	final long AD_INTERVAL = 1000 * 60 * 5;
	final long VIEW_POINT = 1;
	
	public FunctionManager(DBConnectionPool connPool) {
		this.connPool = connPool;
	}
	
	public String doEmailCheck(String OTC) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String sql_update = "";
		
		map = new ETCManager(connPool).selectOTC(OTC);
		if (map.get("result").equals("N")) { return "N"; }
		else if (!map.get("result").equals("T")) { return "F"; }
		OTCInfo o = (OTCInfo)map.get("OTCInfo");
		
		if (o.isUsed()) { return "U"; }
		
		sql_update = o.getOTCQuery();

		Connection conn = null;
		Statement st = null;
		int rs = 0;
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			try {
				rs = st.executeUpdate(sql_update);
				if (rs != 1) { conn.rollback(); return "F"; }
				
				sql_update
				= " UPDATE ASTK_OTC_INFO SET IS_USED='T' WHERE OTC='"+ OTC +"' ";
				rs = st.executeUpdate(sql_update);
				if (rs != 1) { conn.rollback(); return "F"; }
				
				conn.commit();
				return "T";
				
			} catch (Exception ex) {
				System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName() + "\n"+ex);
				conn.rollback();
				return "E";
				
			} finally {
				try { if (st != null) st.close();} catch (Exception ex) { }
				try { if (conn != null) conn.close();} catch (Exception ex) { }
				
			}
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName() + "\n"+ex);
			return "E";
			
		}
		
	}
	
	public JSONObject getProfile(long userId)  {
		JSONObject resultSet = new JSONObject();
		try {
			resultSet = new ADParser().parsingUserInfo(userId);
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName()+ "\n"+ex);
			resultSet.put("result", "E");
		}
		return resultSet;
	}
	
	public String sendAD(long userCode, long chatGrpCode) {
		boolean result = false;
		long sendedCode = 0;
		JSONObject userProfile = new JSONObject();
		ADInfo randomAD = new ADInfo();
		ArrayList<Integer> failedList = new ArrayList<Integer>();
		ArrayList<ADInfo> ADInfoList = new ArrayList<ADInfo>();
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		ADManager am = new ADManager(connPool);
		
			try {
				//Step 1. 프로필을 파싱한다.
				userProfile = new ADParser().parsingUserInfo(userCode);
				if (!userProfile.get("result").equals("success")) { return "F"; }
				
				//Step 2. 파싱한 프로필로 광고 목록을 가져온다.
				tmp = am.selectAD_condition(userProfile);
				result = tmp.get("result").equals("T");
				if ( result ) { //Step 2-1. 성공한 경우
					ADInfoList = (ArrayList<ADInfo>)tmp.get("ADInfoList"); 
				} else { // Step 2-2. 실패한 경우 모든 광고 중 하나 고른다.
					tmp = am.selectAD_all_sendable();
					result = tmp.get("result").equals("T");
					if (result) { ADInfoList = (ArrayList<ADInfo>)tmp.get("ADInfoList"); } 
					else { return "F"; } // 모든 광고에서 고르는거 실패시 종료
				}	
	
				//Step 3. 랜덤 AD 중 하나 고른다.
				send :
				while (true) {
					if (failedList.size() == ADInfoList.size()) { return "N"; } //보낼 광고가 없으면 N
					
					boolean chargeable = false;
					boolean isSendable = false;
					int target = 0;
					
					select :
					while (true) {
						if (failedList.size() == ADInfoList.size()) { return "N"; }
						target = (int)(Math.random() * ADInfoList.size()) ;
						if ( !failedList.contains(target) ) { break select; }
					}
					
					randomAD = ADInfoList.get(target);
					long ADCode = randomAD.getADCode();
					long clientCode = randomAD.getClientCode();
					
					if (!am.isSendable(ADCode, clientCode, userCode)) { failedList.add(target); continue; }
					
					chargeable = am.isChargeable_touch(ADCode, clientCode, userCode); //보너스 포인트의 충전 가능 여부 ; 최근 5분 이내 동일 광고 중 터치 여부
					sendedCode = am.sendAD_database(ADCode, clientCode, userCode, chatGrpCode ); //Push 직전 AD의 Point를 차감시킨다... 0.SendedAD 추가 1.Histroy 기록 2.ADPoint 조작 
					if (sendedCode != -1) {new FCMPushManager().ADPush(userCode, chatGrpCode, randomAD, sendedCode, chargeable); }
	
					return "T";
				}
			
		} catch (Exception ex) { 
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_now() +"\n"+ex);
			return "E";
			
		}
	}
	
	public boolean touchAD(long sendedCode, long point) {
		HashMap<String, Object> tmp = new HashMap<String ,Object>();
		ADSended a = new ADSended();
		long ADCode = 0;
		long clientCode = 0;
		long userCode = 0;
		long chatGrpCode = 0;
		
		tmp = new ADManager(connPool).selectADSended_someSendedCode(sendedCode);
		if (!tmp.get("result").equals("T")) { return false; }
		a = (ADSended)tmp.get("ADSended");
		if (a.itWasTouch()) { return true; }
		
		ADCode = a.getADCode();
		clientCode = a.getClinetCode();
		userCode = a.getUserCode();
		chatGrpCode = a.getChatGrpCode();
		
		return this.touchAD(userCode, chatGrpCode, ADCode, clientCode, sendedCode, point);
	}
	
	public boolean touchAD(long userCode, long chatGrpCode, long ADCode, long clientCode, long sendedCode, long point) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int rs2 = 0;
		
		final long NOW = System.currentTimeMillis();
		boolean isChargeable = point != 0;
		String touchType = ( point == 0 ? "N" : "T" ); //포인트가 0이라면 ; 발송 당시 충전불가 걸려있었다면 wasTouch 값을 N으로 한다.
		
		String sql_insertADHistory = "";
		String sql_updateSendedAD = "";
		String sql_updateADInfo = "";

		boolean result = false;
		
		JSONObject json = new JSONObject();
		
		//검증은 Push 이전에 마쳤다. 단지 전달받은 포인트 대로 충전하라. 그뿐.
//		if (!am.isChargeable_touch(sendedCode) ) { return false; } 
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			
			// Step 1. ADHistory 에 기입
			sql_insertADHistory
				= " INSERT INTO ASTK_AD_HISTORY (";
			sql_insertADHistory
				+= " AD_CODE, CLIENT_CODE, USER_CODE, HIST_DATE, HIST_TYPE, HIST_POINT ";
			sql_insertADHistory
				+= " ) VALUES ( ";
			sql_insertADHistory
				+= " "+ ADCode +","+ clientCode +","+ userCode +","+ NOW +",'T',"+ point +" ";
			sql_insertADHistory
				+= " ) ";
			rs2 = st.executeUpdate(sql_insertADHistory);
			if (rs2 != 1) { conn.rollback(); return false; }
			
			// Step 2. SendedAD 를 변경
			sql_updateSendedAD
				= " UPDATE ASTK_AD_SENDED ";
			sql_updateSendedAD
				+= " SET WAS_TOUCH = '"+ touchType +"' ";
			sql_updateSendedAD
				+= " WHERE SENDED_CODE="+ sendedCode + " AND (WAS_VIEW='T' OR WAS_VIEW='N') "; //2차 검증...View 한 광고가 맞느냐?
			rs2 = st.executeUpdate(sql_updateSendedAD);
			if (rs2 != 1) { conn.rollback(); return false; }

			/** Step4. update ADInfo - Point */
			sql_updateADInfo
				= " UPDATE ASTK_AD_INFO ";
			sql_updateADInfo
				+= " SET AD_REMAIN_POINT=AD_REMAIN_POINT-"+ point +" "; //적립에 따른 포인트
			sql_updateADInfo
				+= " WHERE AD_CODE="+ ADCode +" AND CLIENT_CODE="+ clientCode +" ";
			rs2 = st.executeUpdate(sql_updateADInfo);
			if (rs2 != 1) { conn.rollback(); return false; }
			
			// Step 3. ASTK 서버 Update 요청
			json = new ADParser().parsingUpdateUserPoint(userCode, chatGrpCode, ADCode, clientCode, point);
			result = json.get("result").equals("success") ? true : false;
			if (!result) { conn.rollback(); }
			else { conn.commit(); }
			
			return result;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_now() +"\n"+ex);
			return false;
			
		} finally {
			try { if ( rs != null ) rs.close(); } catch (Exception ex) { }
			try { if ( st != null ) st.close(); } catch (Exception ex) { }
			try { if ( conn != null ) conn.close(); } catch (Exception ex) { }
			
		}
		
	}
	
	public boolean viewAD(long sendedCode) {
		HashMap<String, Object> tmp = new HashMap<String ,Object>();
		ADSended a = new ADSended();
		long ADCode = 0;
		long clientCode = 0;
		long userCode = 0;
		long chatGrpCode = 0;
		try {
			tmp = new ADManager(connPool).selectADSended_someSendedCode(sendedCode);
			if (!tmp.get("result").equals("T")) { return false; }
			a = (ADSended)tmp.get("ADSended");
			
			ADCode = a.getADCode();
			clientCode = a.getClinetCode();
			userCode = a.getUserCode();
			chatGrpCode = a.getChatGrpCode();
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_now() +"\n"+ex);
			return false;
		}
		
		return this.viewAD(userCode, chatGrpCode, ADCode, clientCode, sendedCode);
	}
	
	public boolean viewAD(long userCode, long chatGrpCode, long ADCode, long clientCode, long sendedCode) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int rs2 = 0;
		
		final long NOW = System.currentTimeMillis();
		
		String sql_insertADHistory = "";
		String sql_updateSendedAD = "";

		boolean result = false;
		
		ADManager am = new ADManager(connPool);
		JSONObject json = new JSONObject();
		
		if (!am.isChargeable_view(sendedCode) ) { return false; } //이미 wasView 인 경우에는 return false;
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			
			try {
				// Step 1. ADHistory 에 기입
				sql_insertADHistory
					= " INSERT INTO ASTK_AD_HISTORY (";
				sql_insertADHistory
					+= " AD_CODE, CLIENT_CODE, USER_CODE, HIST_DATE, HIST_TYPE, HIST_POINT ";
				sql_insertADHistory
					+= " ) VALUES ( ";
				sql_insertADHistory
					+= " "+ ADCode +","+ clientCode +","+ userCode +","+ NOW +",'V',"+ VIEW_POINT +" ";
				sql_insertADHistory
					+= " ) ";
				rs2 = st.executeUpdate(sql_insertADHistory);
				if (rs2 != 1) { conn.rollback(); return false; }
				
				// Step 2. SendedAD 를 변경
				sql_updateSendedAD
					= " UPDATE ASTK_AD_SENDED ";
				sql_updateSendedAD
					+= " SET WAS_VIEW = 'T' ";
				sql_updateSendedAD
					+= " WHERE SENDED_CODE="+ sendedCode;
				rs2 = st.executeUpdate(sql_updateSendedAD);
				if (rs2 != 1) { conn.rollback(); return false; }
				
				// Step 3. ASTK 서버에 UserPoint Update 요청
				json = new ADParser().parsingUpdateUserPoint(userCode, chatGrpCode, ADCode, clientCode, 1);
				String resultStr = (String)json.get("result");
				result = resultStr.equals("success") ? true : false;
				if (!result) { conn.rollback(); } //결과 false 이면 위의 모든 것을 롤백한다.
				else { conn.commit(); }
				
				return result;
				
			} catch (Exception ex) {
				System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName() +"\n"+ex);
				conn.rollback();
				return false;
			}
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName() +"\n"+ex);
			return false;
			
		} finally {
			try { if ( rs != null ) rs.close(); } catch (Exception ex) { }
			try { if ( st != null ) st.close(); } catch (Exception ex) { }
			try { if ( conn != null ) conn.close(); } catch (Exception ex) { }
			
		}
	}
	
	
}
