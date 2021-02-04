package com.file.handler.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateParser extends AbstractBeanField {

  @Override
  protected LocalDate convert(String s) throws CsvDataTypeMismatchException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
    return LocalDate.parse(s, formatter);
  }

}
