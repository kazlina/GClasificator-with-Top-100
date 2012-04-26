package models;

import java.util.*;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Table(name="Word_Synonyms")
@Entity
public class Synonym extends Model {

	@Required
	@MaxSize(value=30)
	@Column(name="Synonym", length=30, nullable=false, unique=true)
	public String synonym;
	
	@JoinColumn(name="Id_Word")
	@ManyToOne
	public Word word;
   
	public String toString(){
		return this.synonym;
	}
    
	public Synonym(){};
   
	public Synonym(String synonym){
		this.synonym = synonym;
	}

}
