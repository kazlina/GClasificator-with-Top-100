package models;

import java.util.*;
import java.util.concurrent.TimeUnit;
import com.avaje.ebean.SqlRow;

public class UpdateControl {

	public static void Start() throws InterruptedException {
		float countPostGPM = 30;
		float countProfileGPM = 30;
		float countNewGPM = 100 - countPostGPM - countProfileGPM;
		
		int updateCountNewGPM = (int) (NewGPM.size() / 100 * countNewGPM);
		int updateCountPostGPM = (int) (GPM.size() / 100 * countPostGPM);
		int updateCountProfileGPM = (int) (GPM.size() / 100 * countProfileGPM);
		
		List<NewGPM> newGpmForGet = NewGPM.get(updateCountNewGPM);
		for (NewGPM element: newGpmForGet) {	
			DataExtraction.newGPM(element.idGpm);
			TimeUnit.SECONDS.sleep(1);
		}
		
		List<SqlRow> gpmForUpdatePost = GPM.getIdGpmByLastPosts(updateCountPostGPM);
		for (SqlRow element: gpmForUpdatePost) {
			Long id = element.getLong("gpm");
			GPM gpm = GPM.findById(id);
			if (gpm != null) {
				DataExtraction.updateActivity(gpm, updateCountNewGPM);
				TimeUnit.SECONDS.sleep(1);
			}
		}
		
		List<SqlRow> gpmForUpdateProfile = GPM.getIdGpmByLastProfile(updateCountProfileGPM);
		for (SqlRow element: gpmForUpdateProfile) {
			Long id = element.getLong("gpm");
			GPM gpm = GPM.findById(id);
			if (gpm != null) {
				DataExtraction.updateProfile(gpm);
				TimeUnit.SECONDS.sleep(1);
			}
		}
	}
}
