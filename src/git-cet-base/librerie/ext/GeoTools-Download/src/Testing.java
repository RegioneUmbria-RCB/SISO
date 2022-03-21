import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFactorySpi;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultQuery;
import org.geotools.data.FeatureReader;
import org.geotools.data.Query;
import org.geotools.data.Transaction;
import org.geotools.data.jdbc.FilterToSQL;
import org.geotools.data.jdbc.datasource.JNDIDataSourceFactory;
import org.geotools.data.joining.JoiningQuery;
import org.geotools.data.joining.JoiningQuery.Join;
import org.geotools.data.oracle.OracleNGDataStoreFactory;
import org.geotools.data.oracle.sdo.GeometryConverter;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.filter.AttributeExpressionImpl;
import org.geotools.filter.GeometryFilter;
import org.geotools.filter.expression.PropertyNameBuilder;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.jdbc.JDBCFeatureStore;
import org.geotools.swing.data.JDataStoreWizard;
import org.geotools.swing.wizard.JWizard;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.FeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.expression.PropertyName;

public class Testing {

	private org.geotools.data.DataAccessFactory  a;
	
	
	 private static DataStore connect(DataStoreFactorySpi format) throws Exception {
	        
		 java.util.Map params = new java.util.HashMap();
		 params.put( "dbtype", "oracle");
		 params.put( "host", "172.16.2.61");
		 params.put( "port", 1521);
		 params.put( "schema", "SITI_F704");
		 params.put( "database", "DBCAT");
		 params.put( "user", "SITI_F704");
		 params.put( "passwd", "SITI_F704");
		 params.put("Expose primary keys", true);

		 DataStore dataStore=DataStoreFinder.getDataStore(params);
		  return dataStore;
	 }
	     
	 public static void main(String[] args) throws Exception {
		 DataStore dstore = Testing.connect(null);
		 
		 // LETTURA SITIPART
		 
		 FeatureReader<SimpleFeatureType, SimpleFeature> fr = getFeatureReader(dstore, "SITIPART", "SE_ROW_ID=228328");
         Feature arc = null;
         Integer id = new Integer(0);
         //String geomName2 = fr.getFeatureType().getGeometryDescriptor().getLocalName();

         
         
         FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2(null);
         if (fr.hasNext()) {
             arc = fr.next();
           //  Filter innerFilter = ff.intersects(ff.property(geomName2), ff.literal(arc));
           //  Query innerQuery = new Query(typeName2, innerFilter, Query.NO_NAMES);
             
             System.out.println(arc.getValue());
             
         }
         
		 //FeatureReader<SimpleFeatureType, SimpleFeature> fr2 = getFeatureReader(dstore, "SITICIVI", "PKID_STRA = 3470");
        
		 SimpleFeatureSource fs = dstore.getFeatureSource("SITIPART");
		 SimpleFeatureSource fs2 = dstore.getFeatureSource("AGGR_LOCALIZZA");

		 
		 
		    SimpleFeatureType schema = fs.getSchema();
		    String typeName = schema.getTypeName();
		    String geomName = schema.getGeometryDescriptor().getLocalName();
		    
		    SimpleFeatureType schema2 = fs2.getSchema();
		    String typeName2 = schema2.getTypeName();
		    String geomName2 = schema2.getGeometryDescriptor().getLocalName();
		    
		   
		    

		    Query outerGeometry = new Query(typeName, CQL.toFilter("FOGLIO='24' AND PARTICELLA = '00080' AND DATA_FINE_VAL='31/12/9999'"), new String[] { geomName, "FOGLIO", "PARTICELLA" });
		    
		    SimpleFeatureCollection outerFeatures = fs.getFeatures(outerGeometry);
		    SimpleFeatureIterator iterator = outerFeatures.features();

		 
		 
         List<SimpleFeature> intersezione = JoinExample.joinExample(typeName, geomName, typeName2, geomName2, outerFeatures,iterator, fs2);         
         //JOIN SITICIVI
         
         System.out.println(intersezione.size());
          Join join = new JoiningQuery.Join();
          JoiningQuery qjoin = new JoiningQuery();


         
         
		 
	 }
	 
	 protected static FeatureReader<SimpleFeatureType , SimpleFeature> getFeatureReader(DataStore dstore, String featureTypeName,
             String filter) throws Exception {
		 
		 Filter f = CQL.toFilter(filter);
         Query q = new Query(featureTypeName);
         q.setFilter(f);

         return dstore.getFeatureReader(q, Transaction.AUTO_COMMIT);
     }
 
	
}
