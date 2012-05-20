package models;

import java.util.*;
import java.util.concurrent.TimeUnit;
import com.avaje.ebean.SqlRow;

public class UpdateControl {

	public static void Start() throws Exception {
		float countPostGPM = 30;
		float countProfileGPM = 30;
		float countNewGPM = 100 - countPostGPM - countProfileGPM;
		
		int updateCountNewGPM = (int) (NewGPM.size() / 100 * countNewGPM);
		int updateCountPostGPM = (int) (GPM.size() / 100 * countPostGPM);
		int updateCountProfileGPM = (int) (GPM.size() / 100 * countProfileGPM);
		
		List<NewGPM> newGpmForGet = NewGPM.get(updateCountNewGPM);
		for (int i = 0; i < newGpmForGet.size(); i ++) {
			DataExtraction.newGPM(newGpmForGet.get(i).idGpm);
			TimeUnit.SECONDS.sleep(1);
		}
		
		List<SqlRow> gpmForUpdatePost = GPM.getIdGpmByLastPosts(updateCountPostGPM);
		for (int i = 0; i < gpmForUpdatePost.size(); i ++) {
			Long id = gpmForUpdatePost.get(i).getLong("gpm");
			GPM gpm = GPM.findById(id);
			if (gpm != null) {
				DataExtraction.updateActivity(gpm, updateCountNewGPM);
				TimeUnit.SECONDS.sleep(1);
			}
		}
		
		List<SqlRow> gpmForUpdateProfile = GPM.getIdGpmByLastProfile(updateCountProfileGPM);
		for (int i = 0; i < gpmForUpdateProfile.size(); i ++) {
			Long id = gpmForUpdateProfile.get(i).getLong("gpm");
			GPM gpm = GPM.findById(id);
			if (gpm != null) {
				DataExtraction.updateProfile(gpm);
				TimeUnit.SECONDS.sleep(1);
			}
		}
	}
}
