package com.file.handler.services;

import com.file.handler.dtos.OpportunityDto;
import com.file.handler.repositories.OpportunityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OpportunityService(OpportunityRepository opportunityRepository, ModelMapper modelMapper) {
        this.opportunityRepository = opportunityRepository;
        this.modelMapper = modelMapper;
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<OpportunityDto> getOpportunities(Optional<String> team,
                                                 Optional<String> product,
                                                 Optional<String> bookingType,
                                                 Optional<LocalDate> startDate,
                                                 Optional<LocalDate> endDate) {
        List<OpportunityDto> opportunityDtoList = new ArrayList<>();
        String sqlQuery = generateRaqSqlQuery(team, product, bookingType, startDate, endDate);
        opportunityDtoList = getBySql(entityManager, sqlQuery);

        return opportunityDtoList;
    }

    private List<OpportunityDto> getBySql(EntityManager entityManager,String sqlRaw){
        Query query = entityManager.createNativeQuery(sqlRaw);
         return query.getResultList();
    }

    private String generateRaqSqlQuery(Optional<String> team,
                                       Optional<String> product,
                                       Optional<String> bookingType,
                                       Optional<LocalDate> startDate,
                                       Optional<LocalDate> endDate) {
        int params = countParams(team, product, bookingType, startDate, endDate);
        String query = generateBasicRawSqlQuery()
                .concat(
                        (params > 0) ? generateWhereClause(team, product, bookingType, startDate, endDate, params) : "");
        return query;
    }


    private String generateBasicRawSqlQuery(){
        return "Select * from opportunities";
    }

    private String generateWhereClause(Optional<String> team,
                                       Optional<String> product,
                                       Optional<String> bookingType,
                                       Optional<LocalDate> startDate,
                                       Optional<LocalDate> endDate,
                                       int params) {
        if (params == 0)
            return "";
        else {
            return " WHERE "
                    .concat(team.map(this::generateFilteredByTeamClause).orElse(""))
                    .concat((params > 1 && team.isPresent()) ? " AND " : "")
                    .concat(product.map(this::generateFilteredByProductClause).orElse(""))
                    .concat((params > 2 && product.isPresent()) ? " AND " : "")
                    .concat(bookingType.map(this::generateFilteredByBookingTypeClause).orElse(""))
                    .concat((params > 3 && bookingType.isPresent()) ? " AND " : "")
                    .concat(startDate.map(this::generateFilteredByStartDateClause).orElse(""))
                    .concat(startDate.isPresent() ? " AND ": "")
                    .concat(endDate.map(this::generateFilteredByEndDateClause).orElse(""));
        }
    }

    private String generateFilteredByTeamClause(String team) {
        return "team = \'" + team + "\'";
    }

    private String generateFilteredByProductClause(String product) {
        return "product = \'" + product + "\'";
    }

    private String generateFilteredByStartDateClause(LocalDate date) {
        return "booking_date > \'" + date.toString() + "\'";
    }

    private String generateFilteredByEndDateClause(LocalDate date) {
        return "booking_date < \'" + date.toString() + "\'";
    }

    private String generateFilteredByBookingTypeClause(String bookingType) {
        return "booking_type = \'" + bookingType + "\'";
    }

    private int countParams(Optional<String> team,
                            Optional<String> product,
                            Optional<String> bookingType,
                            Optional<LocalDate> startDate,
                            Optional<LocalDate> endDate) {
        int nr = 0;
        if (team.isPresent()) nr++;
        if (product.isPresent()) nr++;
        if (bookingType.isPresent()) nr++;
        if (startDate.isPresent()) nr++;
        if (endDate.isPresent()) nr++;

        return nr;
    }
}
