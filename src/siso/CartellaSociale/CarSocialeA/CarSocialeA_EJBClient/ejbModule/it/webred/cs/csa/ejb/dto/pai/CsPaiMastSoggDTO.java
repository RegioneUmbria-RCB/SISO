package it.webred.cs.csa.ejb.dto.pai;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.webred.cs.csa.ejb.dto.SoggettoMastBaseDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsPaiMastSogg;

@JsonIgnoreProperties(value = { "csASoggetto", "caso", "casoId", "datiValidi", "allineatoResidenza","alineatoAnagrafica",
								"nazioneResidenzaNonDefinita","tooltipDifferenzeAnagrafica","tooltipDifferenzeResidenza"})
public class CsPaiMastSoggDTO extends SoggettoMastBaseDTO{

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long diarioId;

	private Date dtIns;

	private String userIns;

	private Date dtMod;

	private String userMod;

	private Long casoId;
	
	// Allineamento dati da cartella
	private CsACaso caso;

	/*Nuovo PAI da Fascicolo - uso i dati del caso*/
	public CsPaiMastSoggDTO(CsASoggettoLAZY soggetto, String viaResidenza, String jsonComuneResidenza, String statoEsteroResidenza, String jsonComuneNascita, boolean intestatario) {
		super(soggetto, viaResidenza, jsonComuneResidenza, statoEsteroResidenza, jsonComuneNascita, intestatario);
	}

	/*Nuovo PAI da ricerca anagrafica*/
	public CsPaiMastSoggDTO(String cognome, String nome, String cf, String cittadinanza, Date dataNascita, String viaResidenza, String comuneResidenza, String nazioneResidenza, String sesso, String comuneNascita, String nazioneNascita, boolean intestatario) {
		super(cognome, nome, cf, cittadinanza, dataNascita, viaResidenza, comuneResidenza, nazioneResidenza, 
			  comuneNascita, nazioneNascita, sesso, intestatario);
	}

	public CsPaiMastSoggDTO(CsPaiMastSogg es){
		super(es.getRiferimento()!=null && es.getRiferimento() ? true : false);
		
		this.cognome=es.getCognome();
		this.nome = es.getNome();
		this.cf = es.getCf();
		this.cittadinanza = es.getCittadinanza();
		this.cittadinanza2 = es.getCittadinanza2();
		this.annoNascita = es.getAnnoNascita();
		this.riferimento = es.getRiferimento()!=null && es.getRiferimento() ? true : false;
		
		this.comuneNascita = es.getComuneNascita();
		this.nazioneNascita = es.getNazioneNascita();
		
 		 this.comuneResidenza = es.getComuneResidenza();
 		 this.viaResidenza = es.getViaResidenza();
 		 this.nazioneResidenza= es.getNazioneResidenza();
 		 this.nazioneResidenzaNonDefinita = es.getNazioneResidenzaNonDefinita();
 		 
 		 this.sesso = es.getSesso() ;
 		 
 		 this.id = es.getId();
 		 this.diarioId = es.getDiarioId();
 		 this.dtIns = es.getDtIns();
 		 this.dtMod = es.getDtMod();
 		 this.userIns = es.getUserIns();
 		 this.userMod = es.getUserMod();
 	
 		 if(es.getCaso()!=null) {
 			 this.casoId = es.getCasoId();
 			 this.caso = es.getCaso();
 			 this.csASoggetto = es.getCaso()!=null ?  es.getCaso().getCsASoggetto() : null;
 		 }
	}
	
	public CsPaiMastSoggDTO(CsPaiMastSogg es, String viaResidenzaCaso, String jsonLuogoResidenzaCaso, String nazioneResidenzaCaso, String jsonComuneNascitaCaso){
		this(es);
		this.integraDatiMancanti(csASoggetto, viaResidenzaCaso, jsonLuogoResidenzaCaso, nazioneResidenzaCaso, jsonComuneNascitaCaso);
	}
		
	public CsPaiMastSoggDTO(boolean intestatario){
		super(intestatario);
	}

	@Override
	public boolean isAllineatoResidenza() {
		// TODO da modificare
		return true;
//		return allineatoResidenza;
	}

	public CsACaso getCaso() {
		return caso;
	}

	public void setCaso(CsACaso caso) {
		this.caso = caso;
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public Long getCasoId() {
		return casoId;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getUserMod() {
		return userMod;
	}

	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}
	
	
}
