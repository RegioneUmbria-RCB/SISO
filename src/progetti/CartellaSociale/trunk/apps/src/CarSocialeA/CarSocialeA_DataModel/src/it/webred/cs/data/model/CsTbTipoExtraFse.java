package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="CS_TB_TIPO_EXTRA_FSE")
@NamedQuery(name="CsTbTipoExtraFse.findAll", query="SELECT c FROM CsTbTipoExtraFse c order by descrizione")
public class CsTbTipoExtraFse implements Serializable {

	private static final long serialVersionUID = 7615799496059779728L;

	@Id
	@SequenceGenerator(name="CS_TB_TIPO_EXTRA_FSE_ID_GENERATOR", sequenceName="SQ_EXTRA_FSE",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_TIPO_EXTRA_FSE_ID_GENERATOR")
	private Long id;

	private String descrizione;

	public CsTbTipoExtraFse() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


}