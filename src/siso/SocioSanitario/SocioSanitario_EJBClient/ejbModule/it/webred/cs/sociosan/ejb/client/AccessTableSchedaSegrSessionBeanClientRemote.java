package it.webred.cs.sociosan.ejb.client;

import it.webred.cs.csa.ejb.dto.SegnalazioneDTO;
import it.webred.cs.sociosan.ejb.dto.SegnalazioneSerenaDTO;
import it.webred.ct.support.validation.CeTToken;

import javax.ejb.Remote;

@Remote
public interface AccessTableSchedaSegrSessionBeanClientRemote {
	/**
	 * Esegue il salvataggio delle informazioni ricevute da Serena sul SISO e restituisce l'identificativo di riferimento
	 * che servirà a Serena per successive interrogazioni (che passeranno per {@link #getStatusSegnalazioneSerena(CeTToken, Long)})
	 * sullo stato di lavorazione della segnalazione
	 * 
	 * @param tok	Token di sicurezza, ricevuto dalla chiamata al servizio Rest
	 * @param dto	Wrapper contenente i dati ricevuti dalla chiamata Rest <code>InvioSegnalazioneRestService</code>
	 * @return	Identificativo della segnalazione; di fatto, si tratta di <code>CS_SCHEDE_ALTRA_PROVENIENZA.ID_SCHEDA_SEGR</code>
	 */
	public long saveSegnalazioneSerena(CeTToken tok, SegnalazioneDTO dto);
	
	/**
	 * Interroga il SISO sullo stato di lavorazione della segnalazione Serena identificata da <code>idSchedaSegr</code>
	 * 
	 * @param tok	Token di sicurezza, ricevuto dalla chiamata al servizio Rest
	 * @param idSchedaSegr	Identificativo della segnalazione Serena
	 * @return	Wrapper contenente il record di <code>CS_SS_SCHEDA_SEGR</code> corrispondente a <code>idSchedaSegr</code>,
	 * 			dal quale si possono estrarre le informazioni dai vari campi della tabela (es: <code>STATO</code>,
	 * 			<code>NOTA_STATO</code>...)
	 */
	public SegnalazioneSerenaDTO getStatusSegnalazioneSerena(CeTToken tok, Long idSchedaSegr);
}
