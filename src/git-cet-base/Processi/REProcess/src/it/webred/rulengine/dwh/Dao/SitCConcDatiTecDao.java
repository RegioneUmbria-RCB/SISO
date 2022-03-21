package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitCConcDatiTec;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitCConcDatiTecDao extends TabellaDwhDao
{

	public SitCConcDatiTecDao(SitCConcDatiTec tab)
	{
		super(tab);
	}
	public SitCConcDatiTecDao(SitCConcDatiTec tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}

}
