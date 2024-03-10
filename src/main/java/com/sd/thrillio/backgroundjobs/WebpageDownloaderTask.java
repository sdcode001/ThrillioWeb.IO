package com.sd.thrillio.backgroundjobs;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import com.sd.thrillio.daos.BookmarkDao;
import com.sd.thrillio.entities.WebLink;
import com.sd.thrillio.util.HttpConnect;
import com.sd.thrillio.util.IOUtil;



public class WebpageDownloaderTask implements Runnable{
    private boolean downloadAll = false;
    private static BookmarkDao dao = new BookmarkDao();
    private static final long TIME_FRAME = 3000000000L; //3 seconds
    
    private ExecutorService downloadExecutor = Executors.newFixedThreadPool(5);
	
	public WebpageDownloaderTask(boolean downloadAll) {
		this.downloadAll=downloadAll;
    }
	
	private static class Downloader<T extends WebLink> implements Callable<T>{
        private T weblink;
        
        public Downloader(T weblink) {
        	this.weblink=weblink;
        }
		
		@Override
		public T call() {
			try {
				if(!weblink.getUrl().endsWith(".pdf")) {
					weblink.setDownloadStatus(WebLink.DownloadStatus.FAILED);
					String responcePage = HttpConnect.download(weblink.getUrl());
					weblink.setHtmlPage(responcePage);
				}
				else {
					weblink.setDownloadStatus(WebLink.DownloadStatus.NOT_ELIGIBLE);
				}
				
			} catch (MalformedURLException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return weblink;
		}
	}
	
	private List<WebLink> getWeblinks(){
		List<WebLink> result = null;
		if(downloadAll) {
			result = dao.getAllWebLinks();
			downloadAll = false;
		}
		else {
			result = dao.getWebLinks(WebLink.DownloadStatus.NOT_ATTEMPTED);
		}
		
		return result;
	}
	
	private List<Downloader<WebLink>> getDownloadTasks(List<WebLink> webLinks) {
		List<Downloader<WebLink>> result = new ArrayList<>();
		for(WebLink link:webLinks) {
			result.add(new Downloader<WebLink>(link));
		}
		return result;
	}
	
	
	private void download(List<WebLink> webLinks) {
		List<Downloader<WebLink>> tasks = getDownloadTasks(webLinks);
		List<Future<WebLink>> futures = new ArrayList<>();
	    
		try {
			futures = downloadExecutor.invokeAll(tasks, TIME_FRAME, TimeUnit.NANOSECONDS);	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(Future<WebLink> future: futures) {
			try {
				if(!future.isCancelled()) {
				   WebLink weblink = future.get();
				   String htmlPage = weblink.getHtmlPage();
				   if(htmlPage != null) {
					   IOUtil.write(htmlPage, weblink.getId());
					   weblink.setDownloadStatus(WebLink.DownloadStatus.SUCCESS);
					   System.out.println("Download Success: " + weblink.getUrl());
				   }
				   else {
					   System.out.println("Webpage not downloaded: " + weblink.getUrl());
				   }
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void run() {
		
		while(!Thread.currentThread().isInterrupted()) {
			//get weblinks
			List<WebLink> webLinks = getWeblinks();
			
			//download concurrently
			if(webLinks.size()>0) {
	            download(webLinks);
			}
			else {
				System.out.println("No new web-links to download!");
			}
			
			//wait for next period
			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		downloadExecutor.shutdown();
	}

	
   
}
