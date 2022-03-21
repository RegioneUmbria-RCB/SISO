package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitLicenzeCommercioTit;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitLicenzeCommercioTitDao extends TabellaDwhDao {

	public SitLicenzeCommercioTitDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitLicenzeCommercioTitDao(SitLicenzeCommercioTit tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
