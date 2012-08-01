package models;

import java.util.*;
import java.util.concurrent.TimeUnit;
import com.avaje.ebean.SqlRow;

import controllers.Admin;

public class UpdateControl {

    public static void Start() throws InterruptedException {
        
    	List<NewGPM> newGpmForGet = NewGPM.get(Admin.updateParameters.percentGetNew);
        for (NewGPM element: newGpmForGet) {
        	if (!Admin.isBaseUpdaterThreadRun)
        		return;
            DataExtraction.newGPM(element.idGpm);
        }
        TimeUnit.SECONDS.sleep(1);
                
        List<SqlRow> gpmForUpdateProfile = GPM.getIdGpmByLastProfile(Admin.updateParameters.percentUpdateProfiles);
        for (SqlRow element: gpmForUpdateProfile) {
        	if (!Admin.isBaseUpdaterThreadRun)
        		return;
            Long id = element.getLong("gpm");
            GPM gpm = GPM.findById(id);
            if (gpm != null) {
                DataExtraction.updateProfile(gpm);
            }
        }
        TimeUnit.SECONDS.sleep(1);

        List<SqlRow> gpmForUpdatePost = GPM.getIdGpmByLastPosts(Admin.updateParameters.percentUpdatePosts);
        for (SqlRow element: gpmForUpdatePost) {
        	if (!Admin.isBaseUpdaterThreadRun)
        		return;
            Long id = element.getLong("gpm");
            GPM gpm = GPM.findById(id);
            if (gpm != null) {
                DataExtraction.updateActivity(gpm, 100);
            }
        }
        TimeUnit.SECONDS.sleep(1);
    }
}
