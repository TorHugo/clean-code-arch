package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RideJpaRepository extends JpaRepository<RideEntity, UUID> {
    @Query("SELECT r FROM ride r WHERE r.status = :status AND r.driverId = :driverId")
    List<RideEntity> findRidesByStatusAndDriverId(@Param("status") final String status,
                                                  @Param("driverId") final UUID driverId);
    @Query("SELECT r FROM ride r WHERE r.status = :status AND r.passengerId = :passengerId")
    List<RideEntity> findRidesByStatusAndPassengerId(@Param("status") final String status,
                                                     @Param("passengerId") final UUID passengerId);
}
