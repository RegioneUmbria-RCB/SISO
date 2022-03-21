package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.RDemanioMappali;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class RDemanioMappaliDao extends TabellaDwhDao
{

	public RDemanioMappaliDao(RDemanioMappali tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}
	public RDemanioMappaliDao(RDemanioMappali tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
