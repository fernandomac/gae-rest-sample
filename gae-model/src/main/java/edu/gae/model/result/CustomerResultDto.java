package edu.gae.model.result;

import javax.xml.bind.annotation.XmlRootElement;

import edu.gae.model.ContactDto;
import edu.gae.model.ModelDto;

@XmlRootElement
public class CustomerResultDto implements ModelDto {
	
	private Long id;
	private String name;
    private ContactDto contact;
    private Boolean ativo;
    
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
	
    public ContactDto getContact() {
		return contact;
	}
	
    public void setContact(ContactDto contact) {
		this.contact = contact;
	}
	
    public Boolean getAtivo() {
		return ativo;
	}
	
    public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "CustomerResultDto [name=" + name + ", contact=" + contact + ", ativo=" + ativo + "]";
	}

}
