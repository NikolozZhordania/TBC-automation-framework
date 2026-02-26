package ge.tbc.testautomation.tbcbankapp.ui.utils;

import ge.tbc.testautomation.tbcbankapp.ui.pages.ConsumerLoanPage;

import java.util.List;
import java.util.stream.Collectors;

public class ConsumerLoanHelper {

    private final ConsumerLoanPage consumerLoanPage;

    public ConsumerLoanHelper(ConsumerLoanPage consumerLoanPage) {
        this.consumerLoanPage = consumerLoanPage;
    }

    public double parsePrincipal() {
        return Double.parseDouble(
                consumerLoanPage.loanAmountIndicator
                        .textContent()
                        .replaceAll("[₾,\\s\u00A0]", "")
                        .trim()
        );
    }

    public int parseTermMonths() {
        return Integer.parseInt(
                consumerLoanPage.monthCountIndicator
                        .textContent()
                        .replaceAll("[^\\d]", "")
                        .trim()
        );
    }

    public double parseNominalRate() {
        return Double.parseDouble(
                consumerLoanPage.nominalPercentageRate
                        .textContent()
                        .replaceAll("[^\\d.,]", "")
                        .replace(",", ".")
                        .trim()
        ) / 100;
    }

    public double parseSitePayment() {
        List<String> spanTexts = consumerLoanPage.estimatedMonthlyPaymentContainer
                .locator("span")
                .allTextContents();
        return Double.parseDouble(
                spanTexts.stream()
                        .collect(Collectors.joining())
                        .replaceAll("[₾,\\s\u00A0]", "")
                        .trim()
        );
    }

    public double calculateAnnuityPayment(double principal, int termMonths, double nominalRate) {
        double monthlyRate = nominalRate / 12;
        double payment = (principal * monthlyRate * Math.pow(1 + monthlyRate, termMonths)) /
                (Math.pow(1 + monthlyRate, termMonths) - 1);
        return Math.round(payment * 100.0) / 100.0;
    }
}