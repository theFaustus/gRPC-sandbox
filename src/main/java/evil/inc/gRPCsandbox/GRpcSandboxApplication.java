package evil.inc.gRPCsandbox;

import com.google.protobuf.BoolValue;
import evil.inc.proto.domain.Achievement;
import evil.inc.proto.domain.Address;
import evil.inc.proto.domain.Credentials;
import evil.inc.proto.domain.Grant;
import evil.inc.proto.domain.SoftwareDeveloper;
import evil.inc.proto.domain.Token;
import evil.inc.proto.examples.PrimitiveHolder;
import evil.inc.proto.examples.WrapperHolder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class GRpcSandboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(GRpcSandboxApplication.class, args);
    }


    @Bean
    public CommandLineRunner init() {
        return args -> {

            Address address = Address.newBuilder().setCity("Scranton").setCountry("Pennsylvania").setZipcode("1234").build();
            List<Achievement> achievements = List.of(Achievement.newBuilder().setName("Worker of the month").build());
            Map<String, Grant> authorizations = Map.of("Floor 1", Grant.GRANTED, "Floor 2", Grant.DENIED);
            Token token = Token.newBuilder().setAccessToken("abc-123").build();

            SoftwareDeveloper softwareDeveloper = SoftwareDeveloper.newBuilder()
                    .setFirstName("michael")
                    .setLastName("scott")
                    .setAddress(address)
                    .addAllAchievements(achievements)
                    .putAllAuthorizations(authorizations)
                    .setCredentials(Credentials.newBuilder().setToken(token).build())
                    .build();

            log.info("Customer is: \n{}", softwareDeveloper);

            Credentials credentials = softwareDeveloper.getCredentials();
            Credentials.AuthenticationCase authenticationCase = credentials.getAuthenticationCase();
            switch (authenticationCase){
                case BASICAUTH -> log.info("Basic auth {}", credentials.getBasicAuth());
                case TOKEN -> log.info("Token authenticated {}", credentials.getToken());
                case AUTHENTICATION_NOT_SET -> log.info("No authentication");
            }

        };
    }

}
