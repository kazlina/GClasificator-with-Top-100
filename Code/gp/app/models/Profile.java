package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Profile")
@Entity
@IdClass(Key.class)
public class Profile extends GenericModel {
        
    @Id
    @Required
    @JoinColumn(name="Id_GPM")
    @ManyToOne
    public GPM Id_GPM;
    
    @Id
    @Column(name="Date")
    public Date date;

    @MaxSize(value=100)
    @Column(name="Name", length=100)
    public String Name;
    
    @Column(name="Image_URL")
    @URL
	public String Image_URL;
    
    @Column(name="Gender")
    public Boolean Gender;
    
    @Column(name="Aim")
    public String Aim;
    
    @Column(name="Tagline")
    public String Tagline;
    
    @MaxSize(value=20)
    @Column(name="Status", length=20)
    public String Status;
    
    @Column(name="Followers")
    public int Followers;    

    @OneToMany(cascade=CascadeType.ALL, mappedBy="profile")
    /*@JoinColumns({
    	@JoinColumn(name="Id", referencedColumnName="Id_GPM"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    })*/ 
    public List<Profile_link> followedLinks; 
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="profile")
    /*@JoinColumns({
    	@JoinColumn(name="Id", referencedColumnName="Id_GPM"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    })*/
    public List<Profile_word> followedWords; 
    
    public Profile(){};
   
	public Profile(GPM Id_GPM, String Name, String Photo_link, Boolean Gender, String Aim, String Tagline, String Status, int followers) {
        this.Id_GPM = Id_GPM;
        this.date = Calendar.getInstance().getTime();
        this.Name = Name;
        this.Image_URL = Photo_link;
        this.Gender = Gender;
        this.Aim = Aim;
        this.Tagline = Tagline;
        this.Status = Status;
        this.Followers = followers;
        }

}
