package cn.chronoswap.chronoswap.UI;

/**
 * Created by Shi on 2018/2/28.
 */

public class lst {
    private String item_name;
    private String item_place;
    private String item_time;
    public lst(String item_name,String item_place,String item_time){
        this.item_name=item_name;
        this.item_place=item_place;
        this.item_time=item_time;
    }
    public String getItem_name(){
        return item_name;
    }
    public String getItem_place(){
        return item_place;
    }
    public String getItem_time(){
        return item_time;
    }
}
