import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends  People{
    private int id;
     public Scanner scanner = new Scanner(System.in);
    Admin (String newNameFamulyPatr, String newTelephoneNumber,String newEmail, String newPassword, int newCash ){
        super (newNameFamulyPatr, newTelephoneNumber, newEmail, newPassword, newCash);
    }
    public void SaveStatistic (String FileName,String sep)throws Exception{
        FileWriter fin= new FileWriter(FileName,true);
        fin.write(Main.CountOfSoldTickets+sep+Main.CountOfEarnedMoney+sep+Main.CountOfEarnedMoneyWithRent+sep+Main.CountOfVipClients
                +sep+Main.CountOfFriendClients+ sep+Main.CountOfCommonClients+sep+"ЭТО СТРОКА А НЕ \":C\""+sep);
        fin.close(); //...наверное
        super.Save(FileName,sep); //я офигею если это стработает (сохраняем личные данные админа!)
        //fin.close();
    }
    private void GodModOn(){
        String answer="начало";
        while (true){
            switch (answer) {
                case "начало":
                    System.out.println("Вы активировали режим бога...");
                    break;
                case "сеанс":
                    Main.GodOfSession();
                    break;
                case "кинотеатр":
                    Main.GodOfCinema();
                    break;
                case "зал":
                    Main.GodOfHall();
                    break;
                case "выход":
                    return;
                //break; //а вдруг!
                default:
                    System.out.println("Накосячили либо вы, либо java (плохо читает), повторите ввод");

            }
            Main.AdminGodMod();
            answer = scanner.nextLine();
            Main.ClearConsole();
            Main.ClearConsole();
        }
    }

    public void AdminAccount(){
        String answer="начало";
        while (true){
            switch (answer) {
                case "начало":
                    System.out.println("Добро пожаловать, админ!");
                    break;
                case "выручка":
                    System.out.println("Общая выручка за все сеансы: "+Main.CountOfEarnedMoney+" рублей");
                    break;
                case "билеты":
                    System.out.println("Общее количество проданных билетов: "+Main.CountOfSoldTickets+" штук");
                    break;
                case "аренда":
                    Main.ShowRentedSessions();
                    break;
                case "сеансы":
                    AdminView();
                    break;
                case "клиенты":
                    Main.ClientStatusStatistic();
                    break;
                case "статистика":
                    Main.HallStatistic();
                    break;
                case "купить":
                    super.Account();
                    break;
                case "редактор":
                    GodModOn();
                    break;
                case "всё":
                    Main.AdminShowAll();
                    break;
                case "выход":
                    return;
                //break; //а вдруг!    AdminShowAll()
                default:
                    System.out.println("Накосячили либо вы, либо java (плохо читает), повторите ввод");

            }
            Main.AdminAccount();
            answer = scanner.nextLine();
            Main.ClearConsole();
            Main.ClearConsole();
        }
    }
    private void AdminView(){
        Main.ShowSessions();
        System.out.println("Введите номер сеанса");
        int NumberOfSession=scanner.nextInt();
        NumberOfSession--;
        Main.SessionNum.get(NumberOfSession).getCinema().ViewHall();
        System.out.println("5 сек");
        Main.Pause(4999);

    }

}
