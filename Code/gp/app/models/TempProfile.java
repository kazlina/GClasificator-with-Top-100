package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;


//import java.util.Vector;

public class TempProfile {
	public String id;
	public String displayName;
	public String tagline;
	public String gender;
	public String relationshipStatus;
	public String image;
	public String aboutMe;
	public int amounturls;
	Vector <String> urls = new Vector<String>(amounturls);
	
}
