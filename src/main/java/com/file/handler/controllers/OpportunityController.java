package com.file.handler.controllers;


import com.file.handler.dtos.OpportunityDto;
import com.file.handler.services.OpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class OpportunityController {

    private final OpportunityService opportunityService;

    @Autowired
    public OpportunityController(OpportunityService opportunityService){
        this.opportunityService = opportunityService;
    }


    @RequestMapping(value = "/opportunity", method = RequestMethod.GET)
    public ResponseEntity<List<OpportunityDto>> getOpportunities(@RequestParam(required = false) Optional<String> team,
                                                                 @RequestParam(required = false) Optional<String> product,
                                                                 @RequestParam(required = false) Optional<String> bookingType,
                                                                 @RequestParam(required = false)
                                                                 @DateTimeFormat(pattern = "M/d/yy") Optional<LocalDate> startDate,
                                                                 @RequestParam(required = false)
                                                                 @DateTimeFormat(pattern = "M/d/yy") Optional<LocalDate> endDate){
        if(startDate.isPresent() && !endDate.isPresent())
            return ResponseEntity
                    .badRequest()
                    .body(new ArrayList<>());

        if(!startDate.isPresent() && endDate.isPresent())
            return ResponseEntity
                    .badRequest()
                    .body(new ArrayList<>());

        return ResponseEntity
                .ok(opportunityService
                        .getOpportunities(team, product, bookingType, startDate, endDate));
    }
}
