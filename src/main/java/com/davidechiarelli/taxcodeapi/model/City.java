package com.davidechiarelli.taxcodeapi.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

/**
 * Model class for cities
 */

@Data
public class City {
    @CsvBindByPosition(position = 6)
    private String denominazione;
    @CsvBindByPosition(position = 14)
    private String siglaProvincia;
    @CsvBindByPosition(position = 19)
    private String codiceCatastale;
}
