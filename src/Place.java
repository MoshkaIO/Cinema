public class Place extends Ticket {
   // private int NumberOfRow;
  //  private int NumberOfPlace;
   // private int Cost;
    private boolean IsBought=false;
    //private String OwnerOfPlace="";

    Place () {}
    Place (int row,int place, int cost, int NumberOfSession) {
       //NumberOfRow=row;
       // NumberOfPlace=place;
       //Cost=cost;
        super ( row, place, cost,NumberOfSession);
    }
    public void BuyingPlace(String NewOwner) {
        IsBought=true;
        OwnerOfPlace=NewOwner;
    }

    public void ShowInf(){
        System.out.println("Билет на фильм \""+FilmOfTicket.getName()+"\"");
        System.out.println("Ряд "+(NumberOfRow+1) + " Место " + (NumberOfPlace+1));
        System.out.println("Номер сеанса: "+NumberOfSession);
        System.out.println("Время начала:"+Main.SessionNum.get(NumberOfSession).getTimeOfBeginning());
        System.out.println("Время конца:"+Main.SessionNum.get(NumberOfSession).getTimeOfEnding());
    }

    public boolean getIsBought() {
        return IsBought;
    }

    public void setBought(boolean bought) {
        IsBought = bought;
    }
}

