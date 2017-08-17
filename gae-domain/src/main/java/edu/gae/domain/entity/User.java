package edu.gae.domain.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import edu.gae.domain.entity.base.AbstractLifeCycleEntity;
import edu.gae.domain.entity.base.DescendantEntity;
import edu.gae.domain.entity.base.SearcheableString;
import edu.gae.domain.entity.base.Selectable;
import edu.gae.domain.entity.ref.UserProfile;
import edu.gae.domain.entity.ref.UserStatus;

@Entity
@Cache
public class User extends AbstractLifeCycleEntity implements Selectable, DescendantEntity {
	
	@Index
	private SearcheableString name;	
	@Index
	private String email;
	private String password;
	private String logo;
	@Index
	private UserProfile profile;
	@Index
	private UserStatus status;
	private Date lastAccessDate;
	@Index
	private Long ancestor;
	
	public User(){}		
	
	public User(SearcheableString name, String email, String password, UserProfile profile, UserStatus status,
			Boolean active) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.profile = profile;
		this.status = status;
		this.setActive(active);
	}	
	
	@NotNull
	public SearcheableString getName() {
		return name;
	}

	public void setName(SearcheableString name) {
		this.name = name;
	}

	@NotNull
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}	

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}	

	@NotNull
	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile perfil) {
		this.profile = perfil;
	}
	
	@NotNull
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	@Override
	public Long getAncestor() {
		return ancestor;
	}

	@Override
	public void setAncestor(Long ancestor) {
		this.ancestor = ancestor;
	}

	@Override
	public String getKey() {
		return this.email;
	}

	@Override
	public String getValue() {
		return this.name.getValue();
	}	

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", password=" + password + ", logo=" + logo + ", profile="
				+ profile + ", status=" + status + ", lastAccessDate=" + lastAccessDate + ", getCreateDate()="
				+ getCreateDate() + ", getActive()=" + getActive() + ", getId()=" + getId() + "]";
	}
	
}
