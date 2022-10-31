echo "init application for deploy \n "
echo "APP_NAME : "
read app_name
echo "APP_NAMESPACE(=default) : "
read app_namespace
echo "SIZE OF REPLICASET(=1) : "
read replica_size
echo "APP_IMAGE"
read app_img
echo "APP_PATH"
read app_path
echo "====setting BE env===="
echo "DB_HOST"
read db_host
echo "DB_NAME"
read db_name
echo "DB_USER"
read db_user
echo "DB_PASSWORD"
read db_password

if [ -z $app_name ]
then
  echo "app_name required\n"
  exit 9
fi
if [ -z $app_img ]
then
  echo "app_img required\n"
  exit 9
fi
if [ -z $app_path ]
then
  echo "app_path required\n"
  exit 9
fi
if [ -z $app_namespace ]
then
  echo "app_namespace is set default\n"
fi
if [ -z $replica_size ] 
then
  echo "replica_size is set 1\n"
fi

export APP=$app_name
export APP_NAMESPACE=$app_namespace
export APP_IMAGE=$app_img
export APP_PATH=$app_path
export REPLICAS=$replica_size
export DB_HOST=$db_host
export DB_NAME=$db_name
export DB_USER=$db_user
export DB_PASSWORD=$db_password

# envsubst < ./template.yaml | kubectl apply -f -
envsubst < ./template-be.yaml > ${app_namespace}-be-deployment.yaml

if [ $? -eq 0 ];then
    echo "SUCCESS\n
    APP_NAME : $APP\n
    APP_NAMESPACE : $APP_NAMESPACE\n
    APP_IMAGE : $APP_IMAGE\n
    APP_PATH : $APP_PATH\n
    REPLICAS : $APP_PATH\n"
else
    echo "deploy Failure!"
    exit 9
fi