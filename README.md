# EsperMobileStore

ESPER ANDROID ASSIGNMENT

This Repository contains source code to fetch mobile phones and their features. 

## Model

Selection has featureId and optionId
Features has meta data of feature
Options has meta data of options and a foreign key of feature which this option belongs to
Exclusions, has two column names selection1Id and selection2Id
## UI

UI has two screens 
You can select the right combination onclick of the items and click select
If the combination exists you will be redirected to another screen with the selected items in a list.
Usage of recyclerview. 

## Networking
Retrofit is used for handling networking

## Database
Sqlite is used to store data for no internet local caching


