package com.example.administrator.myapplication.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.Activity.RegisterActivity;
import com.example.administrator.myapplication.Activity.Server;
import com.example.administrator.myapplication.Activity.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/12.
 */

public class AvatarView extends View {

    public AvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarView(Context context) {
        super(context);
    }

    Paint paint;
    float radius;
    Handler mainThreadHandler = new Handler();

    public void setBitmap(Bitmap bmp) {
        paint = new Paint();
        paint.setShader(new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        radius = Math.min(bmp.getWidth(), bmp.getHeight());
        invalidate();

    }

    public void load(User user) {
        OkHttpClient client = Server.getSharedClient();

        Request request = new Request.Builder()
                .url(Server.serverAddress + user.getAvatar())
                .method("get", null)
                .build();

        client.newCall(request).enqueue(new Callback() {//向后台发送请求进入队列中

            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                try {
                    byte[] bytes = arg1.body().bytes();
                    final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setBitmap(bmp);
                        }
                    });

                } catch (Exception ex) {

                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (paint != null) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
        }
    }
}
