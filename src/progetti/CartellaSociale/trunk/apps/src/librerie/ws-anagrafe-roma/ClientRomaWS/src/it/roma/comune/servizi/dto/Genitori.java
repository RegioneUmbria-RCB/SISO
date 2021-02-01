package it.roma.comune.servizi.dto;

public class Genitori implements java.io.Serializable{
 /**
  *   <Genitori>
                  <Padre>
                    <Nominativo>PAOLO</Nominativo>
                  </Padre>
                  <Madre>
                    <Nominativo>BERUCCI MICHELA</Nominativo>
                  </Madre>
                </Genitori>
  * **/
	
	private String Padre;
	private String Madre;
	private String NominativoPadre;
	private String NominativoMadre;
	
	public String getPadre() {
		return Padre;
	}
	public void setPadre(String padre) {
		Padre = padre;
	}
	public String getMadre() {
		return Madre;
	}
	public void setMadre(String madre) {
		Madre = madre;
	}
	public String getNominativoPadre() {
		return NominativoPadre;
	}
	public void setNominativoPadre(String nominativoPadre) {
		NominativoPadre = nominativoPadre;
	}
	public String getNominativoMadre() {
		return NominativoMadre;
	}
	public void setNominativoMadre(String nominativoMadre) {
		NominativoMadre = nominativoMadre;
	}
}
