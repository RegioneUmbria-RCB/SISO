package it.webred.utils.type.def;

import java.util.HashMap;

/**
 * L'interfaccia prevede i metodi che deve avere un costruttore di tipi 
 * il cui tipo debba accedere ai tipi dichiarativi
 * registrati nel contesto.
 * Alla costruzione del tipo viene usato questo metodo per sostituire i segnaposto dei tipi dato dichiarati
 * con il loro valore
 * @author marcoric
 * @version $Revision: 1.1 $ $Date: 2009/02/06 14:51:25 $
 */
public interface DeclarativeTypeAccessor
{
	public Object constructWithDeclarativeType(Object obj, HashMap<String,DeclarativeType> var) throws Exception;
}
