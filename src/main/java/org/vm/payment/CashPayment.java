package org.vm.payment;

import java.util.Scanner;

public class CashPayment implements PaymentInterface {
    Scanner scanner = new Scanner(System.in);

    //잔고
    int won100Count;
    int won500Count;
    int won1000Count;
    int won5000Count;
    int won10000Count;

    //유저지불 임시캐쉬
    int collectWon100Count;
    int collectWon500Count;
    int collectWon1000Count;
    int collectWon5000Count;
    int collectWon10000Count;
    int collectTotalAmount;

    private void addCollect() {
        won100Count += collectWon100Count;
        won500Count += collectWon500Count;
        won1000Count += collectWon1000Count;
        won5000Count += collectWon5000Count;
        won10000Count += collectWon10000Count;
    }

    private void restoreAddCollect() {
        won100Count -= collectWon100Count;
        won500Count -= collectWon500Count;
        won1000Count -= collectWon1000Count;
        won5000Count -= collectWon5000Count;
        won10000Count -= collectWon10000Count;
    }

    private void resetCollect() {
        collectWon100Count = 0;
        collectWon500Count = 0;
        collectWon1000Count = 0;
        collectWon5000Count = 0;
        collectWon10000Count = 0;
        collectTotalAmount = 0;
    }

    private boolean giveChange(int change) {
        int temp10000 = won10000Count;
        int temp5000 = won5000Count;
        int temp1000 = won1000Count;
        int temp500 = won500Count;
        int temp100 = won100Count;

        int count10000 = 0, count5000 = 0, count1000 = 0, count500 = 0, count100 = 0;

        while (change > 0) {
            if (change >= 10000 && temp10000 > 0) {
                change -= 10000;
                temp10000--;
                count10000++;
            } else if (change >= 5000 && temp5000 > 0) {
                change -= 5000;
                temp5000--;
                count5000++;
            } else if (change >= 1000 && temp1000 > 0) {
                change -= 1000;
                temp1000--;
                count1000++;
            } else if (change >= 500 && temp500 > 0) {
                change -= 500;
                temp500--;
                count500++;
            } else if (change >= 100 && temp100 > 0) {
                change -= 100;
                temp100--;
                count100++;
            } else {
                System.out.println("잔고가 부족하므로 " + collectTotalAmount + "원을 환불해 드렸습니다.");
                return false;
            }
        }

        won10000Count = temp10000;
        won5000Count = temp5000;
        won1000Count = temp1000;
        won500Count = temp500;
        won100Count = temp100;

        System.out.println("잔돈 반환:");
        if (count10000 > 0) System.out.println("10000원권: " + count10000 + "개");
        if (count5000 > 0) System.out.println("5000원권: " + count5000 + "개");
        if (count1000 > 0) System.out.println("1000원권: " + count1000 + "개");
        if (count500 > 0) System.out.println("500원: " + count500 + "개");
        if (count100 > 0) System.out.println("100원: " + count100 + "개");

        return true;
    }

    @Override
    public boolean pay(int price) {
        addCollect();

        int change = collectTotalAmount - price;
        System.out.println("총 반환해야할 잔돈 :" + change);
        if (!giveChange(change)) {
            restoreAddCollect();
            resetCollect();
            return false;
        }

        resetCollect();
        return true;
    }

    @Override
    public int getTotalBalance() {
        int totalBalance = won100Count * 100 +
                won500Count * 500 +
                won1000Count * 1000 +
                won5000Count * 5000 +
                won10000Count * 10000;

        return totalBalance;
    }

    @Override
    public boolean collectAmount(int price) {
        System.out.println("[현금 결제] 금액을 투입하세요. (단위: 100 / 500 / 1000 / 5000 / 10000), 취소는 0번");
        System.out.println("총 필요한 금액: " + price + "원");

        while (collectTotalAmount < price) {
            System.out.print("금액 입력: ");
            int input = scanner.nextInt();

            switch (input) {
                case 100:
                    collectWon100Count++;
                    break;
                case 500:
                    collectWon500Count++;
                    break;
                case 1000:
                    collectWon1000Count++;
                    break;
                case 5000:
                    collectWon5000Count++;
                    break;
                case 10000:
                    collectWon10000Count++;
                    break;
                case 0:
                    System.out.println("입금한 금액:" + collectTotalAmount + "을 반환하였습니다.");
                    collectWon100Count = 0;
                    collectWon500Count = 0;
                    collectWon1000Count = 0;
                    collectWon5000Count = 0;
                    collectWon10000Count = 0;
                    collectTotalAmount = 0;
                    return false;

                default:
                    System.out.println("유효하지 않은 금액입니다. 다시 입력해주세요.");
                    continue;
            }

            collectTotalAmount += input;
            int remaining = Math.max(0, price - collectTotalAmount);
            System.out.println("현재 투입 금액: " + collectTotalAmount + "원 / 투입해야할 남은 금액: " + remaining + "원");
        }

        return true;
    }

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.CASH;
    }

    public void setInitBalanceEachCount(int count) {
        won100Count = count;
        won500Count = count;
        won1000Count = count;
        won5000Count = count;
        won10000Count = count;
    }
}
