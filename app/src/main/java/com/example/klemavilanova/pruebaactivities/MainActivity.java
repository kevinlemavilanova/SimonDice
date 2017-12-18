package com.example.klemavilanova.pruebaactivities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> history = null;
    Button red = null;
    Button yellow = null;
    Button blue = null;
    TextView Puntos = null;
    Button green = null;
    Boolean next = true;
    ArrayList<String> PlayerHistory = new ArrayList<String>();
    Boolean hasNext = true;
    int cont = 0;

    public void restaurar() {
        red.setBackgroundColor(Color.parseColor("#ff6d6d"));
        yellow.setBackgroundColor(Color.parseColor("#f4ff84"));
        green.setBackgroundColor(Color.parseColor("#9ffc8a"));
        blue.setBackgroundColor(Color.parseColor("#71c9fc"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        red = (Button) findViewById(R.id.red);
        yellow = (Button) findViewById(R.id.yellow);
        blue = (Button) findViewById(R.id.blue);
        green = (Button) findViewById(R.id.green);
        Puntos = (TextView) findViewById(R.id.points);;
        history = new ArrayList<String>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String RandomColor() {
        int n = (int) Math.floor(Math.random() * (5 - 1)) + 1;
        String colorSelect = "";
        switch (n) {
            case 1:
                colorSelect = "red";
                break;
            case 2:
                colorSelect = "blue";
                break;
            case 3:
                colorSelect = "yellow";
                break;
            case 4:
                colorSelect = "green";
                break;
        }
        return colorSelect;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.red: {
                PlayerHistory.add("red");
                markColor("red");
                if (history.size() == PlayerHistory.size()) {
                    Comprobar();
                } else if (history.size() <= PlayerHistory.size()) {
                    System.out.println("Esperando entrada");
                } else {
                    System.out.println("Perdiste");
                }
                break;
            }
            case R.id.blue: {
                PlayerHistory.add("blue");
                markColor("blue");
                if (history.size() == PlayerHistory.size()) {
                    Comprobar();
                } else if (history.size() <= PlayerHistory.size()) {
                    System.out.println("Esperando entrada");
                } else {
                    System.out.println("Perdiste");
                }
                break;
            }
            case R.id.yellow: {
                PlayerHistory.add("yellow");
                markColor("yellow");
                if (history.size() == PlayerHistory.size()) {
                    Comprobar();
                } else if (history.size() <= PlayerHistory.size()) {
                    System.out.println("Esperando entrada");
                } else {
                    System.out.println("Perdiste");
                }
                break;
            }
            case R.id.green: {
                PlayerHistory.add("green");
                markColor("green");
                if (history.size() == PlayerHistory.size()) {
                    Comprobar();
                } else if (history.size() <= PlayerHistory.size()) {
                    System.out.println("Esperando entrada");
                } else {
                    System.out.println("Perdiste");
                }
                break;
            }
            case R.id.start: {
                Start();
                break;
            }

        }
    }


    public void MostrarHistory() {
        new Thread() {
            public void run() {
                if (cont > 1) {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (final String n : history) {
                    BackgroundTask cambio = new BackgroundTask();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            markColor(n);
                        }
                    });
                    try {
                        cambio.get(2000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void markColor(String color) {
        BackgroundTask cambio = new BackgroundTask();
        switch (color) {
            case "red": {
                red.setBackgroundColor(Color.RED);
                cambio.execute();
                break;
            }
            case "blue": {
                blue.setBackgroundColor(Color.BLUE);
                cambio.execute();
                break;
            }
            case "yellow": {
                yellow.setBackgroundColor(Color.YELLOW);
                cambio.execute();
                break;
            }
            case "green": {
                green.setBackgroundColor(Color.GREEN);
                cambio.execute();
                break;
            }
        }
    }

    public void Comprobar() {
        for (int x = 0; x < history.size() - 1; x++) {
            if (history.get(x) != PlayerHistory.get(x)) {
                next = false;
                cont = 0;
                history = new ArrayList<String>();
                break;
            }
        }
        cont+=10;
        Puntos.setText("Puntos: "+ cont);
        PlayerHistory = new ArrayList<String>();
        Start();
    }


    public void Start() {
        history.add(RandomColor());
        MostrarHistory();
    }

    private class BackgroundTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            new Thread() {
                public void run() {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            restaurar();
                        }
                    });
                }
            }.start();
            return null;
        }
    }


}
