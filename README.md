## Andromeda
#### "Andromeda" the name of the galaxy that represents nothing about this task. 

#### Having Store, Product, Items relationship apis.
    
 - ProductController
 - StoreController
 - StoreItemController
    
#### Also, 

- It is used to search the repositories from GitHub (VersionControlRepositoryController) for the moment and having the flexibility to add search functionalities from the other repositories as well.


#### Prerequisite
- jdk 11 
- maven
- docker
- make sure port 8085 is not used. If so, help yourself to change the port number(server.port=xxxx) in application.properties

#### swagger 
http://localhost:8085/api/swagger-ui.html

#### up docker-compose
- docker-compose up <br/>
  This creates the postgres db `postgres` username: `postgres` password: `password`

  <br\>
   is contains below value
   ```sh
   docker run -p 5432:5432 -d \
    -e POSTGRES_PASSWORD=password \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_DB=postgres \
    -v pgdata:/var/lib/postgres/data \
    postgres
    ```

- To check this please run the application, should connect with the db on mentioned port 5432 (make sure the port is available)

#### mvn clean install
- run `mvn clean install`: This creates andromeda-0.0.1-SNAPSHOT.jar inside target

#### docker build .
- run `docker build andromeda:latest .` : This builds docker image
- verify with `docker images`

  | REPOSITORY | TAG    | IMAGE ID | CREATED    |  SIZE |
  |-----|----------|-----|---|---|
  | andromeda | latest | 5146dc8bc857 | 4 minutes ago |  197MB |
  | postgres  | alpine | 5605c4748747 | 3 days ago    |  237MB |

- you could see the information like above

#### Start container
- make sure before run the below command stop your application in local and free the port 8085
- run `docker run -dp 8085:8085 andromeda:latest`
- run `docker ps` to see the running containers
- check swagger url to view available end points http://localhost:8085/api/swagger-ui.html

