package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "Content")
public class Content extends Model {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Constraints.Required
    @Column(name = "kind", length = 5, unique = true, nullable = false)
    public String kind;

    public String toString(){
		return this.kind;
	}

	private Content(String type){
		this.kind = type;
	}
    
    private static Model.Finder<Long, Content> find = new Model.Finder<Long, Content>(Long.class, Content.class);

	public static List<Content> all() {
		return find.all();
	}

	public static Content findById(Long Id) {
		return find.ref(Id);
	}

	public static Content findByKind(String element) {
		return find.where().eq("kind", element).findUnique();
	}
	
	public static void add(String type) {
		Content element = new Content(type);
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
