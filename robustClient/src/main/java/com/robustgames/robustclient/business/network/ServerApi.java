package com.robustgames.robustclient.business.network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerApi {

    // MUSS auf die Server-URL geändert werden, wenn der echte Robust-Server genutzt wird!
    private static final String BASE_URL = "http://localhost:8080/api";

    public static String getGameState() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/state")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String postTurn(String turnData) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/turn")).header("Content-Type", "text/plain").POST(HttpRequest.BodyPublishers.ofString(turnData)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}