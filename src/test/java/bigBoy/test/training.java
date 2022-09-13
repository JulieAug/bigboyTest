package bigBoy.test;

import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * @program: zaoApiTest
 * @description: 测试学习
 * @author: zhuli
 * @create: 2021-03-12 17:17
 **/
public class training {

    /**
     * 冒泡排序
     */
    @Test
    public static void sortTest(){
        int num[] = {11,2,5,1,8,87,33,0};
        for(int i=1;i<num.length;i++){
            for(int j=0;j<num.length-1;j++){
                if(num[j]>num[j+1]){
                    int temp;
                    temp = num[j];
                    num[j] = num[j+1];
                    num[j+1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(num));
    }


    /**
     * Java递归实现字符串的倒序输出
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(reverse(new StringBuilder("abcd"), new StringBuilder(), 0).toString());
    }

    public static StringBuilder reverse(StringBuilder target,StringBuilder result,int index)
    {
        if (index<target.length()) {
            result = reverse(target, result, index+1).append(target.charAt(index));
            return result;
        }else {
            return result;
        }
    }
}
