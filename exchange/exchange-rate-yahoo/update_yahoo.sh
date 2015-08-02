printf "\n Downloading the Yahoo resource. \n"
wget http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote
mv quote src/main/resources/java-money/defaults/YAHOO/finance.xml
printf "\n Done. \n"
