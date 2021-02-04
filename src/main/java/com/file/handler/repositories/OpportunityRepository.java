package com.file.handler.repositories;

import com.file.handler.models.Opportunity;
import com.sun.istack.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, String>, JpaSpecificationExecutor<Opportunity> {

    boolean existsByOpportunityId(String id);

    List<Opportunity> findAllByTeam(String team);

    List<Opportunity> findAllByBookingType(String bookingType);

    List<Opportunity> findAllByBookingDateGreaterThanEqualAndBookingDateLessThanEqual(LocalDate startDate, LocalDate endDate);

    List<Opportunity> findAll(@Nullable Specification<Opportunity> specification);

}
