package deansg.solr.solr_plugin_utilities;

/**
 * An exception thrown during the initialization of a SolrPLuginConfiguration
 * instance. It derives from runtime exception as it can be thrown on a
 * SolrPlugin's init functino, and therefore cannot be declared explicitly.
 * Also, logically, the application cannot be expected to recover when the
 * configuration is faulty
 */
public class SolrConfigurationInitializationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SolrConfigurationInitializationException(String message) {
	super(message);
    }

    public SolrConfigurationInitializationException(Exception innerException) {
	super(innerException);
    }
}