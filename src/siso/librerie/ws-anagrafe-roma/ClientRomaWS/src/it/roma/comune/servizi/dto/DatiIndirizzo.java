package it.roma.comune.servizi.dto;

public class DatiIndirizzo implements java.io.Serializable{
/***
 * <DatiIndirizzo>
                <Indirizzo>
                  <IndirizzoItaliano>
                    <CodiceVia>031727</CodiceVia>
                    <Toponimo>Via Costantino Corvisieri</Toponimo>
                    <indirizzoBreve>Via C. Corvisieri N.54 S.A Pi.4 I.9</indirizzoBreve>
                    <NumeroCivico>
                      <Numero>54</Numero>
                    </NumeroCivico>
                    <Scala>A</Scala>
                    <Piano>4</Piano>
                    <Interno>9</Interno>
                    <Municipio>02</Municipio>
                  </IndirizzoItaliano>
                </Indirizzo>
              </DatiIndirizzo>
 * **/
	
	private String Toponimo;
	private String indirizzoBreve;
	private String Numero;
	private String Scala;
	private String Piano;
	private String Interno;
	private String Municipio;
	private String cap;
	
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getToponimo() {
		return Toponimo;
	}
	public void setToponimo(String toponimo) {
		Toponimo = toponimo;
	}
	public String getIndirizzoBreve() {
		return indirizzoBreve;
	}
	public void setIndirizzoBreve(String indirizzoBreve) {
		this.indirizzoBreve = indirizzoBreve;
	}
	public String getNumero() {
		return Numero;
	}
	public void setNumero(String numero) {
		Numero = numero;
	}
	public String getScala() {
		return Scala;
	}
	public void setScala(String scala) {
		Scala = scala;
	}
	public String getPiano() {
		return Piano;
	}
	public void setPiano(String piano) {
		Piano = piano;
	}
	public String getInterno() {
		return Interno;
	}
	public void setInterno(String interno) {
		Interno = interno;
	}
	public String getMunicipio() {
		return Municipio;
	}
	public void setMunicipio(String municipio) {
		Municipio = municipio;
	}
	
}
