package com.mycamera.dj.mycam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//called when activity is first created
public class CameraIntentActivity extends Activity {

    private static final int  ACTIVITY_START_CAMERA_APP=0;
    private ImageView mPhotoCapturedImageView;
    private String mImageFileLocation="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);//basically displaying you activities on layout or view
   //initialize variable with actual image view defined in layout
    mPhotoCapturedImageView=(ImageView) findViewById(R.id.capturePhotoImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {              //menu function of application
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera_intent, menu);
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
    public void takePhoto(View view){
        //Toast.makeText(this,"camera button pressed",Toast.LENGTH_SHORT).show();//to show message on pressing button
    Intent callCameraApplicationIntent=new Intent();//we created message handler intent object
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//passing a message to a camera application and respond to media store
    //now starting another activity with this returned message to take a picture for us
        File photoFile=null;
        try{
            photoFile =createImageFile();//photo file will have address

        } catch(IOException e){
           e.printStackTrace();
        }
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){   //return to our application, get bundle of data
        if(requestCode==ACTIVITY_START_CAMERA_APP && resultCode==RESULT_OK)//we hav made request to no of application we need to filter when the request was made && we need to check it was successful
        {
            //Toast.makeText(this,"Picture taken successful",Toast.LENGTH_SHORT).show();
        //Bundle extras=data.getExtras();//bitmap data
          //  Bitmap photoCaturedBitmap=(Bitmap) extras.get("data");//from bundle of dta we extract bitmap
            //mPhotoCapturedImageView.setImageBitmap(photoCaturedBitmap);//assigning bitmap to image view
            Bitmap photoCaturedBitmap= BitmapFactory.decodeFile(mImageFileLocation);
            mPhotoCapturedImageView.setImageBitmap(photoCaturedBitmap);//assigning bitmap to image view

        }
        }
    File createImageFile() throws IOException {                //provides the location of images
        String timeStamp = new SimpleDateFormat("yyyyMMMM_HHHss").format(new Date());  //needs to be unique so timestamp
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//gives directory for app to store
        File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);//
        mImageFileLocation = image.getAbsolutePath();
        return image;

    }

    }

