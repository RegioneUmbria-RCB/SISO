package it.webred.ct.data.spatial;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

public class JGeometryType implements UserType, Serializable {
	
	private static final long serialVersionUID = 1L;
    private JGeometry geometryInstance = null;
    
	
	public JGeometryType() {
		super();
		geometryInstance = new JGeometry( 0, 0, 0);
	}
	
	public JGeometryType( JGeometry geometryInstance) {
        this.geometryInstance = geometryInstance;
    }
	
	public JGeometryType( JGeometryType geometryType) {
        this.geometryInstance = geometryType.getJGeometry();
    }
	
	
	/* User Type Information */
    /*
     * Note that the type that is reported is OTHER (1111) not STRUCT (2002),
     * see: org.hibernate.dialect.Dialect
     */
    private static final int[] SQL_TYPES = { Types.OTHER };
    

	public Object assemble(Serializable serializable, Object o)
			throws HibernateException {
		return deepCopy( serializable);
	}

	public Object deepCopy(Object o) throws HibernateException {
		if( o == null)
            return null;
        if( o instanceof JGeometryType) {
            return new JGeometryType( ( (JGeometryType) o).getJGeometry());
        } else {
            return null;
        }
	}

	public Serializable disassemble(Object o) throws HibernateException {
		return (Serializable) deepCopy( o);
	}

	
	/**
     * This method gives back the equals functionality that was deprecated by
     * using the equals that's needed for the UserType
     */
    @Override
    public boolean equals( Object arg0) {
        return equals( this, arg0);
    }
    
    
	public boolean equals(Object obj1, Object obj2) throws HibernateException {
		/* check we are dealing with non-null objects of the correct type */
        if( obj1 instanceof JGeometryType && obj2 instanceof JGeometryType && obj1 != null && obj2 != null) {
            JGeometry geo1 = ( (JGeometryType) obj1).getJGeometry();
            JGeometry geo2 = ( (JGeometryType) obj2).getJGeometry();
            /* check that they are the same type */
            if( geo1.getType() != geo2.getType()) {
                return false;
            }
            /* go through the different types and check for equality */
            switch( geo1.getType()) {
                case JGeometry.GTYPE_POINT: {
                    try {
                        return geo1.getJavaPoint().equals( geo2.getJavaPoint());
                    } catch( NullPointerException npe) {
                        /*
                         * one of the points is null, this is different type
                         * than the reported
                         */
                        return false;
                    }
                }
                case JGeometry.GTYPE_MULTIPOINT: {
                    try {
                        return Arrays.equals( geo1.getJavaPoints(), geo1.getJavaPoints());
                    } catch( NullPointerException npe) {
                        /*
                         * one of the points arrays is null, this is different
                         * type than the reported
                         */
                        return false;
                    }
                }
                    /*
                     * For lack of a better way of doing comparisons right
                     * now... Feel free to implement better comparisons
                     */
                case JGeometry.GTYPE_MULTICURVE:
                case JGeometry.GTYPE_MULTIPOLYGON:
                case JGeometry.GTYPE_POLYGON:
                case JGeometry.GTYPE_CURVE: {
                    return Arrays.equals( geo1.getOrdinatesOfElements(), geo2.getOrdinatesOfElements());
                }
                default: {
                    return false;
                }
            }
        } else {
            return false;
        }
	}

	public int hashCode(Object o) throws HibernateException {
		return ( (JGeometryType) o).hashCode();
	}

	public boolean isMutable() {
		return false;
	}

	public Object replace(Object o, Object o1, Object o2)
			throws HibernateException {
		return (JGeometryType) o;
	}

	public Class returnedClass() {
		return JGeometryType.class;
	}

	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	
	/* accessor */
    public JGeometry getJGeometry() {
        return geometryInstance;
    }
    
    
    /*
     * Helpers so you don't have to work directly with JGeometry if you don't
     * want Add to these as needed
     */
    /* JGeometry Constructors */
    public JGeometryType( double minX, double minY, double maxX, double maxY, int srid) {
        geometryInstance = new JGeometry( minX, minY, maxX, maxY, srid);
    }
 
    public JGeometryType( double x, double y, double z, int srid) {
        geometryInstance = new JGeometry( x, y, z, srid);
    }
 
    public JGeometryType( double x, double y, int srid) {
        geometryInstance = new JGeometry( x, y, srid);
    }
 
    public JGeometryType( int gtype, int srid, double x, double y, double z, int[] elemInfo, double[] ordinates) {
        geometryInstance = new JGeometry( gtype, srid, x, y, z, elemInfo, ordinates);
    }
 
    public JGeometryType( int gtype, int srid, int[] elemInfo, double[] ordinates) {
        geometryInstance = new JGeometry( gtype, srid, elemInfo, ordinates);
    }
    
    static public JGeometryType createCircle( double x1, double y1, double x2, double y2, double x3, double y3, int srid) {
        return new JGeometryType( JGeometry.createCircle( x1, y1, x2, y2, x3, y3, srid));
    }
 
    static public JGeometryType createCircle( double x, double y, double radius, int srid) {
        return new JGeometryType( JGeometry.createCircle( x, y, radius, srid));
    }
 
    static public JGeometryType createLinearLineString( double[] coords, int dim, int srid) {
        return new JGeometryType( JGeometry.createLinearLineString( coords, dim, srid));
    }
 
    static public JGeometryType createLinearMultiLineString( Object[] coords, int dim, int srid) {
        return new JGeometryType( JGeometry.createLinearMultiLineString( coords, dim, srid));
    }
 
    static public JGeometryType createLinearPolygon( double[] coords, int dim, int srid) {
        return new JGeometryType( JGeometry.createLinearPolygon( coords, dim, srid));
    }
 
    static public JGeometryType createLinearPolygon( Object[] coords, int dim, int srid) {
        return new JGeometryType( JGeometry.createLinearPolygon( coords, dim, srid));
    }
 
    static public JGeometryType createMultiPoint( Object[] coords, int dim, int srid) {
        return new JGeometryType( JGeometry.createMultiPoint( coords, dim, srid));
    }
 
    static public JGeometryType createPoint( double[] coord, int dim, int srid) {
        return new JGeometryType( JGeometry.createPoint( coord, dim, srid));
    }
    
    
    /* not really overrides, but helpers */
    static public double[] computeArc( double x1, double y1, double x2, double y2, double x3, double y3) {
        return JGeometry.computeArc( x1, y1, x2, y2, x3, y3);
    }
 
    public java.awt.Shape createShape() {
        return geometryInstance.createShape();
    }
 
    public int getDimensions() {
        return geometryInstance.getDimensions();
    }
 
    public int[] getElemInfo() {
        return geometryInstance.getElemInfo();
    }
 
    public double[] getFirstPoint() {
        return geometryInstance.getFirstPoint();
    }
 
    public java.awt.geom.Point2D getJavaPoint() {
        return geometryInstance.getJavaPoint();
    }
 
    public java.awt.geom.Point2D[] getJavaPoints() {
        return geometryInstance.getJavaPoints();
    }
 
    public java.awt.geom.Point2D getLabelPoint() {
        return geometryInstance.getLabelPoint();
    }
 
    public double[] getLastPoint() {
        return geometryInstance.getLastPoint();
    }
 
    public double[] getMBR() {
        return geometryInstance.getMBR();
    }
 
    public int getNumPoints() {
        return geometryInstance.getNumPoints();
    }
 
    public double[] getOrdinatesArray() {
        return geometryInstance.getOrdinatesArray();
    }
 
    public Object[] getOrdinatesOfElements() {
        return geometryInstance.getOrdinatesOfElements();
    }
 
    public double[] getPoint() {
        return geometryInstance.getPoint();
    }
 
    public long getSize() {
        return geometryInstance.getSize();
    }
 
    public int getSRID() {
        return geometryInstance.getSRID();
    }
 
    public int getType() {
        return geometryInstance.getType();
    }
 
    public boolean hasCircularArcs() {
        return geometryInstance.hasCircularArcs();
    }
 
    public boolean isCircle() {
        return geometryInstance.isCircle();
    }
 
    public boolean isGeodeticMBR() {
        return geometryInstance.isGeodeticMBR();
    }
    
    public boolean isLRSGeometry() {
        return geometryInstance.isLRSGeometry();
    }
 
    public boolean isMultiPoint() {
        return geometryInstance.isMultiPoint();
    }
 
    public boolean isPoint() {
        return geometryInstance.isPoint();
    }
 
    public boolean isRectangle() {
        return geometryInstance.isRectangle();
    }
 
    public static double[] linearizeArc( double x1, double y1, double x2, double y2, double x3, double y3) {
        return JGeometry.linearizeArc( x1, y1, x2, y2, x3, y3);
    }
    
    /*
    public static double[] linearizeArc( double x1, double y1, double x2, double y2, double x3, double y3, double tolerance) {
        return JGeometry.linearizeArc( x1, y1, x2, y2, x3, y3, tolerance);
    }
    */
 
    public static double[] linearizeArc( double x1, double y1, double x2, double y2, double x3, double y3, int numPoints) {
        return JGeometry.linearizeArc( x1, y1, x2, y2, x3, y3, numPoints);
    }
 
    public void setSRID( int srid) {
        geometryInstance.setSRID( srid);
    }
 
    public void setType( int gt) {
        geometryInstance.setType( gt);
    }

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] strings,
			SessionImplementor arg2, Object o) throws HibernateException,
			SQLException {
		
		STRUCT geometry = (STRUCT) resultSet.getObject( strings[0]);
        JGeometry jg = JGeometry.load( geometry);
        return resultSet.wasNull() ? null : new JGeometryType( jg);
	}

	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i,
			SessionImplementor arg3) throws HibernateException, SQLException {
		if( o == null) {
            preparedStatement.setNull( i, Types.OTHER);
        } else {
            if( o instanceof JGeometryType) {
                //OracleConnection oc = (OracleConnection) preparedStatement.getConnection();
            	            	
            	org.hibernate.engine.jdbc.spi.JdbcWrapper wrapper = 
            		(org.hibernate.engine.jdbc.spi.JdbcWrapper)preparedStatement.getConnection();
            	
            	WrappedConnectionJDK6 wrappedConn = (WrappedConnectionJDK6) wrapper.getWrappedObject();
            	java.sql.Connection oc = wrappedConn.getUnderlyingConnection();
            	
                STRUCT struct = JGeometry.store( (JGeometry) ( (JGeometryType) o).getJGeometry(), oc);
                preparedStatement.setObject( i, struct);

            }
        }
	}
	
}
