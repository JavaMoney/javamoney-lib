printf "\n Downloading the Yahoo resource. \n"
wget http://www.federalreserve.gov/feeds/data/H10_H10.XML
mv H10_H10.XML src/main/resources/java-money/defaults/FRB/H10_H10.XML
printf "\n Done. \n"
