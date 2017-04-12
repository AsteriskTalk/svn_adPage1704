package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import DAO.ADManager;
import DAO.ClientManager;
import DAO.ETCManager;
import DAO.FunctionManager;
import DAO.PointManager;
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
			
			System.out.println("log : Server Start..complete");
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

}
