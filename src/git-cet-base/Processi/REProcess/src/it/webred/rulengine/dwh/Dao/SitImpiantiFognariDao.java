package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitImpiantiFognari;




import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitImpiantiFognariDao extends TabellaDwhDao {

	public SitImpiantiFognariDao(TabellaDwh tab) {
		super(tab);
	}//-------------------------------------------------------------------------

	public SitImpiantiFognariDao(SitImpiantiFognari tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

