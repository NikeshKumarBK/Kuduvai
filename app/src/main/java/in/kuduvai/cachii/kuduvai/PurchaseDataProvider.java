package in.kuduvai.cachii.kuduvai;

/**
 * Created by Cachiii on 7/26/2016.
 */
public class PurchaseDataProvider {

    private String date;
    private String can_intake;
    private String can_given;
    private String amount_paid;
    private String balance;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCan_intake() {
        return can_intake;
    }

    public void setCan_intake(String can_intake) {
        this.can_intake = can_intake;
    }

    public String getCan_given() {
        return can_given;
    }

    public void setCan_given(String can_given) {
        this.can_given = can_given;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public PurchaseDataProvider(String date,String can_intake,String can_given,String amount_paid,String balance){

        this.date=date;
        this.can_intake=can_intake;
        this.can_given=can_given;
        this.amount_paid=amount_paid;
        this.balance=balance;

    }

}
