package beom.moondoserver.util;

import beom.moondoserver.model.dto.request.ChatGPTRequest;
import beom.moondoserver.model.dto.response.ChatGPTResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GPTManager {
    private String apiKey;
    private String model;
    private String url;
    private Integer maxToken;
    private Double temperature;
    private Double topP;
    private String authorization;
    private String bearer;
    private String mediaType;

    private static RestTemplate restTemplate = new RestTemplate();

    public GPTManager() {
        apiKey = KeySet.GPT_API_KEY.getValue();
        model = KeySet.GPT_MODEL.getValue();
        url = KeySet.GPT_URL.getValue();

        maxToken = 300;
        temperature = 0.0;
        topP = 1.0;

        authorization = "Authorization";
        bearer = "Bearer ";
        mediaType = "application/json; charset=UTF-8";
    }

    public ChatGPTResponse getProblem(ChatGPTRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mediaType));
        headers.add(authorization, bearer + apiKey);
        HttpEntity<ChatGPTRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatGPTResponse> responseEntity = restTemplate.postForEntity(
                url,
                httpEntity,
                ChatGPTResponse.class);

        return responseEntity.getBody();
    }
}
