package org.vm;

import org.vm.item.ItemManager;
import org.vm.payment.*;

public class Initializer {
    private final ItemManager itemManager;
    private final PaymentSelector paymentSelector;

    public Initializer(ItemManager itemManager, PaymentSelector paymentSelector) {
        this.itemManager = itemManager;
        this.paymentSelector = paymentSelector;
    }

    public void init() {
        initItem();
        initPayment();
    }

    private void initPayment() {
        CardPayment cardPayment = new CardPayment();
        paymentSelector.addPayment(PaymentType.CARD.getIndex(), cardPayment);

        CashPayment cashPayment = new CashPayment();
        cashPayment.addCoin(100, 5);
        cashPayment.addCoin(500, 5);
        cashPayment.addCoin(1000, 5);
        cashPayment.addCoin(5000, 5);
        cashPayment.addCoin(10000, 5);
        paymentSelector.addPayment(PaymentType.CASH.getIndex(), cashPayment);

        if(paymentSelector.getPaymentCount() != PaymentType.values().length){
            throw new RuntimeException("init payment failed");
        }
    }

    private void initItem() {
        itemManager.addItem("콜라", 1100, 2);
        itemManager.addItem("물", 600, 2);
        itemManager.addItem("커피", 700, 2);
    }
}
