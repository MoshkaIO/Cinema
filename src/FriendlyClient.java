public class FriendlyClient implements ClientStatus{
    private double Discoint=0.9;
    public static final int Target=3; //сколько покупок нужно для достижения
    //если сделать его private, то switch орёт как собака резаная
    private static String StatusName="Друг сети";
    public int PaymentWithDiscount(int cash){
        //System.out.println("Вам выдали бесплатный напиток!");
        return (int)(cash*Discoint);
    }
    public void ClientPerks(){
        System.out.println("Билет приобретается со скидкой "+Math.ceil((1-Discoint)*100)+"%");
        System.out.print("Как Другу сети вам должны улыбнуться на кассе. ");
        System.out.println("Всё :)");
        System.out.print("Чаще заглядывайте к нам в кино!");
        System.out.println("Начиная с 8-го посещения вы получите статус VIP-клиента!!1!");
    }
    public void Congrats(String NameFamilyPatr){
        System.out.println("Вот это да, " + NameFamilyPatr + " !");
        System.out.println("Вы сделали "+Target+"-ю покупку у нас, а значит получаете новый статус:");
        System.out.println("\""+getStatusName()+"\" !!");
        System.out.print("Вот ваши новые преимущества:");
        ClientPerks();
    }

    public double getDiscoint() {
        return Discoint;
    }

    public String getStatusName() {
        return StatusName;
    }

    public int getTarget() {
        return Target;
    }
}
