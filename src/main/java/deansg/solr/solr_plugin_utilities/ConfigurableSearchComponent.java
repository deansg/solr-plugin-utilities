package deansg.solr.solr_plugin_utilities;

import org.apache.solr.common.util.NamedList;
import org.apache.solr.handler.component.SearchComponent;

/**
 * A base class for SearchComponents that require a separate configuration.
 * Takes care of initializing the configuration
 * 
 * @param <T>
 *            The type representing the configuration, deriving from
 *            SolrPluginConfigurationBase
 */
@SuppressWarnings("rawtypes")
public abstract class ConfigurableSearchComponent<T extends SolrPluginConfigurationBase> extends SearchComponent {
    private T config;

    /**
     * Creates a configuration object from the given NamedList
     * 
     * @param args
     *            The NamedList originally provided to the factory
     * @return A configuration object using the NamedList
     */
    protected abstract T createConfig(NamedList args);

    protected T config() {
	return this.config;
    }

    @Override
    public void init(NamedList args) {
	super.init(args);
	this.config = createConfig(args);
    }
}