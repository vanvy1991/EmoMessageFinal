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
import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.ShareToMessengerParams;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
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
        builder.setTitle("Share via");
        builder.setAdapter(objShareIntentListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                ResolveInfo info = (ResolveInfo) objShareIntentListAdapter.getItem(item);
                Log.d("xxxO",""+info.activityInfo.packageName);
                if (info.activityInfo.packageName.contains("facebook.orca")) {
                    sendMessageFacebook(mUri);
                } else if (info.activityInfo.packageName.contains("facebook.katana")) {
                    postStatusFB(mResourceID);
                } else if (info.activityInfo.packageName.contains("android.gm")) {
                    sendMessageMail(info.activityInfo.packageName, mResourceID);
                } else {
                    sendMessage(info.activityInfo.packageName, mResourceID);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public static void postStatusFB(int resourceId) {
        Bitmap mBitmap = BitmapFactory.decodeResource(mActivity.getResources(), resourceId);
        SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(mBitmap).build();
        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder().addPhoto(sharePhoto).build();
        shareDialog.show(sharePhotoContent);
    }

    public static void sendMessageMail(String app, int resourceID) {
        Bitmap b = BitmapFactory.decodeResource(mActivity.getResources(), resourceID);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), b, "Title", null);
        Uri imageUri = Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        share.setPackage(app);
        mActivity.startActivity(share);
    }

    public static void sendMessageFacebook(Uri uri) {
        ShareToMessengerParams shareToMessengerParams =
                ShareToMessengerParams.newBuilder(uri, "image/jpeg")
                        .setMetaData("{ \"image\" : \"tree\" }")
                        .build();
        if (mPicking) {
            // If we were launched from Messenger, we call MessengerUtils.finishShareToMessenger to return
            // the content to Messenger.
            MessengerUtils.finishShareToMessenger(mActivity, shareToMessengerParams);
        } else {
            // Otherwise, we were launched directly (for example, user clicked the launcher icon). We
            // initiate the broadcast flow in Messenger. If Messenger is not installed or Messenger needs
            // to be upgraded, this will direct the user to the play store.
            MessengerUtils.shareToMessenger(
                    mActivity,
                    REQUEST_CODE_SHARE_TO_MESSENGER,
                    shareToMessengerParams);
        }
    }


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
        mActivity.startActivity(share);
    }

}
