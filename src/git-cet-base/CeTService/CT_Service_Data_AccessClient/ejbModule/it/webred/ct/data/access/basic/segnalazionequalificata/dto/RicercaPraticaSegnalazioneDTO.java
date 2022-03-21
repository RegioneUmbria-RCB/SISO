package it.webred.ct.data.access.basic.segnalazionequalificata.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Date;

public class RicercaPraticaSegnalazioneDTO extends CeTBaseObject {
	private static final long serialVersionUID = 1L;
	
	private long idPra;
	private long idAll;
	
	//Soggetto Accertato
	private String accCodiFisc;
	private String accCognome;
	private String accNome;
	private String accCodiPiva;
	private String accDenominazione;
	
	//Responsabile
	private String resCodiFisc;
	private String resCognome;
	private String resNome;
	
	//Dati del procedimento
	private String finalita;           //TFL:Tributi fiscali locali, TFE:Tributi fiscali erariali
	private Date dataInizioDa;
	private Date dataInizioA;
	
	private String stato;    
	/* 0: Istruttoria Comunale in corso
	 * 1: Segnalazione in corso presso Agenzia delle Entrate
	 * 2: Iter concluso
	 */
	
	//proprietà per visualizzazione tabella
	private int startm;
	
	private int numberRecord;
	
	//Operatore
	private ArrayList<String> operatori;
	private String sOperatori;
	
	public RicercaPraticaSegnalazioneDTO(String user){
		operatori = new ArrayList<String>();
		operatori.add(user);
	}

	public String getAccCodiFisc() {
		return accCodiFisc;
	}

	public void setAccCodiFisc(String accCodiFisc) {
		this.accCodiFisc = accCodiFisc;
	}

	public String getAccCognome() {
		return accCognome;
	}

	public void setAccCognome(String accCognome) {
		this.accCognome = accCognome;
	}

	public String getAccNome() {
		return accNome;
	}

	public void setAccNome(String accNome) {
		this.accNome = accNome;
	}

	public String getAccCodiPiva() {
		return accCodiPiva;
	}

	public void setAccCodiPiva(String accCodiPiva) {
		this.accCodiPiva = accCodiPiva;
	}

	public String getAccDenominazione() {
		return accDenominazione;
	}

	public void setAccDenominazione(String accDenominazione) {
		this.accDenominazione = accDenominazione;
	}

	public String getResCodiFisc() {
		return resCodiFisc;
	}

	public void setResCodiFisc(String resCodiFisc) {
		this.resCodiFisc = resCodiFisc;
	}

	public String getResCognome() {
		return resCognome;
	}

	public void setResCognome(String resCognome) {
		this.resCognome = resCognome;
	}

	public String getResNome() {
		return resNome;
	}

	public void setResNome(String resNome) {
		this.resNome = resNome;
	}

	public String getFinalita() {
		return finalita;
	}

	public void setFinalita(String finalita) {
		this.finalita = finalita;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Date getDataInizioDa() {
		return dataInizioDa;
	}

	public void setDataInizioDa(Date dataInizioDa) {
		this.dataInizioDa = dataInizioDa;
	}

	public Date getDataInizioA() {
		return dataInizioA;
	}

	public void setDataInizioA(Date dataInizioA) {
		this.dataInizioA = dataInizioA;
	}

	public void addCodOperatore(String cod) {
		operatori.add(cod);
	}
	
	public void setOperatori(ArrayList<String> operatori) {
		this.operatori = operatori;
		
	}

	public ArrayList<String> getOperatori() {
		return operatori;
	}

	public int getStartm() {
		return startm;
	}

	public String getsOperatori() {
		sOperatori = "";
		int i = 0;
		for(String o : operatori){
			sOperatori += o ;
			sOperatori = (i == (operatori.size()-1)? sOperatori : sOperatori + ", ");
			i++;
		}
		
		return sOperatori;
		
	}

	public void setsOperatori(String sOperatori) {
		this.sOperatori = sOperatori;
		
	}

	public void setStartm(int startm) {
		this.startm = startm;
	}

	public int getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(int numberRecord) {
		this.numberRecord = numberRecord;
	}

	public void setIdPra(long idPra) {
		this.idPra = idPra;
	}

	public long getIdPra() {
		return idPra;
	}

	public void setIdAll(long idAll) {
		this.idAll = idAll;
	}

	public long getIdAll() {
		return idAll;
	}
	
	public String getAccFinalitaDecoded(){
		String s = "";
		if("TFL".equalsIgnoreCase(this.finalita))
			s = "Tributi fiscali locali";
		else if("TFE".equalsIgnoreCase(this.finalita))
			s = "Tributi fiscali erariali";
		return s;
	}
	
	public String getAccStatoDecoded(){
		String s = "";
		if("0".equalsIgnoreCase(stato))
			s = "Istruttoria Comunale in corso";
		else if("1".equalsIgnoreCase(stato))
			s = "Segnalazione in corso presso Agenzia delle Entrate";
		else if ("2".equalsIgnoreCase(stato))
			s = "Iter concluso";
		return s;
	}

}
