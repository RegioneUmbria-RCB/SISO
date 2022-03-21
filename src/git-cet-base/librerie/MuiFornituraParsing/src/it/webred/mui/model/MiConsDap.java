package it.webred.mui.model;

import it.webred.mui.input.MuiFornituraParser;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

public class MiConsDap extends MiConsDapBase {

	private DecimalFormat df = new DecimalFormat("0.00");
	public MiConsDap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MiConsDap(MiDupFornitura miDupFornitura,
			MiDupTitolarita miDupTitolarita) {
		super(miDupFornitura, miDupTitolarita);
		// TODO Auto-generated constructor stub
	}

    public MiConsDap(MiDupFornitura miDupFornitura, MiDupTitolarita miDupTitolarita, String idNota, MiDupNotaTras miDupNotaTras, Boolean flagSkipped, Boolean flagAbitativo, Boolean flagAbitazionePrincipale, Date dataInizialeDate, Date dataFinaleDate, String idSoggettoNota, MiDupSoggetti miDupSoggetti, String idSoggettoCatastale, String tipoSoggetto, String idImmobile, String tipologiaImmobile, BigDecimal dapImporto, Integer dapMesi, Boolean flagDapDiritto, Integer dapNumeroSoggetti, Date dapData, Boolean flagRegoleDapNoDataResidenza, Boolean flagRegoleDapDataResOltre90Giorni, Boolean flagRegoleDapPrecentualePossessoTotaleErrata, Boolean flagRegoleDapSoggettoPossessorePiuImmobili, Boolean flagRegoleDapSoggettoPossessorePiuImmobiliStessoIndirizzo, Boolean flagRegoleDapNoDataComposizioneFamiliare, String codiceFiscale, Integer foglio, String numero, Integer subalterno) {
		super(miDupFornitura, miDupTitolarita, idNota, miDupNotaTras,
				flagSkipped, flagAbitativo, flagAbitazionePrincipale,
				dataInizialeDate, dataFinaleDate, idSoggettoNota,
				miDupSoggetti, idSoggettoCatastale, tipoSoggetto, idImmobile,
				tipologiaImmobile, dapImporto, dapMesi, flagDapDiritto,
				dapNumeroSoggetti, dapData, flagRegoleDapNoDataResidenza, flagRegoleDapDataResOltre90Giorni, flagRegoleDapPrecentualePossessoTotaleErrata, flagRegoleDapSoggettoPossessorePiuImmobili, flagRegoleDapSoggettoPossessorePiuImmobiliStessoIndirizzo, flagRegoleDapNoDataComposizioneFamiliare,
				codiceFiscale, foglio, numero, subalterno
				);
	}
	
	public String getDataIniziale(){
		return (getDataInizialeDate()!=null?MuiFornituraParser.dateParser.format(getDataInizialeDate()):"");
	}
	
	public String getDataFinale(){
		return (getDataFinaleDate()!=null?MuiFornituraParser.dateParser.format(getDataFinaleDate()):"00000000");
	}
	public String getIdNota(){
		return (super.getIdNota()!=null?super.getIdNota():"");
	}
	public String getTipoRec(){
		return "6";
	}
	public String getIdSoggettoNota(){
		return (super.getIdSoggettoNota()!=null?super.getIdSoggettoNota():"");
	}
	public String getIdSoggettoCatastale(){
		return (super.getIdSoggettoCatastale()!=null? super.getIdSoggettoCatastale():"0");
	}
	public String getTipologiaImmobile(){
		return (super.getTipologiaImmobile()!=null? super.getTipologiaImmobile():"F");
	}
	public String getDapImportoAsString(){
		return (super.getDapImporto()!=null?df.format( super.getDapImporto()):"0.00");
	}
	public String getDapMesiAsString(){
		return (super.getDapMesi()!=null?""+super.getDapMesi():"0");
	}
	public String getDapNumeroSoggettiAsString(){
		return (super.getDapNumeroSoggetti()!=null?""+(super.getDapNumeroSoggetti()+1):"1");
	}
	public String getDapDiritto(){
		return (super.getFlagDapDiritto()!=null?(super.getFlagDapDiritto()?"S":"N"):"N");
	}
	public String getDataDap(){
		return (getDapData()!=null?MuiFornituraParser.dateParser.format(getDapData()):"00000000");
	}
	public String getDapEsitoAsString(){
		String val =null;
		if(Boolean.TRUE.equals( getFlagRegoleDapNoDataResidenza())){
			val = "001";
		}
		else if(Boolean.TRUE.equals(getFlagRegoleDapDataResOltre90Giorni())){
			val = "003";
		}
		else if(Boolean.TRUE.equals(getFlagRegoleDapPrecentualePossessoTotaleErrata())){
			val = "005";
		}
		else if(Boolean.TRUE.equals(getFlagRegoleDapSoggettoPossessorePiuImmobiliStessoIndirizzo())){
			val = "011";
		}
		else if(Boolean.TRUE.equals(getFlagRegoleDapSoggettoPossessorePiuImmobili())){
			val = "007";
		}
		else if(Boolean.TRUE.equals(getFlagRegoleDapNoDataComposizioneFamiliare())){
			val = "017";
		}
		else{
			val = "0";
		}
		return val;
	}

}
