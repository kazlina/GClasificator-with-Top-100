package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.*;

@Entity
@Table(name = "Relationship")
public class Relationship extends Model {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Constraints.Required
    @Column(name = "status", length = 20, unique = true, nullable = false)
    public String status;

    public static Finder<Long, Relationship> find = new Finder<Long, Relationship>(Long.class, Relationship.class);

	public static List<Relationship> all() {
		return find.all();
	}

	public static Relationship relationshipById(Long Id) {
		return find.ref(Id);
	}

	public static void create(Relationship element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}