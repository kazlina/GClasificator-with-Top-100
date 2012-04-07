package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Profile_word")
@Entity
public class Profile_word extends GenericModel {

	@Id
	//@JoinColumn(name="Id_GPM")
    @ManyToOne
    /*@JoinColumns({
    	@JoinColumn(name="Id_GPM", referencedColumnName="Id"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    }) */
    public Profile profile;
    
    @Id
	@JoinColumn(name="Id_Word")
    @ManyToOne
    public Word_dictionary Id_Word;
    
    @Required
    @Min(1)
	@Column(name="Count", nullable=false)
    public int Count;
    
    public Profile_word(Profile Id, Word_dictionary Id_Word, int Count){
        this.profile = Id;
        this.Id_Word = Id_Word;
        this.Count = Count;
        }
}
