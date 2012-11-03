package models;

import java.util.*;

import com.avaje.ebean.*;

public class Top100 {
	public static List <GpmForOutput> getTopGpms () {
		String request = "select final_result.gpm_id, final_result.rayting from " +
				"( " +
				"select all_gpms_and_rayting.gpm_id as gpm_id, all_gpms_and_rayting.rayting as rayting from " +
				"( " +
						"select last_profiles.gpm_id as gpm_id, rayting/count_posts as rayting from " +
						"( " +
							"SELECT gpm as gpm_id, count(gpm) as count_posts " +
							"FROM post  " +
							"group by post.gpm " +
						") last_profiles " +
						"left join " +
						"( " +
							"select post.gpm as gpm_id, (sum(nPlusOne)*1 + sum(nComment)*5 + sum(nResharers)*20) as rayting  " +
							"from post " +
							"group by post.gpm " +
						") as post_rayting " +
						"on last_profiles.gpm_id = post_rayting.gpm_id " +
						"order by rayting desc " +
				") as all_gpms_and_rayting " +
				"left join blacklist " +
				"on blacklist.id = all_gpms_and_rayting.gpm_id " +
			"where blacklist.id is null " +
			") as final_result " +
			"limit 0, 100; ";
		List <GpmForOutput> gpms = new ArrayList<GpmForOutput>();
		List<SqlRow> res = Ebean.createSqlQuery(request).findList();
		int i = 1;
		for(SqlRow row: res) {
	    	Profile prof = Profile.lastProfileByGpmId(row.getLong("gpm_id"));
	    	if (prof != null) {
	    		gpms.add(new GpmForOutput(
	    				i++, 
	    				prof.gpm.idGpm, 
	    				prof.name, 
	    				prof.image, 
	    				(prof.gender == null)? null : prof.gender.value,
	    				(prof.relationshipStatus == null)? null : prof.relationshipStatus.status,
	    				prof.nFollowers));
	    	}
	    }
		return gpms;
	}
}
