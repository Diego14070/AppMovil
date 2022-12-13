package com.example.honeycumb.activities;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.honeycumb.R;
import com.example.honeycumb.activities.Providers.ImageProvider;
import com.example.honeycumb.activities.Utils.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class PostActivity extends AppCompatActivity {

    File mImageFile;

    ImageProvider mImageProvider;
    ImageView mImageGallery;
    TextInputEditText mTextInputTitle;
    TextInputEditText mTextinputDescription;
    TextView mTextInputcategory;
    String mCategory="";
    ImageView mImageDairy;
    ImageView mImageBeverages;
    ImageView mImageHouseHoldCleaning;
    ImageView mImageVegetables;
    Button mButtonPost;

    private final int Gallery_REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        /* metodo de seleccionar de galeria*/
        mImageGallery=findViewById(R.id.ImageViewGallery);
        mImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        mButtonPost=findViewById(R.id.buttonPost);

        //instanciar
        mImageProvider = new ImageProvider();//provider image
        mTextInputcategory=findViewById(R.id.Categoria);
        mTextInputTitle=findViewById(R.id.nameproduct);
        mTextinputDescription=findViewById(R.id.descriptionProduct);
        mImageDairy=findViewById(R.id.Lacteos);
        mImageVegetables=findViewById(R.id.vegetales);
        mImageBeverages=findViewById(R.id.Bebidas);
        mImageHouseHoldCleaning=findViewById(R.id.AseoHogar);

        //Seleccionar categoria

        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPost();
            }
        });

        mImageDairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory="Lacteos";
            }
        });

        mImageVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory="vegetales";
            }
        });

        mImageBeverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory="bebidas";
            }
        });

        mImageHouseHoldCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory="Productos de aseo";
            }
        });

    }

    private void clickPost() {
    }

    /*metodo guardar image*/
    private void saveImage() {
        mImageProvider.save(PostActivity.this, mImageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PostActivity.this, "La imagen se almaceno correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PostActivity.this, "Error al almacenar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //metodo open gallery
    private void openGallery() {
        Intent galleryIntent= new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");/*desliga la imagen de la uri*/
        startActivityForResult(galleryIntent,Gallery_REQUEST_CODE);/*metodo de abrir la gallery //prove un numero a ejecutar*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* VALIDACION DE IMAGEN CON GALERIA */
        if (requestCode == Gallery_REQUEST_CODE && resultCode == RESULT_OK) {//codigo validacionde gallery
            try {
                //upload the image, convierte en datos ,la comprime ydecodifica  y espera a que accion hacer con ella
                mImageFile = FileUtil.from(this, data.getData());
                mImageGallery.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
            } catch (Exception e) {
                Log.d("ERROR", "Se produjo un error " + e.getMessage());
                Toast.makeText(this, "Se produjo un error " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }




}