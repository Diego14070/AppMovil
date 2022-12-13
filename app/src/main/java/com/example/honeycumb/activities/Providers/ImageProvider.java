package com.example.honeycumb.activities.Providers;


import android.content.Context;

import com.example.honeycumb.activities.Utils.CompressorBitmapImage;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

//Logica encargada de almacenar los datos  en el  storage
public class ImageProvider {
    StorageReference mStorage;

    public ImageProvider() {
        mStorage= FirebaseStorage.getInstance().getReference();//metodos para ubicar los objetos(imagenes)
    }

    public UploadTask save (Context context, File file){//logica para almacenar la imagen
        byte[] imageByte=CompressorBitmapImage.getImage(context, file.getPath(), 500, 500);//arregla la image en byte y dar forma de la imagen
        StorageReference storage=mStorage.child(new Date()+"jpg"); //prepara la image
        UploadTask task=storage.putBytes(imageByte);//sube la imagen en byte para poder ser leida
        return task;

    }
}
