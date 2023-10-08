package inc.evil.grpcserver.config;

import inc.evil.grpcserver.service.EmployeeService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
public class GrpcConfiguration {

    private final EmployeeService employeeService;

    public GrpcConfiguration(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Bean
    public Server grpcServer() throws IOException, InterruptedException {
        int port = 8082;
        Server server = ServerBuilder.forPort(port)
                .addService(employeeService)
                .build();
        server.start();
        log.info("gRPC server initialized with port(s): {} (http)", port);
        server.awaitTermination();
        return server;
    }
}
