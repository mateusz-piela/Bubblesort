package com.example.bubblesort;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Random;

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

        int width = getWidth();
        int height = getHeight();
        int space = 1;
        int maxSpace = space * (array.length - 1);
        int barWidth = (width - maxSpace) / array.length;
        int remainderSpace = (width - maxSpace) % array.length;

        for (int i = 0; i < array.length; i++) {
            int barHeight = (int) ((array[i] / 100.0) * height);

            int currentBarWidth = barWidth;
            if (i < remainderSpace) {
                currentBarWidth += 1;
            }

            int left = i * (barWidth + space) + Math.min(i, remainderSpace);
            int right = left + currentBarWidth;
            canvas.drawRect(left, height - barHeight, right, height, paint);
        }
    }
}
