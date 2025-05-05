package bd2.proyecto1.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
// import com.google.api.client.http.HttpRequest;
// import com.google.api.client.http.HttpResponse;
import com.google.api.core.ApiFuture;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.services.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class StorageService {
    @Value("${app.firebaseUrl}")
    private String baseUrl;

    public String uploadImage(MultipartFile image, String imageName) throws IOException, InterruptedException {
        InputStream stream = image.getInputStream();
        Bucket bucket = StorageClient.getInstance().bucket();
        bucket.create("imagenes/" + imageName, stream, image.getContentType());
        String url=baseUrl + imageName;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = response.body();
        JsonNode node = new ObjectMapper().readTree(jsonResponse);
        String imageToken = node.get("downloadTokens").asText();
        System.out.println(url);
        return url+ "?alt=media&token=" + imageToken;
    }
}
