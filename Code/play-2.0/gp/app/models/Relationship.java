package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "Relationship")
public class Relationship extends Model {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Constraints.MaxLength(30)
    @Constraints.Required
    @Column(name = "status", length = 30, unique = true, nullable = false)
    public String status;

    public String toString(){
		return this.status;
	}

	private Relationship(String type){
		this.status = type;
	}
    
    private static Model.Finder<Long, Relationship> find = new Model.Finder<Long, Relationship>(Long.class, Relationship.class);

	public static List<Relationship> all() {
		return find.all();
	}

	public static Relationship findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}

	public static Relationship findByStatus(String element) {
		return find.where().eq("status", element).findUnique();
	}
	
	public static void add(String type) {
		Relationship element = new Relationship(type);
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
