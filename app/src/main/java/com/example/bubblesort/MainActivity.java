package com.example.bubblesort;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    SortView sortView;
    ExecutorService executorService;
    Handler handler;
    ProgressBar progressBar;
    int[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sortView = findViewById(R.id.sortView);
        progressBar = findViewById(R.id.progressBar);

        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        EditText numInput = findViewById(R.id.numInput);
        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {

            String value = numInput.getText().toString();

            if (value.isEmpty()) {
                Toast.makeText(this, "Proszę wprowadzić liczbę elementów", Toast.LENGTH_SHORT).show();
                return;
            }

            int size = Integer.parseInt(value);
            array = generateRandomArray(size);
            progressBar.setProgress(0);

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    sortArray();
                }
            });
        });
    }

    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100) + 1;
        }
        return array;
    }

    private void sortArray() {
        if (array.length <= 1) {
            handler.post(() -> {
                sortView.setArray(array);
                progressBar.setProgress(100);
                Toast.makeText(this, "Sortowanie zakończone", Toast.LENGTH_SHORT).show();
            });
            return;
        }

        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;

                    int progress = (int) (((float) (i * n + j) / (n * n)) * 100);
                    handler.post(() -> {
                        sortView.setArray(array.clone());
                        progressBar.setProgress(progress);
                    });

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        handler.post(() -> {
            sortView.setArray(array);
            progressBar.setProgress(100);
            Toast.makeText(this, "Sortowanie zakończone", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}