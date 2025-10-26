package ma.gov.pfe.tests;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class Test1 {

    public static void main(String[] args) {
        String apiKey = System.getenv("GEMINI_KEY");


        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-2.5-flash")
                .temperature(0.7)
                .build();

        String question = "Quelle est la capitale de la France ?";

    String reponse = model.chat(question);
    System.out.println("Question : " + question);
    System.out.println("Réponse : " + reponse);
    }
}