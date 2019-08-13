package com.workshop.ordersservice;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor()
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    private String orderNumber;
    private String customerName;

    public OrderItem(String orderNumber, String customerName) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
    }
}