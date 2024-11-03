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
        textView.setTextAppearance(R.style.blueText);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setGravity(1);

        return textView;
    }

    public static LinearLayout createBasicButtonLayout(Context context, String text, float textSize) {

        LinearLayout buttonLayout = new LinearLayout(context);

        buttonLayout.setGravity(1);
        buttonLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_top_button));
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setElevation(3f);
        buttonLayout.setPadding(0, 20, 0, 20);

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
