/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 * 
 *    (C) 2006-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This file is hereby placed into the Public Domain. This means anyone is
 *    free to do whatever they wish with this file. Use it well and enjoy!
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultQuery;
import org.geotools.data.Query;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.GeoTools;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;

import com.vividsolutions.jts.geom.Geometry;

/**
 * This class shows how to "join" two feature sources.
 * 
 * @author Jody
 * 
 * 
 * @source $URL$
 */
public class JoinExample {

/**
 * 
 * @param args
 *            shapefile to use, if not provided the user will be prompted
 */
public static void main(String[] args) throws Exception {
    System.out.println("Welcome to GeoTools:" + GeoTools.getVersion());
    
    File file, file2;
    if (args.length == 0) {
        file = JFileDataStoreChooser.showOpenFile("shp", null);
    } else {
        file = new File(args[0]);
    }
    if (args.length <= 1) {
        file2 = JFileDataStoreChooser.showOpenFile("shp", null);
    } else {
        file2 = new File(args[1]);
    }
    if (file == null || !file.exists() || file2 == null || !file2.exists()) {
        System.exit(1);
    }
    Map<String, Object> connect = new HashMap<String, Object>();
    connect.put("url", file.toURI().toURL());
    DataStore shapefile = DataStoreFinder.getDataStore(connect);
    String typeName = shapefile.getTypeNames()[0];
    SimpleFeatureSource shapes = shapefile.getFeatureSource(typeName);
    
    Map<String, Object> connect2 = new HashMap<String, Object>();
    connect.put("url", file2.toURI().toURL());
    DataStore shapefile2 = DataStoreFinder.getDataStore(connect);
    String typeName2 = shapefile2.getTypeNames()[0];
    SimpleFeatureSource shapes2 = shapefile2.getFeatureSource(typeName2);
    
   // joinExample(shapes, shapes2);
    System.exit(0);
}

// joinExample start
public static List<SimpleFeature> joinExample(String typeName, String geomName, String typeName2, String geomName2 ,
		SimpleFeatureCollection outerFeatures, SimpleFeatureIterator iterator, SimpleFeatureSource shapes2 )
        throws Exception {
  
    FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2(null);
    
    List<SimpleFeature> simpleF = new ArrayList<SimpleFeature>();
    
    int max = 0;
    try {
        while (iterator.hasNext()) {
            SimpleFeature feature = iterator.next();
            try {
                Geometry geometry = (Geometry) feature.getDefaultGeometry();
                if (!geometry.isValid()) {
                    // skip bad data
                    continue;
                }
                Filter innerFilter = ff.intersects(ff.property(geomName2), ff.literal(geometry));
                Query innerQuery = new Query(typeName2, innerFilter, Query.NO_NAMES);
                SimpleFeatureCollection join = shapes2.getFeatures(innerQuery);
                int size = join.size();
                max = Math.max(max, size);
                SimpleFeatureIterator fi = join.features();  
                while (fi.hasNext()) {
                    SimpleFeature sf = fi.next();
                    simpleF.add(sf);

                }
                fi.close();
                
            } catch (Exception skipBadData) {
            }
        }
    } finally {
        iterator.close();
    }

    
    return simpleF;
}
// joinExample end

// Filter innerFilter = ff.dwithin(ff.property(geomName2), ff.literal( geometry
// ),1.0,"km");
// Filter innerFilter = ff.beyond(ff.property(geomName2), ff.literal( geometry
// ),1.0,"km");
// Filter innerFilter = ff.not( ff.disjoint(ff.property(geomName2), ff.literal(
// geometry )) );


}

