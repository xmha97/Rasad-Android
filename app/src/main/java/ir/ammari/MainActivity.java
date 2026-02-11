package ir.ammari.rasad;

import android.app.Activity;
import android.os.Bundle;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView view = new TextView(this);
        view.setText("\n\n\nLoading");
        setContentView(view);
        ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        new Thread(() -> {
            try {
                URL url = new URL("https://raw.githubusercontent.com/xmha97/test/refs/heads/main/status");
                InputStream inputStream = url.openStream();
                final int bufLen = 1024;
                byte[] buf = new byte[bufLen];
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int readLen;
                while ((readLen = inputStream.read(buf, 0, bufLen)) != -1)
                    outputStream.write(buf, 0, readLen);
                final String result = outputStream.toString();
                runOnUiThread(() -> view.setText("\n\n\n" + result));
                toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 200);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
