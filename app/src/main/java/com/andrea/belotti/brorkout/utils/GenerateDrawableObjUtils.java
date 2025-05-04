package com.andrea.belotti.brorkout.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.andrea.belotti.brorkout.R;

public class GenerateDrawableObjUtils {

    private GenerateDrawableObjUtils() {
    }

    public static TextView createBasicTextView(Context context, String text, float textSize) {

        TextView textView = new TextView(context);
        textView.setTextAppearance(R.style.blue_text);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setGravity(1);
        textView.setPadding(20, 10, 20, 10);

        return textView;
    }

    public static LinearLayout createHorizotalLinearLayout(Context context, int marginTop, int marginLeft, int marginRight, int marginBottom) {

        LinearLayout horizontalLayout = new LinearLayout(context);

        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        Resources r = context.getResources();

        int realMT = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                marginTop,
                r.getDisplayMetrics()
        );

        int realMB = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                marginBottom,
                r.getDisplayMetrics()
        );

        int realMR = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                marginRight,
                r.getDisplayMetrics()
        );

        int realML = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                marginLeft,
                r.getDisplayMetrics()
        );

        params.setMargins(realML, realMT, realMR, realMB);

        horizontalLayout.setLayoutParams(params);

        return horizontalLayout;
    }

}
