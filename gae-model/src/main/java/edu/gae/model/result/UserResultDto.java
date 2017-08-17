package edu.gae.model.result;

import javax.xml.bind.annotation.XmlRootElement;

import edu.gae.model.ModelDto;
import edu.gae.model.ref.SelectableDto;

@XmlRootElement
public class UserResultDto implements ModelDto {
	
	private Long id;
	private String name;	
	private String email;
	private SelectableDto profile;
    private Boolean active;
    
    @Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public SelectableDto getProfile() {
		return profile;
	}

	public void setProfile(SelectableDto profile) {
		this.profile = profile;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "UserResultDto [id=" + id + ", name=" + name + ", email=" + email + ", profile=" + profile + ", active="
				+ active + "]";
	}
	
}
