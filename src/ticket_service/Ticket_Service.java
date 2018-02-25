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

    static String helpString = "Login: Login to a specific user account\nLogout: Logout of the "
            + "current account\nCreate: Create a new user account (can only be done by an admin)\n"
            + "Delete: Remove a user account (can only be done by an admin)\nSell: Sell a ticket "
            + "or tickets to an event\nBuy: Purchase a ticket or tickets to an event\nRefund: "
            + "Issue a credit to a buyer from a seller's account\nAddcredit: Add credit to an "
            + "account (can only be done by an admin)\nExit: Closes the application";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Account> accountsList = new ArrayList<>();
        Hashtable<String, Integer> accountsHash = new Hashtable<String, Integer>();
        
        ArrayList<AvailableTicket> availableTicketsList = new ArrayList<>();
        BuyManager buyManager = new BuyManager();
        SellManager sellManager = new SellManager();
        
        Account currentAccount = null;
        System.out.println("Ticket Selling System\n"
                + "These are the valid input commands that you can enter. At any point, enter help "
                + "to see this list again (commands are not case sensitive).\n\n" + helpString + "\n");

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

        try (BufferedReader br = new BufferedReader(new FileReader("Available Tickets File.txt"))) {
            String line = br.readLine();
            if (!line.equals("END")) {
                AvailableTicket ticket = new AvailableTicket(line);
                availableTicketsList.add(ticket);
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
                    if (currentAccount == null) {
                        boolean attemptingLogin = true;

                        while (attemptingLogin) {
                            System.out.print("Enter your username: ");
                            input = scanner.nextLine();
                            System.out.print("Enter your password: ");
                            String password = scanner.nextLine();

                            if (accountsHash.containsKey(input)
                                    && accountsList.get(accountsHash.get(input)).getPassword().equals(password)) {
                                currentAccount = accountsList.get(accountsHash.get(input));
                                attemptingLogin = false;
                                System.out.println("Welcome " + currentAccount.getUsername());
                            } else {
                                boolean validInput = false;
                                while (!validInput) {
                                    System.out.print("Invalid user credentials. Would you like to " +
                                            "try again (y/n): ");
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
                    } else {
                        System.out.println("You are currently logged in as "
                                + currentAccount.getUsername() + ". Please logout in order to "
                                + "login as a different user");
                    }
                    break;
                case "logout":
                    if (currentAccount != null) {
                        System.out.println(currentAccount.getUsername()
                                + "successfully logged out.");
                        currentAccount = null;
                    } else {
                        System.out.println("You must be logged in to use the logout command.");
                    }
                    break;
                case "create":
                    if (currentAccount != null) {
                        //Write the create code here
                    } else {
                        System.out.println("You must be logged in to use the create command.");
                    }
                    break;
                case "delete":
                    if (currentAccount != null) {
                        //Write the delete code here
                    } else {
                        System.out.println("You must be logged in to use the delete command.");
                    }
                    break;
                case "sell":
                    if (currentAccount != null) {
                        sellManager.Sell(availableTicketsList, currentAccount);
                    } else {
                        System.out.println("You must be logged in to use the sell command.");
                    }
                    break;
                case "buy":
                    if (currentAccount != null) {
                        buyManager.Buy(availableTicketsList, currentAccount);
                    } else {
                        System.out.println("You must be logged in to use the buy command.");
                    }
                    break;
                case "refund":
                    if (currentAccount != null) {
                        //Write the refund code here
                    } else {
                        System.out.println("You must be logged in to use the refund command.");
                    }
                    break;
                case "addcredit":
                    if (currentAccount != null) {
                        //Write the addcredit code here
                    } else {
                        System.out.println("You must be logged in to use the addcredit command.");
                    }
                    break;
                case "help":
                    System.out.println(helpString + "\n");
                    break;
                case "exit":
                    System.out.println("Good bye");
                default:
                    System.out.println("Sorry. You have not entered a valid input. Please try "
                            + "again. If you would like to see the list of commands, enter " +
                            "\"help\" (without quotations)\n");
                    break;
            }
        }
    }
}
