package edu.gae.model;

public class Page {
	
	private int limit;
	private int offset;
	
	public Page(){}
	
	public Page(int limit, int offset){
		this.limit = limit;
		this.offset = offset;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	@Override
	public String toString() {
		return "[limit=" + limit + ", offset=" + offset + "]";
	}
	
	
	
}
