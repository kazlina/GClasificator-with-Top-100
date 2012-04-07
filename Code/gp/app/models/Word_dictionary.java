package models;

import java.util.*;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Table(name="Word_dictionary")
@Entity
public class Word_dictionary extends Model {

	@Required
	@MaxSize(value=30)
	@Column(name="Word", length=30, nullable=false, unique=true)
	public String Word;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Word")
    public List<Group_Word> group_word;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Word")
    public List<Post_word> post_word;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Id_Word")
    public List<Profile_word> profile_word;
    
    public Word_dictionary(String Word){
        this.Word = Word;
        }

}
