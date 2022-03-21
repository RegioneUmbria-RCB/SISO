package it.webred.cs.csa.ejb.dto.pai.pti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.webred.cs.csa.ejb.util.CustomDateSerializer;

@JsonIgnoreProperties(value = { "paiDoc", "ptiDoc", "richiesteDisponibilita", "documentiPTI" })
public class CsPaiPTIDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long diarioPaiId;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date dataRedazione;

	private Boolean flgEmergenza;

	private Long tipoStruttura;

	private Long idStruttura;

	private Boolean flgCondVerifPresenzaAdulti;

	private Boolean flgCoinvFamiglia;

	private Boolean flgDisabilita;

	@JsonSerialize(using = CustomDateSerializer.class)
	private Date periodoInsPianificazioneDa;

	@JsonSerialize(using = CustomDateSerializer.class)
	private Date periodoInsPianificazioneA;

	private Boolean flgInterventiDisabili;

	private Boolean flgGravidanza;

	private Boolean flgNeonato;

	private Boolean flgAreaPenale;

	private Long idCaseManager;

	private String caseManager;

	private Boolean flgProrRichMagg;

	private Boolean flgProrLimiteEta;

	private Boolean flgEsisteEduPeda;

	private Boolean flgInvioSegnTM;

	private Long diarioSinbaId;

	private String codRouting;
	
	private Long tipoMinore;
	
	private String descTipoMinore;

	private List<CsPaiPtiFaseDTO> fasiPTI = new ArrayList<CsPaiPtiFaseDTO>();

//	private List<CsPaiPtiDocumentoDTO> documentiPTI = new ArrayList<CsPaiPtiDocumentoDTO>();
	
	private List<ArCsPaiPTIDocumentoDTO> documentiPTI = new ArrayList<ArCsPaiPTIDocumentoDTO>();


	private List<RichiestaDisponibilitaPaiPtiDTO> richiesteDisponibilita;

	public CsPaiPTIDTO() {
		super();
	}

	public CsPaiPTIDTO(PaiPTIFaseEnum fase) {
		CsPaiPtiFaseDTO faseIniziale = new CsPaiPtiFaseDTO(fase.getId());
		fasiPTI.add(faseIniziale);

	}

//	public CsPaiPtiDocumentoDTO getPaiTipoDocumentoDoc(String tipoDocumento) {
//
//		for (CsPaiPtiDocumentoDTO documento : documentiPTI) {
//			if (documento.getTipoDocumento().equalsIgnoreCase(tipoDocumento)) {
//
//				return documento;
//			}
//		}
//		return null;
//	}
	
	public ArCsPaiPTIDocumentoDTO getPaiTipoDocumentoDoc(String tipoDocumento) {

		for (ArCsPaiPTIDocumentoDTO documento : documentiPTI) {
			if (documento.getTipoDocumento().equalsIgnoreCase(tipoDocumento)) {

				return documento;
			}
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiarioPaiId() {
		return diarioPaiId;
	}

	public void setDiarioPaiId(Long diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}

	public Date getDataRedazione() {
		return dataRedazione;
	}

	public void setDataRedazione(Date dataRedazione) {
		this.dataRedazione = dataRedazione;
	}

	public Boolean getFlgEmergenza() {
		return flgEmergenza;
	}

	public void setFlgEmergenza(Boolean flgEmergenza) {
		this.flgEmergenza = flgEmergenza;
	}

	public Long getTipoStruttura() {
		return tipoStruttura;
	}

	public void setTipoStruttura(Long tipoStruttura) {
		this.tipoStruttura = tipoStruttura;
	}

	public Long getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(Long idStruttura) {
		this.idStruttura = idStruttura;
	}

	public Boolean getFlgCondVerifPresenzaAdulti() {
		return flgCondVerifPresenzaAdulti;
	}

	public void setFlgCondVerifPresenzaAdulti(Boolean flgCondVerifPresenzaAdulti) {
		this.flgCondVerifPresenzaAdulti = flgCondVerifPresenzaAdulti;
	}

	public Boolean getFlgCoinvFamiglia() {
		return flgCoinvFamiglia;
	}

	public void setFlgCoinvFamiglia(Boolean flgCoinvFamiglia) {
		this.flgCoinvFamiglia = flgCoinvFamiglia;
	}

	public Boolean getFlgDisabilita() {
		return flgDisabilita;
	}

	public void setFlgDisabilita(Boolean flgDisabilita) {
		this.flgDisabilita = flgDisabilita;
	}

	public Date getPeriodoInsPianificazioneDa() {
		return periodoInsPianificazioneDa;
	}

	public void setPeriodoInsPianificazioneDa(Date periodoInsPianificazioneDa) {
		this.periodoInsPianificazioneDa = periodoInsPianificazioneDa;
	}

	public Date getPeriodoInsPianificazioneA() {
		return periodoInsPianificazioneA;
	}

	public void setPeriodoInsPianificazioneA(Date periodoInsPianificazioneA) {
		this.periodoInsPianificazioneA = periodoInsPianificazioneA;
	}

	public Boolean getFlgInterventiDisabili() {
		return flgInterventiDisabili;
	}

	public void setFlgInterventiDisabili(Boolean flgInterventiDisabili) {
		this.flgInterventiDisabili = flgInterventiDisabili;
	}

	public Boolean getFlgGravidanza() {
		return flgGravidanza;
	}

	public void setFlgGravidanza(Boolean flgGravidanza) {
		this.flgGravidanza = flgGravidanza;
	}

	public Boolean getFlgNeonato() {
		return flgNeonato;
	}

	public void setFlgNeonato(Boolean flgNeonato) {
		this.flgNeonato = flgNeonato;
	}

	public Boolean getFlgAreaPenale() {
		return flgAreaPenale;
	}

	public void setFlgAreaPenale(Boolean flgAreaPenale) {
		this.flgAreaPenale = flgAreaPenale;
	}

	public String getCaseManager() {
		return caseManager;
	}

	public void setCaseManager(String caseManager) {
		this.caseManager = caseManager;
	}

	public List<CsPaiPtiFaseDTO> getFasiPTI() {
		return fasiPTI;
	}

	public void setFasiPTI(List<CsPaiPtiFaseDTO> fasiPTI) {
		this.fasiPTI = fasiPTI;
	}

	public List<RichiestaDisponibilitaPaiPtiDTO> getRichiesteDisponibilita() {
		return richiesteDisponibilita;
	}

	public void setRichiesteDisponibilita(List<RichiestaDisponibilitaPaiPtiDTO> richiesteDisponibilita) {
		this.richiesteDisponibilita = richiesteDisponibilita;
	}

	public CsPaiPtiFaseDTO getFaseAttuale() {
		for (CsPaiPtiFaseDTO fase : fasiPTI) {
			if (fase.getValidaA() == null) {
				return fase;
			}
		}
		return null;
	}

	public Boolean getFlgProrRichMagg() {
		return flgProrRichMagg;
	}

	public void setFlgProrRichMagg(Boolean flgProrRichMagg) {
		this.flgProrRichMagg = flgProrRichMagg;
	}

	public Boolean getFlgProrLimiteEta() {
		return flgProrLimiteEta;
	}

	public void setFlgProrLimiteEta(Boolean flgProrLimiteEta) {
		this.flgProrLimiteEta = flgProrLimiteEta;
	}

	public Boolean getFlgEsisteEduPeda() {
		return flgEsisteEduPeda;
	}

	public void setFlgEsisteEduPeda(Boolean flgEsisteEduPeda) {
		this.flgEsisteEduPeda = flgEsisteEduPeda;
	}

	public Boolean getFlgInvioSegnTM() {
		return flgInvioSegnTM;
	}

	public void setFlgInvioSegnTM(Boolean flgInvioSegnTM) {
		this.flgInvioSegnTM = flgInvioSegnTM;
	}

	public Long getDiarioSinbaId() {
		return diarioSinbaId;
	}

	public void setDiarioSinbaId(Long diarioSinbaId) {
		this.diarioSinbaId = diarioSinbaId;
	}

	public Long getIdCaseManager() {
		return idCaseManager;
	}

	public void setIdCaseManager(Long idCaseManager) {
		this.idCaseManager = idCaseManager;
	}

//	public List<CsPaiPtiDocumentoDTO> getDocumentiPTI() {
//		return documentiPTI;
//	}
//
//	public void setDocumentiPTI(List<CsPaiPtiDocumentoDTO> documentiPTI) {
//		this.documentiPTI = documentiPTI;
//	}

	public String getCodRouting() {
		return codRouting;
	}

	public List<ArCsPaiPTIDocumentoDTO> getDocumentiPTI() {
		return documentiPTI;
	}

	public void setDocumentiPTI(List<ArCsPaiPTIDocumentoDTO> documentiPTI) {
		this.documentiPTI = documentiPTI;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public Long getTipoMinore() {
		return tipoMinore;
	}

	public void setTipoMinore(Long tipoMinore) {
		this.tipoMinore = tipoMinore;
	}

	public String getDescTipoMinore() {
		return descTipoMinore;
	}

	public void setDescTipoMinore(String descTipoMinore) {
		this.descTipoMinore = descTipoMinore;
	}


}
