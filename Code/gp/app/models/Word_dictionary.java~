package models;

import java.util.*;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Table(name="Word_dictionary")
@Entity
public class Word_dictionary extends Model {

	@Column(name="Word", length=30, nullable=false, unique=true)
	public String Word;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Word")
    public List<Group_Word> group_word;
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Word")
    public List<Post_word> post_word;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Word")
    public List<Profile_word> profile_word;
   */ 

   public String toString() {
   return Word;
   }

    public Word_dictionary(String Word){
        this.group_word = new ArrayList<Group_Word>();
        this.Word = Word;
        }

}
