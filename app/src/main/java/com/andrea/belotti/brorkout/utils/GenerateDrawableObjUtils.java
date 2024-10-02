package com.andrea.belotti.brorkout.utils;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrea.belotti.brorkout.R;

import androidx.core.content.ContextCompat;

public class GenerateDrawableObjUtils {

    private GenerateDrawableObjUtils() {}

    public static TextView createBasicTextView(Context context, String text) {

        TextView textView = new TextView(context);
        textView.setTextAppearance(R.style.blueText);
        textView.setText(text);
        textView.setGravity(1);

        return textView;
    }

    public static LinearLayout createBasicButtonLayout(Context context, String text) {

        LinearLayout buttonLayout = new LinearLayout(context);

        buttonLayout.setGravity(1);
        buttonLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_top_button));
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setElevation(6f);
        buttonLayout.setPadding(0, 20, 0, 20);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 10);

        buttonLayout.setLayoutParams(params);


        TextView textView = createBasicTextView(context, text);
        buttonLayout.addView(textView);

        return buttonLayout;
    }
}
