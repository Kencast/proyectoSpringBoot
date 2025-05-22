package bd2.proyecto1.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StorageServiceTest {

    StorageService storageService;
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        storageService = new StorageService();

        Field field = StorageService.class.getDeclaredField("baseUrl");
        field.setAccessible(true);
        field.set(storageService, "https://firebasestorage.googleapis.com/v0/b/nature-d45bb.appspot.com/o/imagenes%2F");
    }

    @BeforeAll
    static void setUpFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/credential.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("nature-d45bb.appspot.com")
                    .build();
            FirebaseApp.initializeApp(options);
        }
    }

    @Test
    public void testUploadImage() throws IOException {
        MockMultipartFile image = new MockMultipartFile("image",
                "test.png", "image/png",
                new FileInputStream(new File("src/test/java/bd2/proyecto1/storage/data/imageTest.png")));
        String imageName = RandomStringUtils.randomAlphabetic(20);
        assertDoesNotThrow(() ->{
            String response = storageService.uploadImage(image,imageName);
            assertNotNull(response);
        });
    }

    @Test
    public void testUploadEmptyImage() {
        MockMultipartFile emptyImage = new MockMultipartFile(
                "image",
                "empty.png",
                "image/png",
                new byte[0]
        );
        String imageName = "";
        assertThrows(Exception.class, () -> {
            storageService.uploadImage(emptyImage, imageName);
        });
    }

    @Test
    public void testUploadNullImage() {
        String imageName = "empty.jpg";
        assertThrows(Exception.class, () -> {
            storageService.uploadImage(null, imageName);
        });
    }

}