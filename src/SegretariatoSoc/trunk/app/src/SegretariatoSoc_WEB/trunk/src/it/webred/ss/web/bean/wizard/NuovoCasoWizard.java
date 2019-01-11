package it.webred.ss.web.bean.wizard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.webred.ss.web.bean.SegretariatoSocBaseBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jboss.logging.Logger;
import org.primefaces.event.FlowEvent;

@ManagedBean
@SessionScoped
public class NuovoCasoWizard extends SegretariatoSocBaseBean {  
	       	
    private boolean skip;
    private String firstname = "";
    private String city = "";
    private String lastname = "";
    private int age = 0;
    private String street = "";
    private String postalCode = "";
    private String phone = "";
    private String info = "";
    
    private String ufficio = "";
    private List<String> uffici = new ArrayList<String>();
    
    private String assistente = "";
    private List<String> assistenti = new ArrayList<String>();

	private Date date;
	private Date dataNascita;
	
	private boolean comunitario = true;
	private String luogoNascita;
	
	private boolean maschio = true;
	
	private int statoCivile;
	
	private String codiceFiscale;
	private String medico;

	private List<String> medici;
	private String telefono;
	private String email;
	private String cel;
	
	public void setComunitarioChanged(){
		this.comunitario = !this.comunitario;
	}
	
	public String getMedico() {
		return medico;
	}
	public void setMedico(String medico) {
		this.medico = medico;
	}
	public List<String> getMedici() {
		return medici;
	}
	public void setMedici(List<String> medici) {
		this.medici = medici;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCel() {
		return cel;
	}
	public void setCel(String cel) {
		this.cel = cel;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public void setStatoCivile(int statoCivile){
		this.statoCivile = statoCivile;
	}
	public int getStatoCivile(){
		return this.statoCivile;
	}
	
	public void SetMaschio(boolean maschio){
		this.maschio = maschio;
	}
	public boolean getMaschio(){
		return this.maschio;
	}
	
	public void setLuogoNascita(String luogoNascita){
		this.luogoNascita = luogoNascita;
	}
	
	public String getLuogoNascita(){
		return this.luogoNascita;
	}
	
	public void setComunitario(boolean comunitario){
		this.comunitario = comunitario;
	}
	
	public boolean getComunitario(){
		return this.comunitario;
	}
    
	public List<String> getUffici() {
		if(uffici.isEmpty()){
			uffici.add("Minori");
			uffici.add("Adulti");
			uffici.add("Disabili");
		}
		return uffici;
	}

	public void setUffici(List<String> uffici) {
		this.uffici = uffici;
	}
	
	public List<String> getAssistenti() {
		if(assistenti.isEmpty()){
			assistenti.add("Albertini Silvia");
			assistenti.add("Azzoni Maurizia");
		}
		return assistenti;
	}

	public void setAssistenti(List<String> assistenti) {
		this.assistenti = assistenti;
	}
	
	public String getAssistente(){
		return this.assistente;
	}
	
	public void setAssistente(String assistente){
		this.assistente = assistente;
	}
    
    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date date) {
		this.dataNascita = date;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
  
   /* public User getUser() {  
        return user;  
    } */ 
  
   /* public void setUser(User user) {  
        this.user = user;  
    }  */ 
      
    public void save(ActionEvent actionEvent) {  
        //Persist user  
          
        FacesMessage msg = new FacesMessage("Successful", "Welcome");  
        FacesContext.getCurrentInstance().addMessage(null, msg);
        //addInfo("Welcome");
    }  
      
    public boolean isSkip() {  
        return skip;  
    }  
  
    public void setSkip(boolean skip) {  
        this.skip = skip;  
    }
    
  
    public String onFlowProcess(FlowEvent event) {  
        logger.info("Current wizard step:" + event.getOldStep());  
        logger.info("Next step:" + event.getNewStep());  
          
        if(skip) {  
            skip = false;   //reset in case user goes back  
            return "confirm";  
        }  
        else {  
            return event.getNewStep();  
        }  
    }  
    
    public List<String> completeLuogoNascita(String query){
    	List<String> luoghi = new ArrayList<String>();
    	String temp = "luogo_";
    	if(comunitario)
    		temp+="comunitario_";
    	else
    		temp+="non_comunitario_";
    		
    	for(int i=0; i<20; i++)
    		luoghi.add(temp+i);
    	
    	return luoghi;
    }
} 
