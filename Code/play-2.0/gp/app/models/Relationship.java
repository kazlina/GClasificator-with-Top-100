package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;

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
    @Constraints.Pattern("[a-zA-Zа-яА-Я\\s/]+")
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
		Relationship rel = findById(id);
		if (rel == null)
			return;
		
		List<SqlRow> result = Ebean.createSqlQuery("SELECT id"
				+ " FROM profile"
				+ " WHERE relationshipStatus = :idRel;").setParameter("idRel", id).findList();
		for (SqlRow res: result) {
			Profile prof = Profile.findById(res.getLong("id"));
			if (prof != null) {
				prof.relationshipStatus = null;
				prof.update();
			}
		}
		
		rel.delete();
    }

    public static void updateRelationship(Long idRelationship, Relationship relationship) {
	System.out.println(relationship.status);
	Relationship findRelationship = Relationship.findById(idRelationship);
	if (findRelationship == null)
		return;

	findRelationship.status = relationship.status;
	findRelationship.update();
	}
	
	
	
}
