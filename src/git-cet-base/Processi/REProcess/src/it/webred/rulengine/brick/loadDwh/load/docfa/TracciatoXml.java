package it.webred.rulengine.brick.loadDwh.load.docfa;

import java.util.LinkedList;
import java.util.List;

/**
 * Bean contenente le informazione e i dati di un data source
 * 
 */
public class TracciatoXml
{

	private List	listaColonne	= new LinkedList();

	private String	nomeFile;

	private String	nome;
	private String	versione		= "1.0";
	private String	caricatore;
	private String  gestoreDataEstrazione;
	private String	driver;
	private String	stringaConnessione;
	private String	sourceTab;
	private String	separatore;

	private int		maxLenght		= 0;

	public TracciatoXml(String nomeFile, String nome, String versione, String separatore, String caricatore, String driver, String stringaConnessione, String sourceTab, List listaColonne, String gestoreDataEstrazione)
	{
		super();
		this.nomeFile = nomeFile;
		this.nome = nome;
		this.separatore = separatore;
		this.listaColonne = listaColonne;
		this.caricatore = caricatore;
		this.driver = driver;
		this.stringaConnessione = stringaConnessione;
		this.versione = versione;
		this.sourceTab = sourceTab;
		this.gestoreDataEstrazione=gestoreDataEstrazione;
	}

	public TracciatoXml()
	{
	}

	/**
	 * Conta il numero di record nel tracciato
	 * 
	 * @return La size della lista 
	 */
	public int count()
	{
		return listaColonne.size();
	}

	/**
	 * In un tracciato con separatore determina la presenza dell'enesima
	 * posizione
	 * 
	 * @param p
	 * @return true posizione nel tracciato necessaria / false posizione nel
	 *         tracciato NON necessaria
	 */
	public boolean hasIdx(int p)
	{
		for (int i = 0; i < listaColonne.size(); i++)
		{
			if (((Colonna) listaColonne.get(i)).getPos() == p)
				return true;
		}
		return false;
	}

	public String getNome()
	{
		return nome;
	}

	public String getNomeTabella()
	{
		String expReg = "[\\., \\s]+";
		String n = (nome + "_" + versione).replaceAll(expReg, "_");
		return n;
	}

	public String getNomeTabellaAccess()
	{
		return sourceTab;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getSeparatore()
	{
		if(separatore != null && separatore.equals("|"))
			separatore = "[|]";
		return separatore;
	}

	public void setSeparatore(String separatore)
	{
		this.separatore = separatore;
	}

	public String getVersione()
	{
		return versione;
	}

	public void setVersione(String versione)
	{
		this.versione = versione;
	}

	public List getListaColonne()
	{
		return listaColonne;
	}

	public void setListaColonne(List listaColonne)
	{
		this.listaColonne = listaColonne;
	}

	public int getMaxLenght()
	{
		if (maxLenght < 1)
		{
			for (int i = 0; i < listaColonne.size(); i++)
			{
				if (((Colonna) listaColonne.get(i)).getStopp() > maxLenght)
					;
				maxLenght = ((Colonna) listaColonne.get(i)).getStopp();
			}
		}
		return maxLenght;
	}

	public String getCaricatore()
	{
		return caricatore;
	}

	public void setCaricatore(String caricatore)
	{
		this.caricatore = caricatore;
	}

	public String getStringaConnessione()
	{
		return stringaConnessione;
	}

	public void setStringaConnessione(String stringaConnessione)
	{
		this.stringaConnessione = stringaConnessione;
	}

	public String getDriver()
	{
		return driver;
	}

	public void setDriver(String driver)
	{
		this.driver = driver;
	}

	public String getNomeFile()
	{
		return nomeFile;
	}

	public void setNomeFile(String nomeFile)
	{
		this.nomeFile = nomeFile;
	}

	public String getSourceTab()
	{
		return sourceTab;
	}

	public void setSourceTab(String sourceTab)
	{
		String expReg = "[\\\\]+";
		this.sourceTab = sourceTab.replaceAll(expReg,"/");
	}

	public String getGestoreDataEstrazione()
	{
		return gestoreDataEstrazione;
	}

	public void setGestoreDataEstrazione(String gestoreDataEstrazione)
	{
		this.gestoreDataEstrazione = gestoreDataEstrazione;
	}

}
