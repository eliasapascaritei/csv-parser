package com.file.handler.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class DoubleParser extends AbstractBeanField {

  @Override
  protected Double convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
    String amountCurrency = s
            .replaceAll(",", "")
            .replaceAll("\\$", "");
    return Double.parseDouble(amountCurrency);
  }

}
