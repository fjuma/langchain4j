package dev.langchain4j.data.message;

import static dev.langchain4j.data.message.ChatMessageType.CONTEXT;
import static dev.langchain4j.internal.Utils.quoted;
import static dev.langchain4j.internal.ValidationUtils.ensureNotBlank;

import java.util.Objects;

/**
 * Represents the context used for retrieval augmented generation.
 */
public class ContextMessage implements ChatMessage {

    private final String text;

    /**
     * Creates a new context message.
     * @param text the message text.
     */
    public ContextMessage(String text) {
        this.text = ensureNotBlank(text, "text");
    }

    /**
     * Returns the message text.
     * @return the message text.
     */
    public String text() {
        return text;
    }

    @Override
    public ChatMessageType type() {
        return CONTEXT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContextMessage that = (ContextMessage) o;
        return Objects.equals(this.text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "ContextMessage {" + " text = " + quoted(text) + " }";
    }

    /**
     * Creates a new context message.
     * @param text the message text.
     * @return the context message.
     */
    public static ContextMessage from(String text) {
        return new ContextMessage(text);
    }

    /**
     * Creates a new context message.
     * @param text the message text.
     * @return the context message.
     */
    public static ContextMessage contextMessage(String text) {
        return from(text);
    }
}
