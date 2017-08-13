package deansg.solr.solr_plugin_utilities;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation placed on a field of a SolrPluginConfiguration class with
 * information about the field. It is automatically initialized when the
 * intiializeConfiguration() method is called (usually when the configuration
 * instance is created)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface ConfigField {

    /**
     * 
     * @return The name (in the configuration) of the field
     */
    String fieldName();
    
    /**
     * 
     * @return The type of the configuration field 
     */
    ConfigFieldType fieldType();
    
    /**
     * 
     * @return Whether a value for the field must be specified in the configuration
     */
    boolean isRequired();
}