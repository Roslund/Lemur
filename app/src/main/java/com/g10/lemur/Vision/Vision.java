package com.g10.lemur.Vision;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.g10.lemur.Accelerometer.Accelerometer;
import com.g10.lemur.Altimeter.Altimeter;
import com.g10.lemur.Decibel.Decibel;
import com.g10.lemur.MainActivity;
import com.g10.lemur.R;
import com.g10.lemur.Settings.Settings;
import com.g10.lemur.Vision.content.VisionContent;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.ColorInfo;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.SafeSearchAnnotation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Vision extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Choice.OnFragmentInteractionListener, CardFragment.OnListFragmentInteractionListener, VisionAction.OnFragmentInteractionListener
{
    Fragment fragment;
    NavigationView navigationView;
    protected static VisionContent.VisionItem it;
    protected static Bitmap image;
    Uri uri;
    // Min, ge faen i
    protected static final String CLOUD_VISION_API_KEY = "AIzaSyCpvWMJr9ocKAA5vaRBZpY6AzVrcnKOxFo";
    public static String imageLabels;
    public static String imageColors;
    public static String imageSafeSearch;
    ProgressDialog uploading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set the current activity as marked in the menu
        navigationView.setCheckedItem(R.id.menuVision);
        image = null;
        imageLabels = "";
        imageColors = "";
        imageSafeSearch = "";

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new Choice();
        fragmentTransaction.add(R.id.fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }

    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        navigationView.setCheckedItem(R.id.menuVision);
    }

    @Override
    public void onResume(){
        super.onResume();

        navigationView.setCheckedItem(R.id.menuVision);
    }

    @Override
    public void onBackPressed()
    {
        // Physical back button pressed

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Right sub-menu.
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        Intent intent;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menuHome)
        {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.menuVision)
        {
            // Go to Google Vision
            intent = new Intent(this, Vision.class);
            startActivity(intent);
        }
        else if (id == R.id.menuAlti)
        {
            // Go to altimeter
            intent = new Intent(this, Altimeter.class);
            startActivity(intent);
        }
        else if (id == R.id.menuAcc)
        {
            // Go to accelerometer
            intent = new Intent(this, Accelerometer.class);
            startActivity(intent);
        }
        else if (id == R.id.menuSound)
        {
            // Go to decibel
            intent = new Intent(this, Decibel.class);
            startActivity(intent);
        }
        else if (id == R.id.menuHelp)
        {
            // Stay here
        }
        else if (id == R.id.menuSettings)
        {
            // Go to Settings
            intent = new Intent(this, Settings.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, "fragz", Toast.LENGTH_SHORT).show();
    }

    public void onListFragmentInteraction(VisionContent.VisionItem item)
    {
        it = item;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new VisionAction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    protected void openCamera(View view)
    {
        // Filename
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";

        // Directory
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        // Path
        String pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        uri = Uri.fromFile(file);

        // Open camera
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(cameraIntent, 1);
    }

    protected void openGallery (View view)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try
        {
            if (requestCode == 0)
                uri = data.getData();

            image = scaleBitmapDown(
                    MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                    1200);
        }
        catch (IOException ex)
        {
            Log.e("image error", ex.getMessage());
        }

        if (image == null)
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else
        {
            try
            {
                callCloudVision(image);
            }
            catch (IOException ex)
            {
                Log.e("VISION ERROR", ex.getMessage());
            }
        }
    }

    //
    // Google Vision functions
    //

    public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private void callCloudVision(final Bitmap bitmap) throws IOException {
        // Switch text to loading
        //mImageDetails.setText(R.string.loading_message);

        // Do the real work in an async task, because we need to use the network anyway
        new AsyncTask<Object, Void, String>() {

            @Override
            protected  void onPreExecute()
            {
                uploading = ProgressDialog.show(Vision.this, "",
                        "Uploading...", true);
            }

            @Override
            protected String doInBackground(Object... params) {
                try {
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    com.google.api.services.vision.v1.Vision.Builder builder = new com.google.api.services.vision.v1.Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(new
                            VisionRequestInitializer(CLOUD_VISION_API_KEY));
                    com.google.api.services.vision.v1.Vision vi = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                        // Add the image
                        com.google.api.services.vision.v1.model.Image base64EncodedImage = new com.google.api.services.vision.v1.model.Image();
                        // Convert the bitmap to a JPEG
                        // Just in case it's a format that Android understands but Cloud Vision
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();

                        // Base64 encode the JPEG
                        base64EncodedImage.encodeContent(imageBytes);
                        annotateImageRequest.setImage(base64EncodedImage);

                        // add the features we want
                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature labelDetection = new Feature();
                            labelDetection.setType("LABEL_DETECTION");
                            labelDetection.setMaxResults(5);
                            add(labelDetection);

                            Feature imageProp = new Feature();
                            imageProp.setType("IMAGE_PROPERTIES");
                            //imageProp.setMaxResults(5);
                            add(imageProp);

                            Feature safeSearch = new Feature();
                            safeSearch.setType("SAFE_SEARCH_DETECTION");
                            add(safeSearch);
                        }});

                        // Add the list of one thing to the request
                        add(annotateImageRequest);
                    }});

                    com.google.api.services.vision.v1.Vision.Images.Annotate annotateRequest =
                            vi.images().annotate(batchAnnotateImagesRequest);
                    // Due to a bug: requests to Vision API containing large images fail when GZipped.
                    annotateRequest.setDisableGZipContent(true);
                    Log.d("TAG", "created Cloud Vision request object, sending request");

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return convertResponseToString(response);

                } catch (GoogleJsonResponseException e) {
                    Log.d("TAG", "failed to make API request because " + e.getContent());
                } catch (IOException e) {
                    Log.d("TAG", "failed to make API request because of other IOException " +
                            e.getMessage());
                }
                return "Cloud Vision API request failed. Check logs for details.";
            }

            protected void onPostExecute(String result) {
                uploading.dismiss();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragment = new CardFragment();
                fragmentTransaction.replace(R.id.fragment_placeholder, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
            }
        }.execute();
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        List<ColorInfo> colors = response.getResponses().get(0).getImagePropertiesAnnotation().getDominantColors().getColors();
        SafeSearchAnnotation safeSearch = response.getResponses().get(0).getSafeSearchAnnotation();
        com.google.api.services.vision.v1.model.Color col;
        Color colorHex;

        // Fix the colors
        for (ColorInfo color : colors)
        {
            col = color.getColor();
            String co = Integer.toHexString(Color.rgb(Math.round(col.getRed()), Math.round(col.getGreen()), Math.round(col.getBlue())));
            co = "#"+co.substring(2)+";";
            imageColors += Math.round(color.getScore()*100) + co;
        }

        // Remove last ; in colors string
        imageColors = imageColors.substring(0, imageColors.length()-1);

        // Fix the labels
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                imageLabels += String.format("%d%%: %s", Math.round(label.getScore()*100), label.getDescription());
                imageLabels += '\n';
            }
        } else {
            imageLabels += "nothing";
        }

        // Fix Safe Search
        imageSafeSearch += "Adult: " + safeSearch.getAdult() + '\n';
        imageSafeSearch += "Medical: " + safeSearch.getMedical() + '\n';
        imageSafeSearch += "Spoof: " + safeSearch.getSpoof() + '\n';
        imageSafeSearch += "Violence: " + safeSearch.getViolence() + '\n';

        return imageLabels;
    }
}
