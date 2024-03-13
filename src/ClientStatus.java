import java.io.Serializable;

public interface ClientStatus extends Serializable {
    public int PaymentWithDiscount(int cash);
    public void ClientPerks();
    public double getDiscoint();
    public int getTarget();
    public void Congrats(String NameFamilyPatr);
    public  String  getStatusName();
}
