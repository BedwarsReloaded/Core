package com.exortions.bedwarsreloaded.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {

    public static String[] subArray(String[] array) {
        List<String> ls = new ArrayList<>(Arrays.asList(array));
        ls.remove(0);
        String str = "";
        for (String s : ls) {
            str = str.concat(s + " ");
        }
        str = str.substring(0, str.length()-1);
        return str.split("\\s");
    }

    public static String arrayToString(String[] array, int start) {
        String str = "";
        int i = 0;
        for (String s : array) {
            if (i >= start) str = str.concat(s + " ");
            i++;
        }
        return str.substring(0, str.length()-1);
    }

    public static String arrayToString(String[] array) {
        String str = "";
        for (String s : array) str = str.concat(s + " ");
        return str.substring(0, str.length()-1);
    }

}
