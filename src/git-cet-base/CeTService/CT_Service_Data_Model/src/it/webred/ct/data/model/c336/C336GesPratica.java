package it.webred.ct.data.model.c336;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the S_C336_GES_PRATICA database table.
 * 
 */
@Entity
@Table(name="S_C336_GES_PRATICA")
public class C336GesPratica implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private C336GesPraticaPK id;
	
    @Temporal( TemporalType.DATE)
	@Column(name="DT_FIN_GES")
	private Date dtFinGes;

  
    public C336GesPratica() {
    }

	public C336GesPraticaPK getId() {
		return id;
	}

	public void setId(C336GesPraticaPK id) {
		this.id = id;
	}

	public Date getDtFinGes() {
		return this.dtFinGes;
	}

	public void setDtFinGes(Date dtFinGes) {
		this.dtFinGes = dtFinGes;
	}

	
	

}