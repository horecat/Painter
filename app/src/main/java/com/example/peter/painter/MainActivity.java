package com.example.peter.painter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends Activity {
    // Třída pro vykreslování. Dědí "View"
    private MyCanvas myCanvas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Přepnutí zobrazování na naši vlastní třídu
        myCanvas = new MyCanvas(this);
        setContentView(myCanvas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Práce s menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // získání ID vybrané položky
        switch (item.getItemId()) {
            // Ukončení aplikace
            case R.id.exit:
                this.finish();
                return true;

            // Vyčištění plátna
            case R.id.clear:
                myCanvas.clearCanvas();
                return true;

            // Nastavení farby pera
            case R.id.pen_color:
                final HashMap<String, Integer> colorList = new HashMap<String, Integer>();
                colorList.put("Čierna", Color.BLACK);
                colorList.put("Červená", Color.RED);
                colorList.put("Žltá", Color.YELLOW);
                colorList.put("Zelená", Color.GREEN);
                colorList.put("Modrá", Color.BLUE);
                colorList.put("Fialová", Color.MAGENTA);
                colorList.put("Šedá", Color.GRAY);
                final CharSequence[] colors = colorList.keySet().toArray(new CharSequence[colorList.size()]);
                // Vytvoření dialogu
                AlertDialog.Builder colorPickerDialog = new AlertDialog.Builder(this);
                colorPickerDialog.setTitle("Vyber farbu pera:");
                colorPickerDialog.setItems(colors, new DialogInterface.OnClickListener() {
                    // Po zvolení se zobrazí informace o vybrané položce a zavolá se příslušná metoda, která nastaví vybranou vlastnost
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), "Vybraná farba pera: " + colors[item], Toast.LENGTH_SHORT).show();
                        myCanvas.setPenColor(colorList.get(colors[item]));
                    }
                });
                AlertDialog pickColor = colorPickerDialog.create();
                pickColor.show();
                return true;

            // Nastavenie farby pozadia
            case R.id.background_color:
                final HashMap<String, Integer> colorBackgroundList = new HashMap<String, Integer>();
                colorBackgroundList.put("Čierna", Color.BLACK);
                colorBackgroundList.put("Červená", Color.RED);
                colorBackgroundList.put("Žltá", Color.YELLOW);
                colorBackgroundList.put("Zelená", Color.GREEN);
                colorBackgroundList.put("Modrá", Color.BLUE);
                colorBackgroundList.put("Fialová", Color.MAGENTA);
                colorBackgroundList.put("Šedá", Color.GRAY);
                colorBackgroundList.put("Biela", Color.WHITE);
                final CharSequence[] colorsBackground = colorBackgroundList.keySet().toArray(new CharSequence[colorBackgroundList.size()]);
                // Vytvoření dialogu
                AlertDialog.Builder colorPickerDialogBackground = new AlertDialog.Builder(this);
                colorPickerDialogBackground.setTitle("Vyber farbu pozadia:");
                colorPickerDialogBackground.setItems(colorsBackground, new DialogInterface.OnClickListener() {
                    // Po zvolení se zobrazí informace o vybrané položce a zavolá se příslušná metoda, která nastaví vybranou vlastnost
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), "Vybraná farba pozadia: " + colorsBackground[item], Toast.LENGTH_SHORT).show();
                        myCanvas.setBackgroundColor(colorBackgroundList.get(colorsBackground[item]));
                    }
                });
                AlertDialog pickColorBackground = colorPickerDialogBackground.create();
                pickColorBackground.show();
                return true;

            // Nastevenie hrúbky pera
            case R.id.pen_width:
                final CharSequence[] sizes = {"1", "3", "5", "10", "15", "20"};
                AlertDialog.Builder sizePickerDialog = new AlertDialog.Builder(this);
                sizePickerDialog.setTitle("Vyber hrúbku pera:");
                sizePickerDialog.setItems(sizes, new DialogInterface.OnClickListener() {
                    // Po zvolení se zobrazí informace o vybrané položce a zavolá se příslušná metoda, která nastaví vybranou vlastnost
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), "Vybraná hrúbka pera: " + sizes[item] + "px", Toast.LENGTH_SHORT).show();
                        myCanvas.setPenWidth(Float.valueOf((String) sizes[item]));
                    }
                });
                AlertDialog pickSize = sizePickerDialog.create();
                pickSize.show();
                return true;

            // Zmazanie naposledy nakreslenej ciary
            case R.id.zmazat_poslednu_ciaru:
                myCanvas.zmazPoslednuCiaru();
                return true;

            // Reset hodnot
            case R.id.reset_hodnot:
                myCanvas.reset();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}