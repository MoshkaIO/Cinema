public class Session { //Сеанс
    private Cinema cinema;
    private Film film;
    private String timeOfBeginning;
    private String timeOfEnding;
    private int NumOfSession=0;
    Session (Film newFilm, Cinema newCinema, String newTimeOfBeginning, String newTimeOfEnding) {
        cinema=newCinema;
        film=newFilm;
        timeOfBeginning=newTimeOfBeginning;
        timeOfEnding=newTimeOfEnding;
       // NumOfSession=NewNum;
    }
    Session () {}
    public void showInf (){
        if (cinema.getOneHall().isRented()) System.out.println("ВНИМАНИЕ! ЭТОТ ЗАЛ АРЕНДОВАН!");
        film.ShowInf();
        System.out.println("Тип зала: "+cinema.getOneHall().getTypeOfHall() );
        System.out.println(" Начало сеанса: "+timeOfBeginning+", конец сеанса: "+timeOfEnding+", максимальная цена билета: "+cinema.getOneHall().getMaxCost());
        System.out.println(" Цена аренды зала: "+ cinema.getOneHall().CostOfRent());
        System.out.println(" Информация о кинотеатре.  Кинотеатр \""+ cinema.getName()+"\" по адресу: "+cinema.getAdress());
        if (cinema.getOneHall().isRented()) System.out.println("ВНИМАНИЕ! ЭТОТ ЗАЛ АРЕНДОВАН!");
    }
    public void UpdateInformation(){
        cinema.UpdateInformation();
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getTimeOfEnding() {
        return timeOfEnding;
    }

    public void setTimeOfEnding(String timeOfEnding) {
        this.timeOfEnding = timeOfEnding;
    }

    public String getTimeOfBeginning() {
        return timeOfBeginning;
    }
    public void DecramentNumberOfSession(){
        NumOfSession--;
        cinema.DecrementNumberOfSession();
    }

    public void setTimeOfBeginning(String timeOfBeginning) {
        this.timeOfBeginning = timeOfBeginning;
    }
}

