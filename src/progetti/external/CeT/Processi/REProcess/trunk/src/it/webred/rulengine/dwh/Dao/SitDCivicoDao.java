package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitDCivico;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitDCivicoDao extends TabellaDwhDao
{

	public SitDCivicoDao(SitDCivico tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}
	public SitDCivicoDao(SitDCivico tab , BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
}
