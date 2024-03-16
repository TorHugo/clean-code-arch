import com.dev.torhugo.clean.code.arch.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = WebServerConfig.class)
@ActiveProfiles("test")
public class MainTest {

    @Test
    void contextLoads() {
    }
}
