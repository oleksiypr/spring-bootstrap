# tomcat6 or tomcat7
TOMCAT="tomcat7"
WEBAPPS="${HOME}/${TOMCAT}/webapps"
APP_NAME="$artifactId"
WAR_FILE="${WEBAPPS}/${APP_NAME}.war"

echo "Start"

# remove the file only if it exist
if [ -f "${WAR_FILE}" ]
then
    echo "Removing old war file: ""${WAR_FILE}"" "
    sudo rm "${WAR_FILE}"
    echo "Done."
fi

# remove unpacket folder only if it exist
if [ -d "${WEBAPPS}/${APP_NAME}" ]
then
    echo "Removing old folder: ""${WEBAPPS}/${APP_NAME}"" "
    sudo rm -r "${WEBAPPS}/${APP_NAME}"
    echo "Done."
fi

sudo cp ./"${APP_NAME}"*.war "${WAR_FILE}"
sudo chown ${TOMCAT}:${TOMCAT} "${WAR_FILE}"

echo "Tomcat starting up..."
sudo sh ${TOMCAT}/bin/startup.sh
echo "Complete"

exit 0