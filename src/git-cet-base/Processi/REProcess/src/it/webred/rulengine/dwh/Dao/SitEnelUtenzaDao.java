package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitEnelUtenza;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitEnelUtenzaDao extends TabellaDwhDao
{

	public SitEnelUtenzaDao(it.webred.rulengine.dwh.table.SitEnelUtenza tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}
	public SitEnelUtenzaDao(SitEnelUtenza tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
}
