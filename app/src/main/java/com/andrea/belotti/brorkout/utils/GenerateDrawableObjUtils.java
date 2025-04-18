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

    public static LinearLayout createBasicButtonLayout(Context context, String text, float textSize) {

        LinearLayout buttonLayout = new LinearLayout(context);

        buttonLayout.setGravity(1);
        buttonLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_top_button));
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setElevation(3f);
        buttonLayout.setPadding(0, 40, 0, 40);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 20, 0, 10);

        buttonLayout.setLayoutParams(params);

        TextView textView = createBasicTextView(context, text, textSize);
        buttonLayout.addView(textView);

        return buttonLayout;
    }


    public static LinearLayout createWrapButtonLayout(Context context, String text) {

        LinearLayout buttonLayout = new LinearLayout(context);

        buttonLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_top_button));
        buttonLayout.setOrientation(LinearLayout.VERTICAL);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        buttonLayout.setLayoutParams(params);

        text = text.charAt(0) + text.substring(1).toLowerCase();

        TextView textView = createBasicTextView(context, text, 20f);
        textView.setTextSize(15); //TODO fare costanti per il size da usare almeno sono sempre le stesse, posso anche usarle negli style

        Resources r = context.getResources();
        int pxX = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                15,
                r.getDisplayMetrics()
        );

        int pxY = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                15,
                r.getDisplayMetrics()
        );

        textView.setPadding(pxX, pxY, pxX, pxY);
        buttonLayout.addView(textView);

        return buttonLayout;
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

    public static LinearLayout createBasicCardView(Context context) {

        GridLayout.LayoutParams param = new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f));
        param.height = 0;
        param.width = 0;

        LinearLayout cardView = new LinearLayout(context);
        cardView.setGravity(Gravity.CENTER);
        cardView.setLayoutParams(param);

        return cardView;
    }


}
