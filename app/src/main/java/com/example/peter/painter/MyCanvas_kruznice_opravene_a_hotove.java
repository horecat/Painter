package com.example.peter.painter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyCanvas_kruznice_opravene_a_hotove extends View {
    // Slouží k ukládání souřadnic cesty.
    private Path path = new Path();
    private List<Path> paths = new ArrayList<Path>();
    // Obsahuje nastavení pera.
    private MyPaint paint = new MyPaint();
    private List<MyPaint> paints = new ArrayList<MyPaint>();
    // Detekuje zmenu atributu pera.
    private boolean zmenaAtributuPera = false;

    private float radius;

    public MyCanvas_kruznice_opravene_a_hotove(Context context) {
        super(context);
    }

    /**
     * Vykreslování.
     */
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
        for (int p = 0; p < paths.size(); p++) {
            canvas.drawPath(paths.get(p), paints.get(p));
        }
    }

    /**
     * Obsluha události kliku (dotyku) obrazovky
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Inicializace proměnných pro ukládání pozice dotyku
        float xPos;
        float yPos;

        // Počet prstů, kterými se dotýkáme obrazovky. Maximum je (asi v závislosti na zařízení) limitováno na 4
        final int pointersCount = event.getPointerCount();
        // Projede všechny pointery - umožňuje multi-touch
        for (int p = 0; p < pointersCount; p++) {
            xPos = event.getX(p);
            yPos = event.getY(p);
            //  x = event.getX(0);

            // Zpracování akcí
            switch (event.getAction()) {
                // Při dotyku, se vyresetuje výchozí pozice
                case MotionEvent.ACTION_DOWN:
                    path = new Path();
                    //   path.moveTo(xPos, yPos);
                    //   path.lineTo(xPos + 1, yPos + 1);
                    //   path.addCircle(xPos, yPos, 10, Path.Direction.CW);
                    radius = 10;
                    break;

                // Při pohybu nebo opuštění obrazovky
                case MotionEvent.ACTION_MOVE:
                    radius++;
                    path.reset();
                    path.addCircle(xPos, yPos, radius, Path.Direction.CW);
                    break;

                case MotionEvent.ACTION_UP:
                    paths.add(path);
                    paints.add(paint);
                    path = new Path();
                    //   path.reset();
                    //  path.addCircle(xPos, yPos, radius, Path.Direction.CW);
                    // Nastaví se současné souřadnice
                    //  path.lineTo(xPos, yPos);
                    break;
            }
        }

        // Překreslení
        invalidate();
        return true;
    }

    /**
     * Vyčistí kreslící plátno. (Vymaže všechny souřadnice a překreslí)
     */
    public void clearCanvas() {
        zmenaAtributuPera = true;
        paths.clear();
        paints.clear();

        path = new Path();
        paths.add(path);
        paints.add(paint);
        invalidate();
    }

    public void reset() {
        zmenaAtributuPera = true;
        paths.clear();
        paints.clear();

        path = new Path();
        paths.add(path);

        paint = new MyPaint();
        paints.add(paint);
        setBackgroundColor(Color.WHITE);
        invalidate();
    }

    public void zmazPoslednuCiaru() {
        zmenaAtributuPera = true;
        if (paths.size() > 0) {
            //  paths.get(paths.size() - 1).reset();
            paths.remove(paths.size() - 1);
            paints.remove(paints.size() - 1);
        }
        invalidate();
    }

    /**
     * Nastavení barvy pera
     * @param color Barva pera - lze použít Color.BARVA
     */
    public void setPenColor(int color) {
        zmenaAtributuPera = true;
        float odpamatanieSirky = paint.getStrokeWidth();
        paint = new MyPaint();
        paint.setStrokeWidth(odpamatanieSirky);
        paint.setColor(color);
        invalidate();
    }

    /**
     * Nastavení tloušťky
     * @param width Tloušťka pera
     */
    public void setPenWidth(float width) {
        zmenaAtributuPera = true;
        int odpamatanieFarby = paint.getColor();
        paint = new MyPaint();
        paint.setColor(odpamatanieFarby);
        paint.setStrokeWidth(width);
        invalidate();
    }

}
