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
        //An arraylist for the list of accounts
        //ArrayList<Account> accountsList = new ArrayList<>();
        
        //A hashtable that stores the username and the Account itself
        Hashtable<String, Account> accountsHash = new Hashtable<String, Account>();
        
        ArrayList<AvailableTicket> availableTicketsList = new ArrayList<>();
        BuyManager buyManager = new BuyManager();
        SellManager sellManager = new SellManager();
        
        //currentAccount is the account of the current user or null if there is no user logged in
        Account currentAccount = null;
        System.out.println("Ticket Selling System\n"
                + "These are the valid input commands that you can enter. At any point, enter help "
                + "to see this list again (commands are not case sensitive).\n\n" + helpString + "\n");

        //Read in the accounts from the Current User Accounts file and store each in
        //the accountsHash hash table
        try (BufferedReader br = new BufferedReader(new FileReader("Current User Accounts.txt"))) {
            String line = br.readLine();
            if (!line.equals("END                         ")) {
                Account newAccount = new Account(line);
                //accountsList.add(newAccount);
                accountsHash.put(newAccount.getUsername(), newAccount);
            } else {
                br.close();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        //Read in the available tickets from the "Available Tickets File" file and store each in
        //the accountsHash hash table
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
        
        //Read in input from the user and handle it accordingly
        Scanner scanner = new Scanner(System.in);
        String input = "Not Exit";
        while (!input.equals("exit")) {
            System.out.print("Please enter a command: ");
            input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "login":
                    //If there is no account currently logged in, continue, else display a prompt
                    if (currentAccount == null) {
                        boolean attemptingLogin = true;

                        //Continue to allow the user to attempt to login while they still want to
                        while (attemptingLogin) {
                            //Read in username and password
                            System.out.print("Enter your username: ");
                            input = scanner.nextLine();
                            System.out.print("Enter your password: ");
                            String password = scanner.nextLine();

                            //Check if the username exists and if it does, check if the password
                            //is correct for that user. If yes, log them in
                            if (accountsHash.containsKey(input)
                                    && accountsHash.get(input).getPassword().equals(password)){
                                    //&& accountsList.get(accountsHash.get(input)).getPassword().equals(password)) {
                                currentAccount = accountsHash.get(input);//accountsList.get(accountsHash.get(input));
                                attemptingLogin = false;
                                System.out.println("Welcome " + currentAccount.getUsername());
                            } else {
                                //Invalid username or password. Ask them if the want to try again
                                //If y then let them try again. If n then go back to the main menu
                                //Loop until you get either y or n
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
                    //If there is an account currently logged in, log them out
                    //If not display a prompt saying you must be logged in to logout
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
                    //Display the helpString
                    System.out.println(helpString + "\n");
                    break;
                case "exit":
                    //Display a prompt and exit
                    System.out.println("Good bye");
                default:
                    //Display a prompt letting them know invalid input was entered
                    System.out.println("Sorry. You have not entered a valid input. Please try "
                            + "again. If you would like to see the list of commands, enter " +
                            "\"help\" (without quotations)\n");
                    break;
            }
        }
    }
}
