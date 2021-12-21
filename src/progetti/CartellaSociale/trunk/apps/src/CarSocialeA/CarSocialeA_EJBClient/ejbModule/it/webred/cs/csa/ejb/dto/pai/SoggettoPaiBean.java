package it.webred.cs.csa.ejb.dto.pai;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsPaiMastSogg;

public class SoggettoPaiBean implements Serializable  {
private static final long serialVersionUID = 1L;
	
	private CsASoggettoLAZY csASoggetto;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String cittadinanza;
	private String cittadinanza2;
//	private Date dataNascita;
	private Integer annoNascita;
	private String sessoBeneficiario; //SISO-1138
		
	
	private String viaResidenza;
	private String jsonComuneResidenza;
	private String codiceNazioneResidenzaEstero;
	
	private boolean datiValidi=false;
	private boolean allineatoResidenza;
	private boolean riferimento;
	
	private boolean nazioneResidenzaNonDefinita=false;
	
	public String getViaResidenza() {
		return viaResidenza;
	}

	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}

	public String getCittadinanza2() {
		return cittadinanza2;
	}

	public void setCittadinanza2(String cittadinanza2) {
		this.cittadinanza2 = cittadinanza2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public SoggettoPaiBean(boolean riferimento){
		this.cognome="";
		this.nome="";
		this.codiceFiscale="";
		this.riferimento=riferimento;
		this.cittadinanza=null;
		this.cittadinanza2=null;
		this.jsonComuneResidenza=null;
		this.viaResidenza="";
	}
	
	public SoggettoPaiBean(String cognome, String nome, String cf, String cittadinanza, Date dataNascita, 
								  String viaResidenza, String comuneResidenza, String sesso){
		this.cognome=cognome;
		this.nome = nome;
		this.codiceFiscale = cf;
		this.cittadinanza=cittadinanza;
		this.annoNascita = this.getAnno(dataNascita);
		
		this.viaResidenza = viaResidenza;
		this.jsonComuneResidenza = comuneResidenza;
		this.sessoBeneficiario = sesso; //SISO-1138
		//TODO----verificare se può servire
		this.datiValidi = isValorizzato(); // Se manca qualcosa devo inserirlo a mano, quindi non posso ritenerlo valido
	}
	
     public SoggettoPaiBean(CsAAnagrafica anagraficaSelezionata){
		
		//TODO : verificare che non serva la residenza
		this.cognome=anagraficaSelezionata.getCognome();
		this.nome = anagraficaSelezionata.getNome();
		this.codiceFiscale = anagraficaSelezionata.getCf();
		this.cittadinanza = anagraficaSelezionata.getCittadinanza();
		this.cittadinanza2 = anagraficaSelezionata.getCittadinanza2();
		this.annoNascita = this.getAnno(anagraficaSelezionata.getDataNascita());
 		this.sessoBeneficiario = anagraficaSelezionata.getSesso() ;
	}
     
	public SoggettoPaiBean(CsPaiMastSogg paiSogg) {
		this.csASoggetto = paiSogg.getCaso() != null ? paiSogg.getCaso().getCsASoggetto() : null;
		
		this.cognome = paiSogg.getCognome();
		this.nome = paiSogg.getNome();
		this.codiceFiscale = paiSogg.getCf();
		this.cittadinanza = paiSogg.getCittadinanza();
		this.cittadinanza2 = paiSogg.getSecondaCittadinanza();
		this.annoNascita = paiSogg.getAnnoNascita();
		this.jsonComuneResidenza = paiSogg.getComuneResidenza();
		this.viaResidenza = paiSogg.getViaResidenza();
		this.codiceNazioneResidenzaEstero = paiSogg.getNazioneResidenza();
		this.sessoBeneficiario = paiSogg.getSesso();
		this.riferimento = paiSogg.getIntestatario();
	}
	
	public SoggettoPaiBean( CsASoggettoLAZY soggetto, String viaResidenza, String jsonLuogoResidenza, boolean riferimento) {
		csASoggetto = soggetto;
	
		this.sincronizzaDatiAnagrafici();
		this.sincronizzaResidenza(viaResidenza, jsonLuogoResidenza);
		
		this.verificaDatiValidi(viaResidenza, jsonLuogoResidenza);
		
		this.riferimento = riferimento;
	}
	//SISO-962 Fine
	
	public void verificaAllineamento(CsASoggettoLAZY soggetto, String viaResidenza, String jsonLuogoResidenza, boolean riferimento) {
		csASoggetto = soggetto;
		
		if(this.isAllineatoAnagrafica())
			this.sincronizzaDatiAnagrafici();
		
		if(this.isAllineatoResidenza(viaResidenza, jsonLuogoResidenza))	
		   this.sincronizzaResidenza(viaResidenza, jsonLuogoResidenza);
		
		this.verificaDatiValidi(viaResidenza, jsonLuogoResidenza);
		
		this.riferimento = riferimento;
	}
	
	
	public boolean isAllineatoAnagrafica( ){
		if(csASoggetto!=null){
			CsAAnagrafica ana = csASoggetto.getCsAAnagrafica();
			return StringUtils.equals(this.codiceFiscale,ana.getCf()) && 
				   StringUtils.equals(this.nome,ana.getNome()) &&
				   StringUtils.equals(this.cognome,ana.getCognome()) &&
				   getAnno(ana.getDataNascita()).equals(this.annoNascita) &&
				   StringUtils.equals(this.cittadinanza,ana.getCittadinanza()) && 
				   StringUtils.equals(this.cittadinanza2,ana.getCittadinanza2()) &&
				   StringUtils.equals(this.sessoBeneficiario,ana.getSesso());
		}else return true;
	}
	
	public boolean isAllineatoResidenza(String viaResidenza, String jsonLuogoResidenza){
		if(csASoggetto!=null){
			CsAAnagrafica ana = csASoggetto.getCsAAnagrafica();
			
			String viaReplaced = viaResidenza!=null ? viaResidenza.replaceAll(",","") : viaResidenza;
			
			this.allineatoResidenza = 
				   StringUtils.equalsIgnoreCase(this.codiceFiscale,ana.getCf()) && 
				   StringUtils.equals(this.jsonComuneResidenza,jsonLuogoResidenza) &&
				   (StringUtils.equalsIgnoreCase(this.viaResidenza,viaResidenza) || StringUtils.equalsIgnoreCase(this.viaResidenza, viaReplaced)); //Alcune vie nel mast sono scritte senza virgola
		}else allineatoResidenza = true;
		return allineatoResidenza;
	}
	
	public void sincronizzaDatiAnagrafici(){
		if(csASoggetto!=null){
			nome = csASoggetto.getCsAAnagrafica().getNome();
			cognome = csASoggetto.getCsAAnagrafica().getCognome();
			codiceFiscale = csASoggetto.getCsAAnagrafica().getCf();
			annoNascita = getAnno(csASoggetto.getCsAAnagrafica().getDataNascita());
			cittadinanza = csASoggetto.getCsAAnagrafica().getCittadinanza();
			cittadinanza2 = csASoggetto.getCsAAnagrafica().getCittadinanza2();			
			sessoBeneficiario =  csASoggetto.getCsAAnagrafica().getSesso(); //SISO-1138-
		}
		verificaDatiValidi(null, null);
	}
	
	private void verificaDatiValidi(String viaResidenza, String jsonLuogoResidenza){
		if((viaResidenza != null && jsonLuogoResidenza != null) || StringUtils.equalsIgnoreCase(viaResidenza, DataModelCostanti.SENZA_FISSA_DIMORA))
			this.isAllineatoResidenza(viaResidenza, jsonLuogoResidenza);
		
		if(this.csASoggetto!=null && (this.isAllineatoAnagrafica() & this.allineatoResidenza))
			this.datiValidi=true;
	}
	
	public void sincronizzaResidenza(String viaResidenza, String jsonLuogoResidenza){
		this.jsonComuneResidenza = jsonLuogoResidenza;
		this.viaResidenza = viaResidenza;
		this.verificaDatiValidi(viaResidenza, jsonLuogoResidenza);
	}
	
	public boolean isValorizzato() {
		boolean validato =
			   !StringUtils.isBlank(nome) && 
			   !StringUtils.isBlank(cognome) && 
			   !StringUtils.isBlank(codiceFiscale) && !"0000000000000000".equals(codiceFiscale) &&
			   !StringUtils.isBlank(cittadinanza) &&
			   (annoNascita!=null && annoNascita>0);
		
		if(this.riferimento){
			
			validato = validato && !StringUtils.isBlank(sessoBeneficiario);
			
			boolean statoValido = !StringUtils.isBlank(codiceNazioneResidenzaEstero) || nazioneResidenzaNonDefinita;
			boolean comuneValido = !StringUtils.isBlank(jsonComuneResidenza);
			boolean viaValida = !StringUtils.isBlank(viaResidenza);
			
			   //SISO-962
			 validato = validato && (statoValido || (comuneValido && viaValida) 
					 || StringUtils.equalsIgnoreCase(DataModelCostanti.SENZA_FISSA_DIMORA, viaResidenza));
			   //SISO-962 Fine
		}
	    return validato;	
	}
	 
	private Integer getAnno(Date data){
		if(data!=null){
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(data);
			return calendar1.get(Calendar.YEAR);
		}else return null;
	}
	
	
	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public boolean isRiferimento() {
		return riferimento;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public void setRiferimento(boolean riferimento) {
		this.riferimento = riferimento;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
  //TODO se si lascia AnnoNAscita va cancellata DataNascita
//	public Date getDataNascita() {
//		return dataNascita;
//	}
//
//	public void setDataNascita(Date dataNascita) {
//		this.dataNascita = dataNascita;
//	}
	public Integer getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(Integer annoNascita) {
		this.annoNascita = annoNascita;
	}
	
	public String getJsonComuneResidenza() {
		return jsonComuneResidenza;
	}

	public void setJsonComuneResidenza(String jsonComuneResidenza) {
		this.jsonComuneResidenza = jsonComuneResidenza;
	}

	public String getCodiceNazioneResidenzaEstero() {
		return codiceNazioneResidenzaEstero;
	}

	public void setCodiceNazioneResidenzaEstero(String codiceNazioneResidenzaEstero) {
		this.codiceNazioneResidenzaEstero = codiceNazioneResidenzaEstero;
	}
	
	public String getSessoBeneficiario() {
		return sessoBeneficiario;
	}

	public void setSessoBeneficiario(String sessoBeneficiario) {
		this.sessoBeneficiario = sessoBeneficiario;
	}
	//SISO-1138 FINE

	public Boolean getDatiValidi() {
		return datiValidi;
	}

	public void setDatiValidi(Boolean datiValidi) {
		this.datiValidi = datiValidi;
	}
	
	public boolean isDisabilita(){
		return this.datiValidi && this.isValorizzato();
	}

	public boolean isAllineatoResidenza() {
		if(csASoggetto==null)
			allineatoResidenza=true;
		return allineatoResidenza;
	}

	public void setAllineatoResidenza(boolean allineatoResidenza) {
		this.allineatoResidenza = allineatoResidenza;
	}

	public boolean isNazioneResidenzaNonDefinita() {
		return nazioneResidenzaNonDefinita;
	}

	public void setNazioneResidenzaNonDefinita(boolean nazioneResidenzaNonDefinita) {
		this.nazioneResidenzaNonDefinita = nazioneResidenzaNonDefinita;
	}
}
