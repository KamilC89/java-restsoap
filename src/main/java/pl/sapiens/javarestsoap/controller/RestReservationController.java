package pl.sapiens.javarestsoap.controller;

import lombok.extern.slf4j.Slf4j;
import pl.sapiens.javarestsoap.entity.Reservation;
import pl.sapiens.javarestsoap.exception.NoReservationFoundException;
import pl.sapiens.javarestsoap.service.ReservationsService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@Path("/reservations")
public class RestReservationController {

    private static final Reservation theOnlyOne = new Reservation(1L,
            "Nowak",
            13,
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(2),
            "Main center",
            "Near window!!!");


    private final ReservationsService businessLogic = new ReservationsService();

    @GET
    public Response getReservation() {
        var reservations = businessLogic.getAllReservationsFromDataSource();
        log.info("Getting all reservations");
        return Response.ok(reservations).build();
    }

    // old using
//    @GET
//    @Path("/{id}")
//    public Response findReservationById(@PathParam("id") Long reservationId) {
//        log.info("Trying to find reservation by id: [{}]", reservationId);
//        Response result;
//        try {
//            Reservation found = businessLogic.getReservationById(reservationId);
//            result = Response.ok(found).build();
//
//        } catch (NoReservationFoundException e) {
//            result = Response.status(NOT_FOUND).build();
//        }
//        return result;
//    }

    @GET
    @Path("/{id}")
    public Response findReservationById(@PathParam("id") Long reservationId) {
        log.info("Trying to find reservation by id: [{}]", reservationId);
        Response result;

        Reservation found = businessLogic.getReservationByIdBetter(reservationId);
        result = Response.ok(found).build();
        return result;
    }

    @POST
    public Response createReservation(Reservation toCreate) {
        log.info("Trying to create reservation: [{}]:", toCreate);
        //TODO: use service, add validation
        URI location = null;
        try {
            location = new URI("/reservatons/2");
        } catch (URISyntaxException e) {
            log.error("Cannot create location header");
        }
        return Response.created(location).build();

    }


}


