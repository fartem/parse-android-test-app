package com.smlnskgmail.jaman.randomnotes.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.TextViewCompat;

import com.smlnskgmail.jaman.randomnotes.R;

public class VectorDrawableButton extends AppCompatButton {

    private static final int DRAWABLE_TOP_ID
            = R.styleable.VectorDrawableButton_drawableTop;
    private static final int DRAWABLE_START_ID
            = R.styleable.VectorDrawableButton_drawableStart;
    private static final int DRAWABLE_END_ID
            = R.styleable.VectorDrawableButton_drawableEnd;
    private static final int DRAWABLE_BOTTOM_ID
            = R.styleable.VectorDrawableButton_drawableBottom;

    public VectorDrawableButton(Context context) {
        super(context);
    }

    public VectorDrawableButton(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initSupportVectorDrawablesAttrs(attrs);
    }

    public VectorDrawableButton(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
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
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
                this,
                getDrawable(
                        attributeArray,
                        DRAWABLE_START_ID
                ),
                getDrawable(
                        attributeArray,
                        DRAWABLE_TOP_ID
                ),
                getDrawable(
                        attributeArray,
                        DRAWABLE_END_ID
                ),
                getDrawable(
                        attributeArray,
                        DRAWABLE_BOTTOM_ID
                )
        );
        attributeArray.recycle();
    }

    private Drawable getDrawable(
            TypedArray attributeArray,
            int resId
    ) {
        return attributeArray.getDrawable(
                resId
        );
    }

}
