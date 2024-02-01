package com.edstem.taxibookingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Taxi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taxiId;
    private String driverName;
    private Long licenseNumber;
    private Long currentLocation;
}
