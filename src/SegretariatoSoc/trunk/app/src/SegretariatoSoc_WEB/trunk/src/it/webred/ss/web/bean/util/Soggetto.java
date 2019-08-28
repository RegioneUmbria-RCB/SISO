package it.webred.ss.web.bean.util;

import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagDTO;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;

import java.util.Date;
import java.util.List;

public class Soggetto {
	
	//private Long id;
	private Long idAnagSanitaria;
	private String idAnagComunale;
	private Long idSsAnag;
	private String cognome;
	private String nome;
	private String cf;
	private Date dataNascita;
    private Date dataMorte;	
	private String idExt;
	private String parentela;
	private String sesso;
	private String privacy;
	
	private IndirizzoAnagDTO indirizzo;
	
	// soggetto conosciuto da
	private boolean cs = false; // cartella sociale
	private String  csOrganizzazione;
	private String  csOSettore;
	
	private List<KeyValueDTO>  ssComplete;   // segretariato sociale
	private List<KeyValueDTO>  ssIncomplete; // segretariato sociale
	
	private boolean ev = false; // ente volontariato
	private boolean intErogati = false;
	private boolean anagSanitaria=false;
	private boolean anagEsterna  =false;
	
	public Soggetto(Long idAS, String idAC, Long idSSAnag,  String cognome, String nome, String cf, Date dataNascita, Date dataMorte, String sesso) {
		super();
		this.idAnagSanitaria = idAS!=null ? idAS : new Long(-1);
		idAnagComunale= idAC;
		idSsAnag = idSSAnag!=null ? idSSAnag : new Long(-1);
		this.cognome = cognome!=null ? cognome.toUpperCase() : null;
		this.nome = nome!=null ? nome.toUpperCase() : null;
		this.cf = cf!=null ? cf.toUpperCase() : null;
		this.dataNascita = dataNascita;
		this.dataMorte = dataMorte;
		this.sesso = sesso!=null ? sesso.toUpperCase() : "";
	}

	public Long getIdAnagSanitaria() {
		return idAnagSanitaria;
	}

	public String getIdAnagComunale() {
		return idAnagComunale;
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

	public String getIdExt() {
		return idExt;
	}

	public boolean isCs() {
		return cs;
	}

	public boolean isEv() {
		return ev;
	}

	public boolean isIntErogati() {
		return intErogati;
	}

	public boolean isAnagSanitaria() {
		return anagSanitaria;
	}

	public boolean isAnagEsterna() {
		return anagEsterna;
	}

	public void setIdAnagSanitaria(Long idAnagSanitaria) {
		this.idAnagSanitaria = idAnagSanitaria;
	}

	public void setIdAnagComunale(String idAnagComunale) {
		this.idAnagComunale = idAnagComunale;
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

	public void setIdExt(String idExt) {
		this.idExt = idExt;
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

	public void setAnagSanitaria(boolean anagSanitaria) {
		this.anagSanitaria = anagSanitaria;
	}

	public void setAnagEsterna(boolean anagEsterna) {
		this.anagEsterna = anagEsterna;
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

}
