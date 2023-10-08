package inc.evil.grpcserver.service;

import inc.evil.proto.service.EmailNotificationRequest;
import inc.evil.proto.service.EmailNotificationResponse;
import inc.evil.proto.service.NotificationStatus;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotifyEmployeeStreamObserver implements StreamObserver<EmailNotificationRequest> {

    private StreamObserver<EmailNotificationResponse> emailNotificationResponseStreamObserver;

    public NotifyEmployeeStreamObserver(StreamObserver<EmailNotificationResponse> emailNotificationResponseStreamObserver) {
        this.emailNotificationResponseStreamObserver = emailNotificationResponseStreamObserver;
    }


    @Override
    public void onNext(EmailNotificationRequest value) {
        log.info("Notifying employee {} with payload {}", value.getEmployeeNumber().getNumber(), value.getPayload());
        EmailNotificationResponse emailNotificationResponse = EmailNotificationResponse.newBuilder().setStatus(NotificationStatus.NOTIFICATION_SUCCESS).build();
        this.emailNotificationResponseStreamObserver.onNext(emailNotificationResponse);
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onCompleted() {
        emailNotificationResponseStreamObserver.onCompleted();
    }
}
