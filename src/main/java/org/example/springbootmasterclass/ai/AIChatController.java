package org.example.springbootmasterclass.ai;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequestMapping("api/v1/ai2")
@RestController
public class AIChatController {

    private final ChatClient chatClient;
    private final OpenAiAudioApi openAiAudioApi;

    public AIChatController(
            ChatClient.Builder builder, OpenAiAudioApi openAiAudioApi
    ) {
        this.chatClient = builder.build();
        this.openAiAudioApi = openAiAudioApi;
    }

    @GetMapping("chat")
    public List<JavaFrameworkRank> chat() {
        return chatClient.prompt()
                .user("What are the top 5 best Java frameworks to build web apps? I want the output to be minimal and in list format.")
                .call()
                .entity(new ParameterizedTypeReference<List<JavaFrameworkRank>>() {
                });
    }

    @GetMapping("images/describe")
    public String describeImage() throws IOException {
        ClassPathResource diagram = new ClassPathResource("diagram.png");
        ClassPathResource pancake = new ClassPathResource("pancake.jpg");
        return chatClient.prompt()
                .messages(new UserMessage(
                        "Describe this image",
                       List.of(
                               new Media(MimeTypeUtils.IMAGE_PNG, diagram),
                               new Media(MimeTypeUtils.IMAGE_JPEG, pancake)
                       )
                ))
                .call()
                .content();
    }

    @GetMapping("openai/transcribe")
    public String transcribeAudio() {
        ClassPathResource testAudio = new ClassPathResource("test-audio.m4a");

        OpenAiAudioTranscriptionModel model = new OpenAiAudioTranscriptionModel(openAiAudioApi);

        OpenAiAudioTranscriptionOptions options = OpenAiAudioTranscriptionOptions.builder()
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.SRT)
                .temperature(0f)
                .language("en")
                .build();

        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(testAudio, options);

        return model.call(prompt).getResult().getOutput();
    }
}
