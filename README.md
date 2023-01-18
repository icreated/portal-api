# &int; created 
> Integration created
[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=7TYVAGLZ7XATQ&source=url) 

# Icreated Portal API (IPA) for ERP Idempiere
> 

This REST API OSGI plugin for ERP Idempiere initially has been created to communicate with Icreated Web Portal 
[https://github.com/icreated/portal-frontend](https://github.com/icreated/portal-frontend) 
It can be used with other type of integration, for example as an alternative to Idempiere Web Services

IPA is fully based on Spring Framework and integrates its features:

*   Spring configuration with @Configuration annotation
*	Idempiere context injected by application.properties
*	Simple architecture based on Dependency Injection
*	Spring Security integration
 


IPA provides following features:

*	API-first product-centric approach 
*	Automatic generated DTO models with openapi-generator maven plugin
*	MapStruct converting between DTO and Idempiere Model
*	Database requests with PQuery (Idempiere Query wrapper) 
*	JWT Spring Security authentication
*   Hibernate Validator for declarative OpenApi DTO validation
*	Swagger Integration with [https://github.com/icreated/swagger](https://github.com/icreated/swagger) 
*	Invoices and Payments lists
*	OpenItems, i.e. payment due under contract
*	Payment Imitation by Credit Card



## Installing / Getting started

To build this plugin you need to get sources in your project directory with same parent as Idempiere source folder

```shell
git clone https://github.com/icreated/portal-api.git
```
Import this project to Eclipse.
Be sure to satisfy all required dependencies. All needed jars are added directly to lib folder.
Eclipse needs a mapstruct plugin to compile converters. It can be found here [https://mapstruct.org/documentation/ide-support/](https://mapstruct.org/documentation/ide-support/) 

Follow instructions for installation
Fill in generated source directory as: src

openapi.yaml is configured in Icreated Web Portal. It's easier to work with one file and share it between backend & frontend. The copy of openapi.yaml is automatically copied with a maven plugin to project because of use of swagger plugin when Idempiere sever is started.

### Deploying / Publishing / Testing
Check if it works by accessing to Swagger Home Page:
[http://localhost:8888/portal/api](http://localhost:8888/portal/api) 
To connect to API you have to provide AD_User Value and Password. For example: gardenusr / GardenUser

!["Swagger UI"](Swagger_UI.png "Swagger UI") 


## Contributing

If you'd like to contribute, please fork the repository and use a feature
branch. 

Pull requests are warmly welcome


## Licensing

GNU General Public License
