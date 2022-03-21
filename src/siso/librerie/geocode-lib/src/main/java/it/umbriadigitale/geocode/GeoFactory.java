package it.umbriadigitale.geocode;


import org.apache.log4j.Logger;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;


public class GeoFactory {
	
	private  static Logger log = Logger.getLogger(GeoFactory.class.getSimpleName());

	
	
	private String apiKey;
	
	public GeoFactory() {
		// api key di sisoumbria@gmail.com

			this.apiKey = "AIzaSyDeptcTKKYeuyz556lmivO_VIaAjYsoIFc";
		
	}
	
	public GeoFactory(String apiKey) {

			this.apiKey = apiKey;
		
	}
	
	
	
	public  GeocodeDTO buildCoordinates(String indirizzo ) {
		// Replace the API key below with a valid API key.
		GeoApiContext context = new GeoApiContext().setApiKey(this.apiKey);
		GeocodingResult[] results;
		
		GeocodeDTO dto = new GeocodeDTO();
		
		try {
			results = GeocodingApi.geocode(context, indirizzo).await();
			double latitude = 0;
			double longitudine = 0;
			
			if (results.length!=0)  {
				longitudine = results[0].geometry.location.lng;
				latitude = results[0].geometry.location.lat;
				dto.setLatitude(latitude);
				dto.setLongitudine(longitudine);
				dto.setPartialMatch( results[0].partialMatch);
				dto.setFormattedAddress( results[0].formattedAddress);
				dto.setPlaceId( results[0].placeId);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Geocoding " + indirizzo, e);
		}
		return dto;
		
		

	}
	
	public static void main(String[] args) { 
		GeoFactory f =  new  GeoFactory();
		GeocodeDTO COORD = f.buildCoordinates("VIA ANGELO DA ORVIETO, 4 ORVIETO TR");
		
		System.out.println(COORD.getLatitude() + "-" +  COORD.getLongitudine());
		
		
	}

	
}
