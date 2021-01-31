package com.pkrok.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor

@Entity
@Table(name = "orderr")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String name;

    @Column(name = "qty", nullable = false)
    private int quantity;

    @Column(columnDefinition = "DECIMAL(8,3)")
    private BigDecimal price;

    @Column(columnDefinition = "DECIMAL(8,3)")
    private BigDecimal summ;

    @Column(name = "user", nullable=false)
    private String user;

    @Column
    private LocalDateTime dayRecieveOrder;

    @Column
    private String dayMakeOrder;

    @Column
    private String adress;

    @Column
    private String weight;
}
