package it.webred.cs.csa.ejb.dto;

public class PaginationDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	
	private int first;
	private int pageSize;
	
	public int getFirst() {
		return first;
	}
	
	public void setFirst(int first) {
		this.first = first;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
