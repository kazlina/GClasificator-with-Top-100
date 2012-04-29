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

    @OneToMany(mappedBy = "kindContent")
    public List<Post> posts;

    public static Model.Finder<Long, Content> find = new Model.Finder<Long, Content>(Long.class, Content.class);

	public static List<Content> all() {
		return find.all();
	}

	public static Content contentById(Long Id) {
		return find.ref(Id);
	}

	public static void create(Content element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
