package models;

public class GpmForOutput {
	
	public Integer position;
	
	public String gpm;

    public String name;

    public String image;

    public String gender;

    public String relationshipStatus;

    public Integer nFollowers;
    
    public GpmForOutput(Integer position, String gpm, String name, String image, String gender, String relationshipStatus, Integer nFollowers) {
    	this.position = position;
    	this.gpm = gpm;
    	this.name = name;
    	this.image = image;
    	this.gender = gender;
    	this.relationshipStatus = relationshipStatus;
    	this.nFollowers = nFollowers;
    }
}
