package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitLicenzeCommercioAnag;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitLicenzeCommercioAnagDao extends TabellaDwhDao {

	public SitLicenzeCommercioAnagDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitLicenzeCommercioAnagDao(SitLicenzeCommercioAnag tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
