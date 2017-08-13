# solr-plugin-utilities
Several classes that assist developers of plug-ins for Apache Solr

This project was written to help develop plug-ins for Apache Solr, mainly Search Componenets and Update Processors (but not just!). 
The SolrPluginConfigurationBase class and related classes help developing plug-ins with relatively large configurations (containing
multiple fields) and can save writing a lot of boilerplate code needed when accessing Solr's given NamedList. Using the ConfigField
annotation on fields of a separate configuration class can help divide the code of large plug-ins, and make it more readable.

In addition, the ExtendedSearchHandler can be used in a Solr cluster instead of a regular SearchHandler if one wants to develop
SearchComponents with extended functionality. Currently, the only added method is onException, but more might be added in the future.
