package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.RDemanioBeneEventi;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class RDemanioBeneEventiDao extends TabellaDwhDao
{

	public RDemanioBeneEventiDao(RDemanioBeneEventi tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}
	public RDemanioBeneEventiDao(RDemanioBeneEventi tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
