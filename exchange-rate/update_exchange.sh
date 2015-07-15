printf "\n Downloading the ECB daily resource. \n"
wget http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml
mv eurofxref-daily.xml exchange-rate-ecb/src/main/resources/java-money/defaults/ECB/eurofxref-daily.xml
printf "\n Done. Downloading the ECB-90 resource. \n"
wget http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml
mv eurofxref-hist-90d.xml exchange-rate-ecb/src/main/resources/java-money/defaults/ECB/eurofxref-hist-90d.xml
printf "\n Done. Downloading the ECB-hist resource. \n"
wget http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist.xml
mv eurofxref-hist.xml exchange-rate-ecb/src/main/resources/java-money/defaults/ECB/eurofxref-hist.xml
printf "\n Done. Downloading the IMF resource. \n"
wget http://www.imf.org/external/np/fin/data/rms_five.aspx?tsvflag=Y
mv rms_five.aspx?tsvflag=Y rms_five.xls
mv rms_five.xls exchange-rate-imf/src/main/resources/java-money/defaults/IMF/rms_five.xls
printf "\n Done. Job finished and all exchange rate resources were updated. \n"
