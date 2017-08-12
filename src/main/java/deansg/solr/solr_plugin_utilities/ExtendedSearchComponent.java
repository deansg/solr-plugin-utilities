package deansg.solr.solr_plugin_utilities;

import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;

/*
 * 
 */
public abstract class ExtendedSearchComponent extends SearchComponent {
   public abstract void onException(ResponseBuilder rb); 
}