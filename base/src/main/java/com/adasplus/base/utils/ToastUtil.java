package com.adasplus.base.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;

    //    public static void showToast(Context context,
//                                 String content) {
//        if (toast == null) {
//            toast = Toast.makeText(context,
//                    content, Toast.LENGTH_SHORT);
//        } else {
//            toast.setText(content);
//        }
//        toast.show();
//    }
    public static void showToast(Context context,
                                 String content) {
        Toast.makeText(context,
                content, Toast.LENGTH_SHORT).show();
    }

//    public static void showToast(Context context,
//                                 int content) {
//        if (toast == null) {
//            toast = Toast.makeText(context,
//                    content,
//                    Toast.LENGTH_SHORT);
//        } else {
//            toast.setText(content);
//        }
//        toast.show();
//    }

    public static void showToast(Context context,
                                 int content) {
        Toast.makeText(context,
                content,
                Toast.LENGTH_SHORT).show();
    }
}
