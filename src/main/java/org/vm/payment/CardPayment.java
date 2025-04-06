package org.vm.payment;

//카드결제는 실패할리 없다고 가정.
public class CardPayment implements PaymentInterface {
    int totalBalance;
    int collectAmount;

    @Override
    public boolean pay(int price) {
        totalBalance += collectAmount;
        collectAmount = 0;
        return true;
    }

    @Override
    public int getTotalBalance() {
        return totalBalance;
    }

    @Override
    public boolean collectAmount(int price) {
        collectAmount = price;
        return true;
    }

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.CARD;
    }
}
