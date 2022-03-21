package it.webred.ct.data.model.concedilizie;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;
import it.webred.ct.data.model.locazioni.LocazioneAPK;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name="MI_CONC_EDILIZIE_VISURE_D")
public class ConcEdilizieVisureDoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String inxdoc;
	private String cdr;
	private String vlm;
	private String pag;
	private String ext;
	private String sizee;
	private String npag;
	private String filee;
	
	public ConcEdilizieVisureDoc(){}
	
	public String getInxdoc() {
		return inxdoc;
	}
	public void setInxdoc(String inxdoc) {
		this.inxdoc = inxdoc;
	}
	public String getCdr() {
		return cdr;
	}
	public void setCdr(String cdr) {
		this.cdr = cdr;
	}
	public String getVlm() {
		return vlm;
	}
	public void setVlm(String vlm) {
		this.vlm = vlm;
	}
	public String getPag() {
		return pag;
	}
	public void setPag(String pag) {
		this.pag = pag;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getSizee() {
		return sizee;
	}
	public void setSizee(String sizee) {
		this.sizee = sizee;
	}
	public String getNpag() {
		return npag;
	}
	public void setNpag(String npag) {
		this.npag = npag;
	}
	public String getFilee() {
		return filee;
	}
	public void setFilee(String filee) {
		this.filee = filee;
	}

}