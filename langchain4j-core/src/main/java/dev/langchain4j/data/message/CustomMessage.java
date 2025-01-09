package dev.langchain4j.data.message;

import static dev.langchain4j.data.message.ChatMessageType.CUSTOM;
import static dev.langchain4j.internal.Utils.copyIfNotNull;
import static dev.langchain4j.internal.Utils.quoted;
import static dev.langchain4j.internal.ValidationUtils.ensureNotBlank;

import java.util.Map;
import java.util.Objects;

/**
 * Represents a custom message.
 */
public class CustomMessage implements ChatMessage {

    private final String role;
    private final Map<String, Object> attributes;

    /**
     * Creates a new custom message.
     * @param role the message role.
     * @param attributes the message attributes.
     */
    public CustomMessage(String role, Map<String, Object> attributes) {
        this.role = ensureNotBlank(role, "role");
        this.attributes = copyIfNotNull(attributes);
    }

    /**
     * Returns the message role.
     * @return the message role.
     */
    public String role() {
        return role;
    }

    /**
     * Returns the message attributes.
     * @return the message attributes.
     */
    public Map<String, Object> attributes() {
        return attributes;
    }

    @Override
    public ChatMessageType type() {
        return CUSTOM;
    }

    /**
     * Returns the message text if present.
     * @return the message text if present.
     */
    public String text() {
        if (attributes != null) {
            return (String) attributes.get("text");
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomMessage that = (CustomMessage) o;
        return Objects.equals(this.role, that.role) && Objects.equals(this.attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, attributes);
    }

    @Override
    public String toString() {
        return "CustomMessage {" + " role = " + quoted(role) + " attributes = " + quoted(attributes) + " }";
    }

    /**
     * Creates a new custom message.
     * @param role the message role.
     * @param attributes the message attributes.
     * @return the custom message.
     */
    public static CustomMessage from(String role, Map<String, Object> attributes) {
        return new CustomMessage(role, attributes);
    }

    /**
     * Creates a new custom message.
     * @param role the message role.
     * @param attributes the message attributes.
     * @return the custom message
     */
    public static CustomMessage customMessage(String role, Map<String, Object> attributes) {
        return from(role, attributes);
    }
}
