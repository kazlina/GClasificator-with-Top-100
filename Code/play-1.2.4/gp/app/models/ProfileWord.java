package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Profile_words")
@Entity
public class ProfileWord extends GenericModel {

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
    public Word word;
    
    @Required
    @Min(1)
	@Column(name="Amount", nullable=false)
    public int amount;
    
    public ProfileWord(){};
    
    public ProfileWord(Profile profile, Word word, int amount){
        this.profile = profile;
        this.word = word;
        this.amount = amount;
        }
}
