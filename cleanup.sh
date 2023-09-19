#!/bin/bash
set -eo pipefail

source ./setupEnvironment.sh

echo "Deleting Application LBC_DEPLOY_STACK"
echo "This may take 2-3 minutes...  But if takes more than 5 minutes then it may have failed. Check your CloudFormation Stack on the AWS UI for errors."
aws cloudformation delete-stack --stack-name $LBC_DEPLOY_STACK
aws cloudformation wait stack-delete-complete --stack-name $LBC_DEPLOY_STACK
echo ""


echo "Checking Artifact Bucket $LBC_ARTIFACT_BUCKET"
if [ -z "$(aws s3api head-bucket --bucket "$LBC_ARTIFACT_BUCKET" 2>&1)" ] ; then
  echo "Deleting Artifact Bucket $LBC_ARTIFACT_BUCKET" ;
  aws s3 rm s3://$LBC_ARTIFACT_BUCKET --recursive ;
  {
    aws s3api delete-objects --bucket $LBC_ARTIFACT_BUCKET --delete "$(aws s3api list-object-versions --bucket $LBC_ARTIFACT_BUCKET --query='{Objects: Versions[].{Key:Key,VersionId:VersionId}}')" 1>/dev/null
  } || {
    echo "Deleting objects failed. Check your S3 Bucket on the AWS UI for errors."
  } ;
  {
    aws s3api delete-objects --bucket $LBC_ARTIFACT_BUCKET --delete "$(aws s3api list-object-versions --bucket $LBC_ARTIFACT_BUCKET --query='{Objects: DeleteMarkers[].{Key:Key,VersionId:VersionId}}')" 1>/dev/null
  } || {
    echo "Deleting marked objects failed. Check your S3 Bucket on the AWS UI for errors."
  } ;
  {
    aws s3 rb --force s3://$LBC_ARTIFACT_BUCKET
  } || {
    echo "Deleting Bucket $LBC_ARTIFACT_BUCKET failed. Check your S3 Bucket on the AWS UI for errors."
  } ;
fi
echo ""


echo "Deleting Pipeline $LBC_PIPELINE_STACK"
echo "This may take 2-3 minutes...  But if takes more than 5 minutes then it may have failed. Check your CloudFormation Stack on the AWS UI for errors."
aws cloudformation delete-stack --stack-name $LBC_PIPELINE_STACK
aws cloudformation wait stack-delete-complete --stack-name $LBC_PIPELINE_STACK
echo ""

#change Example to the name of the table needing to be deleted,
# if there is more than one table copy the block of code and modify the name accordingly
TABLE_NAME="Example"
echo "Checking Table $TABLE_NAME"
{
  aws dynamodb delete-table --table-name $TABLE_NAME > /dev/null 2>&1 &&
  echo "Deleting Table $TABLE_NAME" &&
  echo "This may take 2-3 minutes...  But if takes more than 5 minutes then it may have failed. Check your DynamoDB tables on the AWS UI for errors." &&
  ( aws dynamodb wait table-not-exists --table-name $TABLE_NAME ||
   echo "Table may have not deleted. Check your DynamoDB tables on the AWS UI for errors." )
} || {
  echo "Table $TABLE_NAME does not exist"
} ;


rm -rf build .gradle target
