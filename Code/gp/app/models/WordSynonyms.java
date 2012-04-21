package models;

import java.util.*;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Table(name="Word_Synonyms")
@Entity
public class WordSynonyms extends Model {

	@Required
	@MaxSize(value=30)
	@Column(name="Word", length=30, nullable=false, unique=true)
	public String word;
	
	@JoinColumn(name="Id_Word")
	@ManyToOne
	public Word_dictionary Id_Word;
   
	public String toString(){
		return this.word;
	}
    
	public WordSynonyms(){};
   
	public WordSynonyms(String word){
		this.word = word;
	}

}
