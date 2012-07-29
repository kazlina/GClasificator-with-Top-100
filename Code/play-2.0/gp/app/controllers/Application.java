package controllers;

//import java.io.*;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
import java.util.*;
import java.lang.InterruptedException;
import java.util.concurrent.TimeUnit;
import play.data.Form;
import play.mvc.*;
import models.*;

public class Application extends Controller {

    private static boolean isCacheUpdaterThreadRun;
    
    private static class CacheUpdater implements Runnable {
        public void run() {
            while (true) {
                try {
                	for (Group currentGroup: Group.all()) {
                    	
                    	System.out.println("Cache updating: start of updating group with id: " + currentGroup.id);
                    	TimeClass.printlnReadyCurrentTime();
                    	
	            		Classifier.getGpmForGroup(currentGroup.id);
	            		
	            		System.out.println("Updating group with id: " + currentGroup.id + "was finished.");
	            		TimeClass.printlnReadyCurrentTime();
	            		
	            		TimeUnit.SECONDS.sleep(5);
                    }
                }
                catch (InterruptedException ex) {}
            }
        }
    }
    
    public static Result index() {
        if (!isCacheUpdaterThreadRun) {
			    //running of cache updating
			    Thread cacheUpdaterThread = new Thread(new CacheUpdater());
			    cacheUpdaterThread.setDaemon(true);
			    isCacheUpdaterThreadRun = true;
			    cacheUpdaterThread.start();
        }
        
        return ok(views.html.index.render(Group.all()));
    }
    
    public static Result viewGroup(Long idGroup) {
        List <CacheClassifier> gpms = CacheClassifier.findByGroup(idGroup);
        return ok(views.html.usergroup.render(
        		gpms, 
        		Group.all(), 
        		Group.findById(idGroup),
        		form(GPM.class)));
    }
    
    public static Result indexGpm(Long id) {
    	Form<GPM> filledForm = form(GPM.class).bindFromRequest();
        if(filledForm.hasErrors()) {
        	List <CacheClassifier> gpms = CacheClassifier.findByGroup(id);
            return badRequest(views.html.usergroup.render(
            		gpms, 
            		Group.all(), 
            		Group.findById(id),
            		filledForm));
        } 
        
    	NewGPM.add(filledForm.get().idGpm);
    	
    	return redirect(routes.Application.viewGroup(id));
    }
    
    public static Result ind() {
    	return ok("all good");
    }
}
