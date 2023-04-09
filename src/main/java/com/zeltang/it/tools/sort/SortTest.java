package com.zeltang.it.tools.sort;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

/**
 * 排序：https://zhuanlan.zhihu.com/p/60152722
 */
public class SortTest {

    private static int[] arr = new int[]{2,3,1,1,9,7,8,4,5,6};

    /**
     * 冒泡排序：
     * 1.从第一个元素开始，比较相邻的两个元素。如果第一个比第二个大，则进行交换
     * 2.轮到下一组相邻元素，执行同样的比较操作，再找下一组，直到没有相邻元素可比较为止，此时最后的元素应是最大的数
     * 3.除了每次排序得到的最后一个元素，对剩余元素重复以上步骤，直到没有任何一对元素需要比较为止
     */
    @Test
    public void bubbleSort () {
        if (arr == null) {
            throw new NullPointerException();
        }
        if (arr.length < 2) {
            return;
        }
        int temp = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j+1]) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        System.out.println(JSON.toJSONString(arr));
    }

    /**
     * 快速排序：先把待排序的数组拆成左右两个区间，左边都比中间的基准数小，右边都比基准数大。接着左右两边各自再做同样的操作，完成后再拆分再继续，一直到各区间只有一个数为止
     */
    @Test
    public void quicksort () {


    }


}
