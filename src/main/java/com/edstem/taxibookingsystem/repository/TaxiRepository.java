package com.edstem.taxibookingsystem.repository;

import com.edstem.taxibookingsystem.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {}
