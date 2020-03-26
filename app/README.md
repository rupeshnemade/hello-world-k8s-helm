# Build Docker Image

Test locally your Mongo and NodeJs API image before pushing it to image registry.
- Build MongoDB docker image:
	```
	cd db/
    docker build -t <DOCKER_ID>/mongodb .
	```
	
- Build API docker image:
	```
	cd server/
    docker build -t <DOCKER_ID>/helloapi .
	```
	
# Run Docker Image

- Run the MongoDB container (attached mode to see the logs)
	```
    docker run --name mongoContainer -p 27017:27017 <DOCKER_ID>/mongodb
	```
	
- Run the hello API container and verify if MongoDB connections are working (attached mode to see the logs).
	```
    docker run --name helloapi -p 3000:3000 <DOCKER_ID>/helloapi
	```