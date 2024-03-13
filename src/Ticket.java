public class Ticket {
    Film FilmOfTicket;
    protected String OwnerOfPlace="";
    protected int NumberOfRow;
    protected int NumberOfPlace;
    protected int Cost;
    protected int NumberOfSession=0;
    Ticket (int row,int place, int cost, int NewNum) {
        NumberOfRow=row;
        NumberOfPlace=place;
        Cost=cost;
        NumberOfSession=NewNum;

    }
    Ticket(){}
    public int getNumberOfRow() {
        return NumberOfRow;
    }
    public void setNumberOfRow(int numberOfRow) {
        NumberOfRow = numberOfRow;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public int getNumberOfSession() {
        return NumberOfSession;
    }

    public void setNumberOfSession(int numberOfSession) {
        NumberOfSession = numberOfSession;
        FilmOfTicket=Main.SessionNum.get(NumberOfSession).getFilm();
    }


    public int getNumberOfPlace() {
        return NumberOfPlace;
    }

    public void setNumberOfPlace(int numberOfPlace) {
        NumberOfPlace = numberOfPlace;
    }
}
