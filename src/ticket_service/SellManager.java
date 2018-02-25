package ticket_service;

import java.util.Scanner;

/**
 *
 * @author Hareesh Mathiyalagan
 */
public class SellManager {

    private String input;
    private Scanner scanner;
    private boolean gotEventTitle, gotSellerUsername, gotNumTickets;

    public void Sell() {
        input = "Not Return";
        gotEventTitle = false;
        gotSellerUsername = false;
        gotNumTickets = false;
        scanner = new Scanner(System.in);

        CreateDialogue();
    }

    private void CreateDialogue() {
        Output(true, "Enter return at any time to cancel operation.");
        while (!input.equals("return")) {
          
        }

        // Clean up and return to main loop.
        Output(true, "Exiting...");
    }

    private void Output(boolean newLine, String s) {
        if (newLine) {
            System.out.println("Sell Manager | " + s);
        } else {
            System.out.print("Sell Manager | " + s);
        }
    }
}
