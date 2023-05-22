# Andromeda
##### "Andromeda" the name of the galaxy that represents nothing about this task. It is used to search the repositories from GitHub for the moment and having the flexibility to add search functionalities from the other repositories as well.
<br/>

##### Prerequisite
- jdk 11 
- maven
- docker
- make sure port 8085 is not used. If so, help yourself to change the port number(server.port=xxxx) in application.properties

####swagger 
http://localhost:8085/api/swagger-ui.html

####up docker-compose
- docker-compose up

docker run -p 5432:5432 -d \
    -e POSTGRES_PASSWORD=postgres \
    -e POSTGRES_USER=posrtgres \
    -e POSTGRES_DB=andromeda \
    -v pgdata:/var/lib/postgres/data \
    postgres