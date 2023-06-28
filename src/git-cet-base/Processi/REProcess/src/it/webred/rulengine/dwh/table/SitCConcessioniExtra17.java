package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;
import oracle.sql.CLOB;

public class SitCConcessioniExtra17 extends TabellaDwhMultiProv
{

	private RelazioneToMasterTable idExtCConcessioni = new RelazioneToMasterTable(SitCConcessioni.class,new ChiaveEsterna());
	private CLOB altriNumeroPg;
	private CLOB altriAnnoPg;
	private CLOB altriSubPg;
	private CLOB altriDataPg;
	private CLOB altriOggettoPg;
	private CLOB npgAttoPratRif;
	private CLOB annoPgAttoPratRif;
	private CLOB praticheCollegate;
	private String codUfficio;

	public Relazione getIdExtCConcessioni()
	{
		return idExtCConcessioni;
	}

	public void setIdExtCConcessioni(ChiaveEsterna idExtCConcessioni)
	{
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitCConcessioni.class,idExtCConcessioni);
		this.idExtCConcessioni = r;	
	}
	
	public String getValueForCtrHash() {		
		return (String)idExtCConcessioni.getRelazione().getValore() +
				altriNumeroPg +
				altriAnnoPg +
				altriSubPg +
				altriDataPg +
				altriOggettoPg +
				npgAttoPratRif +
				annoPgAttoPratRif +
				praticheCollegate +
				(codUfficio == null ? "" : codUfficio) +
				getProvenienza();
	}

	public CLOB getAltriNumeroPg() {
		return altriNumeroPg;
	}

	public void setAltriNumeroPg(CLOB altriNumeroPg) {
		this.altriNumeroPg = altriNumeroPg;
	}

	public CLOB getAltriAnnoPg() {
		return altriAnnoPg;
	}

	public void setAltriAnnoPg(CLOB altriAnnoPg) {
		this.altriAnnoPg = altriAnnoPg;
	}

	public CLOB getAltriSubPg() {
		return altriSubPg;
	}

	public void setAltriSubPg(CLOB altriSubPg) {
		this.altriSubPg = altriSubPg;
	}

	public CLOB getAltriDataPg() {
		return altriDataPg;
	}

	public void setAltriDataPg(CLOB altriDataPg) {
		this.altriDataPg = altriDataPg;
	}

	public CLOB getAltriOggettoPg() {
		return altriOggettoPg;
	}

	public void setAltriOggettoPg(CLOB altriOggettoPg) {
		this.altriOggettoPg = altriOggettoPg;
	}

	public CLOB getNpgAttoPratRif() {
		return npgAttoPratRif;
	}

	public void setNpgAttoPratRif(CLOB npgAttoPratRif) {
		this.npgAttoPratRif = npgAttoPratRif;
	}

	public CLOB getAnnoPgAttoPratRif() {
		return annoPgAttoPratRif;
	}

	public void setAnnoPgAttoPratRif(CLOB annoPgAttoPratRif) {
		this.annoPgAttoPratRif = annoPgAttoPratRif;
	}

	public CLOB getPraticheCollegate() {
		return praticheCollegate;
	}

	public void setPraticheCollegate(CLOB praticheCollegate) {
		this.praticheCollegate = praticheCollegate;
	}

	public String getCodUfficio() {
		return codUfficio;
	}

	public void setCodUfficio(String codUfficio) {
		this.codUfficio = codUfficio;
	}	

}
