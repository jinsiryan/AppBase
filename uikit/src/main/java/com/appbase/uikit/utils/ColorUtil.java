package com.appbase.uikit.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Size;

public final class ColorUtil {
    public static float SECOND_COLOR_ALPHA_RATIO = 0.7f;

    public static int MIDDLE_GRAY = convertColorToGray(Color.WHITE) * 3 / 4;

    public static int HsvToColor(float h, int s, int v) {
        return HsvToColor(0xff, h, s, v);
    }

    public static int HsvToColor(int alpha, float h, float s, float v) {
        float[] hsv = new float[]{h, s / 100f, v / 100f};
        return Color.HSVToColor(alpha, hsv);
    }

    public static @Size(3) float[] rgbToHsv(int color) {
        float[] hsv = new float[3];
        Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsv);
        return hsv;
    }

    public static int getLowerColor(int color) {
        float[] hsv = rgbToHsv(color);
        return HsvToColor(hsv[0], ((int) (hsv[1] * 0.6f * 100)), (int)(hsv[2] * 100f));
    }

    public static Drawable setColorTindCompat(Drawable drawable, @ColorInt int color) {
        if (drawable != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable.setTintList(createColorStateList(color));
            } else {
                drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
        }
        return drawable;
    }

    public static int parseColor(String colorStr, int defColor) {
        if (TextUtils.isEmpty(colorStr)) {
            return defColor;
        }
        int color = defColor;
        try {
            colorStr = colorStr.toLowerCase();
            if (colorStr.charAt(0) == '#') {
                color = Color.parseColor(colorStr);
            }else if(colorStr.startsWith("0x")){
                color = Color.parseColor("#" + colorStr.replace("0x",""));
            }else {
                color = Color.parseColor("#" + colorStr);
            }
        } catch (Exception e) {

        }
        return color;
    }

    public static boolean isDarkColor(int color) {
        int i = convertColorToGray(color);
        return i <= MIDDLE_GRAY;
    }

    public static int convertColorToGray(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        return (red * 313524 + green * 615514 + blue * 119538) >> 20;
    }
    public static int convertColorAlpha(int color,int alpha){
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }
    public static ColorStateList createColorStateList(int normal, int pressed, int focused, int unable, int selected) {
        int[] colors = new int[] { pressed, focused, normal, focused, unable, selected,normal };
        int[][] states = new int[7][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_focused };
        states[4] = new int[] { android.R.attr.state_window_focused };
        states[5] = new int[] { android.R.attr.state_selected };
        states[6] = new int[] { };
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }
    public static ColorStateList createColorStateList(int firstColor, int secondColor){
        return createColorStateList(firstColor,secondColor,firstColor,secondColor,secondColor);
    }
    public static ColorStateList createColorStateList(int color){
        int pressed = getSecondColor(color);
        return createColorStateList(color,pressed,color,pressed,pressed);
    }
    public static int getSecondColor(int color){
        return ColorUtil.convertColorAlpha(color, (int) (Color.alpha(color) * SECOND_COLOR_ALPHA_RATIO));
    }
    public static String getColorHtml(int color) {
        return "#" + String.format("%06X", (0xFFFFFF & color));
    }
    public static void setViewPressState(View view, boolean isPress){
        if(isPress){
            view.setAlpha(0.6f);
        }else {
            view.setAlpha(1f);
        }
    }

}
