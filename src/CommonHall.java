public class CommonHall extends Hall{
    private String TypeOfHall="Обычный";
    CommonHall(int Rows, int Places, int MaximumCost, int NewNumberOfHall){
        // CountOfRows=Rows;
        // CountOfPlacesInRow=Places;
        // MaxCost=MaximumCost;
        // Generator (Rows,Places,MaximumCost);
        // TypeOfHall=NewType;
        super( Rows,  Places,  (int)(MaximumCost),  "обычный",NewNumberOfHall);
    }
    CommonHall (CommonHall NewHall){
        // CountOfRows=NewHall.getCountOfRows();
        //  CountOfPlacesInRow=NewHall.getCountOfPlacesInRow();
        // MaxCost=NewHall.getMaxCost();
        //  Generator (CountOfRows,CountOfPlacesInRow,NewHall.getMaxCost());
        //TypeOfHall=NewHall.getTypeOfHall();
        super(NewHall);
    }
}
