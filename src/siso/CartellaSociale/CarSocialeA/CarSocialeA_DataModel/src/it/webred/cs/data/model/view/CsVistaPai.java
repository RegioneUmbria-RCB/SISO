package it.webred.cs.data.model.view;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsPaiMastSogg;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name="CS_VISTA_PAI")
public class CsVistaPai implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="DIARIO_ID")
	private Long diarioId;

	@Column(name="CASO_ID")
	private Long casoId;

	@Column(name="SETTORE_ID")
	private Long settoreId;
	
	@Column(name="VIS_SECONDO_LIVELLO")
	private Long visSecondoLivello;
	
	@Column(name="TIPO_BENEFICIARIO")
	private String tipoBeneficiario;
	
	@Column(name = "TIPO_PAI_ID")
	private Long tipoPaiId;
	
	@Column(name = "TIPO_PAI_DESC")
	private String tipoPaiDesc;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ATTIVAZIONE_DA")
	private Date dataAttivazioneDa;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_CHIUSURA_DA")
	private Date dataChiusuraDa;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_CHIUSURA_PREVISTA")
	private Date dataChiusuraPrevista;

	private Boolean chiuso;
	
	@Column(name="DA_CHIUDERE")
	private Boolean daChiudere;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_MONITORAGGIO")
	private Date dataMonitoraggio;
	
	@Column(name = "VERIFICA_OGNI")
	private BigDecimal verificaOgni;

	@Column(name = "VERIFICA_UNITA_MISURA")
	private String verificaUnitaMisura;
	
	@Column(name="DA_CONTROLLARE")
	private Boolean daControllare;
	
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

	// bi-directional one-to-one association to CsPaiMastSogg
	@OneToMany(mappedBy = "vistaPai", fetch = FetchType.EAGER)
	private Set<CsPaiMastSogg> csPaiBeneficiari;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_ULTIMA_MODIFICA")
	private Date dataUltimaModifica;
	
	@Column(name = "OP_ULTIMA_MODIFICA")
	private String opUltimaModifica;

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public Long getSettoreId() {
		return settoreId;
	}

	public void setSettoreId(Long settoreId) {
		this.settoreId = settoreId;
	}

	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	public Long getTipoPaiId() {
		return tipoPaiId;
	}

	public void setTipoPaiId(Long tipoPaiId) {
		this.tipoPaiId = tipoPaiId;
	}

	

	public Date getDataAttivazioneDa() {
		return dataAttivazioneDa;
	}

	public void setDataAttivazioneDa(Date dataAttivazioneDa) {
		this.dataAttivazioneDa = dataAttivazioneDa;
	}

	public Date getDataChiusuraDa() {
		return dataChiusuraDa;
	}

	public void setDataChiusuraDa(Date dataChiusuraDa) {
		this.dataChiusuraDa = dataChiusuraDa;
	}

	public Date getDataChiusuraPrevista() {
		return dataChiusuraPrevista;
	}

	public void setDataChiusuraPrevista(Date dataChiusuraPrevista) {
		this.dataChiusuraPrevista = dataChiusuraPrevista;
	}

	public Boolean getChiuso() {
		return chiuso;
	}

	public void setChiuso(Boolean chiuso) {
		this.chiuso = chiuso;
	}

	public Boolean getDaChiudere() {
		return daChiudere;
	}

	public void setDaChiudere(Boolean daChiudere) {
		this.daChiudere = daChiudere;
	}

	public Date getDataMonitoraggio() {
		return dataMonitoraggio;
	}

	public void setDataMonitoraggio(Date dataMonitoraggio) {
		this.dataMonitoraggio = dataMonitoraggio;
	}

	public BigDecimal getVerificaOgni() {
		return verificaOgni;
	}

	public void setVerificaOgni(BigDecimal verificaOgni) {
		this.verificaOgni = verificaOgni;
	}

	public String getVerificaUnitaMisura() {
		return verificaUnitaMisura;
	}

	public void setVerificaUnitaMisura(String verificaUnitaMisura) {
		this.verificaUnitaMisura = verificaUnitaMisura;
	}

	public Boolean getDaControllare() {
		return daControllare;
	}

	public void setDaControllare(Boolean daControllare) {
		this.daControllare = daControllare;
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

	public Set<CsPaiMastSogg> getCsPaiBeneficiari() {
		return csPaiBeneficiari;
	}

	public void setCsPaiBeneficiari(Set<CsPaiMastSogg> csPaiBeneficiari) {
		this.csPaiBeneficiari = csPaiBeneficiari;
	}

	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getOpUltimaModifica() {
		return opUltimaModifica;
	}

	public void setOpUltimaModifica(String opUltimaModifica) {
		this.opUltimaModifica = opUltimaModifica;
	}

	public Long getVisSecondoLivello() {
		return visSecondoLivello;
	}

	public void setVisSecondoLivello(Long visSecondoLivello) {
		this.visSecondoLivello = visSecondoLivello;
	}
	
	public Long getCasoId() {
		return casoId;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}
	
	public String getTipoPaiDesc() {
		return tipoPaiDesc;
	}

	public void setTipoPaiDesc(String tipoPaiDesc) {
		this.tipoPaiDesc = tipoPaiDesc;
	}

	public List<CsPaiMastSogg> getBeneficiari(){
		List<CsPaiMastSogg> lst = new ArrayList<CsPaiMastSogg>();
		Iterator<CsPaiMastSogg> itr = csPaiBeneficiari.iterator();
		List<CsPaiMastSogg> altri = new ArrayList<CsPaiMastSogg>();
		CsPaiMastSogg riferimento = null;
		while(itr.hasNext()) {
			CsPaiMastSogg sogg = itr.next();
			if (sogg.getRiferimento()) {
				riferimento = sogg;
			} else {
				altri.add(sogg);
			}
		}
		if (riferimento != null)
			lst.add(riferimento);
		lst.addAll(altri);
		return lst;
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
	
	private static final String txtRaggiunti(int valore) {
		switch (valore) {
		case DataModelCostanti.Pai.RAGGIUNTI_NO:
			return " [obiettivi non raggiunti]";
		case DataModelCostanti.Pai.RAGGIUNTI_PARZIALMENTE:
			return " [obiettivi parzialmente raggiunti]";
		case DataModelCostanti.Pai.RAGGIUNTI_SI:
			return " [obiettivi raggiunti]";
		default:
		}
		return " [n/a]";
	}
}