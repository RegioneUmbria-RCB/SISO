package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the AM_FONTE_TIPOINFO database table.
 * 
 */
@Entity
@Table(name="AM_FONTE_TIPOINFO")
public class AmFonteTipoinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String informazione;

	@Column(name="N_ORDINE_SU_FONTE")
	private BigDecimal nOrdineSuFonte;

	@Column(name="PROG_OLD")
	private BigDecimal progOld;

	//bi-directional many-to-one association to AmFonte
    /*@ManyToOne
	@JoinColumn(name="FK_AM_FONTE")
	private AmFonte amFonte;*/
	@Column(name="FK_AM_FONTE")
	private Integer fkAmFonte;

	public AmFonteTipoinfo() {
    }

	public String getInformazione() {
		return this.informazione;
	}

	public void setInformazione(String informazione) {
		this.informazione = informazione;
	}

	public BigDecimal getNOrdineSuFonte() {
		return this.nOrdineSuFonte;
	}

	public void setNOrdineSuFonte(BigDecimal nOrdineSuFonte) {
		this.nOrdineSuFonte = nOrdineSuFonte;
	}

	public BigDecimal getProgOld() {
		return this.progOld;
	}

	public void setProgOld(BigDecimal progOld) {
		this.progOld = progOld;
	}

    public Integer getFkAmFonte() {
		return fkAmFonte;
	}

	public void setFkAmFonte(Integer fkAmFonte) {
		this.fkAmFonte = fkAmFonte;
	}
	/*public AmFonte getAmFonte() {
		return this.amFonte;
	}

	public void setAmFonte(AmFonte amFonte) {
		this.amFonte = amFonte;
	}*/
	
}