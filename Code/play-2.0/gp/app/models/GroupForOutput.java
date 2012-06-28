package models;

import java.util.List;

public class GroupForOutput {
	public Long groupId;
	public List <GpmForOutput> gpms;
	public GroupForOutput (Long newGroupId, List <GpmForOutput> newGPMs) {
		this.groupId = newGroupId;
		this.gpms = newGPMs;
	}
}
