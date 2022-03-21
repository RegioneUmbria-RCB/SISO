package it.umbriadigitale.sandbox.geocode;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;

public class GeocodeRunner {

	void exerciseGeocodeLib() {

		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDmrvSYFpAfPN2URYZiMz4bZ-DFvUU2wdc");

		String indirizzo = "via parco 2, san mariano, corciano";
		try {
			GeocodingApiRequest gcRequest = GeocodingApi.geocode(context, indirizzo);
			GeocodingResult[] gcResults = gcRequest.await();

			for (GeocodingResult gcr : gcResults) {
				System.out.println("formattedAddress: " + gcr.formattedAddress);
				System.out.println("location: " + gcr.geometry.location);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GeocodeRunner runner = new GeocodeRunner();
		runner.exerciseGeocodeLib();
	}

}
