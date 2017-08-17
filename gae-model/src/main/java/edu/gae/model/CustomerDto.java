package edu.gae.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

import edu.gae.model.constants.Model;

@XmlRootElement
public class CustomerDto implements LifeCycleDto {	
	
	private Long id;
	private String name;
    private String document;
	private ContactDto contact;	
	private AddressDto endereco;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = Model.DATE_FORMAT, timezone = Model.TIME_ZONE)
	private Date createDate;
	private Boolean active;
	private Long ancestor;
	
	public CustomerDto(){}
		
	public CustomerDto(String name, String document, ContactDto contact, AddressDto endereco) {
		super();
		this.name = name;
		this.document = document;
		this.contact = contact;
		this.endereco = endereco;
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

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public ContactDto getContact() {
		return contact;
	}

	public void setContact(ContactDto contato) {
		this.contact = contato;
	}

	public AddressDto getEndereco() {
		return endereco;
	}

	public void setEndereco(AddressDto endereco) {
		this.endereco = endereco;
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

	public Long getAncestor() {
		return ancestor;
	}

	public void setAncestor(Long ancestor) {
		this.ancestor = ancestor;
	}

	@Override
	public String toString() {
		return "CustomerDto [name=" + name + ", document=" + document + ", contat="
				+ contact + ", endereco=" + endereco + ", createDate=" + createDate + ", active=" + active
				+ ", ancestor=" + ancestor + "]";
	}	
}
