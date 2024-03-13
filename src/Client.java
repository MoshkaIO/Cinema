import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

public class Client extends  People implements Serializable {
    private transient Scanner scanner = new Scanner(System.in);
    Client(String newNameFamulyPatr, String newTelephoneNumber,String newEmail, String newPassword, int newCash,ClientStatus NewStatus ){
        super (newNameFamulyPatr, newTelephoneNumber, newEmail, newPassword, newCash, NewStatus);

    }
    Client(String newNameFamulyPatr, String newTelephoneNumber,String newEmail, String newPassword, int newCash ){
        super (newNameFamulyPatr, newTelephoneNumber, newEmail, newPassword, newCash, new CommonClient());

    }
    Client (Client newClient){
        super (newClient.NameFamilyPatr, newClient.TelephoneNumber, newClient.getEmail(), newClient.getPassword(), newClient.getCash(), newClient.getStatus());
    }
    public void SaveSer (ObjectOutputStream objectOutputStream ) throws Exception{
        Client ClientToSave = new Client( NameFamilyPatr,TelephoneNumber,Email,Password,Cash,Status);
        super.SaveSer(objectOutputStream, ClientToSave);
    }

}