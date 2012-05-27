package models;

import java.util.*;
import javax.persistence.*;

import org.jsoup.select.Elements;

import com.avaje.ebean.*;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "NewGPM")
public class NewGPM extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Constraints.Required
	@Column(name = "idGpm", length = 21, unique = true, nullable = false)
	public String idGpm;

	@Constraints.Required
	@Column(name = "nMentiens", nullable = false)
	public int nMentiens;

	public String toString(){
		return this.idGpm;
	}
	
	private NewGPM(String idGpm) {
        this.idGpm = idGpm;
	}
	
	private static Model.Finder<Long, NewGPM> find = new Model.Finder<Long, NewGPM>(Long.class, NewGPM.class);

	public static List<NewGPM> get(int count) {
		return find.where().orderBy("nMentiens desc").setMaxRows(count).findList();
	}
	
	public static int size() {
		return find.findRowCount();
	}
	
	public static NewGPM findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}
	
	public static NewGPM findByIdGpm(String Id) {
		return find.where().eq("idGpm", Id).findUnique();
	}

	public static void add(String idGpm) {
		Ebean.beginTransaction();
		
		try {
			NewGPM searchNewGpm = findByIdGpm(idGpm);
			if (searchNewGpm != null) {
				searchNewGpm.nMentiens ++;
				searchNewGpm.save();
			}
			else {
				GPM searchGpm = GPM.findByIdGpm(idGpm);
				if (searchGpm == null) {
					NewGPM newGpm = new NewGPM(idGpm);
					newGpm.nMentiens = 1;
					newGpm.save();
				}
			}
			Ebean.commitTransaction();
		}
		finally {
			Ebean.endTransaction();
		}
	}
	
	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
