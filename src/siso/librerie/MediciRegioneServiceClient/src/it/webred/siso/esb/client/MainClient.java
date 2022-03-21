package it.webred.siso.esb.client;

import java.net.MalformedURLException;
import java.net.URL;

import it.webred.siso.esb.Medico;

public class MainClient {

	public static void main(String[] args) {
		try {
			System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
			System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
			System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
			System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
			
			String indirizzoIP = args[0];
			String porta = args[1];
			String codMedico = args[2];
			
			URL url = new URL("http://"+indirizzoIP+":"+porta+"/services/MedicoService?wsdl");
			MedicoClient mc= new MedicoClient(url);
			Medico m = mc.getMedicoByCodiceRegionale(codMedico);
			System.out.println(m);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
