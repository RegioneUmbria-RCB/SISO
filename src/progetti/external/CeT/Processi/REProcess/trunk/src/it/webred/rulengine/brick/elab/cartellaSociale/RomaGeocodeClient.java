package it.webred.rulengine.brick.elab.cartellaSociale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class RomaGeocodeClient implements GeolocalizationService {

	private static final Logger log = Logger.getLogger(RomaGeocodeClient.class.getName());
	
	private String wsUsername;
	private String wsPassword;
	
	public RomaGeocodeClient(String wsUsername, String wsPassword) {
		this.wsUsername = wsUsername;
		this.wsPassword = wsPassword;
	}

	@Override
	public List<IndirizziZoneDto> geolocalizzaIndirizzi(List<IndirizziZoneDto> addressList) {

		for (IndirizziZoneDto dto : addressList) {

			String outputJson = "";
			try {

				outputJson = makeHttpRequest(dto);

			} catch (Exception e) {
				dto.setGeocodeStatusDetail(GeolocalizationResult.GENERIC.name());
				dto.setGeocodeStatusDetail(GeolocalizationResult.GENERIC.description());
				log.error("Errore Richiesta Servizio", e);
				continue;
				// e.printStackTrace();
			}

			log.info("Output Json " + outputJson);

			JsonElement je = new JsonParser().parse(outputJson);

			JsonObject root = je.getAsJsonObject();
			JsonElement je1 = root.get("totalFeatures");

			if (je1.getAsInt() == 0) {
				log.info("NESSUN RISULTATO.");
				dto.setGeocodeStatus(GeolocalizationResult.NOT_FOUND.name());
				dto.setGeocodeStatusDetail(GeolocalizationResult.NOT_FOUND.description());
			} else {

				JsonElement je2 = root.get("features");
				JsonArray coordinate = je2.getAsJsonArray().get(0).getAsJsonObject().get("geometry").getAsJsonObject()
						.get("coordinates").getAsJsonArray();
				Double longitudine = coordinate.get(0).getAsDouble();
				Double latitudine = coordinate.get(1).getAsDouble();

				log.info("Longitudine " + longitudine);
				log.info("Latitudine " + latitudine);

				dto.setGeocodeStatus(GeolocalizationResult.OK.name());
				dto.setGeocodeStatusDetail(GeolocalizationResult.OK.description());
				dto.setGmFormattedAddress(dto.getGmIndirizzoEsteso());
				dto.setLatitudine(latitudine);
				dto.setLongitudine(longitudine);

			}

		}

		return addressList;
	}

	public static String codificaCaratteri(String input) {
		if (input == null) {
			return "";
		}
		String codifica = input.replace(" ", "%20");
		codifica = codifica.replace("'", "%27");
		return codifica;
	}

	private String makeHttpRequest(IndirizziZoneDto dto) throws Exception {

		String output = "";
		try {

			String urlServizio = "http://sit.comune.roma/geoserver/toponomastica/wfs";
			String service = "service=wfs";
			String version = "version=1.1.0";
			String request = "request=GetFeature";
			String typename = "TYPENAME=toponomastica:CiviciToponomastica";
			String outputFormat = "outputFormat=application/json";

			StringBuilder filter = new StringBuilder("CQL_FILTER=");

			filter.append("TOPONIMO='").append(dto.getToponimo()).append("'");
			filter.append(" and NUMERO_CIVICO='").append(dto.getNumeroCivico()).append("'");

			StringBuilder stringBuilder = new StringBuilder(urlServizio);
			stringBuilder.append("?");
			stringBuilder.append(service);
			stringBuilder.append("&");
			stringBuilder.append(version);
			stringBuilder.append("&");
			stringBuilder.append(request);
			stringBuilder.append("&");
			stringBuilder.append(typename);
			stringBuilder.append("&");
			stringBuilder.append(outputFormat);
			stringBuilder.append("&");
			stringBuilder.append(codificaCaratteri(filter.toString()));

			String authString = wsUsername + ":" + wsPassword;
			log.info("auth string: " + authString);
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			log.info("Base64 encoded auth string: " + authStringEnc);

			log.info("GET --> " + stringBuilder.toString());

			URL url = new URL(stringBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Basic " + authStringEnc);

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Errore : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			StringBuilder sb = new StringBuilder();

			log.info("Output dal Server ....");

			while ((output = br.readLine()) != null) {
				sb.append(output + "\n");
			}

			output = sb.toString();
			conn.disconnect();

		} catch (MalformedURLException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}

		return output;
	}

}
