package it.webred.classfactory;

import java.util.LinkedList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 * @author Alessandro Feriani
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SpringClassFinder {
	
	public static List<Class> allClassesThatImplementsInterface(Package pkg, Class interface2implements) throws Exception
	{
	    String basePackage = pkg.getName();
	    
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	    MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

	    List<Class> candidates = new LinkedList<Class>();
	    String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(basePackage) + "/" + "**/*.class";
	    
	    Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
	    
	    for (Resource resource : resources) {
	        if (resource.isReadable()) {
	            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
	            
	            if (isCandidate(metadataReader, interface2implements)) {
	                candidates.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
	            }
	        }
	    }
	    
	    return candidates;
	}

	private static String resolveBasePackage(String basePackage) {
	    return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}

	private static boolean isCandidate(MetadataReader metadataReader, Class interface2implements) throws ClassNotFoundException
	{
	    try {
	        Class c = Class.forName(metadataReader.getClassMetadata().getClassName());
	        if ( !c.isInterface() && interface2implements.isAssignableFrom(c) ) {
	            return true;
	        }
	    }
	    catch(Throwable e){
	    }
	    return false;
	}
}
