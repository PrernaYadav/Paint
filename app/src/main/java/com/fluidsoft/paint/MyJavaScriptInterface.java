package com.fluidsoft.paint;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * Created by designer on 12/20/2017.
 */

public class MyJavaScriptInterface {
    private Context ctx;
    MyJavaScriptInterface(Context ctx) {
        this.ctx = ctx;
    }

    @JavascriptInterface
    public void showHTML(String html) {
        System.out.println(html);
    }
}
