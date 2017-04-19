package listener;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import DAO.ADManager;
import DAO.ClientManager;
import DAO.ETCManager;
import DAO.FunctionManager;
import DAO.PointManager;
import DAO.SystemManager;
import DAO.UserManager;
import util.DBConnectionPool;

public class ContextLoaderListener  implements ServletContextListener {
	DBConnectionPool connPool;
	@Override
	public void contextDestroyed(ServletContextEvent ev) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent ev) {
		System.out.println("Server Start...");
		try {
			ServletContext sc = ev.getServletContext();
			
			connPool = new DBConnectionPool(
					sc.getInitParameter("driver"),
					sc.getInitParameter("url"),
					sc.getInitParameter("oid"),
					sc.getInitParameter("opw")
			);
			
			HashMap<String, String> localMap = new HashMap<String,String>();
			localMap.put("서울", "SEOUL");
			localMap.put("대구", "DEAGU");
			localMap.put("대전", "DEAGEON");
			localMap.put("광주", "GWANGJU");
			localMap.put("부산", "BUSAN");
			localMap.put("울산", "ULSAN");
			localMap.put("인천", "INCHEON");
			sc.setAttribute("localMap", localMap);

<<<<<<< HEAD
=======
			final String INDEX_SERVLET_PATH = "index.ad";
			sc.setAttribute("INDEX_SERVLET_PATH", INDEX_SERVLET_PATH);
			final String ERROR_SERVLET_PATH = "error.ad";
			sc.setAttribute("ERROR_SERVLET_PATH", ERROR_SERVLET_PATH);
			
>>>>>>> branch 'solpi' of https://github.com/AsteriskTalk/svn_adPage1704
			final String ASTK_URL = "www.asterisktalk.net";
			sc.setAttribute("ASTK_URL", ASTK_URL);
			final String INDEX_PAGE = "index.jsp";
			sc.setAttribute("INDEX_PAGE", INDEX_PAGE);
			final String DEFAULT_IMG_NAME = "default.png";
			sc.setAttribute("DEFAULT_IMG_NAME", DEFAULT_IMG_NAME);
			final String DEFAULT_FOLDER_URL = "korea.asterisksoft.net:12249/ad";
			sc.setAttribute("DEFAULT_FOLDER_URL", DEFAULT_FOLDER_URL);
			final String DEFAULT_FOLDER_URI = "C:/inetpub/wwwroot/ad";
			sc.setAttribute("DEFAULT_FOLDER_URI", DEFAULT_FOLDER_URI);
			final String ERROR_PAGE = "common/error.jsp";
			sc.setAttribute("ERROR_PAGE", ERROR_PAGE);
			
			final ClientManager cm = new ClientManager(connPool);
			sc.setAttribute("cm", cm);
			final ADManager am = new ADManager(connPool);
			sc.setAttribute("am", am);
			final PointManager pm = new PointManager(connPool);
			sc.setAttribute("pm", pm);
			final FunctionManager fm = new FunctionManager(connPool);
			sc.setAttribute("fm", fm);
			final ETCManager etc = new ETCManager(connPool);
			sc.setAttribute("etc", etc);
			final UserManager um = new UserManager(connPool);
			sc.setAttribute("um", um);
			final SystemManager sm = new SystemManager(connPool);
			sc.setAttribute("sm", sm);
			
			System.out.println("log : Server Start..complete");
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

}
