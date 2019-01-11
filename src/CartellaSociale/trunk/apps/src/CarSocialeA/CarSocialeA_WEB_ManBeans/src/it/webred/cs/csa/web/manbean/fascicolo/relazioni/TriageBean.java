package it.webred.cs.csa.web.manbean.fascicolo.relazioni;

import it.webred.cs.csa.ejb.dto.TriageItemDTO;
import it.webred.cs.data.model.CsDTriage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TriageBean {

	private String morbilita = "MORBILITA";
	private String alimentazione = "ALIMENTAZIONE";
	private String alvoDiuresi = "ALVO_DIURESI";
	private String mobilita = "MOBILITA";
	private String igienePersonale = "IGIENE_PERSONALE";
	private String statoMentale = "STATO_MENTALE";
	private String conChiVive = "CON_CHI_VIVE";
	private String assistenzaDiretta = "ASSISTENZA_DIRETTA";

	/**
	 * Mappa valori dei campi della tabella TRIAGE es: chiave
	 * "MORB_ASSENTE_O_LIEVE" = 1
	 */
	private HashMap<String, List<TriageItemDTO>> valueMap = new HashMap<String, List<TriageItemDTO>>();

	public TriageBean() {
		initializeValueMap();
	}

	// L'ULTIMO VALORE E' IL PUNTEGGIO DELLA SCHEDA TRIAGE PER SIGNOLO VALORE
	// STRINGA TRIAGE: TIPOLOGIA_DETTAGLIO_PUNTEGGIO
	private void initializeValueMap() {
		// MORBILITA
		List<TriageItemDTO> morbList = new LinkedList<TriageItemDTO>();
		morbList.add(new TriageItemDTO(
				"MORB_ASSENTE_O_LIEVE_0",
				0,
				"ASSENTE O LIEVE",
				"Nessuna compromissione d'organo/sistema o la compromissione non interferisce con la normale attività"));
		morbList.add(new TriageItemDTO("MORB_MODERATO_1", 1, "MODERATO",
				"La compromissione d'organoa/sistema interferisce con la normale attività"));
		morbList.add(new TriageItemDTO("MORB_GRAVE1_2", 2, "GRAVE",
				"La compromissione d'organo/sistema produce disabilità"));
		morbList.add(new TriageItemDTO("MORB_GRAVE2_2", 2, "GRAVE",
				"La compromissione d'organo/sistema mette a repentaglio la sopravvivenza"));
		this.valueMap.put(morbilita, morbList);
		// ALIMENTAZIONE
		List<TriageItemDTO> alimList = new LinkedList<TriageItemDTO>();
		alimList.add(new TriageItemDTO("ALIM_AUTONOMO_0", 0, "AUTONOMO", ""));
		alimList.add(new TriageItemDTO("ALIM_CON_AIUTO_0", 0, "CON AIUTO",
				"Supervisione"));
		alimList.add(new TriageItemDTO("ALIM_DIPENDENZA_SEVERA_1", 1,
				"DIPENDENZA SEVERA", "Imboccamento"));
		alimList.add(new TriageItemDTO("ALIM_ENTERALE_PARENTALE_2", 2,
				"ENTERALE-PARENTALE", ""));
		this.valueMap.put(alimentazione, alimList);
		// ALVO E DIURESI
		List<TriageItemDTO> alvdList = new LinkedList<TriageItemDTO>();
		alvdList.add(new TriageItemDTO("ALVO_D_CONTINENZA_0", 0, "CONTINENZA",
				""));
		alvdList.add(new TriageItemDTO("ALVO_D_INCONTINENZA_URINARIA_0", 0,
				"CONTINENZA PER ALVO INCONTINENZA URINARIA", ""));
		alvdList.add(new TriageItemDTO("ALVO_D_INCONTINENZA_STABILE1_1", 1,
				"INCONTINENZA STABILE", "Per alvo e diuresi (uso pannolone)"));
		alvdList.add(new TriageItemDTO("ALVO_D_INCONTINENZA_STABILE2_1", 1,
				"INCONTINENZA STABILE",
				"Per alvo e diuresi (CVP e/o evacuazione assistita)"));
		this.valueMap.put(alvoDiuresi, alvdList);
		// MOBILITA
		List<TriageItemDTO> mobList = new LinkedList<TriageItemDTO>();
		mobList.add(new TriageItemDTO("MOB_AUTONOMO_0", 0, "AUTONOMO", ""));
		mobList.add(new TriageItemDTO("MOB_CON_MIN_AIUTO_0", 0,
				"CON MINIMO AIUTO", "(qualche difficoltà)"));
		mobList.add(new TriageItemDTO("MOB_CON_AUSILI_1", 1, "CON AUSILI",
				"(uso del bastone, walker, carrozzina...)"));
		mobList.add(new TriageItemDTO("MOB_ALLETTATO_2", 2, "ALLETTATO", ""));
		this.valueMap.put(mobilita, mobList);
		// IGIENE PERSONALE
		List<TriageItemDTO> igiList = new LinkedList<TriageItemDTO>();
		igiList.add(new TriageItemDTO("IGIENE_P_AUTONOMO_0", 0, "AUTONOMO", ""));
		igiList.add(new TriageItemDTO("IGIENE_P_CON_MIN_AIUTO_0", 0,
				"CON MINIMO AIUTO", "(qualche difficoltà)"));
		igiList.add(new TriageItemDTO("IGIENE_P_CON_MOD_AIUTO_1", 1,
				"CON AIUTO MODERATO", ""));
		igiList.add(new TriageItemDTO("IGIENE_P_TOT_DIPENDENZA_2", 2,
				"TOTALE DIPENDENZA", ""));
		this.valueMap.put(igienePersonale, igiList);
		// STATO MENTALE
		List<TriageItemDTO> smentaleList = new LinkedList<TriageItemDTO>();
		smentaleList.add(new TriageItemDTO("STATOMENTALE_LIV1_0", 0,
				"Collaborante, capace di intendere e volere", ""));
		smentaleList.add(new TriageItemDTO("STATOMENTALE_LIV2_0", 0,
				"Collaborante, ma con difficoltà a capire le indicazioni", ""));
		smentaleList
				.add(new TriageItemDTO(
						"STATOMENTALE_LIV3_1",
						1,
						"Non collaborante e con difficoltà a capire le indicazioni",
						""));
		smentaleList
				.add(new TriageItemDTO(
						"STATOMENTALE_LIV4_1",
						1,
						"Non collaborante e gravemente incapace di intendere e volere / segni di disturbi comportamentali",
						""));
		this.valueMap.put(statoMentale, smentaleList);
		// CON CHI VIVE
		List<TriageItemDTO> cviveList = new LinkedList<TriageItemDTO>();
		cviveList.add(new TriageItemDTO("CONCHIVIVE_COPPIA_0", 0,
				"COPPIA, NUCLEO FAMILIARE, ASSISTENTE FAMILIARE", ""));
		cviveList.add(new TriageItemDTO("CONCHIVIVE_SOLO1_0", 0, "SOLO",
				"NON necessita di figure di riferimento;"));
		cviveList.add(new TriageItemDTO("CONCHIVIVE_SOLO2_1", 1,
				"SOLO o COPPIA",
				"Ma necessita di figure di riferimento (es.figli);"));
		cviveList.add(new TriageItemDTO("CONCHIVIVE_SOLO3_2", 2, "SOLO",
				"Nessuna rete di riferimento;"));
		this.valueMap.put(conChiVive, cviveList);
		// ASSISTENZA DIRETTA
		List<TriageItemDTO> assdList = new LinkedList<TriageItemDTO>();
		assdList.add(new TriageItemDTO(
				"ASSIST_ADEGUATA_0",
				0,
				"ADEGUATA",
				"Partecipano, familiari, assistenti familiari, servizi territoriali(SAD, pasti a domicilio,...), vicinato, associazioni"));
		assdList.add(new TriageItemDTO(
				"ASSIST_PARZ_ADEG_1",
				1,
				"PARZIAMENTE ADEGUATA",
				"Affidata solo ai familiari, o solo all'assistenza familiare o solo ai servizi territoriali"));
		assdList.add(new TriageItemDTO(
				"ASSIST_POCO_ADEG_2",
				2,
				"POCO ADEGUATA",
				"Affidata ad un soggetto che non assicura un'assistenza adeguata o sufficiente"));
		assdList.add(new TriageItemDTO("ASSIST_INADEGUATA_2", 2, "INADEGUATA",
				"Non è offerta alcun tipo di assistenza;"));
		this.valueMap.put(assistenzaDiretta, assdList);

	}

	public int calcolaRisultato(CsDTriage triage) {
		int somma = 0;

		somma += getPunteggio(morbilita, triage.getMorbilita());

		somma += getPunteggio(alimentazione, triage.getAlimentazione());

		somma += getPunteggio(alvoDiuresi, triage.getAlvoDiuresi());

		somma += getPunteggio(mobilita, triage.getMobilita());

		somma += getPunteggio(igienePersonale, triage.getIgienePersonale());

		somma += getPunteggio(statoMentale, triage.getStatoMentale());

		somma += getPunteggio(conChiVive, triage.getConChiVive());

		somma += getPunteggio(assistenzaDiretta, triage.getAssistenzaDiretta());

		return somma;
	}

	private int getPunteggio(String tipo, String code) {
		int punteggio = 0;

		TriageItemDTO ti = findTrigeItemByTipoAndCode(tipo, code);
		if(ti != null){
			punteggio = ti.getValue();
		}
		return punteggio;
	}
	
	public String valoreCampoTriage(String code){
		String toReturn = "-";
		
		TriageItemDTO ti = findTrigeItemByCode(code);
		
		if(ti != null){
			toReturn = String.valueOf(ti.getValue());
		}
		
		return toReturn;
	}

	public TriageItemDTO findTrigeItemByTipoAndCode(String tipo, String code) {
		TriageItemDTO toReturn = null;
		if (code != null && !code.isEmpty()) {
			List<TriageItemDTO> listaItem = valueMap.get(tipo);
			for (TriageItemDTO triageItemDTO : listaItem) {
				if (triageItemDTO.getCode().equalsIgnoreCase(code)) {
					toReturn = triageItemDTO;
					break;
				}
			}
		}
		return toReturn;
	}

	public TriageItemDTO findTrigeItemByCode(String code) {
		TriageItemDTO toReturn = null;

		if (code != null && !code.isEmpty()) {
			for (List<TriageItemDTO> list : valueMap.values()) {

				for (TriageItemDTO triageItemDTO : list) {
					if (triageItemDTO.getCode().equalsIgnoreCase(code)) {
						toReturn = triageItemDTO;
						break;
					}
				}

				if (toReturn != null) {
					break;
				}
			}
		}
		return toReturn;
	}

	public String getMorbilita() {
		return morbilita;
	}

	public void setMorbilita(String morbilita) {
		this.morbilita = morbilita;
	}

	public String getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(String alimentazione) {
		this.alimentazione = alimentazione;
	}

	public String getAlvoDiuresi() {
		return alvoDiuresi;
	}

	public void setAlvoDiuresi(String alvoDiuresi) {
		this.alvoDiuresi = alvoDiuresi;
	}

	public String getMobilita() {
		return mobilita;
	}

	public void setMobilita(String mobilita) {
		this.mobilita = mobilita;
	}

	public String getIgienePersonale() {
		return igienePersonale;
	}

	public void setIgienePersonale(String igienePersonale) {
		this.igienePersonale = igienePersonale;
	}

	public String getStatoMentale() {
		return statoMentale;
	}

	public void setStatoMentale(String statoMentale) {
		this.statoMentale = statoMentale;
	}

	public String getConChiVive() {
		return conChiVive;
	}

	public void setConChiVive(String conChiVive) {
		this.conChiVive = conChiVive;
	}

	public String getAssistenzaDiretta() {
		return assistenzaDiretta;
	}

	public void setAssistenzaDiretta(String assistenzaDiretta) {
		this.assistenzaDiretta = assistenzaDiretta;
	}

	public HashMap<String, List<TriageItemDTO>> getValueMap() {
		return valueMap;
	}

}
