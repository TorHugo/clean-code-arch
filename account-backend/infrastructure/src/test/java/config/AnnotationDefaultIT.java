package config;

import com.dev.torhugo.clean.code.arch.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = WebServerConfig.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AnnotationDefaultIT {
    public final static String expectedMessageEqual = "Given values, both must be equal.";
    public final static String expectedMessageOptionalTrue = "This optional is not empty!";
    public final static String expectedMessageAccountNotFound = "Account not found!";
    public final static boolean expectedTrue = true;
    public final static boolean expectedFalse = false;
}
