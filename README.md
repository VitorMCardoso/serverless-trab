# serverless

1. Com suas credentials configuradas, execute os comandos abaixo

2. Execute o comando `mvn clean install ` para o build do projeto

3. Executo o comando `sam deploy --template-file serverless.yaml --capabilities CAPABILITY_IAM --stack-name serverless --s3-bucket [BUCKET_NAME]`

4. Acessando a AWS a stack do cloudformation já foi criada.

5. Para testes acessar o API Gateway.


