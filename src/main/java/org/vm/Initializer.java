package org.vm;

import org.reflections.Reflections;
import org.vm.item.ItemManager;
import org.vm.payment.PaymentInterface;
import org.vm.payment.PaymentSelector;

import java.util.*;

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
        Reflections reflections = new Reflections("org.vm.payment");
        Set<Class<? extends PaymentInterface>> classes =
                reflections.getSubTypesOf(PaymentInterface.class);

        for (Class<? extends PaymentInterface> clazz : classes) {
            try {
                PaymentInterface instance = clazz.getDeclaredConstructor().newInstance();
                int index = instance.getPaymentType().getIndex(); // 키로 사용할 값
                paymentSelector.addPayment(index, instance);
            } catch (Exception e) {
                throw new RuntimeException("init payment 초기화 에러", e);
            }
        }
    }


    private void initItem() {
        itemManager.addItem("콜라", 1100, 2);
        itemManager.addItem("물", 600, 2);
        itemManager.addItem("커피", 700, 2);
    }
}
