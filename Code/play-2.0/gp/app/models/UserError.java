package models;

import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.Ebean;
import play.db.ebean.*;

@Entity
@Table(name = "UserError")
public class UserError extends Model {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@Column(name = "idGpm", length = 21, unique = true, nullable = false)
	public String idGpm;

	@Column(name = "dateOfAddition", nullable = false)
    public Date dateOfAddition;

    @Column(name = "reasonOfAddition")
    public String reasonOfAddition;

	public String toString(){
		return this.idGpm;
	}

	public UserError(String gpmId, String reasonOfAddition) {
		this.idGpm = gpmId;
		this.reasonOfAddition = reasonOfAddition;
    }

	private static Model.Finder<Long, UserError> find = new Model.Finder<Long, UserError>(Long.class, UserError.class);

	public static List<UserError> all() {
		return find.all();
	}

	public static UserError findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}
	
	public static UserError findByIdGpm(String Id) {
		return find.where().eq("idGpm", Id).findUnique();
	}

	public static void add(UserError element) {
		Ebean.beginTransaction();
		try {
			GPM gpm = GPM.findByIdGpm(element.idGpm);
			if (gpm != null)
				GPM.delete(gpm.id);
			else {
				NewGPM newGpm = NewGPM.findByIdGpm(element.idGpm);
				if (newGpm != null)
					NewGPM.delete(newGpm.id);
			}
		
			element.dateOfAddition = Calendar.getInstance().getTime();
			element.save();
			
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
