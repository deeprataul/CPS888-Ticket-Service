package ticket_service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ticket_service.Account.UserType;
import static ticket_service.SellManager.padRight;

/**
 *
 * @author Hareesh Mathiyalagan
 */
public class BuyManager {

    private String input;
    private Scanner scanner;
    private boolean gotEventTitle, gotSellerUsername, gotNumTickets;
    private ArrayList<AvailableTicket> availableTicketsList;
    private ArrayList<AvailableTicket> selectedTicketsList;
    private AvailableTicket selectedTicket;
    private Account myAccount;
    private int numberToPurchase;
    private double totalCost;
    private DecimalFormat df;

    public BuyManager() {
        selectedTicketsList = new ArrayList<>();
        df = new DecimalFormat("####0.00");
    }

    public void Buy(ArrayList<AvailableTicket> list, Account currentAccount) {
        input = "Not Return";
        gotEventTitle = false;
        gotSellerUsername = false;
        gotNumTickets = false;
        availableTicketsList = list;
        scanner = new Scanner(System.in);
        myAccount = currentAccount;
        numberToPurchase = 0;
        totalCost = 0.00;
        CreateDialogue();
    }

    private void CreateDialogue() {
        if (myAccount.getType() != UserType.SellStandard) {
            Output(true, "Enter return at any time to cancel operation.");
            while (!input.equals("return")) {
                if (gotSellerUsername) {
                    if (gotEventTitle) {
                        if (gotNumTickets) {
                            if (Confirm()) {
                                input = "return";
                            }
                        } else {
                            gotNumTickets = ParseNumTickets();
                        }
                    } else {
                        gotEventTitle = ParseEventTitle();
                    }
                } else {
                    gotSellerUsername = ParseSellerUsername();
                }
            }
        } else {
            Output(true, "Your account does not have access to buying tickets.");
        }

        // Clean up and return to main loop.
        Output(true, "Exiting...");
    }

    private boolean ParseSellerUsername() {
        Output(false, "Enter a seller's username to check their inventory: ");
        input = scanner.nextLine();
        // TODO: Parse Input    

        boolean exists = false;
        for (AvailableTicket t : availableTicketsList) {
            if (t.GetSellerUsername().trim().equals(input)) {
                selectedTicketsList.add(t);
                Output(true, "Available Event: " + t.GetEventName());
                exists = true;
            }
        }

        if (exists) {
            return true;
        } else {
            Output(true, "Seller username does not exist, try again.");
            return false;
        }
    }

    private boolean ParseEventTitle() {
        Output(false, "Enter the title of the event you'd like: ");
        input = scanner.nextLine();
        // TODO: Parse Input     

        for (AvailableTicket t : selectedTicketsList) {
            if (t.GetEventName().trim().equals(input)) {
                selectedTicket = t;
                Output(true, "Number of tickets available: " + t.GetNumberTickets()
                        + " | Price: " + df.format(t.GetTicketPrice()));
                return true;
            }
        }

        Output(true, "The selected event does not exist, try again.");
        return false;
    }

    private boolean ParseNumTickets() {
        Output(false, "Enter the number of tickets you want to purchase: ");
        input = scanner.nextLine().toLowerCase();
        // TODO: Parse Input
        // TODO: Check if user has enough money;

        if (myAccount.getType() != UserType.Admin) {
            if (Integer.parseInt(input) > 4) {
                Output(true, "You are only allowed to buy at most 4 tickets.");
            }
        }

        if (Integer.parseInt(input) <= selectedTicket.GetNumberTickets()) {
            numberToPurchase = Integer.parseInt(input);
            return true;
        } else {
            Output(true, "The seller does not have enough tickets, try again.");
            return false;
        }
    }

    private boolean Confirm() {
        totalCost = selectedTicket.GetTicketPrice() * numberToPurchase;

        Output(true, "The total cost is $" + df.format(totalCost) + " at a price of $"
                + df.format(selectedTicket.GetTicketPrice()) + " per ticket.");
        Output(false, "Enter 'yes' to purchase or 'no' to return: ");
        input = scanner.nextLine().toLowerCase();
        // TODO: Parse Input

        if (input.equals("yes")) {
            WriteToDailyTransactionsFile(selectedTicket, "04");
            Output(true, "You have successfully purchased the tickets!");
        } else if (input.equals("no")) {
            Output(true, "You have cancelled the transaction.");
        }

        return true;
    }

    private void Output(boolean newLine, String s) {
        if (newLine) {
            System.out.println("BUY MANAGER | " + s);
        } else {
            System.out.print("BUY MANAGER | " + s);
        }
    }

    public static void WriteToDailyTransactionsFile(AvailableTicket t, String code) {
        PrintWriter writer;
        try {
            writer = new PrintWriter("Daily Transactions File.txt", "UTF-8");
            String transaction = code;
            transaction += " ";
            transaction += padRight(t.GetEventName(), 19);
            transaction += " ";
            transaction += padRight(t.GetSellerUsername(), 13);
            transaction += " ";
            transaction += padRight("" + t.GetNumberTickets(), 3);
            transaction += " ";
            transaction += padRight("" + (int) t.GetTicketPrice(), 6);
            writer.println(transaction);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(SellManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
