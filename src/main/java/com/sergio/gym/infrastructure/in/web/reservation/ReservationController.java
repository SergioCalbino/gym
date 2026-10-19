package com.sergio.gym.infrastructure.in.web.reservation;

import com.sergio.gym.application.port.in.ReserveSpotUseCase;

import com.sergio.gym.domain.models.Reservation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReserveSpotUseCase reserveSpotUseCase;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@Valid @RequestBody ReserveSpotRequest reservationRequest) {
        Reservation reserve = reserveSpotUseCase.reserve(reservationRequest.memberId(), reservationRequest.gymClassId());
        return ReservationResponse.from(reserve);
    }




}
