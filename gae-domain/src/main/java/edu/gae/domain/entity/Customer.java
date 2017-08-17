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


@Entity
@Cache
public class Customer extends AbstractLifeCycleEntity implements Selectable, DescendantEntity {	
	
	@Index
	private SearcheableString name;   
    private String document;
    private Date birthdate;
    @Index
    private Contact contact;
    @Index
	private Address endereco;	
	@Index
	private Long ancestor;
	
	public Customer(){}	

	@NotNull
	public SearcheableString getName() {
		return name;
	}

	public void setName(SearcheableString name) {
		this.name = name;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}	

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contato) {
		this.contact = contato;
	}

	public Address getEndereco() {
		return endereco;
	}

	public void setEndereco(Address endereco) {
		this.endereco = endereco;
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
		return null;
	}

	@Override
	public String getValue() {		
		return this.name.getValue();
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", document=" + document + ", contact=" + contact + ", endereco=" + endereco
				+ ", ancestor=" + ancestor + ", getCreateDate()=" + getCreateDate() + ", getActive()=" + getActive()
				+ ", getId()=" + getId() + "]";
	}	
	
}
