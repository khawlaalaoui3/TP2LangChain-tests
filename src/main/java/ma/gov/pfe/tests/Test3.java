package ma.gov.pfe.tests;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;

public class Test3 {

    public static void main(String[] args) {
        String apiKey = System.getenv("GEMINI_KEY");


        EmbeddingModel model = GoogleAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-embedding-001")
                .taskType(GoogleAiEmbeddingModel.TaskType.SEMANTIC_SIMILARITY)
                .outputDimensionality(300)
                .timeout(Duration.ofSeconds(2))
                .build();


        String phrase1 = "Bonjour, comment allez-vous ?";
        String phrase2 = "Salut, comment ça va ?";


        Response<Embedding> emb1 = model.embed(phrase1);
        Response<Embedding> emb2 = model.embed(phrase2);


        double similarite = CosineSimilarity.between(emb1.content(), emb2.content());


        System.out.println("Phrase 1 : " + phrase1);
        System.out.println("Phrase 2 : " + phrase2);
        System.out.println("Similarité cosinus : " + similarite);
    }
}
