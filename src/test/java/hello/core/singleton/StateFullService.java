package hello.core.singleton;

public class StateFullService {
    private int price; // 상태를 유지하는 필드
    public void order(String name,int price){
        System.out.println(name+" "+price);
        this.price=price;
    }
    public int getPrice(){
        return price;
    }
}
