package it.webred.mui.model;

import it.webred.mui.AppProperties;
import it.webred.mui.consolidation.ViarioDecoder;
import it.webred.mui.input.MuiFornituraParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import net.skillbill.logging.Logger;

public class Residenza implements Comparable {

	private static ViarioDecoder viario = new ViarioDecoder();
	
	private String codiceVia = null;

	private String numeroCivico = null;

	private String dataDa = null;

	private String dataA = null;

	private Date dataDaDate = null;

	private Date dataADate = null;
	
	private String civicoUnico = null;
	

	private static SimpleDateFormat _formatter = new SimpleDateFormat(
			"dd/MM/yyyy");

	public String getCodiceVia() {
		try {
			String newCodiceVia = String.valueOf(Integer.valueOf(codiceVia));
			codiceVia = newCodiceVia;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return codiceVia;
	}

	public String getDataA() {
		if (dataADate != null) {
			return _formatter.format(dataADate);
		} else {
			return null;
		}
	}

	public String getDataDa() {
		if (dataDaDate != null) {
			return _formatter.format(dataDaDate);
		} else {
			return null;
		}
	}

	public String getNumeroCivico() {
		return numeroCivico;
	}

	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}

	public void setDataA(String dataA) {
		this.dataA = dataA;
		if (dataA != null) {
			try {
				setDataADate(MuiFornituraParser.dateParser.parse(this.dataA
						.replace("/", "")));
				Logger.log().info(
						this.getClass().getName(),
						"Residenza java.util.Date DataA ="
								+ MuiFornituraParser.dateParser
										.format(getDataADate()));
			} catch (Throwable e) {
				Logger.log().info(this.getClass().getName(),
						"Residenza !java.util.Date DataA =" + getDataA());
			}
		}
	}

	public void setDataDa(String dataDa) {
		this.dataDa = dataDa;
		if (dataDa != null) {
			try {
				setDataDaDate(MuiFornituraParser.dateParser.parse(this.dataDa
						.replace("/", "")));
				Logger.log().info(
						this.getClass().getName(),
						"Residenza java.util.Date DataDa ="
								+ MuiFornituraParser.dateParser
										.format(getDataDaDate()));
			} catch (Throwable e) {
				Logger.log().info(this.getClass().getName(),
						"Residenza !java.util.Date DataDa =" + getDataDa());
			}
		}
	}

	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = evalToNumeroCivico(numeroCivico);
	}

	public Date getDataADate() {
		return dataADate;
	}

	public Date getDataDaDate() {
		return dataDaDate;
	}

	public void setDataADate(Date dataADate) {
		this.dataADate = dataADate;
	}

	public void setDataDaDate(Date dataDaDate) {
		this.dataDaDate = dataDaDate;
	}

	public boolean matchOggetto(MiConsOggetto ogg) {
		Iterator<CodiceViaCivico> codiciViaCivici = ogg.getCodiceViaCivicos();
		boolean match = false;
		while (codiciViaCivici.hasNext()) {
			CodiceViaCivico cvc = codiciViaCivici.next();
			Logger.log().info(
					this.getClass().getName(),
					"Residenza comparing " + getNumeroCivico() + "<->"
							+ cvc.getCivico() + "  " + getCodiceVia() + "<->"
							+ cvc.getCodiceVia() + " oggetto.iid=" + ogg.getIid());
			
			
			
			// 19-9-2008 a milano andava bene ma ora a castrovillari non ha senso
			// dunque si usa Ario
			// tranne che a Milano visto che per ora non c'è Ario
			if (AppProperties.getProperties().get(AppProperties.BELFIORE).equalsIgnoreCase("F205")) {
				match = (compareNullableValue(getCodiceVia(), cvc.getCodiceVia()) == 0 && compareNullableValue(
						getNumeroCivico(), evalToNumeroCivico(cvc.getCivico())) == 0);
			} else {
				if (getCivicoUnico()!=null) // se è null non bado a confrontare niente
					match = (compareNullableValue(getCivicoUnico(), cvc.getCivicoUnico()) == 0);
			}
			
			if(match) {
				break;
			}
			
		}
		return match;
	}

	private String evalToNumeroCivico(String numeroCivico) {
		String newNumeroCivico = null;
		if (numeroCivico != null) {
			try {
				newNumeroCivico = String.valueOf(Integer.valueOf(numeroCivico));
			} catch (Exception e) {
				newNumeroCivico = numeroCivico.trim().toUpperCase();
			}
		}
		return newNumeroCivico;
	}

	public int compareTo(Object resid) {
		return compareTo((Residenza) resid);
	}

	public String toString() {
		return "codice via=" + getCodiceVia() + "; numero civico="
				+ getNumeroCivico() + "; dal " + getDataDa() + "<=>"
				+ getDataDaDate() + "; al " + getDataA() + "<=>"
				+ getDataADate();
	}

	private int compareNullableValue(Comparable c1, Comparable c2) {
		int res;
		res = (c1 != null ? (c2 != null ? c1.compareTo(c2) : 1)
				: (c2 == null ? 0 : -1));
		return res;
	}

	public boolean equals(Object obj) {
		try {
			return compareTo((Residenza) obj) == 0;
		} catch (Exception e) {
			return false;
		}
	}
	public String getVia() throws Exception {
		return viario.decodeViaAna(this);
	}

	public int compareTo(Residenza resid) {
//		Logger.log().info(this.getClass().getName(),
//				"Residenza.compareTo(Residenza) : " + this+".compareTo("+resid+")");
//		
		int res;
		res = compareNullableValue(getDataDaDate(), resid.getDataDaDate());
		if (res == 0) {
			res = compareNullableValue(getDataADate(), resid.getDataADate());
		}
		if (res == 0) {
			res = compareNullableValue(getCodiceVia(), resid.getCodiceVia());
		}
		if (res == 0) {
			res = compareNullableValue(getNumeroCivico(), resid
					.getNumeroCivico());
		}
//		Logger.log().info(this.getClass().getName(),
//				"Residenza.compareTo(Residenza) : " + this+".compareTo("+resid+") = "+res);
		return res;
	}

	public String getCivicoUnico() {
		return civicoUnico;
	}

	public void setCivicoUnico(String civicoUnico) {
		this.civicoUnico = civicoUnico;
	}


}
