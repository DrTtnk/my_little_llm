# My Little LLM

A quick and dirty implementation of a chat frontend for the LlamaCPP server in Spring Boot

## Installation

```bash
git clone <repo-here>
cd <repo-name>

npm install

mvnw clean install

# Start the docker containers
docker compose up -d --wait

# Start the server
./mvnw spring-boot:run

# Try it out
curl --request GET -sL \
     --url 'http://example.com'
```
