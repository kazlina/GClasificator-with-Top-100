package models;

import java.util.*;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Keywords_Def extends Model {

	public String Word;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Word")
    public List<Group_Word> group_word;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Word")
    public List<Keywords> keywords;
    
    public Keywords_Def(String Word){
        this.Word = Word;
        }

}
