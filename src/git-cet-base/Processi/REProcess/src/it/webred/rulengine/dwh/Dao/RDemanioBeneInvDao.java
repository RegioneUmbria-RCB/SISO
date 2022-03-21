package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.RDemanioBeneInv;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class RDemanioBeneInvDao extends TabellaDwhDao
{

	public RDemanioBeneInvDao(RDemanioBeneInv tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}
	public RDemanioBeneInvDao(RDemanioBeneInv tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
