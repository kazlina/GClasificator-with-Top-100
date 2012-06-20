package controllers;
import java.util.*;
import java.lang.InterruptedException;
import java.util.concurrent.TimeUnit;
import play.mvc.*;
import models.*;
import java.io.IOException;

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
        }
        
        return ok(views.html.index.render(Group.all()));
    }
    
    public static Result viewGroup(Long idGroup) {
        List <GpmForOutput> gpms = Classifier.getGpmForGroup(idGroup);
        return ok(views.html.usergroup.render(gpms, Group.all(), Group.findById(idGroup).name));
    }
    
    public static Result ind() {
        return ok("all good");
    }
}
