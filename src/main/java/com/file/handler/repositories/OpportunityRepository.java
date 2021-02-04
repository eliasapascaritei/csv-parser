package com.file.handler.repositories;

import com.file.handler.models.Opportunity;
import com.sun.istack.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, String>, JpaSpecificationExecutor<Opportunity> {

    Optional<Opportunity> findById(String id);

    List<Opportunity> findAll(@Nullable Specification<Opportunity> specification);

}
