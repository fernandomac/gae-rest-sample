package edu.gae.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

import edu.gae.model.constants.Model;
import edu.gae.model.ref.SelectableDto;

@XmlRootElement
public class UserDto implements LifeCycleDto {	
	
	private Long id;
	private String name;	
	private String email;
	private String password;
	private String logo;
	private SelectableDto profile;
	private SelectableDto status;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = Model.DATE_FORMAT, timezone = Model.TIME_ZONE)	
	private Date lastAccessDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = Model.DATE_FORMAT, timezone = Model.TIME_ZONE)
	private Date createDate;
	private Boolean active;	
	
	public UserDto(){}	

	public UserDto(String name, String email, String password, SelectableDto profile) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.profile = profile;
	}
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public SelectableDto getProfile() {
		return profile;
	}

	public void setProfile(SelectableDto profile) {
		this.profile = profile;
	}

	public SelectableDto getStatus() {
		return status;
	}

	public void setStatus(SelectableDto status) {
		this.status = status;
	}

	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public Boolean getActive() {
		return active;
	}

	@Override
	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", logo=" + logo
				+ ", profile=" + profile + ", status=" + status + ", lastAccessDate=" + lastAccessDate + ", createDate="
				+ createDate + ", active=" + active + "]";
	}
}
