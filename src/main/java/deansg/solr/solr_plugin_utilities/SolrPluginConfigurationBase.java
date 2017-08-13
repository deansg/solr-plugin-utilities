package deansg.solr.solr_plugin_utilities;

import java.lang.reflect.Field;

import org.apache.solr.common.util.NamedList;

/**
 * A base class for classes representing a configuration of a certain custom
 * Solr plug-in, providing functionality for initializing the configuration
 * class' fields. It should usually be derived from and used together with
 * fields having the {@link deansg.solr.solr_plugin_utilities.ConfigField}
 * annotation. However, in case the configuration contains only few fields it
 * can be instantiated and used directly through the various get...Field
 * methods.
 */
@SuppressWarnings("rawtypes")
public class SolrPluginConfigurationBase {

    /**
     * The NamedList passed to the current instance when calling
     * {@link deansg.solr.solr_plugin_utilities.SolrPluginConfigurationBase#initializeConfiguration(NamedList)}
     */
    protected NamedList args;

    /**
     * Creates and initializes a new instance of a SolrPluginConfiguration,
     * assigning values to all fields containing the ConfigField annotation in the
     * current instance
     * 
     * @param args
     *            The NamedList used to initialize the SolrPLuginConfiguration
     * @throws SolrConfigurationInitializationException
     *             If one of the configuration's fields didn't meet the requested
     *             criteria
     */
    public SolrPluginConfigurationBase(NamedList args) throws SolrConfigurationInitializationException {
	initializeConfiguration(args);
    }

    private void initializeConfiguration(NamedList args) throws SolrConfigurationInitializationException {
	this.args = args;
	for (Field field : this.getClass().getDeclaredFields()) {
	    ConfigField[] configFieldAnnotations = field.getAnnotationsByType(ConfigField.class);
	    if (configFieldAnnotations.length == 0) {
		continue;
	    }

	    // The ConfigField annotation isn't repeatable
	    ConfigField configFieldsAnnotation = configFieldAnnotations[0];
	    field.setAccessible(true); // In order to modify private fields
	    Object value = getValueForField(configFieldsAnnotation);
	    setValueForField(field, configFieldsAnnotation, value);
	}
    }

    /**
     * Gets an Integer parameter from the NamedList. If the parameter isn't required
     * and isn't in the NamedList, returns null. Throws a
     * {@link deansg.solr.solr_plugin_utilities.SolrConfigurationInitializationException}
     * if the parameter is required but missing, or if the parameter is provided but
     * isn't an Integer
     * 
     * @param parameterName
     *            The name of the parameter in the configuration
     * @param isRequired
     *            Whether the parameter must be specified in the configuration
     * @return The value of the Integer parameter
     */
    public Integer getIntParameter(String parameterName, boolean isRequired)
	    throws SolrConfigurationInitializationException {
	return getParameter(parameterName, isRequired, Integer.class.getName());
    }

    /**
     * Gets a Long parameter from the NamedList. If the parameter isn't required and
     * isn't in the NamedList, returns null. Throws a
     * {@link deansg.solr.solr_plugin_utilities.SolrConfigurationInitializationException}
     * if the parameter is required but missing, or if the parameter is provided but
     * isn't a Long
     * 
     * @param parameterName
     *            The name of the parameter in the configuration
     * @param isRequired
     *            Whether the parameter must be specified in the configuration
     * @return The value of the Long parameter
     */
    public Long getLongParameter(String parameterName, boolean isRequired)
	    throws SolrConfigurationInitializationException {
	return getParameter(parameterName, isRequired, Long.class.getName());
    }

    /**
     * Gets a Float parameter from the NamedList. If the parameter isn't required
     * and isn't in the NamedList, returns null. Throws a
     * {@link deansg.solr.solr_plugin_utilities.SolrConfigurationInitializationException}
     * if the parameter is required but missing, or if the parameter is provided but
     * isn't a Float
     * 
     * @param parameterName
     *            The name of the parameter in the configuration
     * @param isRequired
     *            Whether the parameter must be specified in the configuration
     * @return The value of the Float parameter
     */
    public Float getFloatParameter(String parameterName, boolean isRequired)
	    throws SolrConfigurationInitializationException {
	return getParameter(parameterName, isRequired, Float.class.getName());
    }

    /**
     * Gets a Double parameter from the NamedList. If the parameter isn't required
     * and isn't in the NamedList, returns null. Throws a
     * {@link deansg.solr.solr_plugin_utilities.SolrConfigurationInitializationException}
     * if the parameter is required but missing, or if the parameter is provided but
     * isn't a Double
     * 
     * @param parameterName
     *            The name of the parameter in the configuration
     * @param isRequired
     *            Whether the parameter must be specified in the configuration
     * @return The value of the Double parameter
     */
    public Double getDoubleParameter(String parameterName, boolean isRequired)
	    throws SolrConfigurationInitializationException {
	return getParameter(parameterName, isRequired, Double.class.getName());
    }

    /**
     * Gets a String parameter from the NamedList. If the parameter isn't required
     * and isn't in the NamedList, returns null. Throws a
     * {@link deansg.solr.solr_plugin_utilities.SolrConfigurationInitializationException}
     * if the parameter is required but missing, or if the parameter is provided but
     * isn't a String
     * 
     * @param parameterName
     *            The name of the parameter in the configuration
     * @param isRequired
     *            Whether the parameter must be specified in the configuration
     * @return The value of the String parameter
     */
    public String getStringParameter(String parameterName, boolean isRequired)
	    throws SolrConfigurationInitializationException {
	return getParameter(parameterName, isRequired, String.class.getName());
    }

    /**
     * Gets a Boolean parameter from the NamedList. If the parameter isn't required
     * and isn't in the NamedList, returns null. Throws a
     * {@link deansg.solr.solr_plugin_utilities.SolrConfigurationInitializationException}
     * if the parameter is required but missing, or if the parameter is provided but
     * isn't a Boolean
     * 
     * @param parameterName
     *            The name of the parameter in the configuration
     * @param isRequired
     *            Whether the parameter must be specified in the configuration
     * @return The value of the Boolean parameter
     */
    public Boolean getBooleanParameter(String parameterName, boolean isRequired)
	    throws SolrConfigurationInitializationException {
	return getParameter(parameterName, isRequired, Boolean.class.getName());
    }

    /**
     * Throws an exception for an event that occured during the initialization of
     * the configuration object
     * 
     * @param message
     *            The message of the eception to throw
     * @return Doesn't return a value - guaranteed to throw an exception. The return
     *         type is used to simplify code calling this method
     */
    protected <T> T throwInitializationException(String message) throws SolrConfigurationInitializationException {
	throw new SolrConfigurationInitializationException(message);
    }

    @SuppressWarnings("unchecked")
    private <T> T getParameter(String parameterName, boolean isRequired, String className)
	    throws SolrConfigurationInitializationException {
	Object rawValue = args.get(parameterName);
	if (rawValue == null) {
	    if (isRequired) {
		return throwInitializationException(String.format("No %s parameter was specified", parameterName));
	    }
	    return null;
	}
	try {
	    return (T) rawValue;
	} catch (ClassCastException ex) {
	    return throwInitializationException(String.format("Parameter $s must be a %s", parameterName, className));
	}
    }

    private Object getValueForField(ConfigField configFieldAnnotation) throws SolrConfigurationInitializationException {
	String fieldName = configFieldAnnotation.fieldName();
	boolean isRequired = configFieldAnnotation.isRequired();
	switch (configFieldAnnotation.fieldType()) {
	case INT: {
	    return getIntParameter(fieldName, isRequired);
	}
	case STRING: {
	    return getStringParameter(fieldName, isRequired);
	}
	case BOOLEAN: {
	    return getBooleanParameter(fieldName, isRequired);
	}
	case LONG: {
	    return getLongParameter(fieldName, isRequired);
	}
	case FLOAT: {
	    return getFloatParameter(fieldName, isRequired);
	}
	case DOUBLE: {
	    return getDoubleParameter(fieldName, isRequired);
	}
	default: {
	    return throwInitializationException("Unsupported field type: " + configFieldAnnotation.fieldType());
	}
	}
    }

    private void setValueForField(Field field, ConfigField configFieldAnnotation, Object value)
	    throws SolrConfigurationInitializationException {
	try {
	    if (value != null) {
		field.set(this, value);
	    }
	} catch (IllegalArgumentException ex) {
	    throwInitializationException(String.format("Field $s is of the wrong type. Expected: %s, actual: %s",
		    field.getName(), configFieldAnnotation.fieldType(), field.getDeclaringClass().getName()));
	} catch (IllegalAccessException ex) {
	    throwInitializationException(String.format("Field %s cannot be modified with reflection", field.getName()));
	}
    }
}