package it.webred.ct.data.spatial;

import java.sql.Types;

import org.hibernate.dialect.Oracle9iDialect;

public class OracleSpatialDialect extends Oracle9iDialect {
	public OracleSpatialDialect() {
        super();
        registerColumnType( Types.OTHER, "sdo_geometry");
    }
}
