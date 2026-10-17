package com.sergio.gym.infrastructure.out.persistence.reservation;

import com.sergio.gym.application.port.out.ReservationRepository;
import com.sergio.gym.domain.models.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final SpringDataReservationRepository springDataReservationRepository;

    @Override
    public void save(Reservation reservation) {
        springDataReservationRepository.save(ReservationMapper.toEntity(reservation));

    }
}
