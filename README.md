# Spring Social Salesforce

Spring Social Salesforce is a Spring Social extension that provides connection support and api binding for Salesforce
REST API.

To check out the project and build from source, do the following:

    git clone git://github.com/umut/spring-social-salesforce.git
    cd spring-social-salesforce
    mvn clean install

## Supported Operations
 - Retrieve all available api versions
 - Retrieve services supported by a specific version of the api
 - Retrieve the list of sObject's
 - Retrieve summary-metadata of an sObject
 - Retrieve full-metadata of an sObject
 - Retrieve a row from an sObject
 - Retrieve a blob from a row in an sObject
 - Create a new sObject
 - Update an existing sObject
 - Retrieve recent changes feed
 - Execute a SOSL search and retrieve the results (with paging or all)
 - Run a SOQL query and retrieve the results (with paging or all)
 - Retrieve user profile
 - Retrieve user status
 - Update user status

## License

Spring Social Saleforce is released under version 2.0 of the [Apache License].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0
