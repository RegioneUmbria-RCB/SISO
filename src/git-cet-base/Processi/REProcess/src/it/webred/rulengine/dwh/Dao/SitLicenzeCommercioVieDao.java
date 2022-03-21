package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitLicenzeCommercioVie;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitLicenzeCommercioVieDao extends TabellaDwhDao {

	public SitLicenzeCommercioVieDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitLicenzeCommercioVieDao(SitLicenzeCommercioVie tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
