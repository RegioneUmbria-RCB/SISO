package eu.smartpeg.rilevazionepresenze.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the AR_RP_AREA database table.
 * 
 */
@Entity
@Table(name="AR_RP_AREA")
@NamedQueries(value={
		@NamedQuery(name="Area.findAll", query="SELECT a FROM Area a ORDER BY a.descrizione"),
	    @NamedQuery(name="Area.deleteById", query="DELETE FROM Area a where a.id= :id"),
	    @NamedQuery(name="Area.findAreaById", query="SELECT a FROM Area a where a.id= :id")
	})
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AR_RP_AREA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AR_RP_AREA_ID_GENERATOR")
	private long id;

	private String descrizione;

	//bi-directional many-to-many association to ArRpStruttura
	@ManyToMany(mappedBy="areas")
	private List<Struttura> arRpStrutturas;

	public Area() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Struttura> getArRpStrutturas() {
		return this.arRpStrutturas;
	}

	public void setArRpStrutturas(List<Struttura> arRpStrutturas) {
		this.arRpStrutturas = arRpStrutturas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Area other = (Area) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", descrizione=" + descrizione + "]";
	}
	

}