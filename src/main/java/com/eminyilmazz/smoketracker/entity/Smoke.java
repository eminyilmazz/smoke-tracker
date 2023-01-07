package com.eminyilmazz.smoketracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "smoke")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Smoke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "smoked_date")
    private LocalDateTime smokedDate;

    @Column(name = "activity")
    private String activity;

    @Column(name = "quantity")
    private int quantity;
}
