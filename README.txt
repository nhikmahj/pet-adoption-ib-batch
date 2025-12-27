Hello !

This is a simple inbound batch project that I made based of my prior working experiences. I build this project
using gradle and Spring Batch framework.

So what this batch application will do :
# Read an external text file and store the contents into a staging table with status "PROCESSING"
# The Reader will fetch all data with status "PROCESSING" from staging table
# The Processor will do all the necessary data conversion (e.g string flag to boolean, code to names)
# The writer will store the processed data into the application db and update the staging table status to completed

What you need to set up in order to run the batch :
# setup db connectivity -> we need 2 db connection for this project. I am using mySQL.
 1- batch datasource : for batch processing purpose, this db will have the staging tables.
 2- pet datasource : this is the application datasource.
# input text file -> configure the path in the application.properties
<script and files available in resources package>

Happy Running! ₍^. .^₎Ⳋ
