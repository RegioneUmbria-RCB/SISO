package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitPubPratDettaglio;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitPubPratDettaglioDao extends TabellaDwhDao {

	public SitPubPratDettaglioDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitPubPratDettaglioDao(SitPubPratDettaglio tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
