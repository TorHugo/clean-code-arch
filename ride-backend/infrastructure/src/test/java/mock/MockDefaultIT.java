package mock;

import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideInput;
import com.dev.torhugo.clean.code.arch.application.gateway.models.AccountDTO;
import com.dev.torhugo.clean.code.arch.application.getallposition.GetAllPositionOutput;
import com.dev.torhugo.clean.code.arch.application.getride.BasicAccountOutput;
import com.dev.torhugo.clean.code.arch.application.getride.GetRideOutput;
import com.dev.torhugo.clean.code.arch.application.requestride.CoordinatesRequestInfo;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideInput;
import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionInput;
import com.dev.torhugo.clean.code.arch.domain.entity.Position;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.AcceptRideRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.CoordinatesInfo;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.GetAllPositionResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MockDefaultIT {
    private final static String expectedName = "Account Name";
    private final static String expectedEmail = "account@email.com";
    private final static String expectedCarPlate = "ABC1234";
    private final static Double latitude = Math.random();
    private final static Double longitude = Math.random();
    private final static CoordinatesRequestInfo coordinates = new CoordinatesRequestInfo(latitude, longitude);
    public static UUID generateIdentifier(){
        return UUID.randomUUID();
    }
    public static CoordinatesInfo generateCoordinateInfo(){
        return new CoordinatesInfo(latitude, longitude);
    }
    public static GetRideOutput generateResponseRideRequested() {
        final var passenger = new BasicAccountOutput(UUID.randomUUID(), expectedName, expectedEmail, true, false, expectedCarPlate);
        return new GetRideOutput(UUID.randomUUID(), passenger, null, "REQUESTED", null, null, coordinates, coordinates, coordinates, LocalDateTime.now(), null);
    }
    public static GetRideOutput generateResponseRideAccepted() {
        final var passenger = new BasicAccountOutput(UUID.randomUUID(), expectedName, expectedEmail, true, false, expectedCarPlate);
        final var driver = new BasicAccountOutput(UUID.randomUUID(), expectedName, expectedEmail, false, true, expectedCarPlate);
        return new GetRideOutput(UUID.randomUUID(), passenger, driver, "ACCEPTED", null, null, coordinates, coordinates, coordinates, LocalDateTime.now(), null);
    }
    public static AcceptRideRequest generateAcceptRideRequest() {
        return new AcceptRideRequest(UUID.randomUUID(), UUID.randomUUID());
    }
    public static Ride generateObjectRide() {
        return Ride.create(UUID.randomUUID(), latitude, longitude, latitude, longitude);
    }
    public static Position generateObjectPosition() {
        return Position.create(UUID.randomUUID(), latitude, longitude);
    }
    public static RequestRideInput generateRequestRide(){
        return RequestRideInput.with(UUID.randomUUID(), latitude, longitude, latitude, longitude);
    }
    public static AccountDTO generateAccountPassenger(final UUID passengerId) {
        return AccountDTO.with(passengerId, expectedName, expectedEmail, "", true, false, expectedCarPlate, LocalDateTime.now(), null);
    }
    public static AccountDTO generateAccountDriver(final UUID driverId) {
        return AccountDTO.with(driverId, expectedName, expectedEmail, "", false, true, expectedCarPlate, LocalDateTime.now(), null);
    }
    public static AcceptRideInput generateAcceptRideInput(final UUID rideId){
        return AcceptRideInput.with(rideId, UUID.randomUUID());
    }
    public static UpdatePositionInput generateUpdatePositionInput(final UUID rideId){
        return UpdatePositionInput.with(rideId, Math.random(), Math.random());
    }
    public static GetAllPositionOutput generateGetAllPositionOutput(final UUID rideId){
        final var positions = List.of(Position.create(rideId, latitude, longitude));
        return GetAllPositionOutput.with(rideId, positions);
    }
}
