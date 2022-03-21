package it.escsolution.escwebgis.redditiAnnuali.bean;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DecoRedditiDichiarati {

	private String ideTelematico;
	private String codiceFiscaleDic;
	private String codiceQuadro;	
	private String descrizioneQuadro;
	private String valoreContabile;
	private String annoImposta;
	private boolean flgImporto;
	private int centDivisore;
	
	public String getIdeTelematico() {
		return ideTelematico;
	}
	public void setIdeTelematico(String ideTelematico) {
		this.ideTelematico = ideTelematico;
	}
	public String getCodiceFiscaleDic() {
		return codiceFiscaleDic;
	}
	public void setCodiceFiscaleDic(String codiceFiscaleDic) {
		this.codiceFiscaleDic = codiceFiscaleDic;
	}
	public String getCodiceQuadro() {
		return codiceQuadro;
	}
	public void setCodiceQuadro(String codiceQuadro) {
		this.codiceQuadro = codiceQuadro;
	}
	public String getDescrizioneQuadro() {
		return descrizioneQuadro;
	}
	public void setDescrizioneQuadro(String descrizioneQuadro) {
		this.descrizioneQuadro = descrizioneQuadro;
	}
	public String getValoreContabile() {
		return valoreContabile;
	}
	public void setValoreContabile(String valoreContabile) {
		this.valoreContabile = valoreContabile;
	}
	public String getAnnoImposta() {
		return annoImposta;
	}
	public void setAnnoImposta(String annoImposta) {
		this.annoImposta = annoImposta;
	}
	public double getValoreContabileNum() {
		try {
			if (getValoreContabile() != null && !getValoreContabile().trim().equals("")) {
				return Double.parseDouble(getValoreContabile().trim()) / centDivisore;
			}			
		} catch (Exception e) {
			//alcuni valori hanno già la stringa formattata, es. 23.449,00 invece di +0000000350
			DecimalFormat df = new DecimalFormat();
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator(',');
			dfs.setGroupingSeparator('.');
			df.setDecimalFormatSymbols(dfs);
			try {
				return df.parse(getValoreContabile().trim()).doubleValue() / centDivisore;
			} catch (Exception e1) {}
		}
		return 0;
	}
	
	public String getValoreContabileInt() {
		try {
			if (getValoreContabile() != null && !getValoreContabile().trim().equals("")) {
				return Integer.valueOf(getValoreContabile().trim()).toString();
			}			
		}catch (Exception e) {}
		return getValoreContabile().trim();
	}
	
	public boolean isFlgImporto() {
		return flgImporto;
	}
	public void setFlgImporto(boolean flgImporto) {
		this.flgImporto = flgImporto;
	}
	public int getCentDivisore() {
		return centDivisore;
	}
	public void setCentDivisore(int centDivisore) {
		this.centDivisore = centDivisore;
	}
	
}
