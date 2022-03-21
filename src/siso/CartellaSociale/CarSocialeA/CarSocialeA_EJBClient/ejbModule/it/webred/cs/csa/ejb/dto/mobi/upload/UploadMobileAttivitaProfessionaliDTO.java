package it.webred.cs.csa.ejb.dto.mobi.upload;

import it.webred.cs.csa.ejb.dto.DiarioAnagraficaDTO;
import it.webred.cs.csa.ejb.dto.PaiDTOExt;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDTriage;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelRelazioneProbl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UploadMobileAttivitaProfessionaliDTO implements Serializable {

	//TODO
	//mappare tutti a parametri necessari al Bean di creazione Attività Professionali (gli stessi del siso web)
	
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	private Date createdAt;
//	private Long erOrganizzazioneErId;
//	private Long erSettoreErogId;
	private String erSoggCf;
//	private Long idMobile;
//	private String testo;
//	private VMobiIntErog programmazione;
//	private ErogStatoCfgDTO stato;
//	private String unimis;
//	private BigDecimal valore;
//	private UploadMobileValoreDTO[] valori;
	
	//private RelazioneDTO relazioneDTO;
	//private CsDRelazione relazione;
	//***TODO CsDRelazione esplicitata***
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date diarioDtAmministrativa;
	

	
	private long csOSettore_id;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataProssimaRelazioneAl;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataProssimaRelazioneDal;

	
	private String fontiUtilizzate;
	
	private String proposta;

	
	private String situazioneAmb;

	
	private String situazioneParentale;

	
	private String situazioneSanitaria;
	
	
	private String organizzazioneServizio;
	
	
	private String richiestaIndagine;

	//lo sto creando nuovo
	private Long idCaso;
	
	private CsCTipoIntervento[] csCTipoInterventos;		
		
	
	private Long id_macroAttivita; //CsTbMacroAttivita by id
	
	
	private Long id_microAttivita; //CsTbMicroAttivita by id
	
	
	private String testo;

	
	private String flagRilevazioneProblematiche;

	private CsRelRelazioneProblRequestDTO[] csRelRelazioneProbl;//classe piatta che esplicita CsRelRelazioneProbl[] csRelRelazioneProbl;
	
	private Long[] id_csRelRelazioneTipoint; //List<CsRelRelazioneTipoint>
	
	//private CsRelRelazioneProbl[] csRelRelazioneProblReverseRif; //
		
	
//	private Long id_riunioneCon; //CsOSettore by id //SISO-1481
	
	
	private List<String> lstConChiSel;
	
	private String conChiAltro;
	
	private List<String> lstRiunioneConChi;
	
	private Long numOperatori;
	
	@JsonFormat(pattern = "HH:mm")
	private Date oreImpiegate;
	
	private DiarioAnagraficaDTO[] famigliaSelezionata; //sufficente id;anagraficaId;note;selezionato
	
	
	//*************************
	
	//non sembrano servire
	private CsLoadDocumento csLaodDocumento;
	private List<PaiDTOExt> listaPaiDTO;
	private boolean containsDoc;
	//******************
	
	private CsDTriage triage; //Classe piatta potrei non esplicitarla
	////////////////////////////////////
	
	
	
	/*PARAMETRI UTILIZZATI NEL SAVE e quindi necessari alla creazione attività professionale
	 * relazioneDTO.getRelazione().getMicroAttivita().getFlagTipoForm()
	 * relazioneDTO.getTriage()
	 *
	 * 	relazioneDTO.getRelazione()	
	 * 
	 * IL DIARIO VIENE CREATO E POPOLATO IN FASE DI SALVATAGGIO su RelazioneBean.save()
	 * relazioneDTO.getRelazione().getDiarioId()
	 * relazioneDTO.getRelazione().getCsDDiario()	
	 * relazioneDTO.getRelazione().getCsDDiario().getId()
	 * */
	
	

	

	public Date getDataProssimaRelazioneAl() {
		return dataProssimaRelazioneAl;
	}

	public void setDataProssimaRelazioneAl(Date dataProssimaRelazioneAl) {
		this.dataProssimaRelazioneAl = dataProssimaRelazioneAl;
	}

	public Date getDataProssimaRelazioneDal() {
		return dataProssimaRelazioneDal;
	}

	public void setDataProssimaRelazioneDal(Date dataProssimaRelazioneDal) {
		this.dataProssimaRelazioneDal = dataProssimaRelazioneDal;
	}

	public String getFontiUtilizzate() {
		return fontiUtilizzate;
	}

	public void setFontiUtilizzate(String fontiUtilizzate) {
		this.fontiUtilizzate = fontiUtilizzate;
	}

	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	public String getSituazioneAmb() {
		return situazioneAmb;
	}

	public void setSituazioneAmb(String situazioneAmb) {
		this.situazioneAmb = situazioneAmb;
	}

	public String getSituazioneParentale() {
		return situazioneParentale;
	}

	public void setSituazioneParentale(String situazioneParentale) {
		this.situazioneParentale = situazioneParentale;
	}

	public String getSituazioneSanitaria() {
		return situazioneSanitaria;
	}

	public void setSituazioneSanitaria(String situazioneSanitaria) {
		this.situazioneSanitaria = situazioneSanitaria;
	}

	public String getOrganizzazioneServizio() {
		return organizzazioneServizio;
	}

	public void setOrganizzazioneServizio(String organizzazioneServizio) {
		this.organizzazioneServizio = organizzazioneServizio;
	}

	public String getRichiestaIndagine() {
		return richiestaIndagine;
	}

	public void setRichiestaIndagine(String richiestaIndagine) {
		this.richiestaIndagine = richiestaIndagine;
	}

	public Long getIdCaso() {
		return idCaso;
	}

	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}

	public CsCTipoIntervento[] getCsCTipoInterventos() {
		return csCTipoInterventos;
	}

	public void setCsCTipoInterventos(CsCTipoIntervento[] csCTipoInterventos) {
		this.csCTipoInterventos = csCTipoInterventos;
	}

	public Long getId_macroAttivita() {
		return id_macroAttivita;
	}

	public void setId_macroAttivita(Long id_macroAttivita) {
		this.id_macroAttivita = id_macroAttivita;
	}

	public Long getId_microAttivita() {
		return id_microAttivita;
	}

	public void setId_microAttivita(Long id_microAttivita) {
		this.id_microAttivita = id_microAttivita;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getFlagRilevazioneProblematiche() {
		return flagRilevazioneProblematiche;
	}

	public void setFlagRilevazioneProblematiche(String flagRilevazioneProblematiche) {
		this.flagRilevazioneProblematiche = flagRilevazioneProblematiche;
	}

	

	public Long[] getId_csRelRelazioneTipoint() {
		return id_csRelRelazioneTipoint;
	}

	public void setId_csRelRelazioneTipoint(Long[] id_csRelRelazioneTipoint) {
		this.id_csRelRelazioneTipoint = id_csRelRelazioneTipoint;
	}

//	public CsRelRelazioneProbl[] getCsRelRelazioneProblReverseRif() {
//		return csRelRelazioneProblReverseRif;
//	}
//
//	public void setCsRelRelazioneProblReverseRif(
//			CsRelRelazioneProbl[] csRelRelazioneProblReverseRif) {
//		this.csRelRelazioneProblReverseRif = csRelRelazioneProblReverseRif;
//	}

	//SISO-1481
//	public Long getId_riunioneCon() {
//		return id_riunioneCon;
//	}
//
//	public void setId_riunioneCon(Long id_riunioneCon) {
//		this.id_riunioneCon = id_riunioneCon;
//	}

	

	public String getConChiAltro() {
		return conChiAltro;
	}

	public void setConChiAltro(String conChiAltro) {
		this.conChiAltro = conChiAltro;
	}

	public Long getNumOperatori() {
		return numOperatori;
	}

	public void setNumOperatori(Long numOperatori) {
		this.numOperatori = numOperatori;
	}

	public Date getOreImpiegate() {
		return oreImpiegate;
	}

	public void setOreImpiegate(Date oreImpiegate) {
		this.oreImpiegate = oreImpiegate;
	}

	public DiarioAnagraficaDTO[] getFamigliaSelezionata() {
		return famigliaSelezionata;
	}

	public void setFamigliaSelezionata(DiarioAnagraficaDTO[] famigliaSelezionata) {
		this.famigliaSelezionata = famigliaSelezionata;
	}

	public CsLoadDocumento getCsLaodDocumento() {
		return csLaodDocumento;
	}

	public void setCsLaodDocumento(CsLoadDocumento csLaodDocumento) {
		this.csLaodDocumento = csLaodDocumento;
	}

	public List<PaiDTOExt> getListaPaiDTO() {
		return listaPaiDTO;
	}

	public void setListaPaiDTO(List<PaiDTOExt> listaPaiDTO) {
		this.listaPaiDTO = listaPaiDTO;
	}

	public boolean isContainsDoc() {
		return containsDoc;
	}

	public void setContainsDoc(boolean containsDoc) {
		this.containsDoc = containsDoc;
	}

	public CsDTriage getTriage() {
		return triage;
	}

	public void setTriage(CsDTriage triage) {
		this.triage = triage;
	}

	public String getErSoggCf() {
		return erSoggCf;
	}

	public void setErSoggCf(String erSoggCf) {
		this.erSoggCf = erSoggCf;
	}

	

	public Date getDiarioDtAmministrativa() {
		return diarioDtAmministrativa;
	}

	public void setDiarioDtAmministrativa(Date diarioDtAmministrativa) {
		this.diarioDtAmministrativa = diarioDtAmministrativa;
	}

	public List<String> getLstConChiSel() {
		return lstConChiSel;
	}

	public void setLstConChiSel(List<String> lstConChiSel) {
		this.lstConChiSel = lstConChiSel;
	}

	public CsRelRelazioneProblRequestDTO[] getCsRelRelazioneProbl() {
		return csRelRelazioneProbl;
	}

	public void setCsRelRelazioneProbl(
			CsRelRelazioneProblRequestDTO[] csRelRelazioneProbl) {
		this.csRelRelazioneProbl = csRelRelazioneProbl;
	}

	

	public long getCsOSettore_id() {
		return csOSettore_id;
	}

	public void setCsOSettore_id(long csOSettore_id) {
		this.csOSettore_id = csOSettore_id;
	}

	public List<String> getLstRiunioneConChi() {
		return lstRiunioneConChi;
	}

	public void setLstRiunioneConChi(List<String> lstRiunioneConChi) {
		this.lstRiunioneConChi = lstRiunioneConChi;
	}
	
	
}
