package it.roma.comune.servizi.dto;

public class DatiAnagrafeRoma implements java.io.Serializable{

	/*
	 *   <DatiAnagrafeRoma>
                <DatiIndividuo>
                  <CodiceIndividuale>19012391</CodiceIndividuale>
                  <TipoIndividuo>
                    <FlagVivoResidente>0</FlagVivoResidente>
                    <Vivo>Viva</Vivo>
                    <Residente>Residente</Residente>
                  </TipoIndividuo>
                </DatiIndividuo>
                <DatiFamiglia>
                  <CodiceFamiglia>05462371</CodiceFamiglia>
                  <FlagPresenzaInFamiglia>1</FlagPresenzaInFamiglia>
                </DatiFamiglia>
              </DatiAnagrafeRoma>
	 * **/
	private String Descrizione;
	
	private String CodiceIndividuale;
	public String getCodiceIndividuale() {
		return CodiceIndividuale;
	}
	public void setCodiceIndividuale(String codiceIndividuale) {
		CodiceIndividuale = codiceIndividuale;
	}
	public String getFlagVivoResidente() {
		return FlagVivoResidente;
	}
	public void setFlagVivoResidente(String flagVivoResidente) {
		FlagVivoResidente = flagVivoResidente;
	}
	public String getVivo() {
		return Vivo;
	}
	public void setVivo(String vivo) {
		Vivo = vivo;
	}
	public String getResidente() {
		return Residente;
	}
	public void setResidente(String residente) {
		Residente = residente;
	}
//	public String getCodiceFamiglia() {
//		return CodiceFamiglia;
//	}
//	public void setCodiceFamiglia(String codiceFamiglia) {
//		CodiceFamiglia = codiceFamiglia;
//	}
	public String getFlagPresenzaInFamiglia() {
		return FlagPresenzaInFamiglia;
	}
	public void setFlagPresenzaInFamiglia(String flagPresenzaInFamiglia) {
		FlagPresenzaInFamiglia = flagPresenzaInFamiglia;
	}
	public String getDescrizione() {
		return Descrizione;
	}
	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}
	private String FlagVivoResidente;
	private String Vivo;
	private String Residente;
	//private String CodiceFamiglia;
	private String FlagPresenzaInFamiglia;
	
	
}
