package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Keywords_Def extends Model {

    public int Id;
    public String Word;

    @ManyToMany(mappedBy="followedWords") 
    public Set<Statistics> followsByStatistics = new HashSet<Statistics>(); 

    public Keywords_Def( int Id, String Word){
        this.Id = Id;
        this.Word = Word;
        }

}
