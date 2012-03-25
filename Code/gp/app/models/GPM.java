package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.io.Serializable;
import play.data.validation.*;

@Entity
public class GPM extends GenericModel {

	@Id
	@Match(value="^\\d{21}$", message="Incorrect identifer")
    @Column(length=21)
    public String Id;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id")
    public List<Profile> profiles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_GPM")
    public List<Statistics> statistics;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_GPM")
    public List<Pets> pets;
    
    public GPM(String Id) {

        this.Id = Id;

    }

}
