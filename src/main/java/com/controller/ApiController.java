package com.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bo.ErrorConstants;
import com.bo.InfoBO;
import com.bo.Response;
import com.service.ApiService;

@RestController
@CrossOrigin
@RequestMapping("api/")
public class ApiController {

	private final ApiService apiService;

	public ApiController(ApiService apiService) {
		this.apiService = apiService;
	}

	@GetMapping("cat-fact-api")
	public Response catFactApi() {
		Response response = new Response();
		try {
			return apiService.catFactApi();
		} catch (Exception e) {
			response.setStatus(ErrorConstants.INTERNAL_SERVER_ERROR);
			response.setMessage(ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}

	@PostMapping("get-weather")
	public Response getWeather(@RequestBody InfoBO infoBO) {
		Response response = new Response();
		try {
			return apiService.getWeatherReport(infoBO);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ErrorConstants.INTERNAL_SERVER_ERROR);
			response.setMessage(ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}

	@GetMapping("random-user-generator")
	public Response randomUserGenerator() {
		Response response = new Response();
		try {
			return apiService.randomUserGenerator();
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ErrorConstants.INTERNAL_SERVER_ERROR);
			response.setMessage(ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}

}
