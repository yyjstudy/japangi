package org.vm;

import org.vm.item.ItemManager;
import org.vm.payment.PaymentInterface;
import org.vm.payment.PaymentSelector;
import org.vm.payment.PaymentType;

import java.util.*;

public class VendingMachineService {
    private final Scanner scanner = new Scanner(System.in);
    private final ItemManager itemManager;
    private final PaymentSelector paymentSelector;

    public VendingMachineService(ItemManager itemManager, PaymentSelector paymentSelector) {
        this.itemManager = itemManager;
        this.paymentSelector = paymentSelector;
    }

    public void start() {
        System.out.println("=== 자판기 입니다. ===");

        while (true) {
            System.out.println("\n[상품 목록]");

            Set<Integer> allIndexes = itemManager.getAllIndexes();
            for (int index : allIndexes) {
                String name = itemManager.getName(index);
                int price = itemManager.getPrice(index);
                int stock = itemManager.getStock(index);
                System.out.println("번호=" + index + " 이름:" + name + " 가격:" + price + " 재고:" + stock);
            }

            System.out.println("\n구매할 상품 번호를 입력하세요, (나가려면 -1 입력)");
            int itemNumber = readInt();

            if (itemNumber == -1) {
                break;
            }

            if (!itemManager.isItemExists(itemNumber)) {
                System.out.println("잘못된 상품 번호입니다.");
                continue;
            }

            if (itemManager.isSoldOut(itemNumber)) {
                System.out.println("재고가 없습니다.");
                continue;
            }

            int price = itemManager.getPrice(itemNumber);

            System.out.println("결제 수단을 선택하세요");
            Arrays.stream(PaymentType.values()).forEach(type -> {
                System.out.println("결제번호=" + type.getIndex() + ", " + type.name());
            });

            int paymentNumber = readInt();
            if (!paymentSelector.isExist(paymentNumber)) {
                System.out.println("잘못된 결제수단 번호입니다.");
                continue;
            }

            PaymentInterface payment = paymentSelector.getPayment(paymentNumber);
            boolean isCollected = payment.collectAmount(price);
            if (!isCollected) {
                continue;
            }

            if (!payment.pay(price)) {
                continue;
            }

            itemManager.consumeStock(itemNumber);

            System.out.println("자판기에서 " + itemManager.getName(itemNumber) + "이(가) 1개 나왔습니다.");
        }

        Arrays.stream(PaymentType.values()).forEach(type -> {
            PaymentInterface payment = paymentSelector.getPayment(type.getIndex());

            System.out.println(payment.getPaymentType() + "의 최종 잔고는 " + payment.getTotalBalance() + " 입니다.");
        });
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("숫자를 입력하세요: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
