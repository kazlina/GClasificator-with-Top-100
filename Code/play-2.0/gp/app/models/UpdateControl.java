package models;

import java.util.*;
import java.util.concurrent.TimeUnit;
import com.avaje.ebean.SqlRow;

public class UpdateControl {

    public static void Start() throws InterruptedException {
        
        int updateCountNewGPM = 60;
        int updateCountPostGPM = 20;
        int updateCountProfileGPM = 20;
        
        List<NewGPM> newGpmForGet = NewGPM.get(updateCountNewGPM);
        for (NewGPM element: newGpmForGet) {    
            DataExtraction.newGPM(element.idGpm);
        }
        TimeUnit.SECONDS.sleep(1);
        
        List<SqlRow> gpmForUpdatePost = GPM.getIdGpmByLastPosts(updateCountPostGPM);
        for (SqlRow element: gpmForUpdatePost) {
            Long id = element.getLong("gpm");
            GPM gpm = GPM.findById(id);
            if (gpm != null) {
                DataExtraction.updateActivity(gpm, updateCountNewGPM);
            }
        }
        TimeUnit.SECONDS.sleep(1);
        
        List<SqlRow> gpmForUpdateProfile = GPM.getIdGpmByLastProfile(updateCountProfileGPM);
        for (SqlRow element: gpmForUpdateProfile) {
            Long id = element.getLong("gpm");
            GPM gpm = GPM.findById(id);
            if (gpm != null) {
                DataExtraction.updateProfile(gpm);
            }
        }
        TimeUnit.SECONDS.sleep(1);
    }
}
