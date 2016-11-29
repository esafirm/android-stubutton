
package com.esafirm.stubutton;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StuButton extends RelativeLayout {

    private static final int ANIMATION_DURATION = 300;
    private static final int DEFAULT_SIZE = 48;
    private static final float ALPHA_MODIFIER = 0.02f;

    private TextView stuTxtLabel;
    private ImageView stuImgThumb;
    private ImageView stuBackground;

    private OnUnlockListener listener;

    private int thumbWidth = 0;
    private boolean sliding = false;
    private int sliderPosition = 0;
    private int initialSliderPosition = 0;
    private float initialSlidingX = 0;

    public StuButton(Context context) {
        this(context, null);
    }

    public StuButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StuButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StuButton,
                0, 0);

        init(context);
        setLabel(typedArray.getString(R.styleable.StuButton_stu_label));
        setStuBackground(typedArray.getResourceId(R.styleable.StuButton_stu_background, R.drawable.stu_default_bg));
        setThumb(typedArray.getResourceId(R.styleable.StuButton_stu_thumbDrawable, R.drawable.stu_circle_material));

        typedArray.recycle();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.stu_main_layout, this, true);

        stuTxtLabel = (TextView) findViewById(R.id.stu_text_label);
        stuImgThumb = (ImageView) findViewById(R.id.stu_img_thumb);
        stuBackground = (ImageView) findViewById(R.id.stu_background);

        thumbWidth = dpToPx(DEFAULT_SIZE);

        stuImgThumb.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                stuImgThumb.getViewTreeObserver().removeOnPreDrawListener(this);
                thumbWidth = stuImgThumb.getWidth();
                return false;
            }
        });
    }

    public void setOnUnlockListener(OnUnlockListener listener) {
        this.listener = listener;
    }

    public void reset() {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) stuImgThumb.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(params.leftMargin, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (stuImgThumb != null) {
                    params.leftMargin = (Integer) valueAnimator.getAnimatedValue();
                    stuImgThumb.requestLayout();
                }
            }
        });
        animator.setDuration(ANIMATION_DURATION);
        animator.start();
        stuTxtLabel.setAlpha(1f);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() > sliderPosition && event.getX() < (sliderPosition + thumbWidth)) {
                sliding = true;
                initialSlidingX = event.getX();
                initialSliderPosition = sliderPosition;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            if (sliderPosition >= (getMeasuredWidth() - thumbWidth)) {
                if (listener != null) {
                    listener.onUnlock();
                }
            } else {
                sliding = false;
                sliderPosition = 0;
                reset();
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && sliding) {
            sliderPosition = (int) (initialSliderPosition + (event.getX() - initialSlidingX));

            if (sliderPosition <= 0) {
                sliderPosition = 0;
            }
            if (sliderPosition >= (getMeasuredWidth() - thumbWidth)) {
                sliderPosition = getMeasuredWidth() - thumbWidth;
            } else {
                int max = getMeasuredWidth() - thumbWidth;
                int progress = (int) (sliderPosition * 100 / (max * 1.0f));
                stuTxtLabel.setAlpha(1f - progress * ALPHA_MODIFIER);
            }
            setMarginLeft(sliderPosition);
        }

        return true;
    }

    private void setMarginLeft(int margin) {
        if (stuImgThumb == null) return;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) stuImgThumb.getLayoutParams();
        params.setMargins(margin, params.topMargin, params.rightMargin, params.bottomMargin);
        stuImgThumb.setLayoutParams(params);
    }

    @Override
    protected void onDetachedFromWindow() {
        cleanUp();
        super.onDetachedFromWindow();
    }

    private void cleanUp() {
        stuTxtLabel = null;
        stuImgThumb = null;
        stuBackground = null;
    }

    /* --------------------------------------------------- */
    /* > Public Methods */
    /* --------------------------------------------------- */

    public void setLabel(@StringRes int label) {
        setLabel(getContext().getString(label));
    }

    public void setLabel(String label) {
        if (stuTxtLabel != null) {
            stuTxtLabel.setText(label);
        }
    }

    public void setStuBackground(@DrawableRes int resId) {
        if (stuBackground != null) {
            stuBackground.setImageResource(resId);
        }
    }

    public void setThumb(@DrawableRes int resId) {
        if (stuImgThumb != null) {
            stuImgThumb.setImageResource(resId);
        }
    }

    /* --------------------------------------------------- */
    /* > Helper */
    /* --------------------------------------------------- */

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    /* --------------------------------------------------- */
    /* > Interfaces */
    /* --------------------------------------------------- */

    public interface OnUnlockListener {
        void onUnlock();
    }
}
