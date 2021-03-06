package com.example.vynv.final_emo.fragments;


import android.app.Activity;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.vynv.final_emo.HomeActivity_;
import com.example.vynv.final_emo.R;
import com.example.vynv.final_emo.adapter.HorizontalAdapter;
import com.example.vynv.final_emo.adapter.PagerAdapter;
import com.example.vynv.final_emo.common.HorizontalListView;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.messenger.MessengerThreadParams;
import com.facebook.messenger.MessengerUtils;
import com.facebook.share.widget.ShareDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import static com.example.vynv.final_emo.common.IntentUtil.shareIntent;
import static com.example.vynv.final_emo.common.Util.getResultEmoji;
import static com.example.vynv.final_emo.common.Util.getResultShowHide;
import static com.example.vynv.final_emo.common.Util.setTabEmo;
import static com.example.vynv.final_emo.common.Util.updateShowHide;


@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment implements View.OnDragListener {

    public static int ICON_TAB_EMO = 100;
    public static int TEXT_TAB_EMO = 200;
    private int ADD_ICON_CODE = 111;
    @ViewById(R.id.horizontalListview)
    protected HorizontalListView mHorizontalListView;

    @ViewById(R.id.vp)
    protected ViewPager mVp;

    @ViewById(R.id.img_first)
    ImageView mImgFirst;

    @ViewById(R.id.img_second)
    protected ImageView mImgSecond;

    @ViewById(R.id.img_result)
    protected ImageView mImgResult;

    @ViewById(R.id.imgNew)
    protected ImageView mImageNew;

    @ViewById(R.id.ll_chat_content)
    protected LinearLayout mLlChatContent;
    ArrayList<String> tabName = new ArrayList<>();
    ArrayList<String> icons = new ArrayList<>();
    private HorizontalAdapter mHorizontalAdapter;
    private String TAG = "xxx";
    private boolean mPicking;
    //    Intent sendIntent;
    private ShareDialog shareDialog;
    private PagerAdapter mPagerAdapter;
    public Activity mActivity;
    String itemImage1;
    String itemImage2;
   private String icon;
    private boolean iconImage1 = false;
    private boolean iconImage2 = false;
    private Uri uri;
    private int resourceId;
    int resID,pos;
    int showHide=0;
    @AfterViews
    public void init() {
        mActivity = getActivity();
        initFacebook();
        initDrag();
        initPager();
        initTab();

        mHorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos=i;
                if ((i + 1) == tabName.size()) {
                    HideEmo(false);
                } else {
                    HideEmo(true);
                    resetImage();
                }
                EmojiconFragment f = EmojiconFragment_.builder().numTabs(i).lenghtTab(tabName.size()).build();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frMainContainer, f);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        EmojiconFragment f = EmojiconFragment_.builder().numTabs(pos).lenghtTab(tabName.size()).build();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frMainContainer, f);
        fragmentTransaction.commit();
        resetImage();
        AppEventsLogger.activateApp(getActivity());

    }

    @Override
    public void onPause() {
        mActivity = getActivity();
        super.onPause();
        AppEventsLogger.deactivateApp(getActivity());
    }

    public void initFacebook() {
        mActivity = getActivity();
        FacebookSdk.sdkInitialize(getActivity());
        shareDialog = new ShareDialog(getActivity());
        Intent intent = getActivity().getIntent();
        if (Intent.ACTION_PICK.equals(intent.getAction())) {
            MessengerThreadParams mThreadParams = MessengerUtils.getMessengerThreadParamsForIntent(intent);
            mPicking = true;
        }
    }

    public void initDrag() {
        mImgFirst.setOnDragListener(this);
        mImgSecond.setOnDragListener(this);
        mVp.setOnDragListener(this);
    }

    public void initPager() {
        icons = setTabEmo(getActivity(), "tab_emoji_icon.txt", ICON_TAB_EMO, 0);
        tabName = setTabEmo(getActivity(), "tab_emoji_icon.txt", TEXT_TAB_EMO, 0);
        mHorizontalAdapter = new HorizontalAdapter(getActivity(), tabName, icons);
        mHorizontalListView.setAdapter(mHorizontalAdapter);
    }

    public void initTab() {
        mPagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        mVp.setAdapter(mPagerAdapter);
        mVp.setOffscreenPageLimit(1);
        mVp.setCurrentItem(0);
        mVp.setDuplicateParentStateEnabled(false);
    }

    @Click(R.id.img_result)
    public void imgResultClick() {

        if (iconImage1 && iconImage2) {
            resourceId = getResources().getIdentifier("@drawable/" + randomImage(itemImage1, itemImage2), null, getActivity().getPackageName());
            uri = Uri.parse("android.resource://com.example.vynv.final_emo/" + resourceId);
            updateShowHide(getActivity(), randomImage(itemImage1, itemImage2), "icons_tab.txt", showHide);
            shareIntent(getActivity(), uri, resourceId);

        } else {
            if (!iconImage1) {
                resourceId = getResources().getIdentifier("@drawable/" + itemImage2, null, getActivity().getPackageName());
                uri = Uri.parse("android.resource://com.example.vynv.final_emo/" + resourceId);
                updateShowHide(getActivity(), itemImage2, "icons_tab.txt", showHide);
                shareIntent(getActivity(), uri, resourceId);
            } else {
                resourceId = getResources().getIdentifier("@drawable/" + itemImage1, null, getActivity().getPackageName());
                uri = Uri.parse("android.resource://com.example.vynv.final_emo/" + resourceId);
                updateShowHide(getActivity(),itemImage1, "icons_tab.txt", showHide);
                shareIntent(getActivity(), uri, resourceId);
            }
        }
        ((HomeActivity_) getActivity()).createFile(ADD_ICON_CODE, resID);
    }

    @Override
    public boolean onDrag(View imageViewTarget, DragEvent dragEvent) {
        View draggedImageView = (View) dragEvent.getLocalState();
        // Handles each of the expected events
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                if (dragEvent.getClipDescription()
                        .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    return true;
                }
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                return true;
            case DragEvent.ACTION_DROP:

                int resourceId = getResources().getIdentifier("@drawable/" + dragEvent.getClipData().getItemAt(0).getText().toString(), null, getActivity().getPackageName());

                ViewGroup draggedImageViewParentLayout
                        = (ViewGroup) draggedImageView.getParent();
                draggedImageViewParentLayout.removeView(draggedImageView);
                if (imageViewTarget instanceof ImageView) {
                    ImageView imgTarget = (ImageView) imageViewTarget;
                    imgTarget.setImageResource(resourceId);
                    draggedImageView.setVisibility(View.VISIBLE);
                    if (imgTarget.equals(mImgFirst)) {
                        mImageNew.setVisibility(View.GONE);
                        iconImage1 = true;
                        itemImage1 = dragEvent.getClipData().getItemAt(0).getText().toString();
                        resID = getResources().getIdentifier("@drawable/" + itemImage1, null, getActivity().getPackageName());
                        mImgResult.setImageResource(resID);
                    } else if (imgTarget.equals(mImgSecond)) {
                        iconImage2 = true;
                        mImageNew.setVisibility(View.GONE);
                        itemImage2 = dragEvent.getClipData().getItemAt(0).getText().toString();
                        resID = getResources().getIdentifier("@drawable/" + itemImage2, null, getActivity().getPackageName());
                        mImgResult.setImageResource(resID);
                    }
                    if (mImgFirst.getDrawable() != null && mImgSecond.getDrawable() != null) {
                        showHide=getResultShowHide(getActivity(),randomImage(itemImage1, itemImage2),"icons_tab.txt");
                        if(showHide==0){
                            mImageNew.setVisibility(View.VISIBLE);
                        }
                        else {
                            mImageNew.setVisibility(View.GONE);
                        }
                        resID = getResources().getIdentifier("@drawable/" + randomImage(itemImage1, itemImage2), null, getActivity().getPackageName());
                        mImgResult.setImageResource(resID);
                    }

                }
                return true;
            // An unknown action type was received.
            default:
                Log.i(TAG, "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }

    public String randomImage(String itemImage1, String itemImage2) {
        icon = getResultEmoji(getActivity(), itemImage1, itemImage2, "emoji_alchemy.txt");
        return icon;
    }

    public void resetImage() {
        mImgFirst.setImageResource(0);
        mImgSecond.setImageResource(0);
        mImgResult.setImageResource(0);
        mImageNew.setVisibility(View.GONE);
        iconImage1=false;
        iconImage2=false;
    }
public void HideEmo(boolean hideShow){
    if(!hideShow) {
        mImgFirst.setVisibility(View.GONE);
        mImgResult.setVisibility(View.GONE);
        mImgSecond.setVisibility(View.GONE);
        mImageNew.setVisibility(View.GONE);
    }
    else{
        mImgFirst.setVisibility(View.VISIBLE);
        mImgResult.setVisibility(View.VISIBLE);
        mImgSecond.setVisibility(View.VISIBLE);
    }
}
}
