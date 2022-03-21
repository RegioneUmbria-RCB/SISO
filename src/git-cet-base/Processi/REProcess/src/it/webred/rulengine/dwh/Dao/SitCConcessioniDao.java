package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitCConcessioni;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;



public class SitCConcessioniDao extends TabellaDwhDao
{

	public SitCConcessioniDao(SitCConcessioni tab)
	{
		super(tab);
	}
	public SitCConcessioniDao(SitCConcessioni tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}

}
