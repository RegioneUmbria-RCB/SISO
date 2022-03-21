package it.webred.rulengine.brick.bsh;

import bsh.CallStack;
import bsh.Interpreter;

/**
 * Comando di esempio richiamabile all'interno di uno script bsh
 * @author marcoric
 * @version $Revision: 1.3 $ $Date: 2010/09/28 12:12:27 $
 */
public class HelloWordCmd {
	  public static void invoke( Interpreter env, CallStack callstack, String messaggio ) 
	  {
	  	System.out.print("Hello Word");
	  }
}
