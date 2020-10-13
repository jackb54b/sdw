package sdw;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ConfigurationPropertiesScan
@EnableConfigurationProperties
@SpringBootApplication
@EnableTransactionManagement
public class SdwApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdwApplication.class, args);
    }
}