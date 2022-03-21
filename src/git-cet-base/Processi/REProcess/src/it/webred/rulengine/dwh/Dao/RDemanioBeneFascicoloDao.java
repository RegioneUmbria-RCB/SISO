package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.RDemanioBeneFascicolo;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class RDemanioBeneFascicoloDao extends TabellaDwhDao
{

	public RDemanioBeneFascicoloDao(RDemanioBeneFascicolo tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}
	public RDemanioBeneFascicoloDao(RDemanioBeneFascicolo tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
