package org.vm;

import org.vm.item.ItemManager;
import org.vm.payment.PaymentSelector;

public class Main {
    private final ItemManager itemManager = new ItemManager();
    private final PaymentSelector paymentSelector = new PaymentSelector();
    private final Initializer initializer = new Initializer(itemManager, paymentSelector);
    private final VendingMachineService vendingMachineService =  new VendingMachineService(itemManager, paymentSelector);

    public static void main(String[] args) {
        Main main = new Main();
        main.initializer.init();
        main.vendingMachineService.start();
    }
}