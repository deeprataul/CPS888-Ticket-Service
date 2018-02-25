package ticket_service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

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

    public BuyManager() {
        selectedTicketsList = new ArrayList<>();
    }

    public void Buy(ArrayList<AvailableTicket> list) {
        input = "Not Return";
        gotEventTitle = false;
        gotSellerUsername = false;
        gotNumTickets = false;
        availableTicketsList = list;
        scanner = new Scanner(System.in);
        CreateDialogue();
    }

    private void CreateDialogue() {
        Output(true, "Enter return at any time to cancel operation.");
        while (!input.equals("return")) {
            if (gotSellerUsername) {
                if (gotEventTitle) {
                    if (gotNumTickets) {
                        Output(true, "Proceed to buy.");
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
                        + " | Price: " + t.GetTicketPrice());
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
        // TODO: Check if admin or not
        if (Integer.parseInt(input) <= selectedTicket.GetNumberTickets()) {
            return true;
        } else {
            Output(true, "The seller does not have enough tickets, try again.");
            return false;
        }
    }

    private void Output(boolean newLine, String s) {
        if (newLine) {
            System.out.println("Buy Manager | " + s);
        } else {
            System.out.print("Buy Manager | " + s);
        }
    }
}
