package ticket_service;

/**
 *
 * @author Hareesh Mathiyalagan
 */
public class AvailableTicket {

    private String eventName;
    private String sellerUsername;
    private int numberTickets;
    private double ticketPrice;

    public AvailableTicket(String event, String seller, int num, double price) {
        this.eventName = event;
        this.sellerUsername = seller;
        this.numberTickets = num;
        this.ticketPrice = price;
    }

    public AvailableTicket(String userAccountsFileLine) {
        if (userAccountsFileLine.length() == 45) {
            eventName = userAccountsFileLine.substring(0, 19);
            sellerUsername = userAccountsFileLine.substring(20, 33);
            numberTickets = Integer.parseInt(userAccountsFileLine.substring(34, 37));
            ticketPrice = Double.parseDouble(userAccountsFileLine.substring(38, 44));
        }
    }

    public String GetEventName() {
        return eventName;
    }

    public String GetSellerUsername() {
        return sellerUsername;
    }

    public int GetNumberTickets() {
        return numberTickets;
    }

    public double GetTicketPrice() {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return "\n      sellerUsername: " + sellerUsername
                + "\n       eventName: " + eventName
                + "\n       numberTickets: " + numberTickets
                + "\n       ticketPrice: " + ticketPrice;
    }
}
