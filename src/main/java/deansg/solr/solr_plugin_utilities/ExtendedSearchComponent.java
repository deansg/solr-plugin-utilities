package deansg.solr.solr_plugin_utilities;

import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;

/**
 * A SearchComponent with more abstract methods that enable more features if
 * invoked appropriately
 */
public abstract class ExtendedSearchComponent extends SearchComponent {
    
    /**
     * Allows the component to react to an exception thrown during one of the query phases
     * @param rb The relevant ResponseBuilder
     * @param e The thrown exception
     */
    public abstract void onException(ResponseBuilder rb, Exception e);
}