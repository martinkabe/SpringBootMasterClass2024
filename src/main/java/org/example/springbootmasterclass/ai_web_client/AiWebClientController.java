package org.example.springbootmasterclass.ai_web_client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestController
@RequestMapping("/api/v1/ai")
public class AiWebClientController {

    private final WebClient webClient;

    public AiWebClientController(@Qualifier("openAiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }


    @GetMapping("/chat")
    public String chat() {
        try {
            // Prepare the body with the prompt for the chat
            String requestBody = "{\n" +
                    "  \"model\": \"gpt-4\",\n" +
                    "  \"messages\": [\n" +
                    "    {\"role\": \"user\", \"content\": \"What are the best Java frameworks to build web apps? I want the output to be minimal and in list format.\"}\n" +
                    "  ]\n" +
                    "}";

            // Send the request to the correct endpoint
            return webClient.post()
                    .uri("/chat/completions") // Ensure the correct path to the OpenAI chat completions endpoint
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unexpected error occurred";
        }
    }

    @GetMapping("/embedding")
    public String getEmbedding() {
        try {
            String requestBody = "{\n" +
                    "  \"model\": \"text-embedding-ada-002\", \n" +  // You can use other available models here
                    "  \"input\": \"What are the best Java frameworks to build web apps? I want the output to be minimal and in list format.\"\n" +
                    "}";

            // Send the request to OpenAI's embeddings endpoint
            return webClient.post()
                    .uri("/embeddings")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
}
