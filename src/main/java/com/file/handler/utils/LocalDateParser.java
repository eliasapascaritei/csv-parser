package com.file.handler.utils;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateParser extends AbstractBeanField {

  @Override
  protected LocalDate convert(String s) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
    return LocalDate.parse(s, formatter);
  }

}
