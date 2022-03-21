package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitDPersFam;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitDPersFamDao extends TabellaDwhDao
{

	public SitDPersFamDao(SitDPersFam tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitDPersFamDao(SitDPersFam tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
}

