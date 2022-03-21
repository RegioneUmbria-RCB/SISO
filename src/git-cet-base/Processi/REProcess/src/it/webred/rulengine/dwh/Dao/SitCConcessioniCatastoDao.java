package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitCPersona;
import it.webred.rulengine.dwh.table.SitCConcessioni;
import it.webred.rulengine.dwh.table.SitCConcessioniCatasto;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;



public class SitCConcessioniCatastoDao extends TabellaDwhDao
{

	public SitCConcessioniCatastoDao(SitCConcessioniCatasto tab)
	{
		super(tab);
	}
	public SitCConcessioniCatastoDao(SitCConcessioniCatasto tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}

}
