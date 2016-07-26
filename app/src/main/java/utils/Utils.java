package utils;

import java.text.DecimalFormat;

/**
 * Created by jere on 7/23/2016.
 */
public class Utils {
    public static String formatNumber(int value){
        DecimalFormat formatter= new DecimalFormat("#,###,###");
        String formatted=formatter.format(value);
        return formatted;
    }
}
