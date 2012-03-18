package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class GPM extends Model {
    
    @Id
    public String id_gplus;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id")
    public List<Profile> profiles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id")
    public List<Statistics> statistics;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_GPM")
    public List<Pets> pets;

    public GPM(String id_gplus) {

        this.id_gplus = id_gplus;

    }

}
