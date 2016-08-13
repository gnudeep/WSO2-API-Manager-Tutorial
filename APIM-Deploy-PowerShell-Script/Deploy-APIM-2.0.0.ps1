Write-Host WSO2 APIM 2.0.0 Deployment Started.

If ($args.Count -lt 3)
{
Write-Host Syntax : c:\Deploy-APIM-2.0.0 ArtifactPath APIManagerHome
}

$ArtifactFullPath=$args[0]
$APIManagerDeploymentPath=$args[1]

$APIArtifactName="wso2am-2.0.0.zip"
$JDBCDriver="sqljdbc4.jar"
$KeyStore="wso2carbon.jks"
$TrustStore="client-truststore.jks"

$APIMFolder="wso2am-2.0.0"
$APIMLibPath = "\repository\components\lib"
$APIMJKSPath = "\repository\resources\security"

#Unzip the APIM Artifact
Write-Host Unziping $APIArtifactName
Expand-Archive -LiteralPath $ArtifactFullPath\$APIArtifactName -DestinationPath $APIManagerDeploymentPath

#Copy JDBC dirver 
Write-Host Coping $JDBCDriver
Copy-Item $ArtifactFullPath\$JDBCDriver $APIManagerDeploymentPath\$APIMFolder\$APIMLibPath

#Copy Keystore
Write-Host Copying $KeyStore
Copy-Item $ArtifactFullPath\$KeyStore $APIManagerDeploymentPath\$APIMFolder\$APIMJKSPath

#Copy Truststore
Write-Host Copying $TrustStore
Copy-Item $ArtifactFullPath\$TrustStore $APIManagerDeploymentPath\$APIMFolder\$APIMJKSPath
