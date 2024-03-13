public class CommonClient implements ClientStatus {
    private double Discoint=1.0;
    public static final int Target=0;
    private String StatusName="Посетитель";
    public int PaymentWithDiscount(int cash){
        //System.out.println("Вам выдали бесплатный напиток!");
        return (int)(cash*Discoint);
    }
    public void ClientPerks(){
        System.out.println("Ваш текущий статус: "+StatusName);
        System.out.println("Билет приобретается без скидки");
        System.out.print("Чаще заглядывайте к нам в кино, начиная с 4-го раза вы станете Другом сети!");
        System.out.println("А начиная с 8-го вы получите статус VIP-клиента!!1!");
    }

    public double getDiscoint() {
        return Discoint;
    }
    public int getTarget(){return Target;}
    public void Congrats(String NameFamilyPatr){
        System.out.println("Вот это да, " + NameFamilyPatr + " !");
        System.out.println("Вы сделали "+Target+"-ю покупку у нас, а значит получаете новый статус:");
        System.out.println("\""+getStatusName()+"\" !!");
        System.out.print("Вот ваши новые преимущества:");
        ClientPerks();
    }

    public String getStatusName() {
        return StatusName;
    }
}
