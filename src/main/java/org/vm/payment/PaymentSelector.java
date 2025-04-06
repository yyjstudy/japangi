package org.vm.payment;

import java.util.*;

public class PaymentSelector {
    private final Map<Integer, PaymentInterface> map = new HashMap<>();

    public void addPayment(int index, PaymentInterface payment) {
        map.put(index, payment);
    }

    public PaymentInterface getPayment(int index) {
        return map.get(index);
    }

    public boolean isExist(int index){
        return map.containsKey(index);
    }
}
