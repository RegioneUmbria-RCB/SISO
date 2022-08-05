package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitCConcessioniExtra17 extends TabellaDwhMultiProv
{

	private RelazioneToMasterTable idExtCConcessioni = new RelazioneToMasterTable(SitCConcessioni.class,new ChiaveEsterna());
	private String altriNumeroPg;
	private String altriAnnoPg;
	private String altriSubPg;
	private String altriDataPg;
	private String altriOggettoPg;
	private String npgAttoPratRif;
	private String annoPgAttoPratRif;
	private String praticheCollegate;

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
				getProvenienza();
	}

	public String getAltriNumeroPg() {
		return altriNumeroPg;
	}

	public void setAltriNumeroPg(String altriNumeroPg) {
		this.altriNumeroPg = altriNumeroPg;
	}

	public String getAltriAnnoPg() {
		return altriAnnoPg;
	}

	public void setAltriAnnoPg(String altriAnnoPg) {
		this.altriAnnoPg = altriAnnoPg;
	}

	public String getAltriSubPg() {
		return altriSubPg;
	}

	public void setAltriSubPg(String altriSubPg) {
		this.altriSubPg = altriSubPg;
	}

	public String getAltriDataPg() {
		return altriDataPg;
	}

	public void setAltriDataPg(String altriDataPg) {
		this.altriDataPg = altriDataPg;
	}

	public String getAltriOggettoPg() {
		return altriOggettoPg;
	}

	public void setAltriOggettoPg(String altriOggettoPg) {
		this.altriOggettoPg = altriOggettoPg;
	}

	public String getNpgAttoPratRif() {
		return npgAttoPratRif;
	}

	public void setNpgAttoPratRif(String npgAttoPratRif) {
		this.npgAttoPratRif = npgAttoPratRif;
	}

	public String getAnnoPgAttoPratRif() {
		return annoPgAttoPratRif;
	}

	public void setAnnoPgAttoPratRif(String annoPgAttoPratRif) {
		this.annoPgAttoPratRif = annoPgAttoPratRif;
	}

	public String getPraticheCollegate() {
		return praticheCollegate;
	}

	public void setPraticheCollegate(String praticheCollegate) {
		this.praticheCollegate = praticheCollegate;
	}

}
