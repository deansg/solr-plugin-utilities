# solr-plugin-utilities
Several classes that assist developers of plugins for Apache Solr

This project was written to help develop plugins for Apache Solr, mainly Search Componenets and Update Processors (but not just!). 
The SolrPluginConfigurationBase class and related classes help developing plugins with relatively large configurations (containing
multiple fields) and can save writing a lot of boilerplate code. Using the ConfigField annotation on fields of a separate configuration class
can help divide the code of large plugins, and make it more readable.

In addition, the ExtendedSearchHandler can be used in a Solr cluster instead of a regular SearchHandler if one wants to develop
SearchComponents with extended functionality. Currently, the only added method is onException, but more might be added in the future.
