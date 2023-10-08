package inc.evil.grpcserver.service;

import inc.evil.proto.service.DeleteEmployeeRequest;
import inc.evil.proto.service.EmployeesCount;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeleteEmployeeStreamObserver implements StreamObserver<DeleteEmployeeRequest> {

    private StreamObserver<EmployeesCount> employeesCountStreamObserver;
    private int countOfEmployees = 100;

    public DeleteEmployeeStreamObserver(StreamObserver<EmployeesCount> employeesCountStreamObserver) {
        this.employeesCountStreamObserver = employeesCountStreamObserver;
    }

    @Override
    public void onNext(DeleteEmployeeRequest value) {
        log.info("Deleting employee {}", value.getEmployeeNumber().getNumber());
        --countOfEmployees;
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onCompleted() {
        employeesCountStreamObserver.onNext(EmployeesCount.newBuilder().setCount(countOfEmployees).build());
        employeesCountStreamObserver.onCompleted();
    }
}
