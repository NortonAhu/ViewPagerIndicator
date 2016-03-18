package com.bluecup.hongyu.viewpagerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Des: 自定义的 ViewPagerIndcator
 * Created by hongyu
 * Date:16/3/17_上午9:02
 */
public class ViewPagerIndicator extends LinearLayout {

    private Paint mPaint;

    private Path mPath;

    private int mTriangleWidth;

    private int mTriangleHeight;

    private static final float RADIO_TRANGLE_WIDTH = 1 / 6F;

    private int mTransaltionX;

    private int mTranslatX;

    private static final int COUNT_DEFAULT_COUNT = 4;   // 默认显示tab的格式

    private int visable_tab_count;
    private int mLineLength;
    private ViewPager mViewPager;                       // 设置的相应的 ViewPager

    public ViewPagerIndicator(Context context) {
        super(context);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        mPaint = new Paint();
//        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(2));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / visable_tab_count * RADIO_TRANGLE_WIDTH);

        mTransaltionX = w / visable_tab_count / 2 - mTriangleWidth / 2;

        initTriangle();
//        mLineLength = w / visable_tab_count;

    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);

        visable_tab_count = typedArray.getInt(R.styleable.ViewPagerIndicator_visable_count, COUNT_DEFAULT_COUNT);
        if (visable_tab_count == 0) {
            return;
        }

        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.weight = 0;
            layoutParams.width = getScreenWidth() / visable_tab_count;
            view.setLayoutParams(layoutParams);
        }
    }

    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private void initTriangle() {
        mPath = new Path();
        mTriangleHeight = mTriangleWidth / 2;
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(mTransaltionX + mTranslatX, getHeight());
        canvas.drawPath(mPath, mPaint);

        canvas.restore();

//        canvas.drawLine(mTranslatX, getHeight(), mLineLength + mTranslatX, getHeight(), mPaint);
        super.dispatchDraw(canvas);

    }

    public void scroll(int position, float positionOffset) {
        int tabWidth = getWidth() / visable_tab_count;
        mTranslatX = (int) (position * tabWidth + tabWidth * positionOffset);

        if (position >= (visable_tab_count - 2) && getChildCount() > visable_tab_count
                && positionOffset > 0) {
            this.scrollTo((int) ((position - (visable_tab_count - 2)) * tabWidth + tabWidth * positionOffset), 0);
        }

        invalidate();
    }

    public void setTabTitles(List<String> mTitles) {
        if (mTitles != null && mTitles.size() > 0) {
            removeAllViews();
        }

        for (String title : mTitles) {
            View view = generateTabView(title);
            addView(view);
        }
    }

    /**
     * 高亮tab 文本
     * @param pos
     */
    private void hightlightTabTitle(int pos) {

        if(pos < 0 ) return;
        normalTabTitle();
        View view = getChildAt(pos);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(Color.parseColor("#ffffff"));
        }
    }

    /**
     * 正常显示
     */
    private void normalTabTitle() {

        for (int i = 0 ; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.parseColor("#9C93A1"));
            }
        }
    }

    private View generateTabView(String title) {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        params.width = getScreenWidth() / visable_tab_count;
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setText(title);
        textView.setTextColor(Color.parseColor("#9C93A1"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        return textView;
    }

    private void setTouchEvent() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            final int j = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    // 设置内部viewpageer

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                hightlightTabTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        hightlightTabTitle(0);

        setTouchEvent();
    }
}
