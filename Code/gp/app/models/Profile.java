package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Profile")
@Entity
public class Profile extends GenericModel {

	@Id
	@JoinColumn(name="Id")
	@ManyToOne
    public GPM Id;
    
    @Id
    @Column(name="Date")
    public Date date;
    
    @Column(name="Name", length=50)
    public String Name;
    
    @Column(name="Image_URL")
    public String Image_URL;
    
    @Column(name="Gender")
    public Boolean Gender;
    
    @Column(name="Aim")
    public String Aim;
    
    @Column(name="Tagline")
    public String Tagline;
    
    @Column(name="Status", length=20)
    public String Status;
    
    @Column(name="Followers")
    public int Followers;    

    /*@OneToMany(cascade=CascadeType.ALL)
    @JoinColumns({
    	@JoinColumn(name="Id", referencedColumnName="Id_GPM"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    }) 
    public List<Profile_link> followedLinks; 
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumns({
    	@JoinColumn(name="Id", referencedColumnName="Id_GPM"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    })
    public List<Profile_word> followedWords; 
   */
	public Profile(GPM Id, String Name, String Photo_link, Boolean Gender, String Aim, String Tagline, String Status, int followers) {
        this.Id = Id;
        this.date = new Date();
        this.Name = Name;
        this.Image_URL = Photo_link;
        this.Gender = Gender;
        this.Aim = Aim;
        this.Tagline = Tagline;
        this.Status = Status;
        this.Followers = followers;
        }

}
