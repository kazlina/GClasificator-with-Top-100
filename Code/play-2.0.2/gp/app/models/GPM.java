package models;

import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "GPM")
public class GPM extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Constraints.MinLength(21)
	@Constraints.MaxLength(21)
	@Constraints.Required
	@Constraints.Pattern("[0-9]+")
	@Column(name = "idGpm", length = 21, unique = true, nullable = false)
	public String idGpm;

	@OneToMany(mappedBy = "gpm")
	public List<Profile> profile;

	@OneToMany(mappedBy = "gpm")
	public List<Post> post;

	@OneToMany(mappedBy = "gpm")
	public List<AddedByAdmin> addedByAdmin;

	public String toString(){
		return this.idGpm;
	}

	private GPM(String id) {
		this.idGpm = id;
	}
	
	private static Model.Finder<Long, GPM> find = new Model.Finder<Long, GPM>(Long.class, GPM.class);
	
	public static List<GPM> all() {
		return find.orderBy("idGpm").findList();
	}

	public static List<String> allInString() {
		List <String> gpmsInString = new ArrayList<String>();
		for (GPM gpm: all()) {
			gpmsInString.add(gpm.idGpm);
		}
		return gpmsInString;
	}

	public static GPM findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}
	
	public static GPM findByIdGpm(String idGpm) {
		return find.where().eq("idGpm", idGpm).findUnique();
	}
	
	public static int size() {
		return find.findRowCount();
	}
	
	public static GPM add(String idGpm) {
		GPM newGpm = null;
		
		Ebean.beginTransaction();
		try {
			GPM searchGpm = findByIdGpm(idGpm);
			if (searchGpm == null) {
				UserError searchUserError = UserError.findByIdGpm(idGpm);
				if (searchUserError == null) {
					NewGPM searchNewGpm = NewGPM.findByIdGpm(idGpm);
					if (searchNewGpm != null)
						NewGPM.delete(searchNewGpm.id);
					
					newGpm = new GPM(idGpm);
					newGpm.save();
				}
			}
			Ebean.commitTransaction();	
		} 
		finally {
			Ebean.endTransaction();
		}
		return newGpm;
	}

	public static void delete(Long id) {
		GPM findGpm = findById(id);
		if (findGpm == null)
			return;
		
		// delete from black list
		BlackList bl = BlackList.findByGpmId(findGpm.id);
		if (bl != null)
			bl.delete();
		
		// delete from added by admin
		for (AddedByAdmin aba: AddedByAdmin.findByGpm(id))
			AddedByAdmin.delete(aba.id);
		
		// delete profiles
		for (Profile prof: Profile.findByGpmId(id))
			Profile.delete(prof.id);
				
		// delete posts
		for (Post pos: Post.findByGpmId(id))
			Post.delete(pos.id);	
		
		findGpm.delete();
    }
	
	public static List<SqlRow> getIdGpmByLastPosts(int count) {
		List<SqlRow> res = Ebean.createSqlQuery("SELECT id AS gpm"
				+ " FROM gpm"
				+ " WHERE id NOT IN ("
					+ " SELECT id" 
					+ " FROM blacklist"
					+ " UNION"
						+ " SELECT gpm AS id"
						+ " FROM post)"
				+ " LIMIT :count;").setParameter("count", count).findList();
		
		if (res.size() > 0)
			return res;
		
		return Ebean.createSqlQuery("SELECT gpm"
				+ " FROM Post"
    			+ " WHERE gpm NOT IN ("
    				+ " SELECT id"
    				+ " FROM blacklist)"
				+ " GROUP BY gpm"
				+ " ORDER BY max(dateCreate)"
				+ " LIMIT :count;").setParameter("count", count).findList();
	}
    
	public static List<SqlRow> getIdGpmByLastProfile(int count) {
		List<SqlRow> res = Ebean.createSqlQuery("SELECT id AS gpm"
				+ " FROM gpm"
				+ " WHERE id NOT IN ("
					+ " SELECT id" 
					+ " FROM blacklist"
					+ " UNION"
						+ " SELECT gpm AS id"
						+ " FROM profile)"
				+ " LIMIT :count;").setParameter("count", count).findList();
		
		if (res.size() > 0)
			return res;
		
		Date date = Calendar.getInstance().getTime();
		String dateToString = date.toString();
		String year = dateToString.substring(dateToString.length() - 4);
		int mount = date.getMonth() + 1;
		String parameter = year + "-" + mount + "-" + date.getDate();
		
		return Ebean.createSqlQuery("SELECT gpm"
				+ " FROM Profile"
				+ " WHERE gpm NOT IN ("
					+ " SELECT id"
					+ " FROM blacklist)"
					+ " AND dateUpdated < '" + parameter + "'"
				+ " GROUP BY gpm"
				+ " ORDER BY max(dateUpdated)"
				+ " LIMIT :count;").setParameter("count", count).findList();
	}
}
