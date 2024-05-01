///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           Loan Payment & Interest Calculator
// Course:          CS200, Fall 2023
//
// Author:          Annie Zhao
// Email:           azhao37@wisc.edu
// Lecturer's Name: Jim Williams
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
// Amortization formula:
// https://www.vertex42.com/ExcelArticles/amortization-calculation.html
//
// Interest formula:
// https://www.bankrate.com/loans/personal-loans/how-to-calculate-loan-interest/#simple
//
///////////////////////////////// REFLECTION ///////////////////////////////////
//
// 1. Describe the problem you wrote the program to solve: This program is designed
// help the user determine what their payment amount should be per period. I chose
// this problem because it pertains to my current life, as I am encountering
// student loans.

// 2. What are the biggest challenges you encountered: One challenge I encountered
// was that the program would not end when the user entered "N". I didn't understand
// why it was wrong, which was why it was challenging. It did help me realize
// the importance of making sure my initialization of variables is correct
// though, as the problem was that I had initialized the String variable in my last
// method to the scnr input, which was incorrect.

// 3. What did you learn from this assignment: I learned how easy it is to just
// include all of my code in the main method, rather than figuring out how to
// add more methods to make the code more concise.
//////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

import java.util.Scanner;
import java.lang.Math;

/**
 * This class calculates the payment the user should make based on the user's
 * loan amount, rate per period, and number of periods.
 */

public class H8CustomApp {

    /**
     * This method prompts the user for inputs and calculates the loan payment amount
     * the user should make based on those inputs.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);

        double userLoanAmount = 0.0;
        double userRate = 0.0;
        double userPeriodAmount = 0.0;
        double paymentAmount = 0.0;
        boolean runAgain = false;

        String userYesOrNo = "";

        boolean isLoanAmountValid = false;
        boolean isRateValid = false;
        boolean isPeriodValid = false;

        System.out.println("Hello! Welcome to the loan payment calculator.");

        do {

            //Loan amount
            do {
                System.out.println("Please enter the current loan amount (ex: $200)" +
                        " without units:");

                if (scnr.hasNextDouble()) {
                    userLoanAmount = scnr.nextDouble();

                    if (validityOfInputs(userLoanAmount)) {
                        isLoanAmountValid = true;
                    } else {
                        isLoanAmountValid = false;
                    }


                } else {
                    System.out.println("Please enter a valid loan amount.");
                    isLoanAmountValid = false;

                    scnr.next();


                }
            } while (!isLoanAmountValid);

            scnr.nextLine();


            //Rate per period

            do {
                System.out.println("Please enter the rate per period (ex: 5.00): ");
                if (scnr.hasNextDouble()) {
                    userRate = scnr.nextDouble();

                    if (validityOfInputs(userRate)) {
                        isRateValid = true;
                    } else {
                        isRateValid = false;
                    }

                    userRate = userRate * 0.01;


                } else {
                    System.out.println("Please enter a valid percentage.");
                    scnr.next();

                }
            } while (!isRateValid);

            scnr.nextLine();

            //Number of periods
            do {
                System.out.println("Please enter the number of periods (ex: 10.0): ");
                if (scnr.hasNextDouble()) {
                    userPeriodAmount = scnr.nextDouble();
                    if (validityOfInputs(userPeriodAmount)) {
                        isPeriodValid = true;
                    } else {
                        isPeriodValid = false;
                    }
                } else {
                    System.out.println("Please enter a valid number.");
                    scnr.next();
                }

            } while (!isPeriodValid);

            scnr.nextLine();

            //Equations

            paymentAmount = (userRate * Math.pow((1 + userRate), userPeriodAmount)) /
                    (Math.pow((1 + userRate), userPeriodAmount) - 1);
            paymentAmount = userLoanAmount * paymentAmount;

            System.out.println("You should pay $" + Math.round(paymentAmount) + " every period");
            System.out.println("The total interest you would pay is: $" +
                    Math.round(calculateInterest(userLoanAmount, userRate, userPeriodAmount))
                    + ".");

            //Another round
            System.out.println("Would you like to run another calculation? (Y/N)");

            runAgain = determineRunAgain(scnr);

        } while (runAgain);

        System.out.println("Okay, thank you for using the calculator!");
    }

    /**
     * This method determines if the user's input is a positive, non-zero number.
     *
     * @param userInput The user's input.
     * @return isValid The boolean value that returns true if the userInput is greater than zero.
     */
    public static boolean validityOfInputs(double userInput) {
        boolean isValid = false;
        if (userInput <= 0.0) {
            System.out.println("Please enter a number greater than zero. ");
            isValid = false;
        } else if (userInput > 0.0){
            isValid = true;
        } else {
            isValid = false;
        }

        return isValid;
    }

    /**
     * This method calculates the total amount of interest the user would pay
     * based on inputs that the main method prompted the user for.
     *
     * @param principalAmt The double value of the user's inputted loan amount
     * @param rate The double value of the user's loan rate
     * @param term The double value of the user's loan period length
     * @return totalInterest The double value of the total interest the user will pay
     *                      based on the inputs.
     */
    public static double calculateInterest(double principalAmt, double rate, double term){

        rate = rate * 0.01;

        double totalInterest = principalAmt * rate * term;

        return totalInterest;
    }

    /**
     * This method determines, based on the user's input, if the
     * loop in the main method should run again.
     *
     * @param scnr An object of the Scanner class.
     * @return runAgain The boolean value that determines if the while loops should
     *                  run again in the main method.
     */
    public static boolean determineRunAgain(Scanner scnr){
        String userYesOrNo = "";
        boolean runAgain = false;

        userYesOrNo = scnr.next().toUpperCase();
        scnr.nextLine();

        if (userYesOrNo.equals("N")) {
            runAgain = false;
        } else if (userYesOrNo.equals("Y")){
            runAgain = true;
        } else {
            runAgain = false;
        }


        return runAgain;
    }

}
