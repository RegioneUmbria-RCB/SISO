package it.webred.ss.web.bean.wizard;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;
import it.webred.ss.data.model.SsSchedaRiferimento;

public class PersonaRiferimento {
	
	private Long id;
	private String nome;
	private String cognome;
	
	private boolean problemi;
	private String qualiProblemi;
	
	private String telefono;
	private String recapito;
	
	private String cellulare;
	private String email;
	
	private Date dataNascita;
	private ComuneNazioneNascitaMan comuneNazioneNascitaMan=new ComuneNazioneNascitaMan();
	private SessoBean datiSesso=new SessoBean();
	private String codStatoCivile;
	private Long codRelazione;
	private boolean affidatario = false;//SISO-906
	//private String parentela;
	
	private boolean comeSegnalante = false;
	
	public PersonaRiferimento(Long id){
		super();
		this.id = id;
	}
	
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public boolean isProblemi() {
		return problemi;
	}
	public void setProblemi(boolean problemi) {
		this.problemi = problemi;
	}
	public String getQualiProblemi() {
		return qualiProblemi;
	}
	public void setQualiProblemi(String qualiProblemi) {
		this.qualiProblemi = qualiProblemi;
	}
	public boolean isComeSegnalante() {
		return comeSegnalante;
	}
	public void setComeSegnalante(boolean comeSegnalante) {
		this.comeSegnalante = comeSegnalante;
	}
	public boolean getComeSegnalante(){
		return comeSegnalante;
	}
	
	public void initFromSegnalante(Segnalante segnalante){
		nome = segnalante.getNome();
		cognome = segnalante.getCognome();
		//cf = segnalante.getCf();
		if(segnalante.getCodRelazione()!= null)
		 codRelazione = segnalante.getCodRelazione();
		affidatario = segnalante.isAffidatario(); //SISO-906
		telefono = segnalante.getTelefono();
		cellulare = segnalante.getCellulare();
		email = segnalante.getEmail();
		if (segnalante.getComune()!=null)
			recapito = segnalante.getIndirizzo() + " " + segnalante.getComune().getDenominazione() + " (" + segnalante.getComune().getSiglaProv() +")";
		else
			recapito = null;
		
		dataNascita = segnalante.getDataNascita();
		codStatoCivile = segnalante.getCodStatoCivile();
		datiSesso = segnalante.getDatiSesso();
		comuneNazioneNascitaMan = segnalante.getComuneNazioneNascitaMan();
	}
	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getRecapito() {
		return recapito;
	}
	public void setRecapito(String recapito) {
		this.recapito = recapito;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public ComuneNazioneNascitaMan getComuneNazioneNascitaMan() {
		return comuneNazioneNascitaMan;
	}
	public void setComuneNazioneNascitaMan(ComuneNazioneNascitaMan comuneNazioneNascitaMan) {
		this.comuneNazioneNascitaMan = comuneNazioneNascitaMan;
	}
	public SessoBean getDatiSesso() {
		return datiSesso;
	}
	public void setDatiSesso(SessoBean datiSesso) {
		this.datiSesso = datiSesso;
	}
	public String getCodStatoCivile() {
		return codStatoCivile;
	}
	public void setCodStatoCivile(String codStatoCivile) {
		this.codStatoCivile = codStatoCivile;
	}
	public Long getCodRelazione() {
		return codRelazione;
	}
	public void setCodRelazione(Long codRelazione) {
		this.codRelazione = codRelazione;
	}
	public void fillModel(SsSchedaRiferimento dataModel) {
		dataModel.setId(id);
		dataModel.setCognome(cognome);
		dataModel.setNome(nome);
		dataModel.setRelazioneId((codRelazione!=null && codRelazione>0) ? codRelazione : null);
		dataModel.setAffidatario(affidatario);//SISO-906
		dataModel.setProblemi(problemi);
		dataModel.setTelefono(telefono);
		dataModel.setRecapito(recapito);
		dataModel.setCel(cellulare);
		dataModel.setEmail(email);
		if(problemi)
			dataModel.setProblemi_desc(qualiProblemi);
		dataModel.setDataNascita(dataNascita);
		dataModel.setCodStatoCivile(codStatoCivile);
		dataModel.setSesso(datiSesso.getSesso());	
		ComuneBean cb = this.comuneNazioneNascitaMan.getComuneNascitaMan().getComune();
		AmTabNazioni nb = this.comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione();
		dataModel.setComuneNascitaCod(cb!=null ? cb.getCodIstatComune() : null);
		dataModel.setComuneNascitaDes(cb!=null ? cb.getDenominazione() : null);
		dataModel.setProvNascitaCod(cb!=null ? cb.getSiglaProv():null);
		dataModel.setStatoNascitaCod(nb!=null ? nb.getCodIstatNazione() : null);
		dataModel.setStatoNascitaDes(nb!=null ? nb.getNazione(): null);		
	}
	public void initFromModel(SsSchedaRiferimento riferimento, boolean copia) {
		if(riferimento != null){
			if(!copia)
				id = riferimento.getId();
			nome = riferimento.getNome();
			cognome = riferimento.getCognome();
			codRelazione = riferimento.getRelazioneId();
			if (riferimento.getAffidatario() != null)
			   affidatario = riferimento.getAffidatario();//SISO-906
			problemi = riferimento.getProblemi();
			qualiProblemi = riferimento.getProblemi_desc();
			telefono= riferimento.getTelefono();
			recapito= riferimento.getRecapito();
			cellulare = riferimento.getCel();
			email = riferimento.getEmail();
			dataNascita = riferimento.getDataNascita();
			codStatoCivile = riferimento.getCodStatoCivile();
			datiSesso.setSesso(riferimento.getSesso());
			if(riferimento.getComuneNascitaCod()!=null){
				boolean attivo = CsUiCompBaseBean.isComuneItaAttivoByIstat(riferimento.getComuneNascitaCod());
		    	ComuneBean comuneBean = new ComuneBean(riferimento.getComuneNascitaCod(), riferimento.getComuneNascitaDes(), riferimento.getProvNascitaCod(), attivo);
		    	this.comuneNazioneNascitaMan.getComuneNascitaMan().setComune(comuneBean);
		    }else if(riferimento.getStatoNascitaCod()!=null){
		    	AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(riferimento.getStatoNascitaCod(), riferimento.getStatoNascitaDes());
				comuneNazioneNascitaMan.getNazioneNascitaMan().setNazione(amTabNazioni);
				comuneNazioneNascitaMan.setNazioneValue();
			}
		}
		
	}
    //SISO-906	
	public boolean isAffidatario() {
		return affidatario;
	}
	public void setAffidatario(boolean affidatario) {
		this.affidatario = affidatario;
	}
	
	public boolean isValorizzato(){
		return this.isComeSegnalante() || (!StringUtils.isBlank(this.getNome()) && !StringUtils.isBlank(this.getCognome()));
	}
	
}
