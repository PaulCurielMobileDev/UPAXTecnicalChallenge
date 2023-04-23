package com.mexicandeveloper.upaxpruebatecnica.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mexicandeveloper.upaxpruebatecnica.R;

@SuppressLint("AppCompatCustomView")
public class CircleImageView extends ImageView {

    private Shader mBitmapShader;
    private Matrix mShaderMatrix;
    private RectF mBitmapDrawBounds;
    private RectF mStrokeBounds;
    private Bitmap mBitmap;
    private Paint mBitmapPaint;
    private Paint mStrokePaint;
    private boolean mInitialized;
    private String name = "";
    private String url;
    private Drawable placeHolder;
    private int bkColor;
    private int textColor;
    private float strokeWidth;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        int strokeColor = Color.TRANSPARENT;
        strokeWidth = 0;
        boolean highlightEnable = true;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, 0, 0);
            strokeColor = a.getColor(R.styleable.CircleImageView_strokeColor, Color.TRANSPARENT);
            bkColor = a.getColor(R.styleable.CircleImageView_backgroundColor, Color.BLACK);
            textColor = a.getColor(R.styleable.CircleImageView_textFillColor, Color.WHITE);
            strokeWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_strokeWidth, 0);
            name = a.getNonResourceString(R.styleable.CircleImageView_name);
            url = a.getNonResourceString(R.styleable.CircleImageView_url);
            placeHolder = a.getDrawable(R.styleable.CircleImageView_placeholder);
            a.recycle();
        }

        if (strokeColor == Color.TRANSPARENT) strokeColor = textColor;

        mShaderMatrix = new Matrix();
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokeBounds = new RectF();
        mBitmapDrawBounds = new RectF();
        mStrokePaint.setColor(strokeColor);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(strokeWidth);
        mInitialized = true;
        setupBitmap();

        if (url != null && !url.isEmpty()) {
            setUrl(url);
        }
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        setupBitmap();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        setupBitmap();
    }

    @Override
    public void setImageBitmap(@Nullable Bitmap bm) {
        super.setImageBitmap(bm);
        setupBitmap();
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        setupBitmap();
    }

    public void setUrl(@NonNull String url) {
        Glide.with(getContext()).asDrawable().load(url).
                into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float halfStrokeWidth = mStrokePaint.getStrokeWidth() / 2f;
        updateCircleDrawBounds(mBitmapDrawBounds);
        mStrokeBounds.set(mBitmapDrawBounds);
        mStrokeBounds.inset(halfStrokeWidth, halfStrokeWidth);

        updateBitmapSize();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            drawName(canvas);
        } else {
            drawBitmap(canvas);
        }
    }

    public void setName(String name) {
        this.name = name;
        invalidate();
    }

    @ColorInt
    public int getStrokeColor() {
        return mStrokePaint.getColor();
    }

    public void setStrokeColor(@ColorInt int color) {
        mStrokePaint.setColor(color);
        invalidate();
    }

    @Dimension
    public float getStrokeWidth() {
        return mStrokePaint.getStrokeWidth();
    }

    public void setStrokeWidth(@Dimension float width) {
        mStrokePaint.setStrokeWidth(width);
        invalidate();
    }

    protected void drawStroke(Canvas canvas) {
        if (mStrokePaint.getStrokeWidth() > 0f) {
            canvas.drawOval(mStrokeBounds, mStrokePaint);
        }
    }

    protected void drawBitmap(Canvas canvas) {
        canvas.drawOval(mBitmapDrawBounds, mBitmapPaint);
    }

    private boolean notAlpha(char one) {
        String minus = "ABCDEFGHIJKLMNOPQRSTUVWXYZÑabcdefghijklmnopqrstuvwxyzñ";
        return !minus.contains("" + one);
    }

    protected void drawName(Canvas canvas) {
        if (name == null || name.isEmpty() || notAlpha(name.charAt(0))) {
            drawBitmap(canvas);
        } else {
            Paint mpaint = new Paint();
            mpaint.setColor(bkColor);
            float cx = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2;
            float cy = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;
            canvas.drawCircle(cx, cy, cx, mpaint);

            String[] names = name.split(" ");
            String initials = "" + names[0].charAt(0);

            float size = cx - getPaddingLeft();
            float tx = cx - size / 1.5f;
            float ty = cy + size / 3;
            if (names.length > 1) {
                initials += names[1].charAt(0);
            } else {
                tx = cx - size / 3;
            }
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(textColor);
            textPaint.setTextSize(size);
            textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(initials.toUpperCase(), tx, ty, textPaint);
            drawStroke(canvas);
        }
    }

    protected void updateCircleDrawBounds(RectF bounds) {
        float contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float contentHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        float left = getPaddingLeft();
        float top = getPaddingTop();
        if (contentWidth > contentHeight) {
            left += (contentWidth - contentHeight) / 2f;
        } else {
            top += (contentHeight - contentWidth) / 2f;
        }

        float diameter = Math.min(contentWidth, contentHeight);
        bounds.set(left, top, left + diameter, top + diameter);
    }

    private void setupBitmap() {
        if (!mInitialized) {
            return;
        }
        mBitmap = getBitmapFromDrawable(getDrawable());
        if (mBitmap == null) {
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setShader(mBitmapShader);

        updateBitmapSize();
    }

    private void updateBitmapSize() {
        if (mBitmap == null) return;

        float dx;
        float dy;
        float scale;

        if (mBitmap.getWidth() < mBitmap.getHeight()) {
            scale = mBitmapDrawBounds.width() / (float) mBitmap.getWidth();
            dx = mBitmapDrawBounds.left;
            dy = mBitmapDrawBounds.top - (mBitmap.getHeight() * scale / 2f) + (mBitmapDrawBounds.width() / 2f);
        } else {
            scale = mBitmapDrawBounds.height() / (float) mBitmap.getHeight();
            dx = mBitmapDrawBounds.left - (mBitmap.getWidth() * scale / 2f) + (mBitmapDrawBounds.width() / 2f);
            dy = mBitmapDrawBounds.top;
        }
        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate(dx, dy);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            drawable = placeHolder;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}