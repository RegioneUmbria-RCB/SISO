package it.webred.cs.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CS_A_SOGG_DATI_ESTERNI")
@NamedQuery(name = "CsASoggettoDatiEsterni.byCf", query = "SELECT sda from CsASoggettoDatiEsterni sda WHERE sda.cf = :cf")
public class CsASoggettoDatiEsterni {

	@Id
	@SequenceGenerator(name = "CS_A_SOGG_DATI_ESTERNI_ID_GENERATOR", sequenceName = "SQ_CS_DATI_ESTERNI", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_A_SOGG_DATI_ESTERNI_ID_GENERATOR")
	private Long id;

	private String cf;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CsASoggettoDatiEsterni other = (CsASoggettoDatiEsterni) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CsASoggettoDatiEsterni [id=" + id + ", cf=" + cf + "]";
	}

}
