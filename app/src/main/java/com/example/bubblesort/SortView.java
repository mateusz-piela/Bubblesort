package com.example.bubblesort;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SortView extends View {

    private int[] array;
    private Paint paint;

    public SortView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
    }

    public void setArray(int[] array) {
        this.array = array;
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (array == null || paint == null) {
            return;
        }

        float width = getWidth();
        float height = getHeight();
        float barWidth = (width) / array.length;

        for (int i = 0; i < array.length; i++) {
            float barHeight = (float) ((array[i] / 100.0) * height);

            float left = i * (barWidth);
            float right = left + barWidth;
            canvas.drawRect(left, height - barHeight, right, height, paint);
        }
    }
}