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

    static List <GroupForOutput> groupsForOutput = new ArrayList <GroupForOutput> ();
    
    private static boolean isCacheUpdaterThreadRun;
    
    private static class CacheUpdater implements Runnable {
        public void run() {
            while (true) {
                try {
                    //if cache is empty
                	if (groupsForOutput.size() == 0) {
                		
                		//quick initialization of cache
                		//System.out.println("Quick initialization of cache has started.");
                		//TimeClass.printlnReadyCurrentTime();
                		
                		List <Group> allGroups = Group.all();
                    	for (Group currentGroup: allGroups) {
                    		//getting profile of Yuri Vashchenko
                    		Profile prof = Profile.lastProfileByGpmId((long) 1);
                    		//creating of fake query
                    		List <GpmForOutput> fakeQueryResult = new ArrayList<GpmForOutput>();
                    		fakeQueryResult.add(new GpmForOutput(
            	    				1, 
            	    				prof.gpm.idGpm, 
            	    				prof.name, 
            	    				prof.image, 
            	    				(prof.gender == null)? null : prof.gender.value,
            	    				(prof.relationshipStatus == null)? null : prof.relationshipStatus.status,
            	    				prof.nFollowers));
                    		//creating element for cache array: groupsForOutput 
                    		GroupForOutput fakeGpms = new GroupForOutput (currentGroup.id, fakeQueryResult);
                    		groupsForOutput.add(fakeGpms);
                    	}
                    	//System.out.println("Quick initialization of cache has finished.");
                		//TimeClass.printlnReadyCurrentTime();
                    }
                    else {
                    	List <Group> allGroups = Group.all();
                        for (Group currentGroup: allGroups) {
                        	
                        	//System.out.println("Cache updating: start of updating group with id: " + currentGroup.id);
                        	//TimeClass.printlnReadyCurrentTime();
                        	
    	            		List <GpmForOutput> gpms = Classifier.getGpmForGroup(currentGroup.id);
    	            		//searching index of element with current group in groupsForOutput array
    	            		int currentGroupId = 0;
    	            		while (groupsForOutput.get(currentGroupId).groupId != currentGroup.id) {
    	            			currentGroupId++;
    	            		}
    	            		//creating new element, which we must add in general array
    	            		GroupForOutput currentGroupForOutput = new GroupForOutput (currentGroup.id, gpms);
    	            		groupsForOutput.set(currentGroupId, currentGroupForOutput);
    	            		
    	            		//System.out.println("Updating group with id: " + currentGroup.id + "was finished.");
    	            		//TimeClass.printlnReadyCurrentTime();
    	            		
    	            		TimeUnit.SECONDS.sleep(5);
                        }
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
