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
