package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import DTO.PasswordQuestion;
import util.ASTKLogManager;
import util.DBConnectionPool;

public class SystemManager {
	DBConnectionPool connPool;
	
	public SystemManager(DBConnectionPool connPool) {
		this.connPool = connPool;
	}
	
	public boolean insertClientPasswordQuestion(String question) {
		String sql_table
			= " AD_SYSTEM_PASSWORD_QUESTION ";
		String sql_targetColumn
			= " Q_NO, QUESTION ";
		String sql_values
			= " SQ_QNO.NEXTVAL, '"+ question +"'";
		return new DBManager(connPool).insertSQL(sql_table, sql_targetColumn, sql_values);
	}
	
	public HashMap<String ,Object> selectPasswordQuestion_all() {
		String sql_where
			= " ORDER BY Q_NO ASC";
		return this.base_selectPasswordQuestion(sql_where, 0);
	}
	
	private HashMap<String, Object> base_selectPasswordQuestion(String sql_where, int result) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			try {
				String sql_selectPasswordQuestion = "";
				
				sql_selectPasswordQuestion
					+= " SELECT * FROM AD_SYSTEM_PASSWORD_QUESTION " + sql_where;
				rs = st.executeQuery(sql_selectPasswordQuestion);
				rs.last();
				if (rs.getRow() == 0) { map.put("result", "N"); return map; }
				switch (result) {
				case 0 : if (rs.getRow() < 0) { map.put("result", "F"); return map; } break;
				default : if (rs.getRow() != result) { map.put("result", "F"); return map; } break;
				}
				
				rs.beforeFirst();
				if (result == 1) {
					rs.next(); 
					map.put("PQ", new PasswordQuestion().setAll(rs));
				} else {
					ArrayList<PasswordQuestion> list = new ArrayList<PasswordQuestion>();
					while (rs.next()) { list.add(new PasswordQuestion().setAll(rs)); }
					map.put("PQList", list);
				}
				map.put("result", "T");
				
			} catch (Exception ex) {
				System.out.println("log : try-catch..."+ ASTKLogManager.getMethodName_withClassName() + "\n"+ex);
				map.put("result", "E");
				
			} 
			
		} catch (Exception ex) {
			System.out.println("log : try-catch..."+ ASTKLogManager.getMethodName_withClassName() + "\n"+ex);
			map.put("result", "E");
			
		} finally {
			try { if (rs!=null) rs.close(); } catch (Exception ex) { }
			try { if (st!=null) st.close(); } catch (Exception ex) { }
			try { if (conn!=null) conn.close(); } catch (Exception ex) { }
			
		}
		
		return map;
		
	}

}
