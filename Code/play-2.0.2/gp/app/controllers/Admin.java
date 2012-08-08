package controllers;

import java.util.concurrent.TimeUnit;
import play.mvc.*;
import play.data.*;
import models.*;


public class Admin extends Controller {
	
	public static boolean isBaseUpdaterThreadRun;
	private static boolean isCacheUpdaterThreadRun;
	
	private static class BaseUpdater implements Runnable {
        public void run() {
            while (isBaseUpdaterThreadRun) {
                try {
                    UpdateControl.Start();
                    TimeUnit.SECONDS.sleep(10);
                }
                catch (InterruptedException ex) {}
            }
        }
    }
    
    private static class CacheUpdater implements Runnable {
        public void run() {
            while (isCacheUpdaterThreadRun) {
                try {
                	for (Group currentGroup: Group.all()) {
                    	if (!isCacheUpdaterThreadRun)
                    		break;
                    	
                    	System.out.println("Cache updating: start of updating group with id: " + currentGroup.id);
                    	TimeClass.printlnReadyCurrentTime();
                    	
	            		Classifier.getGpmForGroup(currentGroup.id);
	            		
	            		System.out.println("Updating group with id: " + currentGroup.id + " was finished.");
	            		TimeClass.printlnReadyCurrentTime();
	            		
	            		TimeUnit.SECONDS.sleep(5);
                    }
                }
                catch (InterruptedException ex) {}
            }
        }
    }
    
	// update's parameters.
	public static Parameters updateParameters = new Parameters();
	
	//MAIN PAGE
	public static Result main() {
        return ok(views.html.admin.render(
        		Group.size(), 
        		Word.size(), 
        		Link.size(), 
        		GPM.size(),
        		Profile.size(), 
        		Post.size()));
    }
	
	public static Result updateControl() {
		return ok(views.html.updateControl.render(
        		GPM.size(),
        		Profile.size(), 
        		Post.size(),
        		isBaseUpdaterThreadRun,
        		form(Parameters.class).fill(updateParameters),
        		isCacheUpdaterThreadRun));
    }
	
	public static Result startUpdate() {
		if (!isBaseUpdaterThreadRun) {
            Thread worker = new Thread(new BaseUpdater());
            worker.setDaemon(true);
            isBaseUpdaterThreadRun = true;
            worker.start();

            System.out.println(" ---=== DATABASE UPDATE STARTED ===---");
        }
		else {
			isBaseUpdaterThreadRun = false;
			
			System.out.println(" ---=== DATABASE UPDATE STOPPED ===---");
		}
		
        return redirect(routes.Admin.updateControl());
    }
	
	public static Result startCacheUpdate() {
		if (!isCacheUpdaterThreadRun) {
			Thread cacheUpdaterThread = new Thread(new CacheUpdater());
		    cacheUpdaterThread.setDaemon(true);
		    isCacheUpdaterThreadRun = true;
		    cacheUpdaterThread.start();

            System.out.println(" ---=== CACHE UPDATE STARTED ===---");
        }
		else {
			isCacheUpdaterThreadRun = false;
			
			System.out.println(" ---=== CACHE UPDATE STOPPED ===---");
		}
		
        return redirect(routes.Admin.updateControl());
    }
	
	public static Result setParameters() {
		Form<Parameters> filledForm = form(Parameters.class).bindFromRequest();
        if(filledForm.hasErrors())
            return badRequest(views.html.updateControl.render(
            		GPM.size(),
            		Profile.size(), 
            		Post.size(),
            		isBaseUpdaterThreadRun,
            		filledForm,
            		isCacheUpdaterThreadRun));
		
        Parameters params = filledForm.get();
        int sumParameters = params.percentGetNew + params.percentUpdatePosts + params.percentUpdateProfiles;
        if (sumParameters == 0) {
        	updateParameters.percentUpdatePosts = updateParameters.percentUpdateProfiles = 33;
        	updateParameters.percentGetNew = 34;
        }
        else if (sumParameters == 100)
        	updateParameters = params;
        else {
        	updateParameters.percentUpdatePosts = params.percentUpdatePosts * 100 / sumParameters;
        	updateParameters.percentUpdateProfiles = params.percentUpdateProfiles * 100 / sumParameters;
        	updateParameters.percentGetNew = 100 - (updateParameters.percentUpdatePosts + updateParameters.percentUpdateProfiles);
        }
        
        return redirect(routes.Admin.updateControl());
    }
}
