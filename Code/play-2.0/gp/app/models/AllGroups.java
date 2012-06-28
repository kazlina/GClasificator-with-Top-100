package models;

import java.util.List;

public class AllGroups {
	public Long groupId;
	public List <GpmForOutput> gpms;
	public AllGroups (Long newGroupId, List <GpmForOutput> newGPMs) {
		this.groupId = newGroupId;
		this.gpms = newGPMs;
	}
}
