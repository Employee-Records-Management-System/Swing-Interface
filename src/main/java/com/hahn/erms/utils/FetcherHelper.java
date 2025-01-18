package com.hahn.erms.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hahn.erms.adapter.LocalDateAdapter;
import com.hahn.erms.models.AuthManager;
import com.hahn.erms.services.ApiClient;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FetcherHelper<K> {
    private final Gson gson;
    private final Type type;
    private final String url;

    public FetcherHelper(Type type, String url) {
        this.url = System.getProperty("BASE_URL") + url;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        this.type = type;
    }

    public List<K> fetchListOfData(Map<String, String> params) {
        String urlParams = params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+'?'+urlParams))
                .header("Authorization", AuthManager.token)
                .GET()
                .build();

        try {
            HttpResponse<String> response = ApiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Type listType = TypeToken.getParameterized(List.class, type).getType();
                return gson.fromJson(response.body(), listType);
            } else {
                throw new RuntimeException("Failed to fetch data: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch data: " + e.getMessage());
        }
    }

    public List<K> fetchListOfData() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", AuthManager.token)
                .GET()
                .build();

        try {
            HttpResponse<String> response = ApiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Type listType = TypeToken.getParameterized(List.class, type).getType();
                return gson.fromJson(response.body(), listType);
            } else {
                throw new RuntimeException("Failed to fetch data: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch data: " + e.getMessage());
        }
    }

    public K fetchData(long id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("/%s/%d", url, id)))
                .header("Authorization", AuthManager.token)
                .GET()
                .build();

        try {
            HttpResponse<String> response = ApiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), type);
            } else {
                throw new RuntimeException("Failed to fetch data: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch data: " + e.getMessage());
        }
    }

    public K createData(K data) {
        String requestBody = gson.toJson(data);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", AuthManager.token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = ApiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 201) {
                throw new RuntimeException("Failed to create data: " + response.statusCode());
            }
            return gson.fromJson(response.body(), type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create data: " + e.getMessage());
        }
    }

    public void deleteData(long id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/%d", url, id)))
                .header("Authorization", AuthManager.token)
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = ApiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (!String.valueOf(response.statusCode()).startsWith("2")) {
                throw new RuntimeException("Failed to delete data: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete data: " + e.getMessage());
        }
    }

    public void patchData(long id, K data) {
        String requestBody = gson.toJson(data);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("/%s/%d", url, id)))
                .header("Authorization", AuthManager.token)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = ApiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to update data: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update data: " + e.getMessage());
        }
    }
}