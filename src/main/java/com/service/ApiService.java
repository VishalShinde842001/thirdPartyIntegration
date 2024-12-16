package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONObject;

import com.bo.ErrorConstants;
import com.bo.InfoBO;
import com.bo.Response;

@Service
public class ApiService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment environment;

	public Response catFactApi() {
		Response response = new Response();
		try {
			String apiResponse = restTemplate.getForObject("https://catfact.ninja/fact", String.class);
			JSONObject jsonResponse = new JSONObject(apiResponse);
			System.out.println("apiResponse=========>" + apiResponse);
			if (null != jsonResponse && jsonResponse.getString("fact") != null) {
				response.setStatus(ErrorConstants.SUCESS);
				response.setMessage(jsonResponse.getString("fact"));
			} else {
				response.setStatus(ErrorConstants.NOT_FOUND);
				response.setMessage(ErrorConstants.NOT_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	public Response getWeatherReport(InfoBO infoBO) {
		Response response = new Response();
		try {
			String apiKey = environment.getProperty("weather.api.key");
			System.out.println("Weather API Key===>" + apiKey);
			if (null != infoBO && null != infoBO.getCity()) {
				if (null != apiKey) {
					String apiUrl = environment.getProperty("weather.api");
					if (null != apiUrl) {
						String finalUrl = UriComponentsBuilder.fromHttpUrl(apiUrl).queryParam("q", infoBO.getCity())
								.queryParam("appId", apiKey).build().toString();
						System.out.println("finalURL==========>" + finalUrl);
						String apiResponse = restTemplate.getForObject(finalUrl, String.class);
						JSONObject jsonResponse = new JSONObject(apiResponse);

						if (jsonResponse != null && jsonResponse.getInt("cod") == 200) {
							response.setStatus(ErrorConstants.SUCESS);
							response.setResult(apiResponse);
						} else {
							response.setStatus(ErrorConstants.NOT_IMPLEMENTED);
							if (jsonResponse.getString("message") != null) {
								response.setMessage(jsonResponse.getString("message"));
							} else {
								response.setMessage("Not Getting proper response from api");
							}
						}
						System.out.println("apiResponse====>" + apiResponse);
					} else {
						response.setStatus(ErrorConstants.REQUIRED_FIELD_MISSING);
						response.setMessage(ErrorConstants.API_URL_NOT_FOUND);
					}

				} else {
					response.setStatus(ErrorConstants.REQUIRED_FIELD_MISSING);
					response.setMessage(ErrorConstants.API_KEY_NOT_FOUND);
				}
			} else {
				response.setStatus(ErrorConstants.REQUIRED_FIELD_MISSING);
				response.setMessage(ErrorConstants.REQUIRED_FIELD_MISSING_MESSAGE);
			}
		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	public Response randomUserGenerator() {
		Response response = new Response();
		try {

		} catch (Exception e) {

		}
		return response;
	}

}
