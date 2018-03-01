package cn.chronoswap.chronoswap.db;

import android.content.Context;

/**
 * Created by Shi on 2018/3/1.
 */

public class lstDataSplit {
    private String originString;
//    private String[] outerString;
//    private String[][] innerString;
    public void setOriginString(String originString){
        this.originString=originString;
    }
    public String getOriginString(){
        return this.originString;
    }
//    public void setOuterString(String[] outerString){
//        this.outerString=outerString;
//    }
//    public String[] getOuterString(){
//        return this.outerString;
//    }
//    public void setInnerString(String[][] innerString){
//        this.innerString=innerString;
//    }
//    public String[][] getInnerString(){
//        return this.innerString;
//    }
//    public static String getSplited(String string, int m, int n){
//        String[] outerString=string.split("&");
//        String[][] innerString=new String[100][];
//        for(int i=0;i<outerString.length;i++){
//            innerString[i] = outerString[i].split("&");
//        }
//        return innerString[m][n];
//        return string;
//    }
}
