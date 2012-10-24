package models;

import java.util.*;

import com.avaje.ebean.*;

public class Classifier {

	public static void getGpmForGroup(Long groupId) {
		String request = "CALL classification (:group_id)";
		
		List<SqlRow> res = Ebean.createSqlQuery(request).setParameter("group_id", groupId).findList();
		CacheClassifier.update(groupId, res);
	}
}
