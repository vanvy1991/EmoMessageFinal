package com.example.vynv.final_emo.fragments;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vynv.final_emo.R;
import com.example.vynv.final_emo.common.ConvolutionMatrix;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;

@EFragment(R.layout.fragment_camera)
public class CameraFragment extends Fragment {
    ImageLoader imageLoader;
    DisplayImageOptions optionsImage;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    private static final String IMAGE_DIRECTORY_NAME = "Emo Image";
    @ViewById(R.id.img_preview)
    ImageView imgPreview;
    private Uri mImageUri;

    @Click(R.id.btnCapturePicture)
    public void btnCapturePicture(){
        captureImage();
    }
    @AfterViews
    public void init(){
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        optionsImage = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        previewCapturedImage();
//    }


    public Bitmap smoothEffect(Bitmap src, double value) {
        //create convolution matrix instance
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.setAll(1);
        convMatrix.Matrix[1][1] = value;
        // set weight of factor and offset
        convMatrix.Factor = value + 8;
        convMatrix.Offset = 1;
        return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( resultCode== Activity.RESULT_OK)
        {
            //... some code to inflate/create/find appropriate ImageView to place grabbed image
            this.grabImage(imgPreview);
            imgPreview.setVisibility(View.VISIBLE);
        }
    }

    private void captureImage() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = null;
        try
        {
            // place where to store camera taken picture
            photo = this.createTemporaryFile("picture", ".jpg");
            photo.delete();
        }
        catch(Exception e)
        {
            Log.d("xxx",e.getMessage());
        }
        mImageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        //start camera intent
        this.startActivityForResult(intent, 200);
    }
    private File createTemporaryFile(String part, String ext) throws Exception
    {
        File tempDir= Environment.getExternalStorageDirectory();
        tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdir();
        }
        return File.createTempFile(part, ext, tempDir);
    }
    public void grabImage(ImageView imageView)
    {
        getActivity().getContentResolver().notifyChange(mImageUri, null);
        ContentResolver cr = getActivity().getContentResolver();
        Bitmap bitmap;
        try
        {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
            imageView.setImageBitmap(bitmap);
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT).show();
			Log.d("xxx", "Failed to load", e);
        }
    }
}
