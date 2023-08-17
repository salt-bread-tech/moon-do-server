package beom.moondoserver.util;

import lombok.Getter;

@Getter
public enum KeySet {
    GPT_API_KEY("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"),
    GPT_MODEL("gpt-3.5-turbo"),
    GPT_URL("https://api.openai.com/v1/completions");

    private String value;

    KeySet(String str) {
        value = str;
    }
}
