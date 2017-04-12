package DAO;

import org.json.simple.JSONObject;

import util.ADParser;
import util.DBConnectionPool;

public class UserManager {
	DBConnectionPool connPool;
	public UserManager(DBConnectionPool connPool) {
		this.connPool = connPool;
	}
	
	public boolean insertUserHistory(long userId, long chatRoomId, String type, String spendType, long point) {
		final long NOW = System.currentTimeMillis();
		String sql_table
			= " ASTK_USER_HISTORY ";
		String sql_targetColumn
			= " USER_CODE, CHAT_GRP_CODE, HIST_TYPE, SPEND_TYPE , HIST_POINT, HIST_DATE ";
		String sql_values
			= " "+ userId +","+ chatRoomId +",'"+ type +"','"+ spendType +"'," + point +","+ NOW +" ";
		
		return new DBManager(connPool).insertSQL(sql_table, sql_targetColumn, sql_values);
	}
	
	public boolean insertUserHistory(long userId, long chatRoomId, String type, long point) {
		final long NOW = System.currentTimeMillis();
		String sql_table
			= " ASTK_USER_HISTORY ";
		String sql_targetColumn
			= " USER_CODE, CHAT_GRP_CODE, HIST_TYPE, HIST_POINT, HIST_DATE ";
		String sql_values
			= " "+ userId +","+ chatRoomId +",'"+ type +"'," + point +","+ NOW +" ";
		
		return new DBManager(connPool).insertSQL(sql_table, sql_targetColumn, sql_values);
	}
	
	public JSONObject updateUserPoint_astk(long userId, long chatRoomId, long point) throws Exception {
		return new ADParser().parsingUpdateUserPoint(userId, chatRoomId, 0, 0, point); 
	}
	
}
