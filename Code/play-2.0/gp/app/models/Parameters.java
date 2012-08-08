package models;

import play.data.validation.Constraints;

public class Parameters {

	@Constraints.Min(0)
	@Constraints.Max(100)
	@Constraints.Required
	public int percentGetNew = 60;
	
	@Constraints.Min(0)
	@Constraints.Max(100)
	@Constraints.Required
	public int percentUpdatePosts = 20;
	
	@Constraints.Min(0)
	@Constraints.Max(100)
	@Constraints.Required
	public int percentUpdateProfiles = 20;
}
