package com.acs560.dataanalyzer.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@NoArgsConstructor
@Entity
@Data
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "ECOM_SALES_DATA")
public class EcomSalesData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_INDEX")
    private Integer recordIndex;

    @Column(unique = true, nullable = false, name = "ORDER_ID")
    private String orderId;

    @Column(name = "ORDER_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "FULFILMENT")
    private String fulfilment;

    @Column(name = "CHANNEL")
    private String channel;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "SIZE")
    private String size;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "SHIP_CITY")
    private String shipCity;

    @Column(name = "SHIP_STATE")
    private String shipState;

    // Constructor without recordIndex
    public EcomSalesData(String orderId, Date date, String status, String fulfilment, 
                         String channel, String category, String size, Double amount, 
                         String shipCity, String shipState) {
        this.orderId = orderId;
        this.date = date;
        this.status = status;
        this.fulfilment = fulfilment;
        this.channel = channel;
        this.category = category;
        this.size = size;
        this.amount = amount;
        this.shipCity = shipCity;
        this.shipState = shipState;
    }
    
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;
}