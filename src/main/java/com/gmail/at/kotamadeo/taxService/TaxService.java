package com.gmail.at.kotamadeo.taxService;

import com.gmail.at.kotamadeo.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
