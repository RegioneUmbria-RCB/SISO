package it.webred.ct.data.access.basic.ruolo.tarsu.dto;

import it.webred.ct.data.access.basic.versamenti.bp.dto.VersamentoTarBpDTO;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuRata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RataDTO implements Serializable {
	
	private String prog;
	private String idExtRuolo;
	private String codRendAuto;
	private Date dataScadenza;
	private BigDecimal impBollettino;
	private String totLettere;
	private String vCampo;
	
	private List<VersamentoTarBpDTO> versamenti;

	public RataDTO(){}
	
	public RataDTO(SitRuoloTarsuRata r){
		this.prog = r.getProg();
		this.idExtRuolo = r.getIdExtRuolo();
		this.codRendAuto = r.getCodRendAuto();
		this.dataScadenza = r.getDataScadenza();
		this.impBollettino = r.getImpBollettino();
		this.totLettere = r.getTotLettere();
		this.vCampo = r.getVCampo();
	}
	
	public String getIdExtRuolo() {
		return idExtRuolo;
	}

	public void setIdExtRuolo(String idExtRuolo) {
		this.idExtRuolo = idExtRuolo;
	}

	public String getCodRendAuto() {
		return codRendAuto;
	}

	public void setCodRendAuto(String codRendAuto) {
		this.codRendAuto = codRendAuto;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public BigDecimal getImpBollettino() {
		return impBollettino;
	}

	public void setImpBollettino(BigDecimal impBollettino) {
		this.impBollettino = impBollettino;
	}

	public String getTotLettere() {
		return totLettere;
	}

	public void setTotLettere(String totLettere) {
		this.totLettere = totLettere;
	}

	public String getvCampo() {
		return vCampo;
	}

	public void setvCampo(String vCampo) {
		this.vCampo = vCampo;
	}

	public List<VersamentoTarBpDTO> getVersamenti() {
		return versamenti;
	}

	public void setVersamenti(List<VersamentoTarBpDTO> versamenti) {
		this.versamenti = versamenti;
	}

	public String getProg() {
		return prog;
	}

	public void setProg(String prog) {
		this.prog = prog;
	}
	
}
