package edu.gae.domain.criteria;

import java.util.Date;

public class Period {
	
	private Date initialDate;
	private Date finalDate;
	
	public Period(){}
	
	public Period(Date initialDate, Date finalDate) {
		super();
		this.initialDate = initialDate;
		this.finalDate = finalDate;
	}
	
	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	@Override
	public String toString() {
		return "Period [initialDate=" + initialDate + ", finalDate=" + finalDate + "]";
	}
	
}
