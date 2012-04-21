package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Profiles")
@Entity
@IdClass(Key.class)
public class Profile extends GenericModel {
        
    @Id
    @Required
    @JoinColumn(name="Id_GPM")
    @ManyToOne
    public GPM gpm;
    
    @Id
    @Column(name="Date")
    public Date date;

    @MaxSize(value=100)
    @Column(name="Name", length=100)
    public String name;
    
    @Column(name="Image_URL")
    @URL
	public String image;
    
    @Column(name="Gender")
    public String gender;
     
    @Column(name="Tagline")
    public String tagline;
    
    @MaxSize(value=20)
    @Column(name="Relationship_Status", length=20)
    public String relationshipStatus;
    
    @Column(name="Followers")
    public int followers;    

    @OneToMany(cascade=CascadeType.ALL, mappedBy="profile")
    /*@JoinColumns({
    	@JoinColumn(name="Id", referencedColumnName="Id_GPM"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    })*/ 
    public List<ProfileLink> links; 
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="profile")
    /*@JoinColumns({
    	@JoinColumn(name="Id", referencedColumnName="Id_GPM"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    })*/
    public List<ProfileWord> words; 
    
    public Profile(){};
   
	public Profile(GPM gpm, String name, String image, String gender, String tagline, 
					String relationshipStatus, int followers) {
        this.gpm = gpm;
        this.date = Calendar.getInstance().getTime();
        this.name = name;
        this.image = image;
        this.gender = gender;
        this.tagline = tagline;
        this.relationshipStatus = relationshipStatus;
        this.followers = followers;
        }

}
