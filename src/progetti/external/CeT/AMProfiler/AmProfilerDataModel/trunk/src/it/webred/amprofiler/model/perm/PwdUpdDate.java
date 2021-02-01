package it.webred.amprofiler.model.perm;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PwdUpdDate {

	@Id
	private Date data;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
}
