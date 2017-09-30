package gameloft.com.easyorder_server;

/**
 * Created by Minh Hoang on 9/30/2017.
 */

public class Food {
    private String foodName;
    private String imageName;
    private int price;
    public Food(String a,String b,int c)
    {
        foodName=a;
        imageName=b;
        price=c;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImageName() {
        return imageName;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodName='" + foodName + '\'' +
                ", imageName='" + imageName + '\'' +
                ", price=" + price +
                '}';
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {

        return price;
    }

    public void setImageName(String imageName) {

        this.imageName = imageName;
    }
}
