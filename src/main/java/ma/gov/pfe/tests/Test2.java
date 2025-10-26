package ma.gov.pfe.tests;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

public class Test2 {

    public static void main(String[] args) {
        String apiKey = System.getenv("GEMINI_KEY");

        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-2.5-flash")
                .temperature(0.7)
                .build();


        PromptTemplate template = PromptTemplate.from("Traduis le texte suivant en anglais : {{it}}");
        Prompt prompt = template.apply("Bonjour, je suis heureuse de te rencontrer.");

        String reponse = model.chat(prompt.text());

        System.out.println("Texte original : " + prompt.text());
        System.out.println("Traduction : " + reponse);
    }
}
