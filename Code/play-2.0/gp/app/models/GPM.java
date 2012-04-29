package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "GPM")
public class GPM extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name = "id_gpm", length = 21, unique = true, nullable = false)
	@Constraints.Required
	public String id_gpm;

	@OneToMany(mappedBy = "gpm")
	public List<Profile> profile;

	@OneToMany(mappedBy = "gpm")
	public List<Post> post;

	@OneToMany(mappedBy = "gpm")
	public List<AddedByAdmin> addedByAdmin;

	public static Model.Finder<Long, GPM> find = new Model.Finder<Long, GPM>(Long.class, GPM.class);
/*
	public String toString(){
		return this.id_gpm;
	}

	public GPM(String id) {
		this.id_gpm = id;
	}
*/
	public static List<GPM> all() {
		return find.all();
	}

	public static GPM gpmById(Long Id) {
		return find.ref(Id);
	}

	public static void create(GPM man) {
		man.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
