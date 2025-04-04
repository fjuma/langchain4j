package dev.langchain4j.service.common.openai;

import static dev.langchain4j.model.chat.Capability.RESPONSE_FORMAT_JSON_SCHEMA;
import static dev.langchain4j.service.common.openai.OpenAiChatModelIT.defaultModelBuilder;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.common.AbstractAiServiceIT;
import java.util.List;

// TODO move to langchain4j-open-ai module once dependency cycle is resolved
class OpenAiAiServiceIT extends AbstractAiServiceIT {

    @Override
    protected List<ChatLanguageModel> models() {
        return List.of(
                defaultModelBuilder().build()
                // TODO more configs?
                );
    }

    @Override
    protected List<ChatLanguageModel> modelsSupportingToolsAndJsonResponseFormatWithSchema() {
        return List.of(
                defaultModelBuilder()
                        .supportedCapabilities(RESPONSE_FORMAT_JSON_SCHEMA)
                        .strictJsonSchema(true)
                        .build(),
                defaultModelBuilder()
                        .responseFormat("json_schema") // testing backward compatibility
                        .strictJsonSchema(true)
                        .build(),
                defaultModelBuilder()
                        .supportedCapabilities(RESPONSE_FORMAT_JSON_SCHEMA)
                        .strictJsonSchema(false)
                        .build(),
                defaultModelBuilder()
                        .responseFormat("json_schema") // testing backward compatibility
                        .strictJsonSchema(false)
                        .build()
                // TODO more configs?
                );
    }
}
