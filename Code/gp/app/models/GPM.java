package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="GPM")
@Entity
public class GPM extends GenericModel {

	@Id
	@Match(value="^\\d{21}$", message="Incorrect identifer")
    @Column(name="Id", length=21)
    public String Id;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id")
    public List<Profile> profiles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "key.Id")
    public List<Posts> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_GPM")
    public List<Pets> pets;
    
    public String toString() {
    return Id;
}
    
    public GPM(String Id) {
        this.Id = Id;
    }

}
