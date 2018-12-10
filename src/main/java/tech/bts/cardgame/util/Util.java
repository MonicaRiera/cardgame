package tech.bts.cardgame.util;

import java.util.Collection;
import java.util.List;

public class Util {

    public static String joinStrings(Collection<String> texts, String separator) {
        return ((List<String>) texts).get(0) + " " + separator + " " + ((List<String>) texts).get(1);
    }

}
