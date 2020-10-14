package com.example.peter.painter;

import android.graphics.Color;
import android.graphics.Paint;

public class MyPaint extends Paint {

    public MyPaint() {
        setAntiAlias(true);
        setColor(Color.BLACK);
        setStyle(Paint.Style.STROKE);
        setStrokeCap(Paint.Cap.ROUND);
        setStrokeJoin(Paint.Join.ROUND);
        setStrokeMiter(.5f);
        setStrokeWidth(5);
    }

}