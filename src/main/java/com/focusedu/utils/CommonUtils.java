package com.focusedu.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * CommonUtils
 *
 * @author liuruichao
 * @date 15/9/13 下午4:45
 */
public final class CommonUtils {
    public static String[] convertCollectionToArr(Collection<String> collection) {
        String[] keys = new String[collection.size()];
        Iterator<String> iterator = collection.iterator();
        for (int i = 0; i < collection.size(); i++) {
            keys[i] = iterator.next();
        }
        return keys;
    }
}
