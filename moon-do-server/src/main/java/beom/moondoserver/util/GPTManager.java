package beom.moondoserver.util;

import beom.moondoserver.model.GPTMessage;
import beom.moondoserver.model.dto.request.ChatGPTRequest;
import beom.moondoserver.model.dto.response.ChatGPTResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class GPTManager {
    private String apiKey;
    private String model;
    private String url;
    private List<GPTMessage> messages;
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

        messages = new ArrayList<>();
        messages.add(GPTMessage.builder()
                        .role("system")
                        .content("당신은 문제를 만들어주는 문제 출제자입니다.")
                        .build());

        messages.add(GPTMessage.builder()
                .role("system")
                .content("사용자가 요구하는 문제 갯수만큼 문제를 출제해주세요.")
                .build());

        messages.add(GPTMessage.builder()
                .role("system")
                .content("문제 혹은 답과 풀이가 아무리 길어진다고 하더라도 절대 문제에 대한 내용 안에 문제를 출제할 때 '\\n'을 넣지 않도록 합니다. 객관식의 경우 보기를 나열할 때 줄바꿈하지 말고 띄어쓰기로 보여주세요.")
                .build());

        messages.add(GPTMessage.builder()
                .role("assistant")
                .content("문제 1: 스택과 큐 자료구조의 주요 차이점에 대해 설명하세요.\n" +
                        "정답 1: 스택(Stack)은 후입선출(LIFO, Last-In-First-Out) 구조로 데이터를 저장하고 접근하는 자료구조입니다. 큐(Queue)는 선입선출(FIFO, First-In-First-Out) 구조로 데이터를 저장하고 접근하는 자료구조입니다.\n" +
                        "풀이 1: 스택은 가장 최근에 추가된 항목을 먼저 제거하는 구조로, 데이터를 쌓고 꺼낼 때 역순으로 처리됩니다. 큐는 가장 먼저 추가된 항목을 먼저 제거하는 구조로, 데이터를 삽입한 순서대로 처리됩니다.\n" +
                        "문제 2: 미적분이란 무엇인가요?\n" +
                        "정답 2: 미적분은 함수의 변화율을 나타내는 도함수와 함수의 면적을 나타내는 정적분을 포함하는 수학적인 개념입니다.\n" +
                        "풀이 2: 미적분은 함수의 변화율을 나타내는 도함수와 함수의 면적을 나타내는 정적분을 포함하는 수학적인 개념입니다. 도함수는 함수의 기울기를 나타내며, 정적분은 함수의 면적을 계산하는 것입니다. 미적분은 함수의 특정 지점에서의 변화율이나 함수의 전체적인 특성을 분석하는 데 사용됩니다.\n")
                .build());

        maxToken = 2000;
        temperature = 0.0;
        topP = 1.0;

        authorization = "Authorization";
        bearer = "Bearer ";
        mediaType = "application/json; charset=UTF-8";
    }

    public ChatGPTResponse getProblem(String prompt) {
        messages.add(GPTMessage.builder()
                .role("user")
                .content(prompt)
                .build());
        HttpHeaders headers = new HttpHeaders();

        ChatGPTRequest chatGPTRequest = ChatGPTRequest.builder()
                .model(model)
                .messages(messages)
                .maxTokens(maxToken)
                .temperature(temperature)
                .topP(topP)
                .build();
        headers.setContentType(MediaType.parseMediaType(mediaType));
        headers.add(authorization, bearer + apiKey);

        HttpEntity<ChatGPTRequest> httpEntity = new HttpEntity<>(chatGPTRequest, headers);
        ResponseEntity<ChatGPTResponse> responseEntity = restTemplate.postForEntity(
                url,
                httpEntity,
                ChatGPTResponse.class);

        return responseEntity.getBody();
    }
}
