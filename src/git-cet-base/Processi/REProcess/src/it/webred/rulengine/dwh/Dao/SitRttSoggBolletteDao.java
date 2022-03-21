package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRttSoggBollette;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRttSoggBolletteDao extends TabellaDwhDao {

	public SitRttSoggBolletteDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitRttSoggBolletteDao(SitRttSoggBollette tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
