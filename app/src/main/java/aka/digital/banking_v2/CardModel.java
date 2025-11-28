package aka.digital.banking_v2;

public class CardModel {
    public String type;
    public String balance;
    public String number;

    public CardModel(String type, String balance, String number){
        this.type = type;
        this.balance = balance;
        this.number = number;
    }
}
