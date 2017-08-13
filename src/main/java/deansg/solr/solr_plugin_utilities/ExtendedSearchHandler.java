package deansg.solr.solr_plugin_utilities;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.solr.core.SolrCore;
import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchHandler;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

/**
 * A SearchHandler that takes advantage of SearchComponents deriving from
 * {@link deansg.solr.solr_plugin_utilities.ExtendedSearchComponent}
 */
public class ExtendedSearchHandler extends SearchHandler {
    protected List<ExtendedSearchComponent> extendedComponents;

    /**
     * Calls {@link org.apache.solr.handler.component.SearchHandler}'s inform method
     * and in addition initializes the list of extended search components
     */
    @Override
    public void inform(SolrCore core) {
	super.inform(core);
	this.extendedComponents = this.getComponents().stream()
		.filter(component -> component instanceof ExtendedSearchComponent)
		.map(component -> (ExtendedSearchComponent) component).collect(Collectors.toList());
    }

    /**
     * Calls {@link org.apache.solr.handler.component.SearchHandler}'s
     * handleRequestBody method inside a try-catch block, and allows
     * ExtendedSearchComponents to react to a thrown exception before passing it
     * further
     */
    @Override
    public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse rsp) throws Exception {
	try {
	    super.handleRequestBody(req, rsp);
	} catch (Exception ex) {
	    ResponseBuilder rb = new ResponseBuilder(req, rsp, components);
	    for (ExtendedSearchComponent component : this.extendedComponents) {
		component.onException(rb, ex);
	    }
	    throw ex;
	}
    }
}