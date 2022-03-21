package it.webred.ct.data.access.basic.pgt.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class PgtBaseDAO extends CTServiceBaseDAO {
	
	//@Resource(mappedName="java:/jdbc/Diogene_DS")
	@Resource(name="ctDataSourceRouter")
	DataSource dataSource;
	
}
