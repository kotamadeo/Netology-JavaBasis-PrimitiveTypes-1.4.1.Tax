package com.gmail.at.kotamadeo.program;

import com.gmail.at.kotamadeo.taxService.TaxService;
import com.gmail.at.kotamadeo.utils.Utils;

import java.util.Scanner;

public class Program {
    private final TaxService taxService = new TaxService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        String input;
        while (true) {
            try {
                printMenu();
                input = scanner.nextLine();
                if ("выход".equalsIgnoreCase(input) || "0".equals(input)) {
                    scanner.close();
                    break;
                } else {
                    int operationNumber = Integer.parseInt(input);
                    switch (operationNumber) {
                        case 1:
                            System.out.println(Utils.ANSI_BLUE + "Введите доход:" + Utils.ANSI_RESET);
                            input = scanner.nextLine();
                            taxService.setEarnings(input);
                            System.out.printf("%sТекущие доходы в рублях: %s коп.%s%n", Utils.ANSI_BLUE,
                                    taxService.getEarnings(), Utils.ANSI_RESET);
                            break;
                        case 2:
                            System.out.println(Utils.ANSI_BLUE + "Введите расходы:" + Utils.ANSI_RESET);
                            input = scanner.nextLine();
                            taxService.setExpenses(input);
                            System.out.printf("%sТекущие расходы в рублях: %s коп.%s%n", Utils.ANSI_BLUE,
                                    taxService.getExpenses(), Utils.ANSI_RESET);
                            break;
                        case 3:
                            taxService.calculateTaxSTS();
                            taxService.calculateTaxIME();
                            System.out.println(Utils.ANSI_GREEN + "Лучшая система налогообложения выбраная нами это:" +
                                    Utils.ANSI_RESET);
                            taxService.BetterTaxSystem();
                            break;
                        case 4:
                            taxService.roundTax();
                            break;
                        default:
                            System.out.println(Utils.ANSI_RED + "Вы ввели неверный номер операции!" + Utils.ANSI_RESET);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(Utils.ANSI_RED + "Неверный ввод!" + Utils.ANSI_RESET);
            }
        }
    }

    private void printMenu() {
        System.out.println(Utils.ANSI_YELLOW + "Эта программа способна выбрать лучшую систему налогообложения."
                + Utils.ANSI_RESET);
        System.out.println(Utils.ANSI_PURPLE + "Возможные команды программы:" + Utils.ANSI_RESET);
        System.out.println("0 или выход: чтобы выйти из программы.");
        System.out.println("1: ввести доход.");
        System.out.println("2: ввести расход.");
        System.out.println("3: выбрать систему налогообложения.");
        System.out.println("4: округлить сумму налога до целого числа.");
        System.out.print(">>>>>>>");
    }
}
