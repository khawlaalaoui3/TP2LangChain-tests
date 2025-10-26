package ma.gov.pfe.tests;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import ma.gov.pfe.tests.MeteoTool;

import java.util.Scanner;

public class Test6 {

    private static final int TAILLE_FENETRE_MEMOIRE = 10;
    private static final double TEMP_MODELE = 0.3;

    // Interface pour l'assistant conversationnel
    interface Assistant {
        String chat(String userMessage);
    }

    public static void main(String[] args) {
        String cleApi = recupererCleApi();

        // Configuration du modèle avec température réduite
        // Le RAG nécessite un contrôle strict de l'exactitude des informations
        ChatModel modeleChat = GoogleAiGeminiChatModel.builder()
                .apiKey(cleApi)
                .modelName("gemini-2.5-flash")
                .temperature(TEMP_MODELE)
                .logRequestsAndResponses(true)
                .build();

        // Initialisation de l'assistant avec mémoire conversationnelle
        // LangChain4j gère l'implémentation de Assistant
        Assistant assistant = construireAssistant(modeleChat);

        conversationAvec(assistant);
    }

    private static String recupererCleApi() {
        return System.getenv("GEMINI_KEY");
    }

    private static Assistant construireAssistant(ChatModel modele) {
        return AiServices.builder(Assistant.class)
                .chatModel(modele)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(TAILLE_FENETRE_MEMOIRE))
                .tools(new MeteoTool())
                .build();
    }

    private static void conversationAvec(Assistant assistant) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("==================================================");
                System.out.println("Posez votre question : ");
                String question = scanner.nextLine();
                if (question.isBlank()) {
                    continue;
                }
                System.out.println("==================================================");
                if ("fin".equalsIgnoreCase(question)) {
                    break;
                }
                String reponse = assistant.chat(question);
                System.out.println("Assistant : " + reponse);
                System.out.println("==================================================");
            }
        }
    }
}