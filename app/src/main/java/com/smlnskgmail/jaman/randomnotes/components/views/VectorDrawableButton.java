package com.smlnskgmail.jaman.randomnotes.components.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.TextViewCompat;

import com.smlnskgmail.jaman.randomnotes.R;

public class VectorDrawableButton extends AppCompatButton {

    private static final int DEFAULT_ID_VALUE = -1;

    private static final int DRAWABLE_TOP_ID = R.styleable.VectorDrawableButton_drawableTop;
    private static final int DRAWABLE_START_ID = R.styleable.VectorDrawableButton_drawableStart;
    private static final int DRAWABLE_END_ID = R.styleable.VectorDrawableButton_drawableEnd;
    private static final int DRAWABLE_BOTTOM_ID = R.styleable.VectorDrawableButton_drawableBottom;

    public VectorDrawableButton(Context context) {
        super(context);
    }

    public VectorDrawableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSupportVectorDrawablesAttrs(attrs);
    }

    public VectorDrawableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSupportVectorDrawablesAttrs(attrs);
    }

    private void initSupportVectorDrawablesAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray attributeArray = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.VectorDrawableButton
        );

        Drawable drawableStart = null;
        Drawable drawableEnd = null;
        Drawable drawableTop = null;
        Drawable drawableBottom = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawableTop = getDrawable(attributeArray, DRAWABLE_TOP_ID);
            drawableStart = getDrawable(attributeArray, DRAWABLE_START_ID);
            drawableEnd = getDrawable(attributeArray, DRAWABLE_END_ID);
            drawableBottom = getDrawable(attributeArray, DRAWABLE_BOTTOM_ID);
        } else {
            int drawableTopId = getResourceId(attributeArray, DRAWABLE_TOP_ID);
            int drawableStartId = getResourceId(attributeArray, DRAWABLE_START_ID);
            int drawableEndId = getResourceId(attributeArray, DRAWABLE_END_ID);
            int drawableBottomId = getResourceId(attributeArray, DRAWABLE_BOTTOM_ID);

            if (drawableStartId != DEFAULT_ID_VALUE) {
                drawableStart = AppCompatResources.getDrawable(getContext(), drawableStartId);
            }
            if (drawableEndId != DEFAULT_ID_VALUE) {
                drawableEnd = AppCompatResources.getDrawable(getContext(), drawableEndId);
            }
            if (drawableTopId != DEFAULT_ID_VALUE) {
                drawableTop = AppCompatResources.getDrawable(getContext(), drawableTopId);
            }
            if (drawableBottomId != DEFAULT_ID_VALUE) {
                drawableBottom = AppCompatResources.getDrawable(getContext(), drawableBottomId);
            }
        }

        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
                this,
                drawableStart,
                drawableTop,
                drawableEnd,
                drawableBottom
        );

        attributeArray.recycle();
    }

    private Drawable getDrawable(TypedArray attributeArray, int resId) {
        return attributeArray.getDrawable(resId);
    }

    private int getResourceId(TypedArray attributeArray, int resId) {
        return attributeArray.getResourceId(resId, DEFAULT_ID_VALUE);
    }

}
