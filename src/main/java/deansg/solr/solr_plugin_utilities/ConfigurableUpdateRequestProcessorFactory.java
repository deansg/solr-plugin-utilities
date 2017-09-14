package deansg.solr.solr_plugin_utilities;

import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.update.processor.UpdateRequestProcessor;
import org.apache.solr.update.processor.UpdateRequestProcessorFactory;

/**
 * A base class for UpdateRequestProcessorFactories that produce
 * UpdateRequestProcessors that require a separate configuration. Takes care of
 * the typical behavior of passing the configuration object from the factory to
 * the individual instances
 * 
 * @param <T>
 *            The type representing the configuration, deriving from
 *            SolrPluginConfigurationBase
 */
@SuppressWarnings("rawtypes")
public abstract class ConfigurableUpdateRequestProcessorFactory<T extends SolrPluginConfigurationBase>
	extends UpdateRequestProcessorFactory {
    private T config;

    /**
     * Creates a configuration object from the given NamedList
     * 
     * @param args
     *            The NamedList originally provided to the factory
     * @return A configuration object using the NamedList
     */
    protected abstract T createConfig(NamedList args);

    /**
     * Same as
     * {@link org.apache.solr.update.processor.UpdateRequestProcessorFactory#getInstance(SolrQueryRequest, SolrQueryResponse, UpdateRequestProcessor)}
     * but provides the configuration as an additional parameter
     * 
     * @param req
     *            the Solr query request
     * @param rsp
     *            The Solr query response
     * @param next
     *            The next UpdateRequestProcessor in the update chain
     * @param config
     *            The configuration for the current update request processor
     * @return A newly created UpdateRequestProcessor instance to use in the update
     *         chain
     */
    protected abstract UpdateRequestProcessor getInstance(SolrQueryRequest req, SolrQueryResponse rsp,
	    UpdateRequestProcessor next, T config);

    /**
     * Returns the current instance's configuration
     * 
     * @return the current instance's configuration
     */
    protected T config() {
	return this.config;
    }

    @Override
    public void init(NamedList args) {
	super.init(args);
	this.config = createConfig(args);
    }

    @Override
    public UpdateRequestProcessor getInstance(SolrQueryRequest req, SolrQueryResponse rsp,
	    UpdateRequestProcessor next) {
	return getInstance(req, rsp, next, this.config);
    }
}