# refactored-octo-umbrella
Spring Boot + Java 21 + GCP


[Google Cloud SQL](https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory)

[Connecting to Cloud SQL from Cloud Run](https://cloud.google.com/sql/docs/postgres/connect-run)

Follow the guide me to create Cloud SQL instance first and then create Cloud Run Service instance to connect

[Click on Guide me](https://cloud.google.com/sql/docs/postgres/connect-instance-cloud-run)


for kafkalistner to read messages from topic 
1. --no-cpu-throttling must be added to the gcloud run deploy

 - name: Deploy to Cloud Run
      run: |
        gcloud run deploy ${{ secrets.GCP_SERVICE_NAME }} \
          --image docker.io/${{ secrets.DOCKERHUB_USERNAME }}/${{ secrets.DOCKERHUB_IMAGE_NAME }}:sha-${{ github.sha }} \
          --platform managed \
          --region ${{ secrets.GCP_REGION }} \
          --add-cloudsql-instances=${{ secrets.CLOUDSQL_INSTANCE }} \
          --no-cpu-throttling \
          --allow-unauthenticated \
          --set-env-vars JDBC_DATABASE_USERNAME=${{ secrets.JDBC_DATABASE_USERNAME }},JDBC_DATABASE_PASSWORD=${{ secrets.JDBC_DATABASE_PASSWORD }},JDBC_DATABASE_URL='${{ secrets.JDBC_DATABASE_URL }}',KAFKA_BOOTSTRAP_SERVER='${{ secrets.KAFKA_BOOTSTRAP_SERVER }}',CLUSTER_API_KEY='${{ secrets.CLUSTER_API_KEY }}',CLUSTER_API_SECRET='${{ secrets.CLUSTER_API_SECRET }}',DEPLOYMENT_TIMESTAMP=$(date +%s)
