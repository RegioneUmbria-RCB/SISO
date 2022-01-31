package it.webred.mui.hibernate;

public interface FieldConverter {
	Object convertObject(Object val);
	Object convertObjects(Object[] val);
}
