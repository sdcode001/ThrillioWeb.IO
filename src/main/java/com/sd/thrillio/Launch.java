package com.sd.thrillio;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.sd.thrillio.backgroundjobs.WebpageDownloaderTask;


public class Launch implements ServletContextListener{
	
	private Thread dowloaderThread = null;
	
	public void contextInitialized(ServletContextEvent sce) {	
	   //launch background jobs
       dowloaderThread = runDownloaderJob();
       
	}
	
	
	 public void contextDestroyed(javax.servlet.ServletContextEvent sce) {
		 //stopping the downloader thread so that Executer can be stopped.
		 if(dowloaderThread!=null) {
			 dowloaderThread.interrupt();
		 }
		 
	 }
	
	


	private static Thread runDownloaderJob() {
		WebpageDownloaderTask task = new WebpageDownloaderTask(true);
		Thread thread = new Thread(task);
		thread.start();
		return thread;
	}
	

}
