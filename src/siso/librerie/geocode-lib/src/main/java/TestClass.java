import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;


public class TestClass {

	public static void main(String[] args) {
		// Replace the API key below with a valid API key.
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDmrvSYFpAfPN2URYZiMz4bZ-DFvUU2wdc");
		GeocodingResult[] results;
		
		
		
		try {
			results = GeocodingApi.geocode(context,
			    "LOCALITA' CORBARA, 62  orvieto ").await();
			double latitude = 0;
			double longitudine = 0;
			
			if (results.length!=0)  {
				longitudine = results[0].geometry.location.lng;
				latitude = results[0].geometry.location.lat;
			}
			
			
			System.out.println(results[0].formattedAddress);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
