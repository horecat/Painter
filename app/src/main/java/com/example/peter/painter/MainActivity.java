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
    public static final int VYBER_Z_PALETY = 0;
    // Trieda pre vykreslovanie. Dedí od "View".
    private MyCanvas myCanvas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Prepnutie zobrazovania na moju vlastnú triedu.
        myCanvas = new MyCanvas(this);
        setContentView(myCanvas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Práca s menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // získanie ID vybranej položky
        switch (item.getItemId()) {
            // Ukončenie aplikacie
            case R.id.exit:
                this.finish();
                return true;

            // Vyčistenie plátna
            case R.id.clear:
                myCanvas.clearCanvas();
                return true;

            // Nastavenie farby pera
            case R.id.pen_color:
                final HashMap<String, Integer> colorList = new HashMap<String, Integer>();
                colorList.put("Čierna", Color.BLACK);
                colorList.put("Červená", Color.RED);
                colorList.put("Žltá", Color.YELLOW);
                colorList.put("Zelená", Color.GREEN);
                colorList.put("Modrá", Color.BLUE);
                colorList.put("Fialová", Color.MAGENTA);
                colorList.put("Šedá", Color.GRAY);
                colorList.put("Vyber z palety...", VYBER_Z_PALETY);
                final CharSequence[] colors = colorList.keySet().toArray(new CharSequence[colorList.size()]);
                // Vytvorenie dialogu
                AlertDialog.Builder colorPickerDialog = new AlertDialog.Builder(this);
                colorPickerDialog.setTitle("Vyber farbu pera:");
                colorPickerDialog.setItems(colors, new DialogInterface.OnClickListener() {
                    // Po zvolení sa zobrazí informacia o vybranej položke a zavolá sa příslušná metoda, ktorá nastaví vybranu vlastnost
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
                colorBackgroundList.put("Vyber z palety...", VYBER_Z_PALETY);
                final CharSequence[] colorsBackground = colorBackgroundList.keySet().toArray(new CharSequence[colorBackgroundList.size()]);
                // Vytvorenie dialogu
                AlertDialog.Builder colorPickerDialogBackground = new AlertDialog.Builder(this);
                colorPickerDialogBackground.setTitle("Vyber farbu pozadia:");
                colorPickerDialogBackground.setItems(colorsBackground, new DialogInterface.OnClickListener() {
                    // Po zvolení sa zobrazí informacia o vybranej položke a zavolá sa příslušná metoda, která nastaví vybranu vlastnost
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), "Vybraná farba pozadia: " + colorsBackground[item], Toast.LENGTH_SHORT).show();
                        myCanvas.setFarbaPozadia(colorBackgroundList.get(colorsBackground[item]));
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

            // Pridanie kruhu
            case R.id.pridat_kruh:
                myCanvas.pridajKruh();
                return true;

            // Pridanie obdlznika
            case R.id.pridat_obdlznik:
                myCanvas.pridajObdlznik();
                return true;

            // Zmazanie posledneho objektu
            case R.id.zmazat_posledny_objekt:
                myCanvas.zmazPoslednyObjekt();
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