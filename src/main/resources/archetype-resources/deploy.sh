APP_NAME="${artifactId}"

# start Maven build if war doesn't exist
if test ! -f target/"${APP_NAME}".war
then
    #Maven build
    mvn clean install
fi

#Copy to demo server
echo "Uploading to demo server"
scp target/"${APP_NAME}".war ${USER}@newdev.anahoret.com:/home/${USER}
scp deploy-local.sh ${USER}@newdev.anahoret.com:/home/${USER}
echo "Done"

#Run remote
ssh newdev.anahoret.com 'sudo chown ${USER}:admin ~/deploy-local.sh; sudo chmod u+x ~/deploy-local.sh; ~/deploy-local.sh'

