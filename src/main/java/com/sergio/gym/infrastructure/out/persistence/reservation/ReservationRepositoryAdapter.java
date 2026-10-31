package com.sergio.gym.infrastructure.out.persistence.reservation;

import com.sergio.gym.application.port.out.ReservationRepository;
import com.sergio.gym.domain.exceptions.AlreadyReservedException;
import com.sergio.gym.domain.models.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final SpringDataReservationRepository springDataReservationRepository;

    @Override
    public void save(Reservation reservation) {
        try {
            springDataReservationRepository.saveAndFlush(ReservationMapper.toEntity(reservation));
        } catch (DataIntegrityViolationException e) {
            if (e.getMostSpecificCause().getMessage().contains("reservation_one_active_per_member_and_class")) {
                throw new AlreadyReservedException("Member " + reservation.getMemberId() + " already has an active reservation for class " + reservation.getGymClassId());
            }
            throw e;
        }


    }

    @Override
    public Optional<Reservation> findByIdForUpdate(UUID id) {
        Optional<Reservation> reservation = springDataReservationRepository.findByIdForUpdate(id)
                .map(reservationEntity -> ReservationMapper.toDomain(reservationEntity));
        return reservation;
    }
}
