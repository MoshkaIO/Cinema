public class VipHall extends Hall{
    private String TypeOfHall="ВИП";
    VipHall(int Rows, int Places, int MaximumCost, int NewNumberOfHall){
       // CountOfRows=Rows;
       // CountOfPlacesInRow=Places;
       // MaxCost=MaximumCost;
       // Generator (Rows,Places,MaximumCost);
       // TypeOfHall=NewType;
        super( Rows,  Places,  (int)(MaximumCost*1.5),  "ВИП", NewNumberOfHall);
    }
    VipHall (VipHall NewHall){
       // CountOfRows=NewHall.getCountOfRows();
      //  CountOfPlacesInRow=NewHall.getCountOfPlacesInRow();
       // MaxCost=NewHall.getMaxCost();
      //  Generator (CountOfRows,CountOfPlacesInRow,NewHall.getMaxCost());
        //TypeOfHall=NewHall.getTypeOfHall();
        super(NewHall);
    }
    VipHall(){}
}
