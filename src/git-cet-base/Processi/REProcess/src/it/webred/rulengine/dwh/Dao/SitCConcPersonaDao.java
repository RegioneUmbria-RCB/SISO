package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitCConcPersona;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;




public class SitCConcPersonaDao extends TabellaDwhDao
{

	public SitCConcPersonaDao(SitCConcPersona tab)
	{
		super(tab);
	}
	public SitCConcPersonaDao(SitCConcPersona tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}

}
