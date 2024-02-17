package ru.ishmatov.russianroulette;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {
    private SoundPool sounds;
    private int sound_shot;
    private int sound_misfire;
    private int sound_drum;
    private ImageView blood_image;
    private int on_shot;
    private int random;
    private TextView bullets;
    private Button shot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sounds = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
        loadSounds();
        blood_image = findViewById(R.id.imageView2);
        bullets = findViewById(R.id.count_bullets);
        shot = findViewById(R.id.btn_shot);
   }


    private void loadSounds() {
        sound_shot = sounds.load(this, R.raw.revolver_shot, 1);
        sound_misfire = sounds.load(this, R.raw.gun_false, 1);
        sound_drum = sounds.load(this, R.raw.revolver_baraban, 1);
    }
    public void onShot(View view) {
        if (on_shot == random) {
            sounds.play(sound_shot, 1.0f, 1.0f, 1, 0, 1);
            blood_image.setVisibility(View.VISIBLE);
            shot.setEnabled(false);
        } else {
            onMisfire();
            if (random < get_bullets()) {
                random++;
            } else {
                random = 0;
            }
        }



    }

    public void onMisfire() {
        sounds.play(sound_misfire, 1.0f, 1.0f, 1, 0, 1);
        blood_image.setVisibility(View.INVISIBLE);
    }

    public void onDrum(View view) {
        sounds.play(sound_drum, 1.0f, 1.0f, 1, 0, 1);
        blood_image.setVisibility(View.INVISIBLE);
        on_shot = new Random().nextInt(get_bullets());
        random = new Random().nextInt(get_bullets());
        shot.setEnabled(true);
    }

    public void add_bullet(View view) {
        int b = get_bullets();
        if (b == 11) {
            return;
        }
        b++;
        bullets.setText(Integer.toString(b));
    }

    public void remote_bullet(View view) {
        int b = get_bullets();
        if (b == 3) {
            return;
        }
        b--;
        bullets.setText(Integer.toString(b));
    }

    private int get_bullets() {
        return Integer.parseInt(bullets.getText().toString());
    }

}