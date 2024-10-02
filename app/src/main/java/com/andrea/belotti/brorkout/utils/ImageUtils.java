package com.andrea.belotti.brorkout.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageUtils {

    public static String convertImgToString(Bitmap selectedImageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap covertFromStringToBitmap(String imageData) {
        if (imageData != null && !imageData.isEmpty()) {
            byte[] b = Base64.decode(imageData, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
}
