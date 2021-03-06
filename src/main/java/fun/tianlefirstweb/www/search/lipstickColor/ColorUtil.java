package fun.tianlefirstweb.www.search.lipstickColor;

import java.awt.*;
import java.util.Locale;

public class ColorUtil {

    public static float[] HEXtoHSB(String hexColor){
        //TODO : handle invalid hex

        if(hexColor.startsWith("#")) hexColor = hexColor.substring(1);
        hexColor = hexColor.toLowerCase(Locale.ROOT);
        int r = HEXtoInteger(hexColor.substring(0,2));
        int g = HEXtoInteger(hexColor.substring(2,4));
        int b = HEXtoInteger(hexColor.substring(4,6));

        float[] hsb = new float[3];
        Color.RGBtoHSB(r,g,b,hsb);
        return hsb;
    }

    private static Integer HEXtoInteger(String hex){
        int tens, ones;
        if(hex.charAt(1) > '9') ones = hex.charAt(1) - 'a' + 10;
        else ones = hex.charAt(1) - '0';
        if(hex.charAt(0) > '9') tens = hex.charAt(0) - 'a' + 10;
        else tens = hex.charAt(0) - '0';
        return tens * 16 + ones;
    }

    public static boolean isValidHex(String hexColor) {
        if(hexColor.startsWith("#")){
            hexColor = hexColor.substring(1);
        }
        if(hexColor.length() != 6) return false;
        for(char c : hexColor.toCharArray()){
            if(!Character.isDigit(c) && !Character.isLetter(c)) return false;
        }
        return true;
    }
}
