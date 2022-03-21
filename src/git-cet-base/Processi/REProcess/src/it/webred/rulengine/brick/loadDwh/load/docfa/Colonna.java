package it.webred.rulengine.brick.loadDwh.load.docfa;

/**
 * Bean che descrive una colonna del tracciato
 * @author Petracca Marco
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:31:54 $
 */
public class Colonna
{

	/**
	*	Costruttore vuoto
	*/
	public Colonna()
	{
		super();
	}

	/**
	 *  Costruisce e popola il bean 
	*	@param nome
	*	@param chiave
	*	@param pos
	*	@param startp
	*	@param stopp
	*	@param tipo
	*	@param dimensioneOrig
	*	@param tipoDest
	*	@param formato
	*	@param dimensioneDest
	*/
	public Colonna(String nome, boolean chiave, Integer pos, Integer startp, Integer stopp, String tipo, Integer dimensioneOrig, String tipoDest, String formato, Integer dimensioneDest)
	{
		super();
		this.chiave = chiave;
		this.nome = nome;
		this.pos = pos;
		this.startp = startp;
		this.stopp = stopp;
		this.tipo = tipo;
		this.dimensioneOrig = dimensioneOrig;
		this.tipoDest = tipoDest;
		this.formato = formato;
		this.dimensioneDest = dimensioneDest;

	}

	private String	nome;
	private Integer	pos;
	private Integer	startp;
	private Integer	stopp;
	private boolean	chiave;
	private String	tipo;
	private Integer	dimensioneOrig;
	private String	tipoDest;
	private String	formato;
	private Integer	dimensioneDest;

	public boolean isChiave()
	{
		return chiave;
	}

	public void setChiave(boolean chiave)
	{
		this.chiave = chiave;
	}

	public String getNome()
	{
		if(nome == null)
			return null;
		return nome.trim().replaceAll(" ", "_");
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Integer getPos()
	{
		return pos;
	}

	public void setPos(Integer pos)
	{
		this.pos = pos;
	}

	public Integer getStartp()
	{
		return startp;
	}

	public void setStartp(Integer startp)
	{
		this.startp = startp;
	}

	public Integer getStopp()
	{
		return stopp;
	}

	public void setStopp(Integer stopp)
	{
		this.stopp = stopp;
	}

	public String getTipo()
	{
		return tipo;
	}

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	public Integer getDimensioneOrig()
	{

		/*
		 * if (dimensioneOrig !=null && dimensioneOrig > 0) return
		 * dimensioneOrig;
		 * 
		 * if (stopp!=null && startp!=null) return stopp - startp;
		 */

		return dimensioneOrig;

	}

	public void setDimensioneOrig(Integer dimensioneOrig)
	{
		this.dimensioneOrig = dimensioneOrig;
	}

	public Integer getDimensioneDest()
	{
		return dimensioneDest;
	}

	public void setDimensioneDest(Integer dimensioneDest)
	{
		this.dimensioneDest = dimensioneDest;
	}

	public String getFormato()
	{
		return formato;
	}

	public void setFormato(String formato)
	{
		this.formato = formato;
	}

	public String getTipoDest()
	{
		return tipoDest;
	}

	public void setTipoDest(String tipoDest)
	{
		this.tipoDest = tipoDest;
	}

}