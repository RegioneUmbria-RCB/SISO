package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitDPersonaCivico;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitDPersonaCivicoDao extends TabellaDwhDao
{

	public SitDPersonaCivicoDao(SitDPersonaCivico tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}
	public SitDPersonaCivicoDao(SitDPersonaCivico tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
}
