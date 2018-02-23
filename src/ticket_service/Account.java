package ticket_service;

/**
 *
 * @author Sean
 */
public class Account {
    public enum UserType{
        Admin,
        FullStandard,
        BuyStandard,
        SellStandard
    }
    
    String username;
    String password;    
    UserType type;
    float credit;

    public Account(String username, String password, UserType type, float credit) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.credit = credit;
    }
    public Account(String userAccountsFileLine) {
        if (userAccountsFileLine.length() == 37){
            String[] data = userAccountsFileLine.split(" +");
            username = data[0];
            credit = Float.valueOf(data[2]);
            this.password = data[3];
            switch(data[1]){
                case "AA":
                    type = UserType.Admin;
                    break;
                case "FS":
                    type = UserType.FullStandard;
                    break;
                case "BS":
                    type = UserType.BuyStandard;
                    break;
                case "SS":
                    type = UserType.SellStandard;
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nPassword: " + password + "\nType: " + type + "\nAvailable Credit: " + credit;
    }
    
    
}
