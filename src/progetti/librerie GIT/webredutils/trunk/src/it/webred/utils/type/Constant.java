package it.webred.utils.type;


/**
 * Rappresenta una costante. Viene usata dal command DI DICHIARAZIONE DI COSTANTI E 
 * istanziata a seconda del tipo passato al command
 * 
 * @author marcoric
 * @version $Revision: 1.1 $ $Date: 2009/02/06 14:51:25 $
 */
public class Constant extends it.webred.utils.type.def.DeclarativeType
{

	public Constant(String name, String type, Object value) {
		super(name,type,value);

	}


}
