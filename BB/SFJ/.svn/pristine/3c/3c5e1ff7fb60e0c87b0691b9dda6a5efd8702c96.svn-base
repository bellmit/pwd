package com.tastebychance.sfj.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by shenbinghong on 2018/2/28.
 */

public class CloseableUtil {
    public static void close(Closeable closeable){
        if (closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
