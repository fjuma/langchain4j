package dev.langchain4j.data.message;

import java.util.LinkedHashMap;
import java.util.Map;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class CustomMessageTest implements WithAssertions {
    @Test
    public void test_methods() {
        LinkedHashMap<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("text", "The sky is blue.");
        attributes.put("myAttribute", "myValue");
        CustomMessage cm = new CustomMessage("context", attributes);
        assertThat(cm.role()).isEqualTo("context");
        assertThat(cm.attributes()).isEqualTo(attributes);
        assertThat(cm.type()).isEqualTo(ChatMessageType.CUSTOM);

        assertThat(cm)
                .hasToString("CustomMessage "
                        + "{ role = \"context\" attributes = \"{text=The sky is blue., myAttribute=myValue}\" }");
    }

    @Test
    public void test_equals_hashCode() {
        Map<String, Object> attributes = Map.of(
                "text", "The sky is blue.",
                "myAttribute", "myValue");
        Map<String, Object> changedAttributes = Map.of(
                "text", "The sky is blue.",
                "myAttribute", "foo");
        CustomMessage c1 = new CustomMessage("context", attributes);
        CustomMessage c2 = new CustomMessage("context", attributes);

        CustomMessage c3 = new CustomMessage("foo", attributes);
        CustomMessage c4 = new CustomMessage("foo", attributes);

        assertThat(c1)
                .isEqualTo(c1)
                .isNotEqualTo(null)
                .isNotEqualTo(new Object())
                .isEqualTo(c2)
                .hasSameHashCodeAs(c2)
                .isNotEqualTo(CustomMessage.from("context", changedAttributes))
                .isNotEqualTo(c3)
                .doesNotHaveSameHashCodeAs(c3);

        assertThat(c3).isEqualTo(c3).isEqualTo(c4).hasSameHashCodeAs(c4);
    }

    @Test
    public void test_builders() {
        Map<String, Object> attributes = Map.of(
                "text", "The sky is blue.",
                "myAttribute", "myValue");
        assertThat(new CustomMessage("context", attributes))
                .isEqualTo(CustomMessage.from("context", attributes))
                .isEqualTo(CustomMessage.customMessage("context", attributes));
    }
}
