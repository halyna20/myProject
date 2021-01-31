package com.pkrok.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TrashDTO {

    private Long id;


    private String name;


    private int quantity;


    private BigDecimal price;


    private BigDecimal summ;

    private String user;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dayRecieveOrder;


    private String dayMakeOrder;


    private String adress;
    private String weight;
}
