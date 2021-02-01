package it.webred.cs.data.model;

import it.webred.cs.data.base.ICsDDiarioChild;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "CS_D_PAI")
@NamedQuery(name = "CsDPai.findAll", query = "SELECT c FROM CsDPai c")
public class CsDPai implements ICsDDiarioChild, Serializable {

	private static final long serialVersionUID = 5364804785555738166L;

	@Id
	@Column(name = "DIARIO_ID")
	private Long diarioId;

	@Column(name = "VERIFICA_OGNI")
	private BigDecimal verificaOgni;

	@Column(name = "VERIFICA_UNITA_MISURA")
	private String verificaUnitaMisura;

	@Column(name = "OBIETTIVI_BREVE")
	private String obiettiviBreve;

	@Column(name = "OBIETTIVI_MEDIO")
	private String obiettiviMedio;

	@Column(name = "OBIETTIVI_LUNGO")
	private String obiettiviLungo;

	@Column(name = "RAGGIUNTI_BREVE")
	private Integer raggiuntiBreve;

	@Column(name = "RAGGIUNTI_MEDIO")
	private Integer raggiuntiMedio;

	@Column(name = "RAGGIUNTI_LUNGO")
	private Integer raggiuntiLungo;

	@Column(name = "MOTIVO_CHIUSURA")
	private String motivoChiusura;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_MONITORAGGIO")
	private Date dataMonitoraggio;

	@Column(name = "MOTIVO_CHIUSURA_SPEC")
	private String motivoChiusuraSpec;
	
	@Column(name = "CITTADINANZA")
	private String cittadinanza;

	@Column(name = "MONITORAGGIO_OBIETTIVI")
	private Boolean monitoraggioObiettivi;
	
	@Column(name = "MOTIVAZIONI_PROGETTO")
	private String motivazioniProgetto;
	
	//bi-directional one-to-one association to CsDDiario
	@OneToOne
	@JoinColumn(name = "DIARIO_ID")
	private CsDDiario csDDiario;

	//bi-directional many-to-one association to CsTbTipoPai
	@ManyToOne
	@JoinColumn(name = "TIPO_PAI_ID")
	private CsTbTipoPai csTbTipoPai;
	
	@Column(name = "TIPO_PROGETTO_ID")
	private Long tipoProgettoId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_CHIUSURA_PREVISTA")
	private Date dataChiusuraPrevista;
	
	/*@ManyToOne
	@JoinColumn(name = "TIPO_PROGETTO_ID", updatable=false, insertable=false)
	private ArFfProgetto tipoProgetto;*/
	
	//SISO-1131
	@ManyToOne
	@JoinColumn(name="ID_PROGETTO_ALTRO")
	private CsTbProgettoAltro csTbProgettoAltro;

	public CsDPai() {
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public CsDDiario getCsDDiario() {
		return csDDiario = (csDDiario == null) ? new CsDDiario() : csDDiario;
	}

	public void setCsDDiario(CsDDiario csDDiario) {
		this.csDDiario = csDDiario;
	}

	public Date getDataMonitoraggio() {
		return dataMonitoraggio;
	}

	public void setDataMonitoraggio(Date dataMonitoraggio) {
		this.dataMonitoraggio = dataMonitoraggio;
	}

	public String getVerificaUnitaMisura() {
		return verificaUnitaMisura;
	}

	public void setVerificaUnitaMisura(String verificaUnitaMisura) {
		this.verificaUnitaMisura = verificaUnitaMisura;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getVerificaOgni() {
		return verificaOgni;
	}

	public void setVerificaOgni(BigDecimal verificaOgni) {
		this.verificaOgni = verificaOgni;
	}

	public String getObiettiviBreve() {
		return obiettiviBreve;
	}

	public void setObiettiviBreve(String obiettiviBreve) {
		this.obiettiviBreve = obiettiviBreve;
	}

	public String getObiettiviMedio() {
		return obiettiviMedio;
	}

	public void setObiettiviMedio(String obiettiviMedio) {
		this.obiettiviMedio = obiettiviMedio;
	}

	public String getObiettiviLungo() {
		return obiettiviLungo;
	}

	public void setObiettiviLungo(String obiettiviLungo) {
		this.obiettiviLungo = obiettiviLungo;
	}

	public Integer getRaggiuntiBreve() {
		return raggiuntiBreve;
	}

	public void setRaggiuntiBreve(Integer raggiuntiBreve) {
		this.raggiuntiBreve = raggiuntiBreve;
	}

	public Integer getRaggiuntiMedio() {
		return raggiuntiMedio;
	}

	public void setRaggiuntiMedio(Integer raggiuntiMedio) {
		this.raggiuntiMedio = raggiuntiMedio;
	}

	public Integer getRaggiuntiLungo() {
		return raggiuntiLungo;
	}

	public void setRaggiuntiLungo(Integer raggiuntiLungo) {
		this.raggiuntiLungo = raggiuntiLungo;
	}

	public String getMotivoChiusura() {
		return motivoChiusura;
	}

	public void setMotivoChiusura(String motivoChiusura) {
		this.motivoChiusura = motivoChiusura;
	}

	public String getMotivoChiusuraSpec() {
		return motivoChiusuraSpec;
	}

	public void setMotivoChiusuraSpec(String motivoChiusuraSpec) {
		this.motivoChiusuraSpec = motivoChiusuraSpec;
	}

	public CsTbTipoPai getCsTbTipoPai() {
		return csTbTipoPai = (csTbTipoPai == null) ? new CsTbTipoPai() : csTbTipoPai;
	}
	
	public void setCsTbTipoPai(CsTbTipoPai csTbTipoPai) {
		this.csTbTipoPai = csTbTipoPai;
	}

	
	public boolean filtraPerObiettivo(String filtroObiettivi) {
		return (this.obiettiviLungo != null && this.obiettiviLungo.toUpperCase().contains(filtroObiettivi.toUpperCase()))
				|| (this.obiettiviMedio != null && this.obiettiviMedio.toUpperCase().contains(filtroObiettivi.toUpperCase()))
				|| (this.obiettiviLungo != null && this.obiettiviLungo.toUpperCase().contains(filtroObiettivi.toUpperCase()));
	}

	/* funzioni di valutazione dello status */

	public boolean isClosable() {
		return !this.isClosed() && this.getDataMonitoraggio() != null && (this.raggiuntiBreve + this.raggiuntiMedio + this.raggiuntiLungo > 0);
	}

	public boolean isClosed() {
		return //StringUtils.isNotBlank(this.motivoChiusura) || StringUtils.isNotBlank(this.motivoChiusuraSpec) 
			  this.getCsDDiario().getDtChiusuraDa()!=null && this.getCsDDiario().getDtChiusuraDa().before(new Date());
	}

	public boolean isShouldBeClosed() {
		if (!this.isClosed() && this.getCsDDiario().getDtChiusuraDa() != null) {
			Date today = new Date();
			if (today.after(this.getCsDDiario().getDtChiusuraDa())) {
				return true;
			}
		}
		return false;
	}

	public boolean isShouldBeChecked() throws IllegalArgumentException {
		if (!this.isClosed() && this.getVerificaUnitaMisura() != null && this.getVerificaOgni() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(this.getDataMonitoraggio() == this.getDataMonitoraggio() ? this.getCsDDiario().getDtAttivazioneDa() : this.getDataMonitoraggio());

			if (this.verificaUnitaMisura.equalsIgnoreCase("Giorni")) {
				calendar.add(Calendar.DAY_OF_YEAR, this.getVerificaOgni().intValue());
			} else if (this.verificaUnitaMisura.equalsIgnoreCase("Settimane")) {
				calendar.add(Calendar.WEEK_OF_YEAR, this.getVerificaOgni().intValue());
			} else if (this.verificaUnitaMisura.equalsIgnoreCase("Mesi")) {
				calendar.add(Calendar.MONTH, this.getVerificaOgni().intValue());
			} else if (this.verificaUnitaMisura.equalsIgnoreCase("Anni")) {
				calendar.add(Calendar.YEAR, this.getVerificaOgni().intValue());
			} else {
				throw new IllegalArgumentException("Errore nel campo 'verifica unità di misura'");
			}

			if (new Date().after(calendar.getTime())) {
				return true;
			}
		}
		return false;
	}

	public List<String> getTxtObiettivi() {
		List<String> txt = new ArrayList<String>();
		
		if (StringUtils.isNotEmpty(this.obiettiviBreve)) {
			txt.add("Breve: " + this.obiettiviBreve + txtRaggiunti(this.raggiuntiBreve));
		}

		if (StringUtils.isNotEmpty(this.obiettiviMedio)) {
			txt.add("Medio: " + this.obiettiviMedio + txtRaggiunti(this.raggiuntiMedio));
		}
		if (StringUtils.isNotEmpty(this.obiettiviLungo)) {
			txt.add("Lungo: " + this.obiettiviLungo + txtRaggiunti(this.raggiuntiLungo));
		}
		return txt;
	}

	public Long getTipoProgettoId() {
		return tipoProgettoId;
	}

	public void setTipoProgettoId(Long tipoProgettoId) {
		this.tipoProgettoId = tipoProgettoId;
	}

	public Date getDataChiusuraPrevista() {
		return dataChiusuraPrevista;
	}

	public void setDataChiusuraPrevista(Date dataChiusuraPrevista) {
		this.dataChiusuraPrevista = dataChiusuraPrevista;
	}
   
	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	//SISO-1034
	public Boolean getMonitoraggioObiettivi() {
		return monitoraggioObiettivi;
	}

	public void setMonitoraggioObiettivi(Boolean monitoraggioObiettivi) {
		this.monitoraggioObiettivi = monitoraggioObiettivi;
	}	
	//SISO-1172
	public String getMotivazioniProgetto() {
		return motivazioniProgetto;
	}

	public void setMotivazioniProgetto(String motivazioniProgetto) {
		this.motivazioniProgetto = motivazioniProgetto;
	}

    

	/**
	 * @return the csTbProgettoAltro
	 */
	public CsTbProgettoAltro getCsTbProgettoAltro() {
		return csTbProgettoAltro;
	}

	/**
	 * @param csTbProgettoAltro the csTbProgettoAltro to set
	 */
	public void setCsTbProgettoAltro(CsTbProgettoAltro csTbProgettoAltro) {
		this.csTbProgettoAltro = csTbProgettoAltro;
	}



	public static final int RAGGIUNTI_NO = 1;
	public static final int RAGGIUNTI_PARZIALMENTE = 2;
	public static final int RAGGIUNTI_SI = 3;

	private static final String txtRaggiunti(int valore) {
		switch (valore) {
		case 1:
			return " [obiettivi non raggiunti]";
		case 2:
			return " [obiettivi parzialmente raggiunti]";
		case 3:
			return " [obiettivi raggiunti]";
		default:
		}
		return " [n/a]";
	}
}