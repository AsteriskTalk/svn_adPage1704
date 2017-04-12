package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import util.ADTools;
import util.ASTKLogManager;
import util.CharManager;
import util.DBConnectionPool;

public class DBManager {
	DBConnectionPool connPool;
	public DBManager(DBConnectionPool connPool) {
		this.connPool = connPool;
	}
		
	public boolean insertSQL(String sql_table, String sql_targetColumn, String sql_values) {
		Connection conn = null;
		Statement st = null;
		int rs = 0;
		final long NOW = System.currentTimeMillis();
		
		String sql_insert = "";

		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_insert
				= " INSERT INTO " + sql_table + "(" + sql_targetColumn + ") VALUES (" + sql_values + ") ";
			
			rs = st.executeUpdate(sql_insert);
//			System.out.println("log : DB time - " + (System.currentTimeMillis() - NOW) );
			if (rs != 1) { 
				conn.rollback();
				return false; 
			}
			conn.commit();
			return true;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch..insertSQL\n" + ex);
			return false;
			
		} finally {
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
	
	public boolean updateSQL(String sql_table, String sql_set, String sql_where, int result) {
		Connection conn = null;
		Statement st = null;
		int rs = 0;
		
		String sql_update = "";

		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_update
				= " UPDATE " + sql_table + " " + sql_set + " " + sql_where;
			
			rs = st.executeUpdate(sql_update);
			if (rs != result) { 
				conn.rollback();
				return false; 
			}
			conn.commit();
			return true;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch..updateSQL\n" + ex);
			return false;
			
		} finally {
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
	
	public boolean deleteSQL(String sql_table, String sql_where, int result) {
		Connection conn = null;
		Statement st = null;
		int rs = 0;
		
		String sql_delete = "";

		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_delete
				= " DELETE " + sql_table + " " + sql_where;
			
			rs = st.executeUpdate(sql_delete);
			if (rs != result) { 
				conn.rollback();
				return false; 
			}
			conn.commit();
			return true;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch..deleteSQL\n" + ex);
			return false;
			
		} finally {
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
	
	public long insertOTPQuery(String SQL){
		Connection conn = null;
		Statement st = null;
		int rs = 0;
		
		final long OTP = ADTools.createOTP();
		SQL = CharManager.beforeOracle_withSpace(SQL);
		
		String sql_insertOTPQuery = "";
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_insertOTPQuery
				= " INSERT INTO ASTK_OTP_QUERY ";
			sql_insertOTPQuery
				+= " (OTP, SQL, CREATE_DATE) ";
			sql_insertOTPQuery
				+= " VALUES ";
			sql_insertOTPQuery
				+= " ("+ OTP +",'"+ SQL +"',"+ System.currentTimeMillis() +")";
			rs = st.executeUpdate(sql_insertOTPQuery);
			if (rs != 1) { conn.rollback(); return -1; }
			
			conn.commit();
			return OTP;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName(1) + "\n"+ ex);
			return -1;
			
		} finally {
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
			
		}
		
	}
	
	public boolean doOTPSQL_update(long OTP) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int rs2 = 0;

		String sql_select = "";
		String sql_OTPSQL_update = "";
		String sql_updateOTPSQL = "";
		
		try {
			conn = connPool.getConn();
			conn.setAutoCommit(false);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql_select
				+= " SELECT SQL FROM ASTK_OTP_QUERY ";
			sql_select
				+= " WHERE OTP="+OTP;
			rs = st.executeQuery(sql_select);
			rs.last();
			if ( rs.getRow() != 1) { return false; }
			rs.beforeFirst();
			
			sql_OTPSQL_update = rs.getString("SQL");
			rs.close();
			
			rs2 = st.executeUpdate(sql_OTPSQL_update);
			if ( rs2 != 1) { conn.rollback(); return false; }
			
			sql_updateOTPSQL
				= " UPDATE ASTK_OTP_QUERY ";
			sql_updateOTPSQL
				+= " SET IS_USED='T' ";
			sql_updateOTPSQL
				+= " WHERE OTP="+ OTP +" ";
			rs2 = st.executeUpdate(sql_updateOTPSQL);
			if ( rs2 != 1) { conn.rollback(); return false; }
			
			conn.commit();
			return true;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName(1) +"\n"+ex);
			return false;
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (st != null) st.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
}
