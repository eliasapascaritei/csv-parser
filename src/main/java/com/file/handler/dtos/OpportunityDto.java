package com.file.handler.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.file.handler.utils.DoubleParser;
import com.file.handler.utils.LocalDateParser;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpportunityDto {

    @CsvBindByPosition(position = 0)
    private String customerName;

    @CsvCustomBindByPosition(position = 1, converter = LocalDateParser.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate bookingDate;

    @CsvBindByPosition(position = 2)
    private String opportunityId;

    @CsvBindByPosition(position = 3)
    private String bookingType;

    @CsvCustomBindByPosition(position = 4, converter = DoubleParser.class)
    private double total;

    @CsvBindByPosition(position = 5)
    private String accountExecutive;

    @CsvBindByPosition(position = 6)
    private String salesOrganization;

    @CsvBindByPosition(position = 7)
    private String team;

    @CsvBindByPosition(position = 8)
    private String product;

    @CsvBindByPosition(position = 9)
    private String renewable;

}