package com.sergio.gym.infrastructure.in.web.gymClass;

import com.sergio.gym.application.port.in.CreateGymClassCommand;
import com.sergio.gym.application.port.in.CreateGymClassUseCase;
import com.sergio.gym.domain.models.GymClass;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gym-classes")
@RequiredArgsConstructor
public class GymClassController {

    private final CreateGymClassUseCase createGymClassUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateGymClassResponse create(@Valid @RequestBody CreateGymClassRequest request) {
        CreateGymClassCommand command = new CreateGymClassCommand(request.name(), request.capacity());
        GymClass gymClass = createGymClassUseCase.create(command);
        return CreateGymClassResponse.from(gymClass);
    }
}
