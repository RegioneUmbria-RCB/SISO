package it.webred.cs.json.provvedimentiMinori.ver1.tabs;

import it.webred.cs.jsf.manbean.ComponenteAltroMan;
import it.webred.cs.json.provvedimentiMinori.tabs.IDatiProvvedimento;

import java.util.LinkedList;
import java.util.List;

public class DatiProvvedimentoMan implements IDatiProvvedimento{

	public static final String NAME = "Dati Provvedimento";
	public static final String AFFIDAMENTO_COMUNE="Affidamento al comune";
	
	private List<String> lstDatiProvvedimento;
	private List<String> lstTipoProvvedimento;
	private List<String> lstNaturaProvvedimento;
	private List<String> lstTipoAffidamento;
	private List<String> lstIndagine;
	private List<String> lstAffidamento;
	private List<String> lstCessazionePotesta;
	private ComponenteAltroMan tutore;
	
	public DatiProvvedimentoMan()
	{
		loadListe();
	}
	
	@Override
	public void init(Long idSoggetto)
	{
		tutore = new ComponenteAltroMan(idSoggetto);
	}

	@Override
	public void valorizzaComponenteMan(DatiProvvedimentoBean datiProvBean){
		//Valorizzo dati componente familiare
		tutore.setCompIndirizzo(datiProvBean.getTutoreIndirizzo());
		tutore.setCompCitta(datiProvBean.getTutoreCitta());
		tutore.setCompDenominazione(datiProvBean.getTutoreDenominazione());
		tutore.setCompTelefono(datiProvBean.getTutoreTelefono());
		if(datiProvBean.getTutoreCitta()!=null){
			int index = datiProvBean.getTutoreCitta().lastIndexOf('-');
			String citta = datiProvBean.getTutoreCitta().substring(0,index);
			String prov = datiProvBean.getTutoreCitta().substring(index+1);
			this.getTutore().setComuneResidenzaMan(citta, prov);
		}
		tutore.setIdComponente(datiProvBean.getIdTutore());
		tutore.aggiornaComponente();
	}
	
	@Override
	public void valorizzaJson(DatiProvvedimentoBean datiProvBean){
		//Valorizzo dati componente familiare
		datiProvBean.setIdTutore(tutore.getIdComponente());
		datiProvBean.setTutoreCitta(tutore.getCompCitta());
		datiProvBean.setTutoreDenominazione(tutore.getCompDenominazione());
		datiProvBean.setTutoreIndirizzo(tutore.getCompIndirizzo());
		datiProvBean.setTutoreTelefono(tutore.getCompTelefono());
	}
	

	private void loadListe()
	{
		if (this.lstDatiProvvedimento == null) {
			this.lstDatiProvvedimento = new LinkedList<String>();
			this.lstDatiProvvedimento.add("Tribunale Minori");
			this.lstDatiProvvedimento.add("Tribunale Ordinario");
			this.lstDatiProvvedimento.add("Giudice Tutelare");
			this.lstDatiProvvedimento.add("Procura");
			//this.lstDatiProvvedimento.add("Richiesta Indagine");
			this.lstDatiProvvedimento.add("Non Definito");
		}
		if (this.lstTipoProvvedimento == null) {
			this.lstTipoProvvedimento = new LinkedList<String>();
			this.lstTipoProvvedimento.add("Provvisorio");
			this.lstTipoProvvedimento.add("Definitivo");
			this.lstTipoProvvedimento.add("Non definito");
		}
		if (this.lstNaturaProvvedimento == null) {
			this.lstNaturaProvvedimento = new LinkedList<String>();
			this.lstNaturaProvvedimento.add("Civile");
			this.lstNaturaProvvedimento.add("Penale subito");
			this.lstNaturaProvvedimento.add("Penale attivo");
			this.lstNaturaProvvedimento.add("Amministrativo");
			this.lstNaturaProvvedimento.add("Non definito");
		}

		if (this.lstIndagine == null) {
			this.lstIndagine = new LinkedList<String>();
			this.lstIndagine.add("Indagine psico-sociale");
			this.lstIndagine.add("Indagine sociale");
			this.lstIndagine.add("Altra Indagine");
			this.lstIndagine.add("Affido al SS");
			this.lstIndagine.add("Valutazione");
			this.lstIndagine.add("Interventi psico-edu");
			
		}
		if (this.lstAffidamento == null)
		{
			this.lstTipoAffidamento = new LinkedList<String>();
			this.lstTipoAffidamento.add(AFFIDAMENTO_COMUNE);
			this.lstTipoAffidamento.add("Revoca affidamento");
		}
		if (this.lstCessazionePotesta == null)
		{
			this.lstCessazionePotesta = new LinkedList<String>();
			this.lstCessazionePotesta.add("Decadenza");
			this.lstCessazionePotesta.add("Limitazione");
			this.lstCessazionePotesta.add("Sospensione");
		}
		
	}

	

	// **GETTER AND SETTER////

	@Override
	public String getTabName()
	{
		return NAME;
	}

	@Override
	public List<String> getLstDatiProvvedimento() {
		return lstDatiProvvedimento;
	}

	@Override
	public List<String> getLstTipoProvvedimento() {
		return lstTipoProvvedimento;
	}

	@Override
	public List<String> getLstNaturaProvvedimento() {
		return lstNaturaProvvedimento;
	}

	@Override
	public List<String> getLstTipoAffidamento() {
		return lstTipoAffidamento;
	}

	@Override
	public List<String> getLstIndagine() {
		return lstIndagine;
	}

	@Override
	public List<String> getLstAffidamento() {
		return lstAffidamento;
	}

	@Override
	public List<String> getLstCessazionePotesta() {
		return lstCessazionePotesta;
	}

	@Override
	public ComponenteAltroMan getTutore() {
		return tutore;
	}

	@Override
	public void setTutore(ComponenteAltroMan tutore) {
		this.tutore = tutore;
	}


}
