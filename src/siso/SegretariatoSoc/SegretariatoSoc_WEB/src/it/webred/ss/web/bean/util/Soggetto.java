package it.webred.ss.web.bean.util;

import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.wizard.Anagrafica;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Soggetto {
	
	private String id;
	
	private Long idSsAnag;
	private String cognome;
	private String nome;
	private String cf;
	private Date dataNascita;
    private Date dataMorte;	
	//private String idExt;     //IDENTIFICATIVO NECESSARIO IN DIOGENE (solamente)
	private String parentela;
	private String sesso;
	private String privacy;
	private String alias;
	
	private IndirizzoAnagDTO indirizzo;
	
	// soggetto conosciuto da
	private boolean cs = false; // cartella sociale
	private String  csOrganizzazione;
	private String  csOSettore;
	private boolean rp = false;
	
	private List<KeyValueDTO>  ssComplete;   // segretariato sociale
	private List<KeyValueDTO>  ssIncomplete; // segretariato sociale
	
	private boolean ev = false; // ente volontariato
	private boolean intErogati = false;
	//private boolean anagSanitaria=false;
	private boolean anagEsistenteUDC  =false;
	private String tipoRicercaSoggetto = null;
	
	//SISO-1531 
	private boolean presenzaDatiEsterni = false;
	private boolean beneficiarioRdC = false;
	
	public Soggetto(String provenienza, String identificativo, Long idSSAnag,  String cognome, String nome, String cf, Date dataNascita, Date dataMorte, String sesso) {
		super();

		this.tipoRicercaSoggetto = provenienza;
		id=identificativo;
		idSsAnag = idSSAnag!=null ? idSSAnag : new Long(-1);
		this.cognome = cognome!=null ? cognome.toUpperCase() : null;
		this.nome = nome!=null ? nome.toUpperCase() : null;
		this.cf = cf!=null ? cf.toUpperCase() : null;
		this.dataNascita = dataNascita;
		this.dataMorte = dataMorte;
		this.sesso = sesso!=null ? sesso.toUpperCase() : "";
	}

	
	
	public boolean isPresenzaDatiEsterni() {
		return presenzaDatiEsterni;
	}



	public void setPresenzaDatiEsterni(boolean presenzaDatiEsterni) {
		this.presenzaDatiEsterni = presenzaDatiEsterni;
	}



	public Long getIdSsAnag() {
		return idSsAnag;
	}

	public String getCognome() {
		return cognome;
	}

	public String getNome() {
		return nome;
	}

	public String getCf() {
		return cf;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public Date getDataMorte() {
		return dataMorte;
	}

	public boolean isCs() {
		return cs;
	}

	public boolean isEv() {
		return ev;
	}

	public boolean isRp() {
		return rp;
	}

	public void setRp(boolean rp) {
		this.rp = rp;
	}

	public boolean isIntErogati() {
		return intErogati;
	}

	public boolean isAnagSanitariaUmbria() {
		return DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA.equalsIgnoreCase(this.tipoRicercaSoggetto);
	}
	
	public boolean isAnagDefault() {
		return DataModelCostanti.TipoRicercaSoggetto.DEFAULT.equalsIgnoreCase(this.tipoRicercaSoggetto);
	}

	public boolean isAnagSigess(){
		return DataModelCostanti.TipoRicercaSoggetto.SIGESS.equalsIgnoreCase(this.tipoRicercaSoggetto);
	}
	
	public boolean isAnagSanitariaMarche(){
		return DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE.equalsIgnoreCase(this.tipoRicercaSoggetto);
	}
	
	public boolean isAnagSanitaria(){
		return this.isAnagSanitariaMarche()||this.isAnagSanitariaUmbria();
	}
	
	public boolean isAnagRilevazionePresenze(){
		return DataModelCostanti.TipoRicercaSoggetto.ANAG_RILEVAZIONE_PRESENZE.equalsIgnoreCase(this.tipoRicercaSoggetto);
	}
	
	public boolean isAnagEsistenteUDC() {
		return this.anagEsistenteUDC;
	}

	public void setIdSsAnag(Long idSsAnag) {
		this.idSsAnag = idSsAnag;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public void setDataMorte(Date dataMorte) {
		this.dataMorte = dataMorte;
	}


	public void setCs(boolean cs) {
		this.cs = cs;
	}

	public void setEv(boolean ev) {
		this.ev = ev;
	}

	public void setIntErogati(boolean intErogati) {
		this.intErogati = intErogati;
	}

	public void setAnagEsistenteUDC(boolean anagEsistenteUDC) {
		this.anagEsistenteUDC = anagEsistenteUDC;
	}

	public List<KeyValueDTO> getSsComplete() {
		return ssComplete;
	}

	public List<KeyValueDTO> getSsIncomplete() {
		return ssIncomplete;
	}

	public void setSsComplete(List<KeyValueDTO> ssComplete) {
		this.ssComplete = ssComplete;
	}

	public void setSsIncomplete(List<KeyValueDTO> ssIncomplete) {
		this.ssIncomplete = ssIncomplete;
	}

	public String getParentela() {
		return parentela;
	}

	public void setParentela(String parentela) {
		this.parentela = parentela;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public IndirizzoAnagDTO getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(IndirizzoAnagDTO indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCsOrganizzazione() {
		return csOrganizzazione;
	}

	public void setCsOrganizzazione(String csOrganizzazione) {
		this.csOrganizzazione = csOrganizzazione;
	}

	public String getCsOSettore() {
		return csOSettore;
	}

	public void setCsOSettore(String csOSettore) {
		this.csOSettore = csOSettore;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getTipoRicercaSoggetto() {
		return tipoRicercaSoggetto;
	}

	public void setTipoRicercaSoggetto(String tipoRicercaSoggetto) {
		this.tipoRicercaSoggetto = tipoRicercaSoggetto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public boolean isAnonimo(){
		return Anagrafica.SEGNALATO_CF_ANONIMO.equalsIgnoreCase(cf)||
		 Anagrafica.SEGNALATO_CF_ANONIMO.equalsIgnoreCase(this.cognome) ||
		 Anagrafica.SEGNALATO_CF_ANONIMO.equalsIgnoreCase(this.nome);
	}
	
	public boolean isBeneficiarioRdC() {
		return beneficiarioRdC;
	}

	public void setBeneficiarioRdC(boolean beneficiarioRdC) {
		this.beneficiarioRdC = beneficiarioRdC;
	}

	public String getSoggettoKey(){
		String key = cf;
		if(StringUtils.isBlank(key) || this.isAnonimo()){
			String descrizione = cognome+" "+nome+" ["+(alias!=null ? alias : "")+"] "+(dataNascita!=null ? SegretariatoSocBaseBean.ddMMyyyy.format(dataNascita) : "");
			key=Integer.toString(descrizione.hashCode());
		}else
			key = key.toUpperCase();
		return key;
	}
}
