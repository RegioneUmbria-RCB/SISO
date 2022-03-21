package it.webred.ct.data.model.isee;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the ISEE_ANNO_DICH database table.
 * 
 */
@Entity
@Table(name="CFG_ISEE_ANNO_DICH")
public class IseeAnnoDich implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer anno;

	@Column(name="VALUE")
	private BigDecimal value;

    public IseeAnnoDich() {
    }

	public Integer getAnno() {
		return this.anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}