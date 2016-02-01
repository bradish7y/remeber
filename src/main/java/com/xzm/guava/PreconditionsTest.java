package com.xzm.guava;


import com.google.common.base.Preconditions;

/**
 * 前置条件，用于方法前的参数检查
 *
 * Created by Bradish7Y on 15/4/23.
 */

public class PreconditionsTest {
    public static void main(String[] args) {
        // 检查是否为true
        Preconditions.checkArgument(1==1);
        // 异常信息
        Preconditions.checkArgument(1==1, "must be true");
        // 格式化异常信息
        Preconditions.checkArgument(1==1, "must be %s", "TRUE");

        String[]  strings = new String[]{"1", "2", "3"} ;
        // 检查索引值
        Preconditions.checkElementIndex(1, strings.length) ;
        // 检查位置值
        Preconditions.checkPositionIndex(2, 2) ;

        // 检查对象状态
        Preconditions.checkState(1==1);

        // 检查非空
        Preconditions.checkNotNull(args, "null") ;
    }
}
