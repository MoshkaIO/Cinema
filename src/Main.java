import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class Main {
public static Cinema FirstCinema;
public static Scanner scanner;
public static final String Sep = ";";
public static String AdminFileName="Admins.txt";
public static String CinemaFileName="Cinemas.txt";
public static String FilmFileName="Films.txt";
public static String ClientFileName="Clients.txt";
public static final String SepArray="&";
public static String[] Formats = {"2D","3D","4D","5D"};
//public static String[] TypeOfHall = {"Обычный", "ВИП"};
    public static ArrayList<String> TypesOfHall = new ArrayList<>();
    public static ArrayList<String> TypesOfClients = new ArrayList<>();
public static ArrayList<Client> ClientNum = new ArrayList<>();
public static ArrayList<Session> SessionNum = new ArrayList<>();
public static ArrayList<Film> Films = new ArrayList<>();
public static ArrayList<Admin> AdminNum = new ArrayList<>();
public static ArrayList<Hall> HallNum = new ArrayList<>();
public static ArrayList<Cinema> CinemaNum = new ArrayList<>();
public static ArrayList<Integer> EarnedMoneyFromHallNum = new ArrayList<>();
    public static int CountOfSoldTickets=0;
    public static int CountOfEarnedMoney=0;
    public static int CountOfEarnedMoneyWithRent=0;
    public static int CountOfVipClients=0;
    public static int CountOfFriendClients=0;
    public static int CountOfCommonClients=0;
    public static String ScannerIsGay; //строка-заглушка, можно будет отследить по "usage" как я задолбался
    public static void main(String[] args)throws Exception {
        scanner = new Scanner (System.in);
        DefaultInicialization();
        Menu(); //так и живём...
        SuperWriter();
    }
    public static void DefaultInicialization(){ //ну или почти :)
        DefaultHalls();
        //DefaultFilms();
        try {
            FilmReading(FilmFileName,Sep);
        } catch(FileNotFoundException e) {
            System.out.println("Whoop-whoop! Не найден Films.txt , подгружаем дефолтное...");
           DefaultFilms(); //фигушки а не катапультация
        } catch (IOException e) {
            System.out.println("Whoop-whoop! Произошло плохое зло...");
            throw new RuntimeException(e);
        }
        //DefaultCinemas();
        try {
            CinemaReading(CinemaFileName,Sep);
        } catch(FileNotFoundException e) {
            System.out.println("Whoop-whoop! Не найден Cinemas.txt , подгружаем дефолтное...");
            DefaultCinemas(); //фигушки а не катапультация
        } catch (IOException e) {
            System.out.println("Whoop-whoop! Произошло плохое зло...");
            throw new RuntimeException(e);
        }
        DefaultSessions();
      //  DefaultClients();





       /* try {
            ClientReading(ClientFileName,Sep);

        } catch(FileNotFoundException e) {
            System.out.println("Whoop-whoop! Не найден Clients.txt , подгружаем дефолтное...");
            DefaultClients();
        } catch (IOException e) {
            System.out.println("Whoop-whoop! Произошло плохое зло...");
            throw new RuntimeException(e); //ГОСПОДИ
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } */

        try {
            ClientReadingSer("ClientsSer");
        } catch (IOException e) {

            if (ClientNum.size()==0) { //если нет значит исключение выбросилось уже после успешного сканирования ВСЕГО ФАЙЛА (ура!) (дефолт не запустится)
                System.out.println("11");
                DefaultClients();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("22");
             DefaultClients();
        }


        // AdminNum.add(new Admin("Михаил Игоревич","79857339833","19657132","йцукен", 1000));
        try {
            AdminReading(AdminFileName,Sep);
        } catch(FileNotFoundException e) {
            System.out.println("Whoop-whoop! Не найден Admins.txt , подключаем значения по умолчанию...");
            DefaultAdmins();
        } catch (IOException e) {
            System.out.println("Whoop-whoop! Произошло плохое зло...");
            throw new RuntimeException(e); //ГОСПОДИ
        }
    }
    public static void DefaultHalls(){
        HallNum.add(new CommonHall(12,12,500,HallNum.size())); // инициализируем несколько залов
        HallNum.add(new  VipHall(8,8,700,HallNum.size()));
        HallNum.add(new CommonHall(6,6,2000,HallNum.size()));
        for (int i=0; i<HallNum.size(); i++){  //заправляем массив возможных типов залов (должно работать автоматически)
            if (!TypesOfHall.contains(HallNum.get(i).TypeOfHall)){
                TypesOfHall.add(HallNum.get(i).TypeOfHall);
            }
        }
        // EarnedMoneyFromHallNum= new ArrayList<>(HallNum.size());
        for (int i=0; i<HallNum.size();i++) EarnedMoneyFromHallNum.add(0); //статистика по каждому залу
    }
    public static void DefaultFilms(){
        // FirstCinema = new Cinema("Костино",120, "город Королёв, парк Костино, ДК Костино", Formats, new ArrayList<>(HallNum));
        Films.add(new Film ("Начало",2012,"Фантастика",120,Formats[1],Films.size()));
        Films.add(new Film ("2012",2012,"Фантастика, ужос",120,Formats[0],Films.size()));
        Films.add(new Film ("МОРГЕНШТЕРН 5D",2021,"Фантастика, ужос",6,Formats[3],Films.size()));
        Films.add(new Film ("Секретный неприличный фильм",2025,"Драма",999,Formats[3],Films.size()));
    }
    public static void DefaultCinemas(){
        CinemaNum.add(new Cinema("Костино", 120, "город Королёв, парк Костино, ДК Костино", Formats,HallNum,CinemaNum.size()));
        CinemaNum.add(new Cinema("Премьера", 120, "город Королёв, ул. Пионерская, ТЦ на выезде из города", Formats, HallNum,CinemaNum.size()));
    }
    public static void DefaultSessions(){
        SessionNum.add(new Session(  Films.get(0), new Cinema ( CinemaNum.get(0),2,SessionNum.size() ),"12:00","14:30")   ) ;
        //SessionNum.add(new Session (Films.get(0),FirstCinema,"14:30","17:00"));
        SessionNum.get(SessionNum.size()-1).UpdateInformation();
        SessionNum.add(new Session(  Films.get(2), new Cinema ( CinemaNum.get(0),0,SessionNum.size() ),"14:30","17:00" )   ) ;
        SessionNum.get(SessionNum.size()-1).UpdateInformation();
        SessionNum.add(new Session(  Films.get(1), new Cinema ( CinemaNum.get(1),1,SessionNum.size() ),"17:00","19:30" )   ) ;
        SessionNum.get(SessionNum.size()-1).UpdateInformation();
    }
    public static void DefaultClients(){
        ClientNum.add(new Client("Аккаунт тестировки","*номер телефона*","тест1","тест1", 999999));
        ClientNum.add(new Client("Мишка Ёу","79857339833","19657132f@gmail.com","qwerty", 1000, new FriendlyClient()));
        ClientNum.add(new Client("Аккаунт тестировки","*номер телефона*","тест","тест", 999999));
        UpdateTypesOfClients(); // на случай если кому-то захочется сделать дефолтного вип-клиента
    }
    public static void DefaultAdmins(){
        AdminNum.add(new Admin("Михаил Игоревич","79857339833","19657132","йцукен", 1000));
        AdminNum.add(new Admin("Владислав Игоревич","7777777777777777","влад","влад", 999999));
    }
    public static void SuperWriter()throws Exception{
        FileWriter fin;
        fin = new FileWriter(ClientFileName, false); //выносим с вертухи всё что было (могу себе позволить)
        fin.close();
        /*for (int i=0; i<ClientNum.size(); i++){
            ClientNum.get(i).Save(ClientFileName,Sep); //заносим то что есть
        }
        fin.close(); */
        //////////////////////////////////////////////////////////////////////////////////
        FileOutputStream outputStream = new FileOutputStream("ClientsSer");
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            for (int i=0; i<ClientNum.size(); i++){
                ClientNum.get(i).SaveSer(objectOutputStream);
            }
        objectOutputStream.close();
        //////////////////////////////////////////////////////////////////////////////////

        fin = new FileWriter(FilmFileName, false);
        fin.close();
        for (int i=0; i<Films.size(); i++){
            Films.get(i).Save(FilmFileName,Sep); //заносим то что есть
        }
        fin.close();
        fin = new FileWriter(CinemaFileName, false);
        fin.close();
        for (int i=0; i<CinemaNum.size(); i++){
            CinemaNum.get(i).Save(CinemaFileName,Sep); //заносим то что есть
        }
        fin.close();
        fin = new FileWriter(AdminFileName, false);
        fin.close();
        for (int i=0; i<AdminNum.size(); i++){
            AdminNum.get(i).SaveStatistic(AdminFileName, Sep);
        }
        fin.close();

    }
    public static void ClearConsole(){
        for (int i=0; i<10; i++)
            System.out.println(" ");
    }
    public static void Pause(int timeOfPause){
        try {
            Thread.sleep(timeOfPause);
        } catch (InterruptedException e) {
            //  throw new RuntimeException(e); господи какие уроды придумали это ограничение и главное зачем!??!?!
        }
    }

    public static void ErrorNotExist(){
        System.out.println("Ошибка: вы выбрали что-то несуществующее!");
        Pause(1300);
    }
    public static void ErrorIsNegative0rZero(){
        System.out.println("Ошибка: вы выбрали что-то меньшее или равное нулю там где не стоило этого делать");
        Pause(2000);
    }
    public static void ClientReading(String FileName, String sep) throws  IOException{ //спасибо за подсказку со считывалкой, иначе бы я СДОХ это самому выдумывать
        File file=new File(FileName);
        FileReader fout =new FileReader(file);
        BufferedReader br = new BufferedReader(fout);
        while (br.ready()){
            System.out.println("Обнаружен пользователь :0");
            String [] params = br.readLine().split(sep);
            ClientNum.add(new Client(params[0],params[1],params[2],params[3],Integer.parseInt(params[4])));
            ClientNum.get(ClientNum.size()-1).setStatus(params[5]);
        }
                //ClientNum.add(new Client("Аккаунт тестировки","*номер телефона*","тест1","тест1", 999999));
    }




    ////////////////////////////////////////////////////////////
    public static void ClientReadingSer(String FileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream( FileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Client ReadPeople =(Client) objectInputStream.readObject();
        while (ReadPeople!=null){
            // ClientNum.add( (Client) ReadPeople);
            ClientNum.add(new Client( ReadPeople));
            ReadPeople =(Client) objectInputStream.readObject();
        }
        objectInputStream.close();
    }
    //////////////////////////////////


    public static void FilmReading(String FileName, String sep) throws IOException{
        File file=new File(FileName);
        FileReader fout =new FileReader(file);
        BufferedReader br = new BufferedReader(fout);
        while (br.ready()){
            System.out.println("Обнаружен фильм :p");
            String [] params = br.readLine().split(sep);
            Films.add(new Film(params[0],Integer.parseInt(params[1]),params[2],Integer.parseInt(params[3]),params[4],Films.size()));
        }
        //Films.add(new Film ("Начало",2012,"Фантастика",120,Formats[1],Films.size()));
    }
    public static void CinemaReading(String FileName, String sep) throws IOException{
        File file=new File(FileName);
        FileReader fout =new FileReader(file);
        BufferedReader br = new BufferedReader(fout);
        while (br.ready()){
            System.out.println("Обнаружен кинотеатр :>");
            String [] params = br.readLine().split(sep);
            String [] NewFormats = params[3].split("&"); //ПОЕХАЛИ
            CinemaNum.add(new Cinema(params[0],Integer.parseInt(params[1]),params[2],NewFormats,HallNum,CinemaNum.size()));
        }
        // CinemaNum.add(new Cinema("Костино", 120, "город Королёв, парк Костино, ДК Костино", Formats,HallNum,CinemaNum.size()));
    }
    public static void AdminReading(String FileName, String sep) throws IOException{
        File file=new File(FileName);
        FileReader fout =new FileReader(file);
        BufferedReader br = new BufferedReader(fout);
        while (br.ready()){
            System.out.println("Обнаружен админ             /\\__(0-0)__/\\    *музыка боссфайта из террарии* ");
            String [] params = br.readLine().split(sep);
            CountOfSoldTickets=Integer.parseInt(params[0]);
            CountOfEarnedMoney=Integer.parseInt(params[1]);
            CountOfEarnedMoneyWithRent=Integer.parseInt(params[2]);
            CountOfVipClients=Integer.parseInt(params[3]);
            CountOfFriendClients=Integer.parseInt(params[4]);
            CountOfCommonClients=Integer.parseInt(params[5]);
            //6-заглушка, чтобы чертов write понял, что имеет дело со строкой
            AdminNum.add(new Admin(params[7],params[8],params[9],params[10],Integer.parseInt(params[11])));
            AdminNum.get(AdminNum.size()-1).setStatus(params[12]);
            //AdminNum.add(new Admin("Михаил Игоревич","79857339833","19657132","йцукен", 1000));
            //ClientNum.add(new Client("Аккаунт тестировки","*номер телефона*","тест1","тест1", 999999));
        }
    }

    public static void Menu () {

        String Role="начало";
        while (true)
        {

            switch (Role) {
                case "начало":
                    System.out.println("Добро пожаловать!");
                    break;
                case "гость":
                    clientMenu ();
                    break;
                case "админ":
                    adminAuthorization();
                    break;
                case "выход":
                    return;
                default:
                    System.out.println("Накосячили либо вы, либо java (плохо читает), повторите ввод");
            }
            System.out.println("Кто вы? (\"гость\"/\"админ\")");
            System.out.println("Чтобы выйти введите \"выход\"");
            Role = scanner.nextLine();
            ClearConsole();

        }

    }
    public static void clientMenu () {
        String answer="начало";
        while (true){
            switch (answer) {
                case "начало":
                    System.out.println("Добро пожаловать, гость!");
                    break;
                case "авторизоваться":
                    clientAutorization();
                    break;
                case "зарегистрироваться":
                    clientRegistration();
                    break;
                case "выход":
                    return;
                //break; //а вдруг!
                default:
                    System.out.println("Накосячили либо вы, либо java (плохо читает), повторите ввод");

            }
            System.out.println("Вы можете \"авторизоваться\" или \"зарегистрироваться\"");
            System.out.println("Чтобы выйти введите \" выход \" ");
            answer = scanner.nextLine();
            ClearConsole();

        }
    }
    public static void clientRegistration (){
        ClearConsole();
        String newEmail;
        System.out.println("Регистрация");
        System.out.println("Введите ваш Email");
        while (true){
            newEmail = scanner.nextLine();
            boolean isFound= false;
            for (int i=0; i<ClientNum.size(); i++){
                if (  ClientNum.get(i).getEmail().equals(newEmail)  ){
                    isFound=true;
                    System.out.println("Этот Email уже используется. Введите корректный Email:");
                }
            }
            if (isFound==false) break;
        }
        System.out.println("Введите пароль:");
        String newPassword = scanner.nextLine();
        System.out.println("Повторите пароль: (шутка)");
        System.out.println("Введите ФИО:");
        String newName=scanner.nextLine();
        System.out.println("Введите номер телефона:");
        String newTelephone=scanner.nextLine();
        System.out.println("Сколько денег вы хотите ");
        int newCash=scanner.nextInt();
        System.out.println("Сделаем вид, что от этого не накроется мировая финансовая система ");
        ClientNum.add(new Client(newName,newTelephone,newEmail,newPassword, newCash));

    }
    public static void clientAutorization (){
        String checkEmail;
        String checkPassword;
        for (int j=0; j<3; j++){  // 3 попытки на ввод пароля
            System.out.println("Авторизация");
            System.out.println("Введите Email");
            checkEmail = scanner.nextLine();
           // if (scanner.hasNext()) scanner.nextLine();
            System.out.println("Введите пароль");
            checkPassword = scanner.nextLine();
          //  if (scanner.hasNext()) scanner.nextLine();
            boolean isFound= false;
            for (int i=0; i<ClientNum.size(); i++){
                if (  ClientNum.get(i).getEmail().equals(checkEmail) && ClientNum.get(i).isPassword(checkPassword)   ){
                    isFound=true;
                    ClientNum.get(i).Account();
                    return;
                }
            }
            ClearConsole();
            System.out.println("Неверное имя пользователя или пароль");
        }

    }
    public static void adminAuthorization () {
        String checkEmail;
        String checkPassword;
        for (int j=0; j<3; j++){  // 3 попытки на ввод пароля
            System.out.println("Авторизация");
            System.out.println("Введите Email");
            checkEmail = scanner.nextLine();
            System.out.println("Введите пароль");
            checkPassword = scanner.nextLine();
            boolean isFound= false;
            for (int i=0; i<AdminNum.size(); i++){ //ищем среди админов нужного
                if (  AdminNum.get(i).getEmail().equals(checkEmail) && AdminNum.get(i).isPassword(checkPassword)   ){
                    isFound=true;
                    AdminNum.get(i).AdminAccount();
                    return;
                }
            }
            ClearConsole();
            System.out.println("Неверное имя пользователя или пароль"); //если не нашли >:(
        }
    }

    public static void AdminAccount (){
        System.out.println("Выберите желаемое действие:");
        System.out.println("Чтобы посмотреть общую выручку за все сеансы введите \"выручка\" ");
        System.out.println("Чтобы просмотреть статистику выручки по типу зала введите  \"статистика\" ");
        System.out.println("Чтобы посмотреть общее количество проданных билетов введите \"билеты\" ");
        System.out.println("Чтобы посмотреть какие залы были арендованы введите \"аренда\"");
        System.out.println("Чтобы просмотреть сеансы введите \"сеансы\" ");
        System.out.println("Чтобы просмотреть количество клиентов в разном статусе введите \"клиенты\" ");
        System.out.println("Чтобы (внезапно) купить билет введите\"купить\" ");
        System.out.println("Чтобы создавать/редактировать/удалять выберите \"редактор\" ");
        System.out.println("Чтобы узнать ВСЁ ЧТО МОЖНО введите \"всё\" ");
        System.out.println("Чтобы выйти введите \"выход\" ");
    }
    public static void AdminGodMod (){
        System.out.println("Время пришло...");
        System.out.println("Мы наделяем вас возможностью создавать/удалять/редактировать  сеансы/кинотеатры/залы");
        System.out.println("Выберите желаемый объект взаимодействия:");
        System.out.println("Чтобы натворить дел с сеансами введите \"сеанс\" ");
        System.out.println("Чтобы натворить дел с кинотеатрами введите \"кинотеатр\" ");
        System.out.println("Чтобы натворить дел с залами введите \"зал\" ");
        System.out.println("Чтобы выйти введите \"выход\" ");
    }
    public static void AdminGodProcesses(String SlaveObject){
        System.out.println("Вы выбрали объект взаимодейтсвия: \""+SlaveObject+"\" ");
        System.out.println("Предупреждение: при создании нового объекта вы можете использовать только уже существующие");
        System.out.println("Пример: при создании сеанса можно выбрать только уже существующий кинотеатр, зал и фильм");
        System.out.println("Пример: при редактировании сеанса можно выбрать только уже существующий кинотеатр, зал и фильм");
        System.out.println("Пример: при редактировании зала можно спокойно вводить что хочешь :)");
        System.out.println("Выберите действие:");
        System.out.println("Чтобы что-то создать введите \"создать\" ");
        System.out.println("Чтобы что-то редактировать введите \"редактировать\" ");
        System.out.println("Чтобы что-то удалить введите \"удалить\" ");
        System.out.println("Чтобы выйти  отсюда пока не натворили делов введите \"выход\" ");
    }
    public static void GodOfSession(){
        String answer="начало";
        while (true){
            switch (answer) {
                case "начало":
                    System.out.println("Время чинить сеансы...");
                    break;
                case "создать":
                    CreateSession();
                    break;
                case "редактировать":
                    EditSession();
                    break;
                case "удалить":
                    DeleteSession();
                    break;
                case "выход":
                    return;
                //break; //а вдруг!
                default:
                    System.out.println("Накосячили либо вы, либо java (плохо читает), повторите ввод");

            }
            Main.AdminGodProcesses("сеанс");
            answer = scanner.nextLine();
            Main.ClearConsole();
            Main.ClearConsole();
        }
    }
    public static void GodAttention(){
        System.out.println("...в благородство играть не буду...");
        System.out.println("Сейчас будем вводить различные данные. Отмотать назад возможности не дам");
        System.out.println("Если где-то ошибся -- жди до конца, там я первый и последний раз спрошу \"Всё верно!?\"");
        System.out.println("Тогда и отменю создание.");
        System.out.println("Ну или введите какиую-нибуть ахинею, вроде \"-5 мест в ряду\", возможно, я вас катапультирую за такую ошибку");
        System.out.println("При редактировании изменения вступают в силу мгновенно, так что если накосячили - редачьте заново");
        System.out.println("Я УСТАЛ");
        System.out.println("Начнём...");
    }
    public static void CreateSession(){
        GodAttention();
        Pause(2000);
       // SessionNum.add(new Session(  Films.get(2), new Cinema ( CinemaNum.get(0),0,SessionNum.size() ),"14:30","17:00" )   ) ;
       // SessionNum.get(SessionNum.size()-1).UpdateInformation();
        System.out.println("Список кинотеатров и входящих в них залов:");
        Pause(1000);
        ShowAllAboutCinemas();
        System.out.println("Введите номер того кинотеатра, в котором будет создан сеанс: ");
        int ChosenCinema=scanner.nextInt();
        if ((ChosenCinema<0)||(ChosenCinema>=CinemaNum.size())){
            ErrorNotExist();
            return;
        }
        System.out.println("Введите номер того зала, в котором будет создан сеанс: ");
        int ChosenHall=scanner.nextInt();
        if ((ChosenHall<0)||(ChosenHall>=HallNum.size())){
            ErrorNotExist();
            return;
        }
        System.out.println("Список доступных к показу фильмов: ");
        ShowAllExistFilms();
        System.out.println("Введите номер фильма, который будет показан на сеансе: ");
        int ChosenFilm=scanner.nextInt();
        if ((ChosenFilm<0)||(ChosenFilm>=Films.size())){
            ErrorNotExist();
            return;
        }
        System.out.println("Дальше проще. Введите время начала сеанса:");
        //String ChosenBeginningTime = scanner.nextLine();
        String ChosenBeginningTime = scanner.next();
        System.out.println("Введите время конца сеанса:");
        //String ChosenEndingTime=scanner.nextLine();
        String ChosenEndingTime=scanner.next();
        System.out.println("Промежуточный итог. Вы ввели:");
        System.out.println("Номер кинотеатра: "+ChosenCinema+"  ("+CinemaNum.get(ChosenCinema).getName()+"), номер зала: "+ChosenHall+"   ("+HallNum.get(ChosenHall).getTypeOfHall()+")");
        System.out.println("Номер фильма: "+ChosenFilm+"  ("+Films.get(ChosenFilm).getName()+") , время начала: "+ChosenBeginningTime+" , время конца: "+ChosenEndingTime);
        System.out.println("Введите \"создать\" если вас всё устраивает. Введите что-то другое чтобы выйти из этого меню");
        //String ChosenAnswer=scanner.nextLine();
        String ChosenAnswer= scanner.next();
        if (ChosenAnswer.equals("создать")){
            SessionNum.add(new Session(  Films.get(ChosenFilm), new Cinema ( CinemaNum.get(ChosenCinema),ChosenHall,SessionNum.size() ),ChosenBeginningTime,ChosenEndingTime )   ) ;
             SessionNum.get(SessionNum.size()-1).UpdateInformation();
            System.out.println("Проверим, что получилось: ");
            SessionNum.get(SessionNum.size()-1).showInf();
            Pause(1500);
        }
        else System.out.println("ДЕЙСТВИЕ ОТМЕНЕНО ");
    }
    public static void DeleteSession(){
        GodAttention();
        Pause(1000);
        System.out.println("Список существующих сеансов:");
        Pause(1500);
        Main.ShowSessions();
        System.out.println("Введите номер сеанса, который хотите удалить. Для отмены введите 0");
        int ChosenSession=scanner.nextInt(); //мы облажались со старой функцией, она выводит i+1 значения на экран
        ChosenSession--; //поэтому понижаем...
        if ((ChosenSession<0)||(ChosenSession>=SessionNum.size())){
            ErrorNotExist();
            return;
        }
        System.out.println("Промежуточный итог. Вы хотите удалить этот сеанс:");
        System.out.println("Сеанс №"+(ChosenSession+1)); //опять же :(
        SessionNum.get(ChosenSession).showInf();
        System.out.println("Введите \"удалить\" если вас всё устраивает. Введите что-то другое чтобы выйти из этого меню");
        // String ChosenAnswer=scanner.nextLine();
        String ChosenAnswer=scanner.next();
        if (ChosenAnswer.equals("удалить")){
            SessionNum.remove(ChosenSession); //удаляем этого добряка
            for (int i=ChosenSession; i<SessionNum.size(); i++){ //из-за этого много хороших ребят сдвинулось влево...
                SessionNum.get(i).DecramentNumberOfSession();  //они должны знать свои новые порядковые номера :(
            }
            System.out.println("Проверим, что получилось (кто занял это место): ");
            System.out.println("Сеанс №"+(ChosenSession+1));
            SessionNum.get(ChosenSession).showInf();
        }else System.out.println("ДЕЙСТВИЕ ОТМЕНЕНО ");
    }
    public static void EditSession(){
        GodAttention();
        Pause(2000);
        System.out.println("Список существующих сеансов:");
        Pause(1500);
        Main.ShowSessions();
        System.out.println("Введите номер сеанса, который хотите отредактировать. Для отмены введите 0");
        int ChosenSession=scanner.nextInt(); //мы облажались со старой функцией, она выводит i+1 значения на экран
        ChosenSession--; //поэтому понижаем...
        if ((ChosenSession<0)||(ChosenSession>=SessionNum.size())){
            ErrorNotExist();
            return;
        }
        SessionNum.get(ChosenSession).showInf();
        System.out.println("Хотите ли вы сменить фильм, который будут показывать? (да/нет");
        String BoolEdit=scanner.next(); //здесь будем хранить выбор пользователя

        if (BoolEdit.equals("да")){
            System.out.println("Список доступных к показу фильмов: ");
            ShowAllExistFilms();
            System.out.println("Введите номер фильма, который будет показан на сеансе: ");
            int ChosenFilm=scanner.nextInt();
            if ((ChosenFilm<0)||(ChosenFilm>=Films.size())){
                ErrorNotExist();
                return;
            }
            SessionNum.get(ChosenSession).setFilm(Films.get(ChosenFilm));
        }
        System.out.println("Вот что получилось:");
        Pause(800);
        SessionNum.get(ChosenSession).showInf();
        System.out.println("Хотите ли вы сменить время начала и конца сеанса? (да/нет)");
        BoolEdit=scanner.next();
        if (BoolEdit.equals("да")){
            System.out.println(" Введите время начала сеанса:");
            //String ChosenBeginningTime = scanner.nextLine();
            String ChosenBeginningTime = scanner.next();
            System.out.println("Введите время конца сеанса:");
            //String ChosenEndingTime=scanner.nextLine();
            String ChosenEndingTime=scanner.next();
            SessionNum.get(ChosenSession).setTimeOfBeginning(ChosenBeginningTime);
            SessionNum.get(ChosenSession).setTimeOfEnding(ChosenEndingTime);
        }
        System.out.println("Вот что получилось:");
        Pause(800);
        SessionNum.get(ChosenSession).showInf();
        System.out.println("Хотите ли вы сменить кинотеатр, в котором будет проведён сеанс (да/нет)? (зал скопируется автоматически)");
        BoolEdit=scanner.next();

        if (BoolEdit.equals("да")){
            System.out.println("Список существующих кинотеатров:");
            Pause(1500);
            ShowCinemas();
            System.out.println("Введите номер того, который вы хотите использовать");
            int ChosenCinema=scanner.nextInt();
            if ((ChosenCinema<0)||(ChosenCinema>=CinemaNum.size())){
                ErrorNotExist();
                return;
            }
            Hall Buffer=SessionNum.get(ChosenSession).getCinema().getOneHall();
            SessionNum.get(ChosenSession).setCinema(new Cinema(CinemaNum.get(ChosenCinema),0,ChosenSession));
            SessionNum.get(ChosenSession).getCinema().setOneHall(Buffer);//да поможет мне господь бог
        }
       // SessionNum.get(ChosenSession).getCinema().getOneHall(). //это чё вообще откуда. блин нафиг я пишу это не в 1 день
        System.out.println("Вот что получилось:");
        Pause(800);
        SessionNum.get(ChosenSession).showInf();
        System.out.println("Хотите ли вы сменить зал, в котором будет проходить сеанс (да/нет)");
        System.out.println("Предупреждение: я не буду подгонять старый зал под новый, всё сотрётся к чёрту. Будьте осторожны");
        BoolEdit=scanner.next();
        if (BoolEdit.equals("да")){
            System.out.println("Список существующих залов (в вашем кинотеатре) (если хотели из другого кинотеатра, то надо было отредачить текуший кинотеатр):");
            Pause(1500);
            SessionNum.get(ChosenSession).getCinema().ShowHallsInf();
            System.out.println("Введите номер того, который вы хотите использовать");
            int ChosenHall=scanner.nextInt();
            Hall Buffer=SessionNum.get(ChosenSession).getCinema().getHallNum().get(ChosenHall);
            SessionNum.get(ChosenSession).getCinema().setOneHall(new CommonHall((CommonHall) Buffer)); //господи как же я не хочу прописывать If-ы или ещё какую-то чернокнижную хрень для различия типов залов при генерации аминь

        }
        System.out.println("Вот что получилось:");
        Pause(800);
        SessionNum.get(ChosenSession).showInf();
            System.out.println("Введите что-нибудь, чтобы продолжить:");
         ScannerIsGay=scanner.next();
    }
    public static void GodOfCinema(){
        String answer="начало";
        while (true){
            switch (answer) {
                case "начало":
                    System.out.println("Время чинить кинотеатры...");
                    break;
                case "создать":
                    CreateCinema();
                    break;
                case "редактировать":
                    EditCinema();
                    break;
                case "удалить":
                    DeleteCinema();
                    break;
                case "выход":
                    return;
                //break; //а вдруг!
                default:
                    System.out.println("Накосячили либо вы, либо java (плохо читает), повторите ввод");

            }
            Main.AdminGodProcesses("кинотеатр");
            answer = scanner.nextLine();
            Main.ClearConsole();
            Main.ClearConsole();
        }

    }
    public static void CreateCinema(){
        //CinemaNum.add(new Cinema("Костино", 120, "город Королёв, парк Костино, ДК Костино", Formats,HallNum,CinemaNum.size()));
        GodAttention();
        Pause(2000);
        System.out.println("Введите название кинотеатра:");
        String ChosenName=scanner.nextLine();
        System.out.println("Введите вместимость кинотеатра:");
        int ChosenCapacity=scanner.nextInt();
        System.out.println("Введите что-нибудь, чтобы продолжить: ");
        ScannerIsGay=scanner.nextLine();
        System.out.println("Введите адрес кинотеатра:");
        String ChosenAdress=scanner.nextLine();
        ArrayList<String> NewFormats = new ArrayList<>();
        while (true){
            System.out.println("Добавляем форматы, которые будет поддерживать кинотеатр. Список возможных:");
            ShowExistFormats();
            System.out.print("из них вы уже выбрали:   ");
            for (int i=0; i<NewFormats.size(); i++) System.out.print(NewFormats.get(i)+", ");
            System.out.println("    . (всё) "); //вдруг там пусто
            System.out.println("Введите номер формата, который хотите добавить. Если уже достаточно, введите \"-1\"");
            int ChosenFormat = scanner.nextInt();
            if ((ChosenFormat<0)||(ChosenFormat>Formats.length)) break;
            if (NewFormats.contains(Formats[ChosenFormat])){
                System.out.println("Такой формат уже есть в списке, не пытайся натворить делов >:(   ");
            }
            else NewFormats.add(Formats[ChosenFormat]);
        }
         String[] NewFormatsArr = new String[NewFormats.size()];
        for (int i=0; i<NewFormatsArr.length; i++){
            NewFormatsArr[i]=NewFormats.get(i);
        }
        ArrayList<Hall> NewHalls = new ArrayList<>();
        while (true){
            System.out.println("Добавляем залы, которые будут располагаться в вашем кинотеатре. Список существующих залов:");
            ShowHalls();
            System.out.print("из них вы уже выбрали:   ");
            for (int i=0; i<NewHalls.size(); i++) System.out.print(" Зал № "+NewHalls.get(i).getNumberOfHall()+", ");
            System.out.println("    . (всё) "); //вдруг там пусто
            System.out.println("Введите номер зала, который также будет располагаться в вашем кинотеатре. Если уже достаточно, введите \"-1\"");
            int ChosenHall = scanner.nextInt();
            if ((ChosenHall<0)||(ChosenHall>HallNum.size())) break;
           // if (NewHalls.contains(Formats[ChosenHall])){ //а может пофиг, пусть будут одинаковые залы, кто им запретит-то?
           //     System.out.println("Такой формат уже есть в списке, не пытайся натворить делов >:(   ");
           // } НЕ ЗАБЫТЬ ДОБАВАИТЬ ELSE В СЛУЧАЕ РАСКОММЕНТИРОВАНИЯ!!!!!!!!!!!!!!!!!!!!
            if (HallNum.get(ChosenHall).getTypeOfHall().equals("ВИП")){
                NewHalls.add(new VipHall((VipHall) HallNum.get(ChosenHall)));
            }
             else NewHalls.add(new CommonHall((CommonHall) HallNum.get(ChosenHall))); // (перед этим)
            NewHalls.get(NewHalls.size()-1).setNumberOfHall(NewHalls.size()-1);
        }
        System.out.println("Промежуточный итог. Вы ввели:");
        System.out.println("Название кинотеатра: \""+ChosenName+"\" , вместимость: "+ChosenCapacity+"  , адрес: "+ChosenAdress);
        System.out.print("Поддерживаемые форматы:");
        for (int i=0; i<NewFormats.size(); i++) System.out.print(NewFormats.get(i)+", ");
        System.out.println(" "); //след. строка
        System.out.println("Залы в кинотеатре: ");
        for (int i=0; i<NewHalls.size(); i++){
            NewHalls.get(i).ShowInf();
        }
        System.out.println("Введите \"создать\" если вас всё устраивает. Введите что-то другое чтобы выйти из этого меню");
        //String ChosenAnswer=scanner.nextLine();
        String ChosenAnswer= scanner.next();
        if (ChosenAnswer.equals("создать")){
            CinemaNum.add(new Cinema(ChosenName, ChosenCapacity, ChosenAdress, NewFormatsArr,NewHalls,CinemaNum.size()));
            System.out.println("Проверим, что получилось: ");
            CinemaNum.get(CinemaNum.size()-1).ShowInf();
            Pause(1500);
        }
        else System.out.println("ДЕЙСТВИЕ ОТМЕНЕНО ");

    }
    public static void EditCinema(){
        GodAttention();
        Pause(2000);
        System.out.println("Список существующих кинотеатров и их залов:");
        Pause(1500);
        Main.ShowCinemas();
        System.out.println("Введите номер того кинотеатра, который вы хотите отредактировать: ");
        int ChosenCinema=scanner.nextInt();
        if ((ChosenCinema<0)||(ChosenCinema>=CinemaNum.size())){
            ErrorNotExist();
            return;
        }
        System.out.println("Хотите сменить название кинотеатра ? (да/нет)");
        String BoolEdit=scanner.next(); //здесь будем хранить выбор пользователя
        if (BoolEdit.equals("да")){
            System.out.println("Текущее название кинотеатра:");
        }
    }
    public static void DeleteCinema(){
        GodAttention();
        Pause(1000);
        System.out.println("Список кинотеатров и входящих в них залов:");
        Pause(1000);
        ShowAllAboutCinemas();
        System.out.println("Введите номер того кинотеатра, который вы собираетесь удалить. Для отмены введите -1 ");
        int ChosenCinema=scanner.nextInt();
        if ((ChosenCinema<0)||(ChosenCinema>=SessionNum.size())){
            ErrorNotExist();
            return;
        }
        System.out.println("Промежуточный итог. Вы хотите удалить этот кинотеатр:");
        System.out.println("Кинотеатр №"+ChosenCinema);
        //SessionNum.get(ChosenSession).showInf();
        CinemaNum.get(ChosenCinema).ShowInf();
        System.out.println("Введите \"удалить\" если вас всё устраивает. Введите что-то другое чтобы выйти из этого меню");
        // String ChosenAnswer=scanner.nextLine();
        String ChosenAnswer=scanner.next();
        if (ChosenAnswer.equals("удалить")){
            SessionNum.remove(ChosenCinema); //удаляем этого добряка
            for (int i=ChosenCinema; i<CinemaNum.size(); i++){ //из-за этого много хороших ребят сдвинулось влево...
                //SessionNum.get(i).DecramentNumberOfSession();  //они должны знать свои новые порядковые номера :(
                CinemaNum.get(i).setNumberOfCinema(CinemaNum.get(i).getNumberOfCinema()-1); //что-то вроде декремента (?)
            }
            System.out.println("Проверим, что получилось (кто занял это место): ");
            System.out.println("Кинотеатр №"+ChosenCinema);
            CinemaNum.get(ChosenCinema).ShowInf();
        }else System.out.println("ДЕЙСТВИЕ ОТМЕНЕНО ");
    }
    public static void GodOfHall(){
        String answer="начало";
        while (true){
            switch (answer) {
                case "начало":
                    System.out.println("Время чинить залы...");
                    break;
                case "создать":
                    CreateHall();
                    break;
                case "редактировать":
                    break;
                case "удалить":
                    break;
                case "выход":
                    return;
                //break; //а вдруг!
                default:
                    System.out.println("Накосячили либо вы, либо java (плохо читает), повторите ввод");

            }
            Main.AdminGodProcesses("зал");
            answer = scanner.nextLine();
            Main.ClearConsole();
            Main.ClearConsole();
        }
    }
    public static void CreateHall(){
        GodAttention();
        Pause(2000);
        System.out.println("Введите количество рядов в создаваемом зале");
        int ChosenCountOfRows=scanner.nextInt();
        if (ChosenCountOfRows<=0) ErrorIsNegative0rZero();
        System.out.println("Введите количество мест в ряду");
        int ChosenCountOfPlacesInRaw= scanner.nextInt();
        if (ChosenCountOfPlacesInRaw<=0) ErrorIsNegative0rZero();
        System.out.println("Введите максимальную стоимость билета в вашем зале");
        int ChosenMaxCost= scanner.nextInt();
        if (ChosenMaxCost<=0) ErrorIsNegative0rZero();
        System.out.println("Введите что-нибудь: ");
        ScannerIsGay=scanner.nextLine();
        System.out.println("Введите тип зала:");
        String ChosenTypeOfHall=scanner.nextLine();
        System.out.println("Промежуточный итог. Вы ввели:");
        System.out.println("Количество рядов: "+ChosenCountOfRows+" , количество мест в ряду: "+ChosenCountOfPlacesInRaw);
        System.out.println("Максимальная стоимость билета: "+ChosenMaxCost+", тип зала: "+ChosenTypeOfHall);
        System.out.println("Введите \"создать\" если вас всё устраивает. Введите что-то другое чтобы выйти из этого меню");
        //String ChosenAnswer=scanner.nextLine();
        String ChosenAnswer= scanner.next();
        if (ChosenAnswer.equals("создать")){
            switch (ChosenTypeOfHall){
                case "ВИП":
                    HallNum.add(new  VipHall(ChosenCountOfRows,ChosenCountOfPlacesInRaw,ChosenMaxCost,HallNum.size()));
                    break;
                case "ОБЫЧНЫЙ": HallNum.add(new  CommonHall(ChosenCountOfRows,ChosenCountOfPlacesInRaw,ChosenMaxCost,HallNum.size()));
                default: HallNum.add(new  CommonHall(ChosenCountOfRows,ChosenCountOfPlacesInRaw,ChosenMaxCost,HallNum.size()));

            }
        }
       // HallNum.add(new  VipHall(8,8,700,HallNum.size()));
    }
    public static void AdminShowAll(){
        System.out.println("Сейчас мы вывалим всю информацию, что у нас есть в листах:");
        Pause(1500);
        ShowExistFormats();
        ShowAllExistFilms();
        ShowCinemas();
        ShowHalls();
        ShowSessions();
        ShowAllClients();

    }
    public static void ClientAccount (){
        System.out.println("Выберите желаемое действие:");
        System.out.println("Чтобы посмотреть все сеансы введите \"сеансы\" ");
        System.out.println("Чтобы посмотреть сеансы по времени введите \"по времени\" ");
        System.out.println("Чтобы посмотреть сеансы по стоимости билета введите \"по стоимости\" ");
        System.out.println("Чтобы просмотреть сеансы по названию фильма введите \"по названию\" ");
        System.out.println("Чтобы пополнить счёт введите \"пополнить\" ");
        System.out.println("Чтобы посмотреть историю покупок введите \"история\" ");
        System.out.println("Чтобы выйти из личного кабинета введите \"выход\" ");
    }
    public static void BuyTicketMenu(int ChoiceOfSession, int Cash, double Discount){
        System.out.println("Меню сеанса " + (ChoiceOfSession + 1));
        Main.SessionNum.get(ChoiceOfSession).getCinema().ViewHall(Discount);
        System.out.println("У вас на счету " + Cash + " рублей");
        System.out.println("Выберите желаемое действие:");
        System.out.println("Чтобы купить билет введите \"купить\"");
        System.out.println("Чтобы арендовать ВЕСЬ ЗАЛ введите \"арендовать\"");
        System.out.println("Если вам опять что-то не понравилось введите \"выход\"");
    }
    public static void BoughtTicketsMenu(){
        System.out.println("Выберите желаемое действие:");
        System.out.println("Чтобы посмотреть все купленные билеты введите \"билеты\" ");
        System.out.println("Чтобы посмотреть все арендованные залы введите \"залы\" ");
        System.out.println("Чтобы выйти из личного кабинета введите \"выход\" ");
    }
    public static void ShowRentedSessions(){
        System.out.println("Информация о арендованых сеансах (залах):");
        boolean isFound=false;
        for (int i=0; i<SessionNum.size(); i++){
            if (SessionNum.get(i).getCinema().getOneHall().isRented()){
                isFound=true;
                System.out.println("Сеанс №"+(i+1));
                SessionNum.get(i).showInf();
                System.out.println("---------------------------------------------------");
            }
        }
        if (!isFound){
            System.out.println("К сожалению, ни одного зала пока не арендовали");
            return;
        }
        System.out.println("Итого, на аренде залов было заработано"+CountOfEarnedMoneyWithRent);
    }
   /* public static boolean TryToBuyTicket(int ChoiceOfSession,int ChoiceOfRow, int ChoiceOfPlace, int Cash){

        return true;
    }*/
    public static void ShowSessions () {
        System.out.println("Информация о сеансах:");
        for (int i=0; i<SessionNum.size(); i++){
            System.out.println("Сеанс №"+(i+1));
            SessionNum.get(i).showInf();
            System.out.println("---------------------------------------------------");
        }
    }
    public static boolean ShowSessionsWithTime() {
        ShowAllSessionPeriods();
        System.out.print("Введите время начала сеанса: ");
        String searchTime=scanner.nextLine();
        boolean isFound=false;
        for (int i=0; i<SessionNum.size(); i++){
            if (SessionNum.get(i).getTimeOfBeginning().equals(searchTime)) {
                isFound=true;
                System.out.println("Сеанс №"+(i+1));
                SessionNum.get(i).showInf();
                System.out.println("---------------------------------------------------");
            }
        }
        if (isFound==false){
            System.out.println("К сожалению, таких сеансов не нашлось...");
            Pause(1500);
            return false;
        }
        return true;
    }
    public static boolean ShowSessionsWithCash(){
       return ShowSessionsWithCash(1.0);
    }
    public static boolean ShowSessionsWithCash(double Discount){
        System.out.print("Введите приемлимую для вас стоимость билета: ");
        int searchCash=scanner.nextInt();
        boolean isFound=false;
        for (int i=0; i<SessionNum.size(); i++){
            if (SessionNum.get(i).getCinema().getOneHall().CheckOfOpportunityToBuy(searchCash)) {
                isFound=true;
                System.out.println("Сеанс №"+(i+1));
                SessionNum.get(i).showInf();
                System.out.println("---------------------------------------------------");
            }
        }
        if (isFound==false){
            System.out.print("К сожалению, таких сеансов не нашлось...");
            Pause(1500);
            return false;
        }
        return true;
    }
    public static boolean ShowSessionsWithNameOfFilm(){
        ShowAllFilmNames();
        System.out.print("Введите название фильма: ");
        String searchName=scanner.nextLine();
        boolean isFound=false;
        for (int i=0; i<SessionNum.size(); i++){
            if (SessionNum.get(i).getFilm().getName().equals(searchName)) {
                isFound=true;
                System.out.print("Сеанс №"+(i+1));
                SessionNum.get(i).showInf();
                System.out.println("---------------------------------------------------");
            }
        }
        if (isFound==false){
            System.out.println("К сожалению, таких сеансов не нашлось...");
            Pause(1500);
            return false;
        }
        return true;
    }
    public static void ShowAllSessionPeriods(){
        for (int i=0; i<SessionNum.size(); i++){
            System.out.print(SessionNum.get(i).getTimeOfBeginning()+"-"+SessionNum.get(i).getTimeOfEnding()+" ");
        }
        System.out.println(" ");
    }
    public static void ShowAllFilmNames(){
        for (int i=0; i<SessionNum.size(); i++){
            System.out.print("\""+SessionNum.get(i).getFilm().getName()+"\", ");
        }
        System.out.println(" ");
    }
    public static void ShowAllExistFilms(){
        for (int i=0; i<Films.size(); i++){
            Films.get(i).ShowInf();
        }
    }
    public static void ShowCinemas(){
        for (int i=0; i<CinemaNum.size(); i++){
            System.out.println("Кинотеатр №"+i);
            System.out.println(CinemaNum.get(i).toString());
        }
    }
    public static void ShowAllAboutCinemas(){
        for (int i=0; i<CinemaNum.size(); i++){
            CinemaNum.get(i).ShowInf();
        }
    }
    public static void ShowHalls(){
        for (int i=0; i<HallNum.size();i++){
            //System.out.println("Зал №"+i);
            HallNum.get(i).ShowInf();
        }
    }
    public static void ShowExistFormats(){
        for (int i=0; i<Formats.length; i++){
            System.out.print("        №"+i+": "+Formats[i]);
        }
        System.out.println(" ");
    }
    public static void ShowAllClients(){
        for (int i=0; i<ClientNum.size(); i++){
            ClientNum.get(i).ShowInf();
        }
    }
    public static void HallStatistic(){
        System.out.println("Статистика по существующим залам:");
        ArrayList<Integer> CountingOfEarnedMoneyFromHallType = new ArrayList<>();
        for (int i=0;i<Main.TypesOfHall.size(); i++){
            CountingOfEarnedMoneyFromHallType.add(0);
        }
        for (int i=0; i<Main.HallNum.size(); i++){
            Main.HallNum.get(i).ShowInf();
            System.out.println("С такого зала было заработано: "+Main.EarnedMoneyFromHallNum.get(i));
            System.out.println("------------------------");
            if (Main.TypesOfHall.indexOf(Main.HallNum.get(i).getTypeOfHall())>-1){
                int Buffer=CountingOfEarnedMoneyFromHallType.get(Main.TypesOfHall.indexOf(Main.HallNum.get(i).getTypeOfHall()));
                CountingOfEarnedMoneyFromHallType.set(Main.TypesOfHall.indexOf(Main.HallNum.get(i).getTypeOfHall()),Buffer+Main.EarnedMoneyFromHallNum.get(i));
            }
        }
        System.out.println(" ");
        System.out.println("Статистика по типам залов:");
        for (int i=0; i<Main.TypesOfHall.size(); i++){
            System.out.println("С залов типа \""+Main.TypesOfHall.get(i)+"\" было заработано: "+CountingOfEarnedMoneyFromHallType.get(i));
        }
        Main.Pause(3500);
        Main.ClearConsole();
    }
    public static void ClientStatusStatistic(){
        UpdateTypesOfClients();
        System.out.println("Статистика по количеству клиентов в разном статусе:");
        ArrayList<Integer> CountingClientsOfStatusType = new ArrayList<>();
        for (int i=0;i<Main.TypesOfClients.size(); i++){
            CountingClientsOfStatusType.add(0);
        }
        for (int i=0; i<Main.ClientNum.size(); i++){
            if (Main.TypesOfClients.indexOf(Main.ClientNum.get(i).getStatus().getStatusName())>-1){ // такой  статус вообще существует?
                int Buffer=CountingClientsOfStatusType.get(Main.TypesOfClients.indexOf(Main.ClientNum.get(i).getStatus().getStatusName())); //скачали число найденных клиентов этого статуса
                CountingClientsOfStatusType.set(Main.TypesOfClients.indexOf(Main.ClientNum.get(i).getStatus().getStatusName()),Buffer+1);
            }
        }
        for (int i=0;i<Main.TypesOfClients.size(); i++){
            System.out.println("Клиентов \""+Main.TypesOfClients.get(i)+"\" в сети зарегистрировано: "+CountingClientsOfStatusType.get(i));
        }
        Main.Pause(3500);
        Main.ClearConsole();
    }
    public static void UpdateTypesOfClients(){
        TypesOfClients = new ArrayList<>();
        for (int i=0; i<Main.ClientNum.size(); i++){
            if (!TypesOfClients.contains(Main.ClientNum.get(i).getStatus().getStatusName())) //заносим различные типы статусов в списочек
                TypesOfClients.add(Main.ClientNum.get(i).getStatus().getStatusName());

        }

    }

    public void CreateSessionMenu() {

    }
}


/* админ это отдельный класс, причём:
он не должен содержать общего кода
статистика администратора должна содержать то что уже содержит
модифицировать клиента, дать возможность арендовать весь зал
все места должны быть до этого свободны.
аренда должна быть отображена в статистике админа.
 */


/*
сериалалайзбл (лекция 6)
запись клиентов в файл через сериализацию
(и чтение)
Это должен быть метод People и работать у наследника
 */
/* разнести запись в файл в отдельный класс */

