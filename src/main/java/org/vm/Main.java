package org.vm;

import org.vm.item.ItemManager;
import org.vm.payment.PaymentSelector;

public class Main {
    public static void main(String[] args) {
        ItemManager itemManager = new ItemManager();
        PaymentSelector paymentSelector = new PaymentSelector();
        Initializer initializer = new Initializer(itemManager, paymentSelector);
        VendingMachineService vendingMachineService =  new VendingMachineService(itemManager, paymentSelector);

        initializer.init();
        vendingMachineService.start();
    }
}