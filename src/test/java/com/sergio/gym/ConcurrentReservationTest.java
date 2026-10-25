package com.sergio.gym;

import com.sergio.gym.application.port.in.ReserveSpotUseCase;
import com.sergio.gym.application.port.out.GymClassRepository;
import com.sergio.gym.domain.models.GymClass;
import com.sergio.gym.infrastructure.out.persistence.reservation.SpringDataReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class ConcurrentReservationTest {

    @Autowired ReserveSpotUseCase reserveSpot;
    @Autowired GymClassRepository gymClassRepository;
    @Autowired SpringDataReservationRepository reservations;

    @Test
    void tenMembersRacingForTheLastSpotOnlyOneWins() throws Exception {
        UUID classId = UUID.randomUUID();
        gymClassRepository.save(new GymClass(classId, "Spinning", 1));   // un solo lugar

        Queue<Throwable> errors = new ConcurrentLinkedQueue<>();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        CountDownLatch start = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                start.await();
                try {
                    reserveSpot.reserve(UUID.randomUUID(), classId);   // cada uno, un socio distinto
                } catch (Throwable t) {
                    errors.add(t);
                }
                return null;
            });
        }
        start.countDown();
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        long saved = reservations.findAll().stream()
                .filter(r -> r.getGymClassId().equals(classId))
                .count();

        System.out.println("Reservations saved: " + saved + " | errors: " + errors.size());
        errors.forEach(e -> System.out.println("ERROR: " + e.getClass().getSimpleName()));

        assertEquals(1, saved, "Only one member should get the last spot");
    }
}