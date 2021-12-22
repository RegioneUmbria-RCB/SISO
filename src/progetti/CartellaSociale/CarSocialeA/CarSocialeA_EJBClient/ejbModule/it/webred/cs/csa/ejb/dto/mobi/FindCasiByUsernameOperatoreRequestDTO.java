package it.webred.cs.csa.ejb.dto.mobi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.webred.cs.data.model.view.VMobiIntErog;

public class FindCasiByUsernameOperatoreRequestDTO implements Serializable {




	/**
	 * 
	 */
	private static final long serialVersionUID = 7609974482932118973L;
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
		
}
