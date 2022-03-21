package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitBonBan;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitBonBanDao extends TabellaDwhDao {

	public SitBonBanDao(TabellaDwh tab) {
		super(tab);
	}//-------------------------------------------------------------------------

	public SitBonBanDao(SitBonBan tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
