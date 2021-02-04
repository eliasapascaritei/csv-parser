csv-parser

requirements:
* java 8
* maven
* docker

Start postgres docker with command
 
`$docker run  --name stratify -e POSTGRES_USER=stratify -e POSTGRES_PASSWORD=pass123 -e POSTGRES_DB=stratify -d -p 5432:5432 postgres`

Start app(intellij has built in tool for that)

Open browser and access

`http://localhost:9000/swagger-ui.html`

Use swagger-ui to test the endpoints.
