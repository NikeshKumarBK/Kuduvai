package in.kuduvai.cachii.kuduvai;

/**
 * Created by Cachiii on 7/26/2016.
 */
public class CustomerListDataProvider {

    private String date;
    private String can_count;
    private String paid;
    private String due;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCan_count() {
        return can_count;
    }

    public void setCan_count(String can_count) {
        this.can_count = can_count;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public CustomerListDataProvider(String date,String can_count,String paid,String due){

        this.date=date;
        this.can_count=can_count;
        this.paid=paid;
        this.due=due;
    }
}
