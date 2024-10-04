import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity {

    private static final int BUTTON_COUNT = 4;
    private static final String[] LETTERS = {"A", "B", "C", "D"};
    private static final int BUTTON_SIZE_DP = 60; // Buton boyutu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ConstraintLayout buttonContainer = findViewById(R.id.buttonContainer);
        
        // Container'ın boyutu hazır olduğunda butonları oluştur
        buttonContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                buttonContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                createButtons(buttonContainer);
            }
        });
    }

    private void createButtons(ConstraintLayout container) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(container);

        int buttonSizePx = (int) (BUTTON_SIZE_DP * getResources().getDisplayMetrics().density);
        int containerWidth = container.getWidth();
        int containerHeight = container.getHeight();
        int radius = Math.min(containerWidth, containerHeight) / 3; // Çember yarıçapı

        for (int i = 0; i < BUTTON_COUNT; i++) {
            Button button = new Button(this);
            button.setId(View.generateViewId());
            button.setText(LETTERS[i]);

            container.addView(button, new ConstraintLayout.LayoutParams(buttonSizePx, buttonSizePx));

            float angle = (float) Math.toRadians(i * (360.0 / BUTTON_COUNT));
            int centerX = containerWidth / 2;
            int centerY = containerHeight / 2;

            constraintSet.constrainWidth(button.getId(), buttonSizePx);
            constraintSet.constrainHeight(button.getId(), buttonSizePx);

            constraintSet.constrainCircle(button.getId(), ConstraintSet.PARENT_ID, radius, (float) Math.toDegrees(angle));
        }

        constraintSet.applyTo(container);
    }
}
