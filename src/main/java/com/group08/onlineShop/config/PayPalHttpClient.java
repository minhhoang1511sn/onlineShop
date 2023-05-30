package com.group08.onlineShop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group08.onlineShop.dto.PayPalDTO.ClientTokenDTO;
import com.group08.onlineShop.dto.PayPalDTO.PayPalOrderDTO;
import com.group08.onlineShop.dto.responseDTO.AccessTokenResponseDTO;
import com.group08.onlineShop.dto.responseDTO.PayPalOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.group08.onlineShop.model.PayPalEndpoints.*;
import static java.net.URI.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayPalHttpClient {
    private final HttpClient httpClient;
    private final PaypalConfig paypalConfig;
    private final ObjectMapper objectMapper;

    @Autowired
    public PayPalHttpClient(PaypalConfig paypalConfig, ObjectMapper objectMapper) {
        this.paypalConfig = paypalConfig;
        this.objectMapper = objectMapper;
        httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }

    public AccessTokenResponseDTO getAccessToken() throws Exception {
        var request = HttpRequest.newBuilder()
                .uri(create(createUrl(paypalConfig.getBaseUrl(), GET_ACCESS_TOKEN)))
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, encodeBasicCredentials())
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en_US")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        var content = response.body();
        return objectMapper.readValue(content, AccessTokenResponseDTO.class);
    }

    public ClientTokenDTO getClientToken() throws Exception {
        var accessTokenDto = getAccessToken();
        var request = HttpRequest.newBuilder()
                .uri(create(createUrl(paypalConfig.getBaseUrl(), GET_CLIENT_TOKEN)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenDto.getAccessToken())
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en_US")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        var content = response.body();

        return objectMapper.readValue(content, ClientTokenDTO.class);
    }

    public PayPalOrderResponseDTO createOrder(PayPalOrderDTO orderDTO) throws Exception {
        var accessTokenDto = getAccessToken();
        var payload = objectMapper.writeValueAsString(orderDTO);

        var request = HttpRequest.newBuilder()
                .uri(create(createUrl(paypalConfig.getBaseUrl(), PAYPAL_ORDER_CHECKOUT)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenDto.getAccessToken())
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        var content = response.body();
        return objectMapper.readValue(content, PayPalOrderResponseDTO.class);

    }


    private String encodeBasicCredentials() {
        var input = paypalConfig.getClientId() + ":" + paypalConfig.getSecret();
        return "Basic " + Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }
}
