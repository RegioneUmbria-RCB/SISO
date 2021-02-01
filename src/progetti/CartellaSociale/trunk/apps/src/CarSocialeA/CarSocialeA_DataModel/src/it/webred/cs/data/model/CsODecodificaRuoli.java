package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="CS_O_DECODIFICA_RUOLI")
@NamedQuery(name="CsODecodificaRuoli.findAll", query="SELECT c FROM CsODecodificaRuoli c order by c.labelRuolo")
public class CsODecodificaRuoli implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PREFISSO_GRUPPO")
	private String prefissoGruppo;

	@Column(name="LABEL_RUOLO")
	private String labelRuolo;

	public String getPrefissoGruppo() {
		return prefissoGruppo;
	}

	public void setPrefissoGruppo(String prefissoGruppo) {
		this.prefissoGruppo = prefissoGruppo;
	}

	public String getLabelRuolo() {
		return labelRuolo;
	}

	public void setLabelRuolo(String labelRuolo) {
		this.labelRuolo = labelRuolo;
	}

}