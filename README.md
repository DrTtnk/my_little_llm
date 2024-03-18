# My Little LLM

A quick and dirty implementation of a chat frontend for the LlamaCPP server in Spring Boot

## Installation

```bash
git clone https://github.com/DrTtnk/my_little_llm
cd my_little_llm

npm install

mvnw clean install

# Start the docker containers
docker compose up -d --wait

# Start the server
./mvnw spring-boot:run

# Try it out
curl -i --request GET -sL --url 'http://127.0.0.1:2123/make-me-coffee' 
```

You also need a model compatible with Llama Cpp, I suggest this one: https://huggingface.co/sayhan/phi-2-super-GGUF/tree/main
Save it as `models/myLittleModel.gguf` in the project root.

### Nice to have

You have a postman collection in the `postman` folder to test the server.
