package models;

import java.util.*;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Table(name="Words_dictionary")
@Entity
public class Word extends Model {

	@Required
	@MaxSize(value=30)
	@Column(name="Word", length=30, nullable=false, unique=true)
	public String word;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "word")
	public List<GroupWord> groupWords;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "word")
	public List<PostWord> postWords;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "word")
	public List<ProfileWord> profileWords;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "word")
	public List<Synonym> synonyms;	
    
	public String toString(){
	return this.word;
	}
    
	public Word(){};
	
	public Word(String word){
		this.word = word;
	}

}
