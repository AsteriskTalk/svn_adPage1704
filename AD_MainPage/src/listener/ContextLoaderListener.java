package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent ev) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent ev) {
		
		System.out.println("Server Start...");
		
		try{
		ServletContext sc = ev.getServletContext();
		System.out.println("log : Server Start..complete");
		}catch(Throwable ex){
			ex.printStackTrace();
			
		}
	}
	


}
