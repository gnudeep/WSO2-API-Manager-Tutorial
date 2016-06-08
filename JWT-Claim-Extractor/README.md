#JWT Claim Extractor Sample

This sample extract and print the Claims encoded in the JWT token pass to the backend.

#API Mananger configuraiton.

Enable JWT creation by enabling 
```
	api-manager.xml

	<APIManager>	
	|	
	+ <JWTConfiguration>
          |
	  + <EnableJWTGeneration>true</EnableJWTGeneration>
``` 

Create an API using the backend as the JWT Claim Extractor sample and invoke using the API Console to see the extracted claims in the JWT Claim Extractor micro service system out.
