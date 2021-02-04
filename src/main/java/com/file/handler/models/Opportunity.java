package com.file.handler.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "opportunities")
@Data
public class Opportunity implements Serializable {

    @Id
    private String opportunityId;

    private String customerName;
    private LocalDate bookingDate;
    private String bookingType;
    private double total;
    private String accountExecutive;
    private String salesOrganization;
    private String team;
    private String product;
    private String renewable;

}