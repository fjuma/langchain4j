package dev.langchain4j.model.ollama;

import static dev.langchain4j.data.message.AiMessage.aiMessage;
import static dev.langchain4j.data.message.ContextMessage.contextMessage;
import static dev.langchain4j.data.message.SystemMessage.systemMessage;
import static dev.langchain4j.data.message.UserMessage.userMessage;
import static dev.langchain4j.internal.Utils.isNullOrEmpty;
import static dev.langchain4j.model.ollama.AbstractOllamaLanguageModelInfrastructure.ollamaBaseUrl;
import static dev.langchain4j.model.ollama.OllamaImage.GRANITE_3_GUARDIAN;
import static dev.langchain4j.model.ollama.OllamaImage.localOllamaImage;
import static org.assertj.core.api.Assertions.assertThat;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ContextMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;

class OllamaContextChatModelIT extends AbstractOllamaLanguageModelInfrastructure {

    private static final String MODEL_NAME = GRANITE_3_GUARDIAN;

    static {
        if (isNullOrEmpty(OLLAMA_BASE_URL)) {
            String localOllamaImage = localOllamaImage(MODEL_NAME);
            ollama = new LC4jOllamaContainer(OllamaImage.resolve(OllamaImage.OLLAMA_IMAGE, localOllamaImage))
                    .withModel(MODEL_NAME);
            ollama.start();
            ollama.commitToImage(localOllamaImage);
        }
    }

    ChatLanguageModel ollamaChatModel = OllamaChatModel.builder()
            .baseUrl(ollamaBaseUrl(ollama))
            .modelName(GRANITE_3_GUARDIAN)
            .temperature(0.0)
            .logRequests(true)
            .logResponses(true)
            .build();

    @Test
    void should_generate_response_with_context() {

        // given
        SystemMessage systemMessage = systemMessage("context_relevance");
        UserMessage userMessage = userMessage("What is the history of treaty making?");
        ContextMessage contextMessage = contextMessage(
                "One significant part of treaty making is that signing a treaty implies recognition that the other side is a sovereign state and that the agreement being considered is enforceable under international law. Hence, nations can be very careful about terming an agreement to be a treaty. For example, within the United States, agreements between states are compacts and agreements between states and the federal government or between agencies of the government are memoranda of understanding.");

        // when
        Response<AiMessage> response = ollamaChatModel.generate(systemMessage, userMessage, contextMessage);

        // then
        AiMessage responseMessage = response.content();
        assertThat(responseMessage.text()).isEqualTo("Yes");

        // given
        systemMessage = systemMessage("groundedness");
        contextMessage = contextMessage(
                "Eat (1964) is a 45-minute underground film created by Andy Warhol and featuring painter Robert Indiana, filmed on Sunday, February 2, 1964, in Indiana's studio. The film was first shown by Jonas Mekas on July 16, 1964, at the Washington Square Gallery at 530 West Broadway.\n"
                        + "Jonas Mekas (December 24, 1922 – January 23, 2019) was a Lithuanian-American filmmaker, poet, and artist who has been called \"the godfather of American avant-garde cinema\". Mekas's work has been exhibited in museums and at festivals worldwide.");
        AiMessage aiMessage = aiMessage(
                "The film Eat was first shown by Jonas Mekas on December 24, 1922 at the Washington Square Gallery at 530 West Broadway.");

        // when
        response = ollamaChatModel.generate(systemMessage, contextMessage, aiMessage);

        // then
        responseMessage = response.content();
        assertThat(responseMessage.text()).isEqualTo("Yes");
    }

    @Test
    void should_generate_response_without_context() {
        // given
        SystemMessage systemMessage = systemMessage("answer_relevance");
        UserMessage userMessage = userMessage("In what month did the AFL season originally begin?");
        AiMessage aiMessage = aiMessage("The AFL season now begins in February.");

        // when
        Response<AiMessage> response = ollamaChatModel.generate(systemMessage, userMessage, aiMessage);

        // then
        AiMessage responseMessage = response.content();
        assertThat(responseMessage.text()).isEqualTo("Yes");

        // given
        aiMessage = aiMessage("The AFL season originally began in January.");

        // when
        response = ollamaChatModel.generate(systemMessage, userMessage, aiMessage);

        // then
        responseMessage = response.content();
        assertThat(responseMessage.text()).isEqualTo("No");
    }
}
