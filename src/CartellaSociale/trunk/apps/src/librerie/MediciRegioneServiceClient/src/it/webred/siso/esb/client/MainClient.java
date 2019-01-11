package it.webred.siso.esb.client;

import java.net.MalformedURLException;
import java.net.URL;

import it.webred.siso.esb.Medico;

public class MainClient {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://localhost:8280/services/MedicoService?wsdl");
			MedicoClient mc= new MedicoClient(url);
			Medico m = mc.getMedicoByCodiceRegionale("395");
			System.out.println(m);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
