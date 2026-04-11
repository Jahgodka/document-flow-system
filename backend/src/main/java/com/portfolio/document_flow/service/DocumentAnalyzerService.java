package com.portfolio.document_flow.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.document_flow.entity.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class DocumentAnalyzerService {

    private final RestClient restClient;
    // Używamy ObjectMappera do ręcznego parsowania JSON-a.
    // Domyślny konwerter Springa (RestClient) gubi się przy klasie abstrakcyjnej JsonNode,
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key:OFFLINE_MODE}")
    private String apiKey;

    public DocumentAnalyzerService() {
        this.restClient = RestClient.create();
        this.objectMapper = new ObjectMapper();
    }

    public void analyzeAndSetStatus(Document document) {
        if (document.getContent() == null || document.getTitle() == null) {
            document.setStatus("BŁĄD_DANYCH");
            return;
        }

        String prompt = String.format(
                        "Jesteś zaawansowanym systemem ECM w dziale HR. Przeczytaj poniższy wniosek pracownika. " +
                        "Zwróć TYLKO JEDNO słowo jako status tego wniosku. Wybierz spośród: URLOP, AWANS, ZWOLNIENIE, ZAKUPY, INNE. " +
                        "Nie dodawaj żadnych kropek ani znaków interpunkcyjnych. " +
                        "Tytuł wniosku: %s. Treść wniosku: %s",
                        document.getTitle(), document.getContent()
        );

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=" + apiKey;
        try {
            String rawResponse = restClient.post()
                    .uri(url)
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            JsonNode response = objectMapper.readTree(rawResponse);

            if (response != null && response.has("candidates")) {
                String aiDecision = response.path("candidates").get(0)
                        .path("content").path("parts").get(0)
                        .path("text").asText().trim().toUpperCase();

                document.setStatus(aiDecision);
            } else {
                document.setStatus("BŁĄD_ANALIZY_AI");
            }

        } catch (Exception e) {
            // Mechanizm Fallback, limit zapytań, brak sieci
            System.err.println("Błąd połączenia z API Gemini: " + e.getMessage());
            document.setStatus("OCZEKUJE_NA_RĘCZNĄ_KLASYFIKACJĘ");
        }
    }
}