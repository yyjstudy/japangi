package org.vm.payment;

public interface PaymentInterface {
    boolean pay(int price);
    int getTotalBalance();
    boolean collectAmount(int price);
    PaymentType getPaymentType();
}