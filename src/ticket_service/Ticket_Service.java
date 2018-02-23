package ticket_service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 *
 * @author Sean
 */
public class Ticket_Service {

    static String helpString = "Login: Login to a specific user account\n"
            + "Logout: Logout of the current account\nCreate: Create a new user"
            + "account (can only be done by an admin)\nDelete: Remove "
            + "a user account (can only be done by an admin)\nSell: "
            + "Sell a ticket or tickets to an event\nBuy: Purchase a ticket or "
            + "tickets to an event\nRefund: Issue a credit to a buyer from a "
            + "seller's account\nAddcredit: Add credit to an account (can only "
            + "be done by an admin)\nExit: Closes the application";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Account> accountsList = new ArrayList<>();
        Hashtable<String, Integer> accountsHash = new Hashtable<String, Integer>();

        Account currentAccount = null;
        System.out.println("Ticket Selling System\n"
                + "These are the valid input commands that you can enter. At "
                + "any point, enter help to see this list again (commands are "
                + "not case sensitive).\n\n" + helpString + "\n");

        try (BufferedReader br = new BufferedReader(new FileReader("Current User Accounts.txt"))) {
            String line = br.readLine();
            if (!line.equals("END                         ")) {
                Account newAccount = new Account(line);
                accountsList.add(newAccount);
                accountsHash.put(newAccount.username, accountsList.size() - 1);
            } else {
                br.close();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        String input = "Not Exit";
        while (!input.equals("exit")) {
            System.out.print("Please enter a command: ");
            input = scanner.nextLine().toLowerCase();
            switch (input) {
                case "login":
                    boolean attemptingLogin = true;

                    while (attemptingLogin) {
                        System.out.print("Enter your username: ");
                        input = scanner.nextLine();
                        System.out.print("Enter your password: ");
                        String password = scanner.nextLine();

                        if (accountsHash.containsKey(input) && 
                                accountsList.get(accountsHash.get(input)).getPassword().equals(password)) {
                            currentAccount = accountsList.get(accountsHash.get(input));
                            attemptingLogin = false;
                            System.out.println("Welcome " + currentAccount.getUsername());
                        } else {
                            boolean validInput = false;
                            while (!validInput) {
                                System.out.print("Invalid user credentials. Would you like to try again (y/n): ");
                                input = scanner.nextLine().toLowerCase();
                                switch (input) {
                                    case "y":
                                        validInput = true;
                                        attemptingLogin = true;
                                        break;
                                    case "n":
                                        validInput = true;
                                        attemptingLogin = false;
                                        break;
                                }
                            }
                        }
                    }
                    break;
                case "logout":
                    break;
                case "create":
                    break;
                case "delete":
                    break;
                case "sell":
                    break;
                case "buy":
                    break;
                case "refund":
                    break;
                case "addcredit":
                    break;
                default:
                    System.out.println("Sorry. You have not entered a valid "
                            + "input. Please try again. If you would like to "
                            + "see the list of commands, enter \"help\" "
                            + "(without quotations)");
                    break;
            }
        }
    }

}
