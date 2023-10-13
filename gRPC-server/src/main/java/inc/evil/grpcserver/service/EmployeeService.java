package inc.evil.grpcserver.service;

import inc.evil.proto.domain.Achievement;
import inc.evil.proto.domain.Address;
import inc.evil.proto.domain.Credentials;
import inc.evil.proto.domain.Grant;
import inc.evil.proto.domain.SoftwareDeveloper;
import inc.evil.proto.domain.Token;
import inc.evil.proto.service.DeleteEmployeeRequest;
import inc.evil.proto.service.EmailNotificationRequest;
import inc.evil.proto.service.EmailNotificationResponse;
import inc.evil.proto.service.EmployeeNumber;
import inc.evil.proto.service.EmployeeServiceGrpc;
import inc.evil.proto.service.EmployeesCount;
import inc.evil.proto.service.NoParams;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@GrpcService
public class EmployeeService extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    @Override
    public void getById(EmployeeNumber request, StreamObserver<SoftwareDeveloper> responseObserver) {
        if (request.getNumber() < 0) {
            responseObserver.onError(Status.FAILED_PRECONDITION.withDescription("Employee number can't be negative").asRuntimeException());

        }
        SoftwareDeveloper softwareDeveloper = getSoftwareDeveloper(request.getNumber());

        responseObserver.onNext(softwareDeveloper);
        responseObserver.onCompleted();
    }


    @Override
    public void getAll(NoParams request, StreamObserver<SoftwareDeveloper> responseObserver) {
        for (int i = 0; i < 10; i++) {
            SoftwareDeveloper softwareDeveloper = getSoftwareDeveloper(i);
            responseObserver.onNext(softwareDeveloper);
        }
        responseObserver.onCompleted();
    }

    private SoftwareDeveloper getSoftwareDeveloper(long employeeNumber) {
        Address address = Address.newBuilder().setCity("Scranton").setCountry("Pennsylvania").setZipcode("1234").build();
        List<Achievement> achievements = List.of(Achievement.newBuilder().setName("Worker of the month").build());
        Map<String, Grant> authorizations = Map.of("Floor 1", Grant.GRANTED, "Floor 2", Grant.DENIED);
        Token token = Token.newBuilder().setAccessToken("abc-123").build();

        SoftwareDeveloper softwareDeveloper = SoftwareDeveloper.newBuilder()
                .setFirstName("michael")
                .setLastName("scott")
                .setAddress(address)
                .setEmployeeNumber(employeeNumber)
                .addAllAchievements(achievements)
                .putAllAuthorizations(authorizations)
                .setCredentials(Credentials.newBuilder().setToken(token).build())
                .build();
        return softwareDeveloper;
    }

    @Override
    public StreamObserver<DeleteEmployeeRequest> deleteEmployee(StreamObserver<EmployeesCount> responseObserver) {
        return new DeleteEmployeeStreamObserver(responseObserver);
    }

    @Override
    public StreamObserver<EmailNotificationRequest> notify(StreamObserver<EmailNotificationResponse> responseObserver) {
        return new NotifyEmployeeStreamObserver(responseObserver);
    }
}
