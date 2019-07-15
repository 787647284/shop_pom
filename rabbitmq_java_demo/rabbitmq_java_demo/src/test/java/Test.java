/**
 * linzebin
 * 时间2019/7/14 20:47
 */
public class Test {
    public static void main(String[] args) {
        int n = 5;
        String s="* ";
        boolean s1=true;
        for(int i=n,j=0;;){
            //打印空格
            for(int x=0;x<j;x++){
                System.out.print(" ");
            }
            //打印星星
            for(int y=i;y>0;y--){
                System.out.print(s);
            }
            System.out.println();
            if(i==1){
                s1=false;
            }
            //如果为true 则为倒序
            if(s1){
                i--;
                j++;
            }else{
                i++;
                j--;
                if(i>n){
                    break;
                }
            }
        }
    }
}
