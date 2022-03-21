package it.webred.cs.csa.ejb.dto.cartella;

import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.data.model.CsTbSettoreImpiego;
import it.webred.cs.data.model.CsTbTitoloStudio;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class DatiSocialiSintesiDTO implements Serializable{
	
	private Long idDatiSociali;
	
	private CsTbCondLavoro condLavoro;
	private CsTbCondLavoro condLavoroDefault;
	
	private CsTbTitoloStudio titoloStudio;
	private CsTbTitoloStudio titoloStudioDefault;
	
	private CsTbSettoreImpiego settImpiego;
	
	private CsDValutazione familiariConviventi;
	
	private String comuneNascitaCod;
	private String comuneNascitaDes;
	private String provNascitaCod;
	
	private String statoNascitaCod;
	private String statoNascitaDes;
	
	private String tel;
	private String cel;
	private String email;
	
	private String indirizzoDomicilio;
	private String comuneDomicilioCod;
	private String comuneDomicilioDes;
	private String provDomicilioCod;
	
	public CsTbTitoloStudio getTitoloStudio() {
		return titoloStudio;
	}
	public void setTitoloStudio(CsTbTitoloStudio titoloStudio) {
		this.titoloStudio = titoloStudio;
	}
	
	public CsDValutazione getFamiliariConviventi() {
		return familiariConviventi;
	}
	public void setFamiliariConviventi(CsDValutazione familiariConviventi) {
		this.familiariConviventi = familiariConviventi;
	}
	public String getComuneNascitaCod() {
		return comuneNascitaCod;
	}
	public void setComuneNascitaCod(String comuneNascitaCod) {
		this.comuneNascitaCod = comuneNascitaCod;
	}
	public String getComuneNascitaDes() {
		return comuneNascitaDes;
	}
	public void setComuneNascitaDes(String comuneNascitaDes) {
		this.comuneNascitaDes = comuneNascitaDes;
	}
	public String getProvNascitaCod() {
		return provNascitaCod;
	}
	public void setProvNascitaCod(String provNascitaCod) {
		this.provNascitaCod = provNascitaCod;
	}
	public String getStatoNascitaCod() {
		return statoNascitaCod;
	}
	public void setStatoNascitaCod(String statoNascitaCod) {
		this.statoNascitaCod = statoNascitaCod;
	}
	public String getStatoNascitaDes() {
		return statoNascitaDes;
	}
	public void setStatoNascitaDes(String statoNascitaDes) {
		this.statoNascitaDes = statoNascitaDes;
	}
	public Long getIdDatiSociali() {
		return idDatiSociali;
	}
	public void setIdDatiSociali(Long idDatiSociali) {
		this.idDatiSociali = idDatiSociali;
	}
	public CsTbCondLavoro getCondLavoro() {
		return condLavoro;
	}
	public void setCondLavoro(CsTbCondLavoro condLavoro) {
		this.condLavoro = condLavoro;
	}
	public CsTbCondLavoro getCondLavoroDefault() {
		return condLavoroDefault;
	}
	public void setCondLavoroDefault(CsTbCondLavoro condLavoroDefault) {
		this.condLavoroDefault = condLavoroDefault;
	}
	public CsTbTitoloStudio getTitoloStudioDefault() {
		return titoloStudioDefault;
	}
	public void setTitoloStudioDefault(CsTbTitoloStudio titoloStudioDefault) {
		this.titoloStudioDefault = titoloStudioDefault;
	}
	public CsTbSettoreImpiego getSettImpiego() {
		return settImpiego;
	}
	public void setSettImpiego(CsTbSettoreImpiego settImpiego) {
		this.settImpiego = settImpiego;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCel() {
		return cel;
	}
	public void setCel(String cel) {
		this.cel = cel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIndirizzoDomicilio() {
		return indirizzoDomicilio;
	}
	public void setIndirizzoDomicilio(String indirizzoDomicilio) {
		this.indirizzoDomicilio = indirizzoDomicilio;
	}
	public String getComuneDomicilioCod() {
		return comuneDomicilioCod;
	}
	public void setComuneDomicilioCod(String comuneDomicilioCod) {
		this.comuneDomicilioCod = comuneDomicilioCod;
	}
	public String getComuneDomicilioDes() {
		return comuneDomicilioDes;
	}
	public void setComuneDomicilioDes(String comuneDomicilioDes) {
		this.comuneDomicilioDes = comuneDomicilioDes;
	}
	public String getProvDomicilioCod() {
		return provDomicilioCod;
	}
	public void setProvDomicilioCod(String provDomicilioCod) {
		this.provDomicilioCod = provDomicilioCod;
	}	
	
}
