package com.example.demo.config.oauth;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.config.oauth.social.GoogleOAuthResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.config.BaseResponseStatus.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleOAuthImpl implements SocialOAuth {
	@Value("${oauth2.client.google.base-url}")
	private String GOOGLE_SNS_BASE_URL;
	@Value("${oauth2.client.google.client-id}")
	private String GOOGLE_SNS_CLIENT_ID;
	@Value("${oauth2.client.google.callback-url}")
	private String GOOGLE_SNS_CALLBACK_URL;
	@Value("${oauth2.client.google.client-secret}")
	private String GOOGLE_SNS_CLIENT_SECRET;
	@Value("${oauth2.client.google.token-url}")
	private String GOOGLE_SNS_TOKEN_BASE_URL;

	@Override
	public String getOauthRedirectURL() {
		Map<String, Object> params = new HashMap<>();
		params.put("scope", "profile email");
		params.put("response_type", "code");
		params.put("client_id", GOOGLE_SNS_CLIENT_ID);
		params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

		String parameterString = params.entrySet().stream()
			.map(x -> x.getKey() + "=" + x.getValue())
			.collect(Collectors.joining("&"));

		return GOOGLE_SNS_BASE_URL + "?" + parameterString;
	}

	@Override
	public String requestAccessToken(String code) throws JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();

		Map<String, Object> params = new HashMap<>();
		params.put("code", code);
		params.put("client_id", GOOGLE_SNS_CLIENT_ID);
		params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
		params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
		params.put("grant_type", "authorization_code");

		//JSON ?????? ????????? ??????
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		//AccessToken ?????? ??????
		ResponseEntity<String> resultEntity = restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);

		//Token Request
		GoogleOAuthResponse result = mapper.readValue(resultEntity.getBody(), new TypeReference<GoogleOAuthResponse>() {});

		//ID Token??? ?????? (???????????? ????????? jwt??? ????????? ????????????)
		String jwtToken = result.getIdToken();
		String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
			.queryParam("id_token", jwtToken).encode().toUriString();

		// ????????? token ( ????????? email??? ?????? )
		String resultJson = restTemplate.getForObject(requestUrl, String.class);

		// userinfo??? ??????
		Map<String,String> userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>(){});

		return userInfo.isEmpty() ? null : userInfo.get("email");

	}


//	@Override
//	public String requestAccessToken(String code) throws JsonProcessingException {
//		RestTemplate restTemplate = new RestTemplate();
//
//		Map<String, Object> params = new HashMap<>();
//		params.put("code", code);
//		params.put("client_id", GOOGLE_SNS_CLIENT_ID);
//		params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
//		params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
//		params.put("grant_type", "authorization_code");
//
//		//JSON ????????? ?????? ????????? ??????
//		//????????? ??????????????? ???????????? ???????????? ??????????????? Object mapper??? ?????? ???????????????.
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//		// AccessToken ????????????
//		ResponseEntity<String> responseEntity =
//			restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);
//
//		//Token Request
//		GoogleOAuthResponse result = mapper.readValue(responseEntity.getBody(), new TypeReference<GoogleOAuthResponse>() {
//		});
//
//		if (responseEntity.getStatusCode() == HttpStatus.OK) {
//			return responseEntity.getBody();
//		}
//
//
////		if (responseEntity.getStatusCode() == HttpStatus.OK) {
////			return responseEntity.getBody();
////		}
//		return "?????? ????????? ?????? ?????? ??????";
//	}

}
