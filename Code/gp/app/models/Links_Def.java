package models;

import java.util.*;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Links_Def extends Model {

	public String Link;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Link")
    public List<Group_Link> group_link;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Link")
    public List<Links> links;
    
    public Links_Def(String Link){
        this.Link = Link;
        }

}
