package com.sergio.gym.infrastructure.out.persistence.gymclass;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "gym_class")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GymClassEntity {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "occupied_spots", nullable = false)
    private int occupiedSpots;



}
