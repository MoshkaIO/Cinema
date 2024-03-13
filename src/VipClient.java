public class VipClient implements ClientStatus{
    private double Discoint=0.8;
    public static final int Target=7; //сколько покупок нужно для достижения
    private String StatusName="VIP-клиент";
    public int PaymentWithDiscount(int cash){

        return (int)(cash*Discoint);
    }
    public void ClientPerks(){
        System.out.println("Билет приобретается со скидкой "+Math.ceil((1-Discoint)*100)+"%");
        System.out.print("Как VIP клиенту вам положен бесплатный напиток.");
        System.out.println("Вы сможете забрать его в нашем буфете!");
    }

    public double getDiscoint() {
        return Discoint;
    }
    public int getTarget(){ return Target;}
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
