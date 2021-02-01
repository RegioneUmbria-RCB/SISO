package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.RDemanioBeneInfo;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class RDemanioBeneInfoDao extends TabellaDwhDao
{

	public RDemanioBeneInfoDao(RDemanioBeneInfo tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}
	public RDemanioBeneInfoDao(RDemanioBeneInfo tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
