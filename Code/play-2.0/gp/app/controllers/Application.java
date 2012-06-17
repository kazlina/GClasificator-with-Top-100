package controllers;

import java.util.*;
import java.util.concurrent.TimeUnit;
//import java.lang.InterruptedException;
import java.lang.*;
import play.mvc.*;
import models.*;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Application extends Controller {

	private static class BackgroundProcess implements Runnable {
		public void run() {
			try {
	    		while (true)
	    			UpdateControl.Start();
	    	}
	    	catch (InterruptedException ex) {}
		}
	}
	
	private static boolean threadRun;
	
	public static Result ind() throws IOException {
		TempProfile temp = GAPI.getProfile("101793532287583914396");
	    return ok(temp.displayName);
	}

    public static Result index() {
    	if (!threadRun) {
    		Thread worker = new Thread(new BackgroundProcess());
    		worker.setDaemon(true);
    		threadRun = true;
    		worker.start();
    	}
    	
    	return ok(views.html.index.render(Group.all()));
    }
    
    public static Result viewGroup(Long idGroup) {
    	List <Profile> gpms = Classifier.getGpmForGroup(idGroup);
    	return ok(views.html.usergroup.render(gpms, Group.all(), Group.findById(idGroup).name));
    }
}
