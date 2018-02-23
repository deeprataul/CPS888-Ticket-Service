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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

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
