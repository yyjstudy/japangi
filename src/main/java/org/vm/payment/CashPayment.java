package org.vm.payment;

import java.util.*;

public class CashPayment implements PaymentInterface {
    private final Scanner scanner = new Scanner(System.in);

    // 금액권과 잔고 수량 관리: key는 금액권, value는 보유 수량
    private final Map<Integer, Integer> balance = new HashMap<>();

    // 정렬된 금액권 목록 (내림차순)
    private final NavigableSet<Integer> sortedDenominations = new TreeSet<>(Comparator.reverseOrder());

    // 유저가 투입한 임시 캐시
    private final Map<Integer, Integer> collectMap = new HashMap<>();
    private int collectTotalAmount = 0;

    @Override
    public boolean pay(int price) {
        addCollect();

        int change = collectTotalAmount - price;
        System.out.println("총 반환해야할 잔돈: " + change);
        if (!giveChange(change)) {
            restoreCollect();
            resetCollect();
            return false;
        }

        resetCollect();
        return true;
    }

    @Override
    public int getTotalBalance() {
        return balance.entrySet().stream()
                .mapToInt(e -> e.getKey() * e.getValue())
                .sum();
    }

    @Override
    public boolean collectAmount(int price) {
        System.out.println("[현금 결제] 금액을 투입하세요. (지원 금액: " + sortedDenominations + "), 취소는 0번");
        System.out.println("총 필요한 금액: " + price + "원");

        while (collectTotalAmount < price) {
            System.out.print("금액 입력: ");
            int input = scanner.nextInt();

            if (input == 0) {
                System.out.println("입금한 금액: " + collectTotalAmount + "원을 반환하였습니다.");
                resetCollect();
                return false;
            }

            if (!balance.containsKey(input)) {
                System.out.println("지원하지 않는 금액입니다. 다시 입력해주세요.");
                continue;
            }

            collectMap.put(input, collectMap.getOrDefault(input, 0) + 1);
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

    public void addCoin(int denomination, int count) {
        balance.put(denomination, count);
        sortedDenominations.add(denomination);
    }

    private void addCollect() {
        for (Map.Entry<Integer, Integer> entry : collectMap.entrySet()) {
            balance.put(entry.getKey(), balance.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
    }

    private void restoreCollect() {
        for (Map.Entry<Integer, Integer> entry : collectMap.entrySet()) {
            balance.put(entry.getKey(), balance.get(entry.getKey()) - entry.getValue());
        }
    }

    private void resetCollect() {
        collectMap.clear();
        collectTotalAmount = 0;
    }

    private boolean giveChange(int change) {
        Map<Integer, Integer> tempBalance = new HashMap<>(balance);
        Map<Integer, Integer> changeMap = new HashMap<>();

        for (int denom : sortedDenominations) {
            while (change >= denom && tempBalance.getOrDefault(denom, 0) > 0) {
                change -= denom;
                tempBalance.put(denom, tempBalance.get(denom) - 1);
                changeMap.put(denom, changeMap.getOrDefault(denom, 0) + 1);
            }
        }

        if (change > 0) {
            System.out.println("잔고가 부족하여 " + collectTotalAmount + "원을 환불해 드렸습니다.");
            return false;
        }

        balance.clear();
        balance.putAll(tempBalance);

        System.out.println("잔돈 반환:");
        for (int denomination : sortedDenominations) {
            Integer count = changeMap.get(denomination);
            if(count != null){
                System.out.println(denomination + "원: " + count + "개");
            }
        }

        return true;
    }
}
