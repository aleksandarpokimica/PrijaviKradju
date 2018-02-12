package com.aleksandarpokimica.prijavikradju;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {

    EditText etMesto, etUlica, etOpis;
    Spinner spElektrodistribucija;
    ImageButton ivGalerija, ivKamera;
    Button bSubmit;
    public static Uri imageUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivGalerija = (ImageButton) findViewById(R.id.ivGalerija);
        ivGalerija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select content image"), 1);
            }
        });

        ivKamera = (ImageButton) findViewById(R.id.ivKamera);
        ivKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        bSubmit = (Button) findViewById(R.id.bSubmit);
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etMesto = (EditText) findViewById(R.id.etMesto);
                etUlica = (EditText) findViewById(R.id.etUlica);
                etOpis = (EditText) findViewById(R.id.etOpis);

                Intent intent = null, chooser = null;

                intent = new Intent(Intent.ACTION_SEND);

                intent.setData(Uri.parse("mailto:"));
                String[] to = {"aco1992@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Prijava kvara");
                intent.putExtra(Intent.EXTRA_TEXT, "Elektrodistribucija: " + spElektrodistribucija + "\nMesto neovlascene potrosnje: " +
                        etMesto.getText().toString() + "\nUlica i broj: " + etUlica.getText().toString() + "\nOpis: " + etOpis.getText().toString());
                intent.setType("message/rfc822");

                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                chooser = Intent.createChooser(intent, "Send email");
                startActivity(chooser);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){

            if(requestCode == 1){

                imageUri = data.getData();

            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageUri = data.getData();
        }
    }
}
