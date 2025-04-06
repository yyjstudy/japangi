package org.vm.payment;

public enum PaymentType {
    CARD(1),
    CASH(2);

    private final int index;

    PaymentType(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}