package it.webred.rulengine.type;

import it.webred.rulengine.type.def.DeclarativeType;

/**
 * Rappresenta una costante. Viene usata dal command DI DICHIARAZIONE DI COSTANTI E 
 * istanziata a seconda del tipo passato al command
 * 
 * @author marcoric
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:24 $
 */
public class Constant extends DeclarativeType
{

	public Constant(String name, String type, Object value) {
		super(name,type,value);

	}


}
