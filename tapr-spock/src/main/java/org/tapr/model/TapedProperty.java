package org.tapr.model;

import java.util.ArrayList;
import java.util.Collection;

public class TapedProperty {

	private String name;
	private Class<?> type;
	private Object value;
	
	private Collection<TapedProperty> properties = new ArrayList<TapedProperty>();
	
	
}
