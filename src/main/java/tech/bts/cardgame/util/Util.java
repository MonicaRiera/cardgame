package tech.bts.cardgame.util;

import java.util.Collection;

public class Util {

    public static String joinStrings(Collection<String> texts, String separator) {
        String result = "";
        for (Object text : texts) {
            result += text + " " + separator + " ";
        }
        return result;
    }

}
