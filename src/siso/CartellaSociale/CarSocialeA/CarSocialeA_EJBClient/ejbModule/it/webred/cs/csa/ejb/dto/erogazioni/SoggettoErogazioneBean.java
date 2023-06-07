package it.webred.cs.csa.ejb.dto.erogazioni;

import java.util.Date;

import it.webred.cs.csa.ejb.dto.SoggettoMastBaseDTO;
import it.webred.cs.csa.ejb.dto.pai.CsPaiMastSoggDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
 

public class SoggettoErogazioneBean extends SoggettoMastBaseDTO {
	
	public SoggettoErogazioneBean(boolean riferimento){
		super(riferimento);
	}
	
	public SoggettoErogazioneBean(String cognome, String nome, String cf, String cittadinanza, Date dataNascita, 
								  String viaResidenza, String comuneResidenza, String nazioneResidenza, 
								  String comuneNascita, String nazioneNascita, 
								  String sesso, boolean riferimento){
		
		super(cognome, nome, cf, cittadinanza, dataNascita, viaResidenza, comuneResidenza, nazioneResidenza, 
			  comuneNascita, nazioneNascita, sesso, riferimento);
	}
	
	public SoggettoErogazioneBean(CsIInterventoEsegMastSogg es){
		super(es.getRiferimento()!=null && es.getRiferimento() ? true : false);
		this.csASoggetto = es.getCaso()!=null ?  es.getCaso().getCsASoggetto() : null;
		
		this.cognome=es.getCognome();
		this.nome = es.getNome();
		this.cf = es.getCf();
		this.cittadinanza = es.getCittadinanza();
		this.cittadinanza2 = es.getSecondaCittadinanza();
		this.annoNascita = es.getAnnoNascita();
		this.riferimento = es.getRiferimento()!=null && es.getRiferimento() ? true : false;
		this.datiValidi = es.getDatiValidi()!=null ? es.getDatiValidi()  : false;
		
		this.comuneNascita = es.getComuneNascita();
		this.nazioneNascita = es.getNazioneNascita();
		
		//SISO-962 Aggiungere l'indirizzo ed il comune di residenza
 		 this.comuneResidenza = es.getComuneResidenza();
 		 this.viaResidenza = es.getViaResidenza();
 		 this.nazioneResidenza= es.getNazioneResidenza();
 		 this.nazioneResidenzaNonDefinita = es.getNazioneResidenzaNonDefinita();//SISO_1021
 		//SISO-962 FINE
 		 
 		//SISO-1138
 		this.sesso = es.getSesso() ;
 		//SISO-1138 FINE
	}
	
	public SoggettoErogazioneBean(CsPaiMastSoggDTO pms) {
		this.csASoggetto = pms.getCaso() != null ? pms.getCaso().getCsASoggetto() : null;

		this.cognome = pms.getCognome();
		this.nome = pms.getNome();
		this.cf = pms.getCf();
		this.cittadinanza = pms.getCittadinanza();
		this.cittadinanza2 = pms.getCittadinanza2();
		this.annoNascita = pms.getAnnoNascita();
		this.riferimento = pms.isRiferimento();

		this.comuneNascita = pms.getComuneNascita();
		this.nazioneNascita = pms.getNazioneNascita();
		
		this.comuneResidenza = pms.getComuneResidenza();
		this.viaResidenza = pms.getViaResidenza();
		this.nazioneResidenza = pms.getNazioneResidenza();
		this.nazioneResidenzaNonDefinita = pms.isNazioneResidenzaNonDefinita();
		
		this.sesso = pms.getSesso();
		
	}
	
	//SISO-962 Inizio
	public SoggettoErogazioneBean( CsASoggettoLAZY soggetto, String viaResidenza, String jsonLuogoResidenza, String statoEsteroResidenza, String jsonComuneNascita, boolean riferimento) {
		csASoggetto = soggetto;
	
		this.sincronizzaDatiAnagrafici(jsonComuneNascita);
		this.sincronizzaResidenza(viaResidenza, jsonLuogoResidenza, statoEsteroResidenza);
		this.riferimento = riferimento;
	}
}
