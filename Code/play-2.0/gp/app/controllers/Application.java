package controllers;

import java.io.*;
import java.util.*;
import java.lang.InterruptedException;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import play.data.Form;
import play.mvc.*;
import models.*;

public class Application extends Controller {

    private static class BackgroundProcess implements Runnable {
        public void run() {
            while (true) {
                try {
                    UpdateControl.Start();
                    TimeUnit.SECONDS.sleep(10);
                }
                catch (InterruptedException ex) {}
            }
        }
    }
    
    private static boolean threadRun;
    
    public static Result index() {
        if (!threadRun) {
            Thread worker = new Thread(new BackgroundProcess());
            worker.setDaemon(true);
            threadRun = true;
            worker.start();

            System.out.println(" ---=== UPDATE STARTED ===---");
            
            cacheUpdate();
        }
        
        return ok(views.html.index.render(Group.all()));
    }
    
    static List <AllGroups> groupsForOutput = new ArrayList <AllGroups> ();
    
    private static boolean isCacheUpdaterThreadRun;
    
    private static class CacheUpdater implements Runnable {
        public void run() {
            while (true) {
                try {
                    //if cache is empty
                	if (groupsForOutput.size() == 0) {
                		System.out.println("Cache updating: cache initialize is starting.");
                    	List <Group> allGroups = Group.all();
                    	for (Group currentGroup: allGroups) {
                    		System.out.println("Cache updating: start of initialaze group with id: " + currentGroup.id);
                    		List <GpmForOutput> gpms = Classifier.getGpmForGroup(currentGroup.id);
                    		AllGroups currentGroupForOutput = new AllGroups (currentGroup.id, gpms);
                    		groupsForOutput.add(currentGroupForOutput);
                    		System.out.println("Was finish of initialize group with id: " + currentGroup.id);
                    		TimeUnit.SECONDS.sleep(5);
                    	}
                    	System.out.println("Cache updating: cache initialize succesfully finish.");
                    }
                    else {
                    	List <Group> allGroups = Group.all();
                        for (Group currentGroup: allGroups) {
                        	System.out.println("Cache updating: start of updating group with id: " + currentGroup.id);
    	            		List <GpmForOutput> gpms = Classifier.getGpmForGroup(currentGroup.id);
    	            		//searching index of element with current group in groupsForOutput array
    	            		int currentGroupId = 0;
    	            		while (groupsForOutput.get(currentGroupId).groupId != currentGroup.id) {
    	            			currentGroupId++;
    	            		}
    	            		//creating new element, which we must add in general array
    	            		AllGroups currentGroupForOutput = new AllGroups (currentGroup.id, gpms);
    	            		groupsForOutput.set(currentGroupId, currentGroupForOutput);
    	            		System.out.println("Was finish of updating group with id: " + currentGroup.id);
    	            		TimeUnit.SECONDS.sleep(5);
                        }
                    }
                }
                catch (InterruptedException ex) {}
            }
        }
    }
    
    public static void cacheUpdate() {
        if (!isCacheUpdaterThreadRun) {
			    //running of cache updating
			    Thread cacheUpdaterThread = new Thread(new CacheUpdater());
			    cacheUpdaterThread.setDaemon(true);
			    isCacheUpdaterThreadRun = true;
			    cacheUpdaterThread.start();
        }
    }
    
    public static Result viewGroup(Long idGroup) {
        //List <GpmForOutput> gpms = Classifier.getGpmForGroup(idGroup);
    	int currentGroupId = 0;
		while (groupsForOutput.get(currentGroupId).groupId != idGroup) {
			currentGroupId++;
		}
    	List <GpmForOutput> gpms = groupsForOutput.get(currentGroupId).gpms;
        return ok(views.html.usergroup.render(
        		gpms, 
        		Group.all(), 
        		Group.findById(idGroup),
        		form(GPM.class)));
    }
    
    public static Result indexGpm(Long id) {
    	Form<GPM> filledForm = form(GPM.class).bindFromRequest();
        if(filledForm.hasErrors()) {
        	int currentGroupId = 0;
    		while (groupsForOutput.get(currentGroupId).groupId != id) {
    			currentGroupId++;
    		}
        	List <GpmForOutput> gpms = groupsForOutput.get(currentGroupId).gpms;
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
