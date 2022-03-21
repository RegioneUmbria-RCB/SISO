package it.webred.AMProfiler.services.test;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.AxisFault;
import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

/**
 * Classe per l'esecuzione dei test sui servizi web.
 * 
 * @author marcoric
 * @version $Revision: 1.1.2.1 $ $Date: 2010/09/07 15:12:11 $
 */
public class ServicesTest {

	private static String enteTest = "Monza";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String nameWS = "http://localhost:8080/AMProfiler/services/Services";
		URL endPointWS = new URL(nameWS);

		// inizializzazione WS
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.removeAllParameters();
		call.setUsername("user1");
		call.setPassword("pippo");

		// configurazione parametri WS
		call.setTargetEndpointAddress(endPointWS);
		call.addParameter("idCommand", Constants.XSD_STRING, ParameterMode.IN);
		call.setOperationName("getListaUrl");
		call.setReturnType(Constants.XSD_ANY);

		// invocazione WS

		try {
			System.out.println("Chiamata a servizio getListaURL");
			HashMap<String, String> risultato = (HashMap<String, String>) call.invoke(new Object[] { enteTest });

			Set set = risultato.entrySet();

			Iterator i = set.iterator();

			while (i.hasNext()) {
				Map.Entry me = (Map.Entry) i.next();
				System.out.println(me.getKey() + " : " + me.getValue());
			}

			System.out.println("Fine Chiamata Servizio");
		}
		catch (AxisFault e) {
			e.printStackTrace();
			System.out.println("Chiamata a servizio getListaURL terminata con errori");

		}
	}
}
