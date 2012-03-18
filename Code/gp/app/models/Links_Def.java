package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Links_Def extends Model {

    public int Id;
    public String Link;
    
    @ManyToMany(mappedBy="followedLinks") 
    public Set<Statistics> followsByStatistics = new HashSet<Statistics>(); 

    public Links_Def( int Id, String Link){
        this.Id = Id;
        this.Link = Link;
        }

}
