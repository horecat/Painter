package com.example.peter.painter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MyCanvas extends View {
    // Služí k ukladaniu souradnic cesty.
    private Path path = new Path();
    private List<Path> paths = new ArrayList<Path>();
    // Obsahuje nastavenie pera.
    private MyPaint paint = new MyPaint();
    private List<MyPaint> paints = new ArrayList<MyPaint>();
    // Detekuje zmenu atributu pera.
    private boolean zmenaAtributuPera = false;
    // Detekuje pridavanie kruhu.
    private boolean pridavanieKruhu = false;
    // Detekuje pridavanie obdlznika.
    private boolean pridavanieObdlznika = false;
    // Ulozenie farby pozadia
    private int farbaPozadia = Color.WHITE;

    //Radius kruhu.
    private float radius;

    // Atributy pre ukladanie pozicie dotyku
    private float xPos;
    private float yPos;
    // Atributy pociatocnej pozicie pri dotyku
    private float xZac;
    private float yZac;

    public MyCanvas(Context context) {
        super(context);
    }

    /**
     * Vykreslovanie.
     */
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
        for (int p = 0; p < paths.size(); p++) {
            canvas.drawPath(paths.get(p), paints.get(p));
        }
    }

    /**
     * Obsluha udalosti kliku (dotyku) obrazovky
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Počet prstov, ktorými se dotýkam obrazovky. Maximum je (asi v závislosti na zariadení) limitovane na 4
        final int pointersCount = event.getPointerCount();
        // Prejde všetky pointery - umožňuje multi-touch
        for (int p = 0; p < pointersCount; p++) {
            xPos = event.getX(p);
            yPos = event.getY(p);

            // Spracovanie akcii
            switch (event.getAction()) {
                // Pri dotyku, sa vyresetuje východzia pozicia
                case MotionEvent.ACTION_DOWN:
                    path = new Path();
                    if (pridavanieKruhu) {    //pridavanie kruhu
                       // radius = 10;
                        xZac = xPos;
                        yZac = yPos;
                    } else if (pridavanieObdlznika) {    //pridavanie obdlznika
                        xZac = xPos;
                        yZac = yPos;
                    } else {      //pridavanie ciary
                        path.moveTo(xPos, yPos);
                        path.lineTo(xPos + 1, yPos + 1);
                    }
                    break;

                // Pri pohybu alebo opustení obrazovky
                case MotionEvent.ACTION_MOVE:
                    if (pridavanieKruhu) {    //vykreslenie kruhu
                        path.reset();
                       // radius++;
                        radius = (float) Math.sqrt(Math.pow((xPos - xZac), 2) + Math.pow((yPos - yZac), 2));
                        path.addCircle(xZac, yZac, radius, Path.Direction.CW);
                    } else if (pridavanieObdlznika) {    //vykreslenie obdlznika
                        path.reset();
                        if (xZac > xPos) {    //prehodenie (swapnutie) X-ovych suradnic
                            float temp = xPos;
                            xPos = xZac;
                            xZac = temp;
                        }
                        if (yZac > yPos) {    //prehodenie (swapnutie) Y-ovych suradnic
                            float temp = yPos;
                            yPos = yZac;
                            yZac = temp;
                        }
                        path.addRect(xZac, yZac, xPos, yPos, Path.Direction.CW);
                    } else {     //vykreslenie ciary
                        path.lineTo(xPos, yPos);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    // ulozenie suradnic a pera do zoznamu (ArrayList-u)
                    paths.add(path);
                    paints.add(paint);
                    path = new Path();
                    pridavanieKruhu = false;
                    pridavanieObdlznika = false;
                    break;
            }
        }

        // Prekreslenie
        invalidate();
        return true;
    }

    public void pridajKruh() {
        pridavanieKruhu = true;
    }

    public void pridajObdlznik() {
        pridavanieObdlznika = true;
    }

    /**
     * Vyčistí kresliace plátno. (Vymaže všetky suradnice a prekreslí)
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
        farbaPozadia = Color.WHITE;
        setBackgroundColor(farbaPozadia);
        invalidate();
    }

    public void zmazPoslednyObjekt() {
        zmenaAtributuPera = true;
        if (paths.size() > 0) {
            paths.remove(paths.size() - 1);
            paints.remove(paints.size() - 1);
        }
        invalidate();
    }

    /**
     * Nastavenie farby pera
     * @param color Barva pera - je mozne použit Color.FARBA
     */
    public void setPenColor(int color) {
        zmenaAtributuPera = true;
        float odpamatanieSirky = paint.getStrokeWidth();
        final int odpamatanieFarby = paint.getColor();
        paint = new MyPaint();
        paint.setStrokeWidth(odpamatanieSirky);
        if (color == MainActivity.VYBER_Z_PALETY) {
            AmbilWarnaDialog dialog = new AmbilWarnaDialog(getContext(), odpamatanieFarby, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    // color is the color selected by the user.
                    paint.setColor(color);
                    invalidate();
                }

                @Override
                public void onCancel(AmbilWarnaDialog dialog) {
                    // cancel was selected by the user
                    paint.setColor(odpamatanieFarby);
                    invalidate();
                }
            });
            dialog.show();

        } else {
            paint.setColor(color);
            invalidate();
        }
    }

    /**
     * Nastavenie hrubky
     * @param width Hrubka pera
     */
    public void setPenWidth(float width) {
        zmenaAtributuPera = true;
        int odpamatanieFarby = paint.getColor();
        paint = new MyPaint();
        paint.setColor(odpamatanieFarby);
        paint.setStrokeWidth(width);
        invalidate();
    }

    public void setFarbaPozadia(int color) {
        if (color == MainActivity.VYBER_Z_PALETY) {
            AmbilWarnaDialog dialog = new AmbilWarnaDialog(getContext(), farbaPozadia, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    // color is the color selected by the user.
                    farbaPozadia = color;
                    setBackgroundColor(farbaPozadia);
                    invalidate();
                }

                @Override
                public void onCancel(AmbilWarnaDialog dialog) {
                    // cancel was selected by the user
                    invalidate();
                }
            });
            dialog.show();

        } else {
            farbaPozadia = color;
            setBackgroundColor(farbaPozadia);
            invalidate();
        }
    }
}