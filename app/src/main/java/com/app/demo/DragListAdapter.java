package com.app.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by thkcheng on 2017/12/23.
 */
public class DragListAdapter extends BaseAdapter {
    private ArrayList<String> mDataList = new ArrayList<>();
    private ArrayList<String> mCopyList = new ArrayList<>();
    private Context context;
    private TextView tvMsg;

    public DragListAdapter(Context context, ArrayList<String> mDataList, TextView tvMsg) {
        this.context = context;
        this.mDataList = mDataList;
        this.tvMsg = tvMsg;
    }

    public void showDropItem(boolean showItem) {
        this.ShowItem = showItem;
    }

    public void setInvisiblePosition(int position) {
        invisilePosition = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.item_drag_list, null);

        LinearLayout ll = convertView.findViewById(R.id.ll_containera);
        TextView textView = convertView.findViewById(R.id.tv);

        textView.setText(mDataList.get(position));

        //是否发生了交换
        if (isChanged) {

            if (position == invisilePosition) {
                if (!ShowItem) {
                    // 因为item背景为白色，故而在这里要设置为全透明色防止有白色遮挡问题（向上拖拽）
                    ll.setAlpha(0f);
                }
            }

            if (lastFlag != -1) {
                if (lastFlag == 1) {
                    if (position > invisilePosition) {
                        Animation animation;
                        animation = getFromSelfAnimation(0, -height);
                        convertView.startAnimation(animation);
                    }
                } else if (lastFlag == 0) {
                    if (position < invisilePosition) {
                        Animation animation;
                        animation = getFromSelfAnimation(0, height);
                        convertView.startAnimation(animation);
                    }
                }
            }
        }
        return convertView;
    }


    private int invisilePosition = -1;
    private boolean isChanged = true;
    private boolean ShowItem = false;

    /**
     * 动态修改Item内容
     *
     * @param startPosition // 开始的位置
     * @param endPosition   // 当前停留的位置
     */
    public void exchangeCopy(int startPosition, int endPosition) {
//        Object startObject = getCopyItem(startPosition);
//
//        if (startPosition < endPosition) {// 向下移动
//            mCopyList.add(endPosition + 1, (String) startObject);
//            mCopyList.remove(startPosition);
//        } else {// 向上拖动或者不动
//            mCopyList.add(endPosition, (String) startObject);
//            mCopyList.remove(startPosition + 1);
//        }
        Collections.swap(mDataList, startPosition, endPosition);

        tvMsg.setText(mDataList.get(endPosition)  + "  交换  " +  mDataList.get(startPosition));

        isChanged = true;
    }


    public Object getCopyItem(int position) {
        return mCopyList.get(position);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addDragItem(int start, Object obj) {
        String title = mDataList.get(start);
        mDataList.remove(start); // 添加删除项
        mDataList.add(start, (String) obj);// 删除该项
    }

    public void copyList() {
        mCopyList.clear();
        for (String str : mDataList) {
            mCopyList.add(str);
        }
    }

    public void pastList() {
        mDataList.clear();
        for (String str : mCopyList) {
            mDataList.add(str);
        }
    }

    private boolean isSameDragDirection = true;
    private int lastFlag = -1;
    private int height;
    private int dragPosition = -1;

    public void setIsSameDragDirection(boolean value) {
        isSameDragDirection = value;
    }

    public void setLastFlag(int flag) {
        lastFlag = flag;
    }

    public void setHeight(int value) {
        height = value;
    }

    public void setCurrentDragPosition(int position) {
        dragPosition = position;
    }

    public Animation getFromSelfAnimation(int x, int y) {
        TranslateAnimation go = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, x,
                Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, y);
        go.setInterpolator(new AccelerateDecelerateInterpolator());
        go.setFillAfter(true);
        go.setDuration(100);
        go.setInterpolator(new AccelerateInterpolator());
        return go;
    }

    public Animation getToSelfAnimation(int x, int y) {
        TranslateAnimation go = new TranslateAnimation(
                Animation.ABSOLUTE, x, Animation.RELATIVE_TO_SELF, 0,
                Animation.ABSOLUTE, y, Animation.RELATIVE_TO_SELF, 0);
        go.setInterpolator(new AccelerateDecelerateInterpolator());
        go.setFillAfter(true);
        go.setDuration(100);
        go.setInterpolator(new AccelerateInterpolator());
        return go;
    }
}