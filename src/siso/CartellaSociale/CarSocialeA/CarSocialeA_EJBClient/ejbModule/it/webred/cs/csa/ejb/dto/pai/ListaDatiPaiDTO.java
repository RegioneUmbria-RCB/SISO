package it.webred.cs.csa.ejb.dto.pai;

import it.webred.cs.csa.ejb.dto.fascicolo.DatiInterceptorDTO;

import java.util.Date;
import java.util.List;

public class ListaDatiPaiDTO extends DatiInterceptorDTO{
	
	private Long diarioId;
	private String tipoBeneficiario;
	private List<CsPaiMastSoggDTO> beneficiari;
	private String tipoPai;
	private Date dataAttivazioneDa;
	private Date dataChiusuraDa;
	private Date dataChiusuraPrevista;
	private boolean chiuso;
	private boolean daChiudere;
	private boolean daControllare;
	private List<String> txtObiettivi;
	
	private Date dataUltimaModifica;
	private String opModifica;
	
	public ListaDatiPaiDTO(Long secondoLivello, Long idSettoreDiario) {
		super(secondoLivello, idSettoreDiario);
	}
	
	public Long getDiarioId() {
		return diarioId;
	}
	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}
	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}
	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}
	public String getOpModifica() {
		return opModifica;
	}
	public void setOpModifica(String opModifica) {
		this.opModifica = opModifica;
	}
	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}
	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}
	public List<CsPaiMastSoggDTO> getBeneficiari() {
		return beneficiari;
	}

	public void setBeneficiari(List<CsPaiMastSoggDTO> beneficiari) {
		this.beneficiari = beneficiari;
	}

	public String getTipoPai() {
		return tipoPai;
	}
	public void setTipoPai(String string) {
		this.tipoPai = string;
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
	public boolean isChiuso() {
		return chiuso;
	}
	public void setChiuso(boolean chiuso) {
		this.chiuso = chiuso;
	}
	public boolean isDaChiudere() {
		return daChiudere;
	}
	public void setDaChiudere(boolean daChiudere) {
		this.daChiudere = daChiudere;
	}
	public boolean isDaControllare() {
		return daControllare;
	}
	public void setDaControllare(boolean daControllare) {
		this.daControllare = daControllare;
	}
	public List<String> getTxtObiettivi() {
		return txtObiettivi;
	}
	public void setTxtObiettivi(List<String> txtObiettivi) {
		this.txtObiettivi = txtObiettivi;
	}
	
	public boolean hasBeneficiarioCF(String cf) {
		for (CsPaiMastSoggDTO s : beneficiari) {
			if (s.getCf().equalsIgnoreCase(cf)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasBeneficiarioCaso() {
		boolean hasCaso = false;
		for (CsPaiMastSoggDTO s : beneficiari) {
			if (s.isRiferimento()) {
				hasCaso = s.getCsASoggetto() != null ? true : false;
			    
			}
		}
		return hasCaso;
	}
	
	public String getCognomeNome() {
		String cognomeNome ="";
		for (CsPaiMastSoggDTO csPaiMastSogg : beneficiari) {
			if (csPaiMastSogg.isRiferimento()) {
				cognomeNome = csPaiMastSogg.getCognome() +" "+ csPaiMastSogg.getNome();
			    
			}
		}
		return cognomeNome;
	}
}
