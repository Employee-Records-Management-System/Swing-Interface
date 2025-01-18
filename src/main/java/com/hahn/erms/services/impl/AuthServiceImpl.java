package com.hahn.erms.services.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hahn.erms.adapter.LocalDateAdapter;
import com.hahn.erms.dtos.LoginRequest;
import com.hahn.erms.dtos.LoginResponse;
import com.hahn.erms.models.Account;
import com.hahn.erms.models.AuthManager;
import com.hahn.erms.services.ApiClient;
import com.hahn.erms.services.AuthService;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class AuthServiceImpl implements AuthService {
    private final String url;
    private final Gson gson;

    public AuthServiceImpl() {
        this.url = System.getProperty("BASE_URL") + "/auth/login";
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
    }

    public LoginResponse login(String username, String password) {
        try {
            String requestBody = gson.toJson(new LoginRequest(username, password));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = ApiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                LoginResponse loginResponse = gson.fromJson(response.body(), LoginResponse.class);
                saveAccountSession(loginResponse);
                return loginResponse;
            } else {
                throw new RuntimeException("Failed to login: " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("An error occurred during connection to server %s.", e.getLocalizedMessage()));
        }
    }

    private void saveAccountSession(LoginResponse loginResponse) {
        AuthManager.account = loginResponse.getAccount();
        AuthManager.token = String.format("%s %s", "Bearer", loginResponse.getToken());
    }



}
