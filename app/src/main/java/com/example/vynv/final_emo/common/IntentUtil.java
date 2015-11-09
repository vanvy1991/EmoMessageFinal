package com.example.vynv.final_emo.common;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.vynv.final_emo.adapter.ShareIntentListAdapter;
import com.facebook.share.widget.ShareDialog;

import java.io.ByteArrayOutputStream;
import java.util.List;


/**
 * Copyright © 2015 AsianTech inc.
 * Created by vynv on 8/25/15.
 */
public class IntentUtil {
    private static final int REQUEST_CODE_SHARE_TO_MESSENGER = 1;
    public static Intent sendIntent;
    public static Activity mActivity;
    public static Uri mUri;
    public static int mResourceID;
    public static ShareDialog shareDialog;
    public static boolean mPicking;
    static ShareIntentListAdapter objShareIntentListAdapter;

    public static void shareIntent(Activity activity, Uri uri,int resourceId) {
        mActivity=activity;
        mUri=uri;
        mResourceID=resourceId;
        sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("image/*");
        PackageManager pm = mActivity.getPackageManager();
        // list package
        List<ResolveInfo> activityList = pm.queryIntentActivities(sendIntent, 0);
        objShareIntentListAdapter = new ShareIntentListAdapter(mActivity, activityList.toArray());
        // Create alert dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Emoicon Chemicals");
        builder.setAdapter(objShareIntentListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                ResolveInfo info = (ResolveInfo) objShareIntentListAdapter.getItem(item);
                Log.d("xxxO",""+info.activityInfo.packageName);
                sendMessage(info.activityInfo.packageName, mResourceID);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

//    public static void postStatusFB(int resourceId) {
//        Bitmap mBitmap = BitmapFactory.decodeResource(mActivity.getResources(), resourceId);
//        SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(mBitmap).build();
//        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder().addPhoto(sharePhoto).build();
//        Log.d("xxx",""+mBitmap.getWidth()+"---"+sharePhotoContent.getContentUrl());
//        shareDialog.show(sharePhotoContent);
//    }
//
//    public static void sendMessageMail(String app, int resourceID) {
//        Bitmap b = BitmapFactory.decodeResource(mActivity.getResources(), resourceID);
//        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("image/*");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), b, "Title", null);
//        Uri imageUri = Uri.parse(path);
//        share.putExtra(Intent.EXTRA_STREAM, imageUri);
//        share.setPackage(app);
//        mActivity.startActivity(share);
//    }

//    public static void sendMessageFacebook(Uri uri) {
//        ShareToMessengerParams shareToMessengerParams =
//                ShareToMessengerParams.newBuilder(uri, "image/jpeg")
//                        .setMetaData("{ \"image\" : \"tree\" }")
//                        .build();
//        if (mPicking) {
//            // If we were launched from Messenger, we call MessengerUtils.finishShareToMessenger to return
//            // the content to Messenger.
//            MessengerUtils.finishShareToMessenger(mActivity, shareToMessengerParams);
//        } else {
//            // Otherwise, we were launched directly (for example, user clicked the launcher icon). We
//            // initiate the broadcast flow in Messenger. If Messenger is not installed or Messenger needs
//            // to be upgraded, this will direct the user to the play store.
//            MessengerUtils.shareToMessenger(
//                    mActivity,
//                    REQUEST_CODE_SHARE_TO_MESSENGER,
//                    shareToMessengerParams);
//        }
//    }
//public static void sendPostFb(int resourceId){
//    Bitmap b = BitmapFactory.decodeResource(mActivity.getResources(), resourceId);
//    Intent intent=new Intent("android.intent.action.SEND");
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//    String path = MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), b, "Title", null);
//    Uri imageUri = Uri.parse(path);
//    intent.putExtra(Intent.EXTRA_STREAM, imageUri);
//    intent.setType("image/png");
//    intent.setPackage("com.facebook.katana");
//    mActivity.startActivity(intent);
//}
//    public static void sendMessageFb(int resourceId){
//        Bitmap b = BitmapFactory.decodeResource(mActivity.getResources(), resourceId);
//        Intent intent=new Intent("android.intent.action.SEND");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), b, "Title", null);
//        Uri imageUri = Uri.parse(path);
//        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
//        intent.setType("image/png");
//        intent.setPackage("com.facebook.orca");
//        mActivity.startActivity(intent);
//    }
    public static void sendMessage(String app, int resourceId) {
        Bitmap b = BitmapFactory.decodeResource(mActivity.getResources(), resourceId);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), b, "Title", null);
        Uri imageUri = Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        share.setPackage(app);
        mActivity.startActivityForResult(share,300);
    }
}
