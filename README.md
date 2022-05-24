# **Задача №1 Лучшая система налогообложения (ИП)**

## **Цель**:

1.Написать программу-помощника индивидуальному предпринимателю (далее - ИП), которая помогает ему выбрать лучшую систему
налогооблажения. ИП будет заносить суммы доходов и расходов, а программа будет выбирать для него лучшую систему
налогообложения из двух:

* УСН доходы - налог 6% от доходов;
* УСН доходы минус расходы - налог 15% от разницы доходов и расходов.

### *Пример*:
``` Пример 1
Возможные команды программы:
0 или выход: чтобы выйти из программы.
1: ввести доход.
2: ввести расход.
3: выбрать систему налогообложения.
4: округлить сумму налога до целого числа.

Введите доход:
20000.15
Текущие доходы в рублях: 20000.15 коп.

Введите расходы:
15000.94
Текущие расходы в рублях: 15000.94 коп.

Лучшая система налогообложения выбраная нами это:
'разница доходов и расходов - 15%', сумма налога составит в рублях: 749.88 коп., а на системе 'доходы - 6%' составит 
в рублях: 1200.01 коп. Экономия составит в рублях: 450.13 коп.

Округленная сумма налога 'разница доходов и расходов - 15%` составляет: 750 рублей.
Эта программа способна выбрать лучшую систему налогообложения.
```

### **Моя реализация**:

1. Реализация осуществлена в парадигме ООП.
2. Создал структуру классов:

* **Program** - отвечающий за запуск программы, путем инициирования метода *start()* (с инициированием внутри себя
  вспомогательного метода *printMenu()*);

#### Класс **Program**:
``` java
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
```

* **TaxService** - задающий логику расчета суммы налога через методы *calculateTaxSTS()* и *calculateTaxIME()*. Методы
  *setEarnings()* и *setExpenses()* позволяют внести доход и расход соответственно. Метод *BetterTaxSystem()*
  реализованный через ```void``` позволяет выбрать лучшую систему налогообложения. Метод *roundTax()* использующий
  ```BigDecimal``` позволяет получить корректно округленную сумму налога.

#### Класс **TaxService**:
``` java   
 public class TaxService {
    private double earnings;
    private double expenses;
    private double parse;
    private double taxSTS; // simplified tax system 6%
    private double taxIME; // income minus expenses 15%
    private BigDecimal taxSTSRound;
    private BigDecimal taxIMERound;

    public void setEarnings(String input) {
        parse = Double.parseDouble(input);
        earnings += parse;
    }

    public void setExpenses(String input) {
        parse = Double.parseDouble(input);
        expenses += parse;
    }

    public double calculateTaxSTS() {
        final var TAX_SIX_PERCENT = 0.06;
        taxSTS = earnings * TAX_SIX_PERCENT;
        if (taxSTS >= 0) {
            return taxSTS;
        } else {
            return 0;
        }
    }

    public double calculateTaxIME() {
        final var TAX_FIFTEEN_PERCENT = 0.15;
        taxIME = (earnings - expenses) * TAX_FIFTEEN_PERCENT;
        if (taxIME >= 0) {
            return taxIME;
        } else {
            return 0;
        }
    }

    public void BetterTaxSystem() {
        double temp;
        BigDecimal resultRound;
        if (taxSTS < taxIME) {
            temp = taxIME - taxSTS;
            taxSTSRound = BigDecimal.valueOf(taxSTS);
            taxIMERound = BigDecimal.valueOf(taxIME);
            resultRound = BigDecimal.valueOf(temp);
            System.out.println("'доходы - 6%', сумма налога составит в рублях: " +
                    taxSTSRound.setScale(2, RoundingMode.HALF_UP) +
                    " коп., а на системе 'разница доходов и расходов - 15%' составит в рублях: " +
                    taxIMERound.setScale(2, RoundingMode.HALF_UP) + " коп. Экономия составит в рублях: " +
                    resultRound.setScale(2, RoundingMode.HALF_UP) + " коп.");
        } else {
            temp = taxSTS - taxIME;
            taxSTSRound = BigDecimal.valueOf(taxSTS);
            taxIMERound = BigDecimal.valueOf(taxIME);
            resultRound = BigDecimal.valueOf(temp);
            System.out.println("'разница доходов и расходов - 15%', сумма налога составит в рублях: " +
                    taxIMERound.setScale(2, RoundingMode.HALF_UP) +
                    " коп., а на системе 'доходы - 6%' составит в рублях: " +
                    taxSTSRound.setScale(2, RoundingMode.HALF_UP) + " коп. Экономия составит в рублях: " +
                    resultRound.setScale(2, RoundingMode.HALF_UP) + " коп.");
        }
    }

    public void roundTax() {
        if (taxSTS < taxIME) {
            taxSTSRound = BigDecimal.valueOf(taxSTS);
            System.out.println(Utils.ANSI_GREEN + "Округленная сумма налога `доходы - 6%` составляет: " +
                    taxSTSRound.setScale(0, RoundingMode.HALF_UP) + " рублей." + Utils.ANSI_RESET);
        } else {
            taxIMERound = BigDecimal.valueOf(taxIME);
            System.out.println(Utils.ANSI_GREEN + "Округленная сумма налога 'разница доходов и расходов - 15%` " +
                    "составляет: " + taxIMERound.setScale(0, RoundingMode.HALF_UP) + " рублей." +
                    Utils.ANSI_RESET);
        }
    }

    public double getEarnings() {
        return earnings;
    }

    public double getExpenses() {
        return expenses;
    }
}
```

2. Использовал кодирование цвета текста (ANSI).

#### Класс **Utils**:
``` java
public class Utils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void printDelim() {
        System.out.println(ANSI_GREEN + "*********************************************" + ANSI_RESET);
    }
}
```

3. Использовал ```try-catch```, чтобы избежать падение программы в исключения.

#### Метод *main()* в классе **Main**:
``` java
public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        program.start();
    }
}
```

## *Вывод в консоль*:

* меню:
``` 
Эта программа способна выбрать лучшую систему налогообложения.
Возможные команды программы:
0 или выход: чтобы выйти из программы.
1: ввести доход.
2: ввести расход.
3: выбрать систему налогообложения.
4: округлить сумму налога до целого числа.
>>>>>>>
```