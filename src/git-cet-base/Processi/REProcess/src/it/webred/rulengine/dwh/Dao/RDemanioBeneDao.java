package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.RDemanioBene;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class RDemanioBeneDao extends TabellaDwhDao
{

	public RDemanioBeneDao(RDemanioBene tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}
	public RDemanioBeneDao(RDemanioBene tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
