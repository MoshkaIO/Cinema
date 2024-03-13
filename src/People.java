import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public  class People implements Serializable {
    protected static final long serialVersionUID = 1L;
    protected  String NameFamilyPatr;
    protected String TelephoneNumber;
    protected  String Email;
    protected  int Cash = 0;
    protected String Password;
    protected  transient ArrayList<Place> BoughtPlaces = new ArrayList<>();
    protected transient ArrayList<Hall> RentedHalls = new ArrayList<>();
    protected ClientStatus Status;
    private transient Scanner scanner = new Scanner(System.in);
    People  (String newNameFamulyPatr, String newTelephoneNumber,String newEmail, String newPassword, int newCash, ClientStatus NewStatus  ){
        this( newNameFamulyPatr,  newTelephoneNumber, newEmail,  newPassword,  newCash);
        this.Status= NewStatus;
        BoughtPlaces=new ArrayList<>(Status.getTarget());
    }
    People (String newNameFamulyPatr, String newTelephoneNumber,String newEmail, String newPassword, int newCash){
        NameFamilyPatr=newNameFamulyPatr;
        TelephoneNumber=newTelephoneNumber;
        Email=newEmail;
        CashFillUp(newCash);
        Password=newPassword;
        this.Status=new CommonClient();
        Main.CountOfCommonClients++;

    }
    public void ShowInf(){
        System.out.println("Имя пользователя:  "+NameFamilyPatr+" , телефон: "+TelephoneNumber+" , почта: "+Email);
        System.out.println("пароль (шепотом:)  "+Password+" , статус: "+Status.getStatusName());
    }
    public void Save (FileWriter fin,char sep)throws Exception{
       // FileWriter fin;
        //fin = new FileWriter("Clients.txt",true);
        fin.write(NameFamilyPatr+sep+TelephoneNumber+sep+Email+sep+Password+sep+Cash+sep+Status.getStatusName()+"\n");
        fin.close();
    }
    public void Save (String FileName,String sep)throws Exception{
         FileWriter fin;
        fin = new FileWriter(FileName,true);
        fin.write(NameFamilyPatr+sep+TelephoneNumber+sep+Email+sep+Password+sep+Cash+sep+Status.getStatusName()+"\n");
        fin.close();
    }
    public void SaveSer (ObjectOutputStream objectOutputStream, People PeopleToSave ) throws Exception{
       // FileOutputStream outputStream = new FileOutputStream(FileName);
       // ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(PeopleToSave);

    }
    public People ReadSer(ObjectInputStream objectInputStream ) throws Exception{
       // FileInputStream fileInputStream = new FileInputStream( FileName);
       // ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        People ReadPeople = (People) objectInputStream.readObject();
        return ReadPeople;

    }

    public void Account() {

        String answer = "начало";
        while (true) {
            switch (answer) {
                case "начало":
                    System.out.println("Добро пожаловать, " + NameFamilyPatr + " !");
                    break;
                case "сеансы":
                    System.out.println("Подгружаем сеансы...");
                    Main.ShowSessions();
                    ChoiseOfSessionMenu();
                    break;

                case "по времени":
                    System.out.println("Подгружаем сеансы...");
                    if (Main.ShowSessionsWithTime()) ;
                    ChoiseOfSessionMenu();
                    break;
                case "по стоимости":
                    System.out.println("Подгружаем сеансы...");
                    if (Main.ShowSessionsWithCash())
                        ChoiseOfSessionMenu();
                    //  SearchSessions();
                    break;
                case "по названию":
                    System.out.println("Подгружаем сеансы...");
                    if (Main.ShowSessionsWithNameOfFilm())
                        ChoiseOfSessionMenu();
                    break;
                case "пополнить":
                    NewCashMenu();
                    break;
                case "история":
                    BoughtTicketsMenu();
                    break;
                case "выход":
                    return;
                //break; //а вдруг!
                default:
                    System.out.println("Накосячили либо вы, либо java (плохо читает), повторите ввод");

            }
            Main.ClientAccount();
            answer = Main.scanner.nextLine();
            Main.ClearConsole();
        }
    }

    protected  void ChoiseOfSessionMenu() {
        System.out.println("Выберите номер сеанса. Чтобы выйти введите \"0\"");
        System.out.println("При вводе некорректной цифры вам может быть предложен сеанс, не удовлетворяющий вашему требованию");
        int ChoiceOfSession;
        ChoiceOfSession = scanner.nextInt();
        ChoiceOfSession--;
        if ((ChoiceOfSession < 0) || (ChoiceOfSession > Main.SessionNum.size()))
            return;
        BuyTicketMenu(ChoiceOfSession);
    }
    private  void BuyTicketMenu(int ChoiceOfSession) {
        Main.ClearConsole();
        String answer = "начало";
        while (true) {
            switch (answer) {
                case "начало":
                    System.out.println("Добро пожаловать, " + NameFamilyPatr + " !");
                    break;
                case "купить":
                    System.out.println("Начинаем процедуру покупки билета...");
                    TryToBuyTicket(ChoiceOfSession);
                    break;
                case "арендовать":
                    System.out.println("Начинаем процедуру аренды зала...");
                    TryToRentHall(ChoiceOfSession);
                    break;
                case "выход":
                    return;
                //break; //а вдруг!
                default:
                    System.out.println("Накосячили либо вы, либо java (плохо читает), повторите ввод");
            }
            Main.BuyTicketMenu(ChoiceOfSession,getCash(),Status.getDiscoint());
            answer = Main.scanner.nextLine();
            Main.ClearConsole();

        }
    }

    private  void TryToBuyTicket(int ChoiceOfSession) {
        Main.SessionNum.get(ChoiceOfSession).getCinema().ViewHall(Status.getDiscoint());
        Status.ClientPerks();
        if (Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().isRented()){
            System.out.println("К сожалению, зал был арендован!");
            Main.Pause(1500);
            return;
        }
        System.out.println("Введите номер ряда, в котором вы хотите приобрести билет");
        int ChoiceOfRow;
        ChoiceOfRow = scanner.nextInt();
        ChoiceOfRow--;
        if ((ChoiceOfRow < 0) || (ChoiceOfRow > Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().getCountOfRows())) {
            System.out.println("Ну и ну, вы попытались выбрать несуществующий ряд... ");
            Main.Pause(4000);
            return;
        }
        System.out.println("Введите номер места, на которое хотите приобрести билет");
        int ChoiceOfPlace;
        ChoiceOfPlace = scanner.nextInt();
        ChoiceOfPlace--;
        Main.ClearConsole();
        if ((ChoiceOfPlace < 0) || (ChoiceOfPlace > Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().getCountOfPlacesInRow())) {
            System.out.println("Ну и ну, вы попытались выбрать несуществующее место в ряду... Мы закрываем это меню через 4 секунды! ");
            Main.Pause(4000);
            return;
        }

        if (Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().getPlaceInHall()[ChoiceOfRow][ChoiceOfPlace].getIsBought()) {
            System.out.println("К сожалению, место занято");
            Main.Pause(1500);
            return;
        }
        if (Cash < Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().getPlaceInHall()[ChoiceOfRow][ChoiceOfPlace].getCost()*Status.getDiscoint()) {
            System.out.println("Вам не хватает денег, извините!");
            Main.Pause(1500);
            return;
        }


        Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().getPlaceInHall()[ChoiceOfRow][ChoiceOfPlace].BuyingPlace(Email);
        Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().UpEarnedCash((int) (Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().getPlaceInHall()[ChoiceOfRow][ChoiceOfPlace].getCost()*Status.getDiscoint()));
        Cash -= Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().getPlaceInHall()[ChoiceOfRow][ChoiceOfPlace].getCost()*Status.getDiscoint();
        Main.CountOfEarnedMoney += Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().getPlaceInHall()[ChoiceOfRow][ChoiceOfPlace].getCost()*Status.getDiscoint();
        Main.CountOfSoldTickets++;
        BoughtPlaces.add( Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().getPlaceInHall()[ChoiceOfRow][ChoiceOfPlace]);
        System.out.println("Покупка произведена успешно!");
        UpdateStatus();
        Main.Pause(2500);
    }
    private void UpdateStatus(){
        switch (BoughtPlaces.size()) {
            case FriendlyClient.Target:
                Status= new FriendlyClient();
                Status.Congrats(NameFamilyPatr);
                Main.CountOfFriendClients++;
                Main.CountOfCommonClients--;
                Main.Pause(1500);
                break;
            case VipClient.Target:
                Status= new VipClient();
                Status.Congrats(NameFamilyPatr);
                Main.CountOfVipClients++;
                Main.CountOfFriendClients--;
                Main.Pause(1500);
                break;
            //break; //а вдруг!
            default:
                System.out.println("Вы совершили покупку №"+BoughtPlaces.size());
                System.out.println("Изменений в вашем стутусе пока нет...");
        }
    }
    private  void TryToRentHall(int ChoiceOfSession){
        Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().ViewTableOfPlaces();
        if (Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().CheckOfOpportunityToRent()==false){
            System.out.println("К сожалению, арендовать зал на этот сеанс невозможно: часть билетов уже куплена");
            Main.Pause(1500);
            return;
        }
        if (Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().CostOfRent()>getCash()){
            System.out.println("К сожалению, арендовать зал на этот сеанс невозможно: у вас недостаточно средств");
            Main.Pause(1500);
            return;
        }
        Main.CountOfEarnedMoneyWithRent+=Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().CostOfRent();
        Cash-=Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().CostOfRent();
        Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().RentingHall(Email);
        Main.CountOfEarnedMoneyWithRent+=Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall().CostOfRent();
        RentedHalls.add( Main.SessionNum.get(ChoiceOfSession).getCinema().getOneHall());
        System.out.println("Аренда произведена успешно!");
        if (Status.getStatusName().equals("Посетитель")){
            Status= new VipClient();
            Status.Congrats(NameFamilyPatr);
            Main.CountOfVipClients++;
            Main.CountOfCommonClients--;
            Main.Pause(1500);

        }
        if (Status.getStatusName().equals("Друг сети")){
            Status= new VipClient();
            Status.Congrats(NameFamilyPatr);
            Main.CountOfVipClients++;
            Main.CountOfFriendClients--;
            Main.Pause(1500);
        }

        Main.Pause(1500);

    }
    private  void NewCashMenu() {
        Main.ClearConsole();
        System.out.println(NameFamilyPatr + ", на вашем счету " + Cash + " рублей");
        System.out.print("Акция невиданной щедрости, введите количество денег, которые хотите положить: ");
        int newCash = scanner.nextInt();
        if (newCash < 0) {
            System.out.print("ТЫ ЗАБАНЕН !1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            Main.Pause(3500);
            Main.ClearConsole();
            return;
        }
        CashFillUp(newCash);
    }
    private void BoughtTicketsMenu() {
        Main.ClearConsole();
        String answer = "начало";
        while (true) {
            switch (answer) {
                case "начало":
                    System.out.println("История покупок");
                    break;
                case "билеты":
                    System.out.println("Загружаем купленные билеты...");
                    //TryToBuyTicket(ChoiceOfSession);
                    ShowBougthTickets();
                    break;
                case "залы":
                    System.out.println("Загружаем арендованные залы");
                    ShowRentedHalls();
                    break;
                case "выход":
                    return;
                //break; //а вдруг!
                default:
                    System.out.println("Накосячили либо вы, либо java (плохо читает), повторите ввод");
            }
            Main.BoughtTicketsMenu();
            answer = Main.scanner.nextLine();
            Main.ClearConsole();

        }
    }
    private void ShowBougthTickets (){
        for (int i=0; i<BoughtPlaces.size(); i++){
            BoughtPlaces.get(i).ShowInf();
            System.out.println("--------------------------");
        }
        Main.Pause(2000);
    }
    private void ShowRentedHalls(){
        for (int i=0; i<RentedHalls.size();i++) {
            RentedHalls.get(i).ViewTableOfPlaces();
            Main.SessionNum.get(RentedHalls.get(i).getNumberOfSession()).showInf();
            System.out.println("--------------------------");
        }
        Main.Pause(2000);
    }
    protected  void CashFillUp(int newCash) {
        Cash += newCash;
    }
    public boolean isPassword (String checkPassword){
        return Password.equals(checkPassword);
    }
    //private void SearchSessions() {}
    private void SearchSessionWithTime() {
        Main.ShowSessionsWithTime();
    }
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
    public String getNameFamilyPatr() {
        return NameFamilyPatr;
    }
    public void setNameFamilyPatr(String nameFamilyPatr) {
        NameFamilyPatr = nameFamilyPatr;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        TelephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public ClientStatus getStatus() {
        return Status;
    }

    public void setStatus(ClientStatus status) {
        Status = status;
    }
    public boolean setStatus(String NewStatus){
        if (NewStatus.equals("Посетитель")){
            Status=new CommonClient();
            return true;
        }
        if (NewStatus.equals("Друг сети")){
            Status=new FriendlyClient();
            return true;
        }
        if (NewStatus.equals("VIP-клиент")){
            Status=new VipClient();
            return true;
        }
        Status=new CommonClient();//неправильные данные, сделаем хоть что-нибудь >:)
        return false;
    }

    public  int getCash() {
        return Cash;
    }
}




