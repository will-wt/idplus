package org.willwt.idplus.common;

/**
 * @author willwt
 * @date 2024/08/15 23:43
 */
public class AssertUtil {


    public static void isTrue(boolean expression, String message){
        if (!expression){
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression, String format, Object... args){
        if (!expression){
            throw new IllegalArgumentException(String.format(format, args));
        }
    }


    public static void notNull(Object obj, String message){
        if (obj == null){
            throw new IllegalArgumentException(message);
        }
    }


}
