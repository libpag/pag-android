package com.tencent.libpag.sample.libpag_sample;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import org.libpag.PAGFile;
import org.libpag.PAGImage;
import org.libpag.PAGText;
import org.libpag.PAGView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private EGLDisplay eglDisplay;
    private EGLSurface eglSurface;
    private EGLContext eglContext;

    private synchronized void eglSetup() {
        eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        int[] version = new int[2];
        EGL14.eglInitialize(eglDisplay, version, 0, version, 1);
        EGL14.eglBindAPI(EGL14.EGL_OPENGL_ES_API);
        int[] attributeList = {
            EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
            EGL14.EGL_RED_SIZE, 8,
            EGL14.EGL_GREEN_SIZE, 8,
            EGL14.EGL_BLUE_SIZE, 8,
            EGL14.EGL_ALPHA_SIZE, 8,
            EGL14.EGL_STENCIL_SIZE, 8,
            EGL14.EGL_SAMPLE_BUFFERS, 1,
            EGL14.EGL_SAMPLES, 4,
            EGL14.EGL_NONE
        };
        EGLConfig[] configs = new EGLConfig[1];
        int[] numConfigs = new int[1];
        EGL14.eglChooseConfig(eglDisplay, attributeList, 0, configs, 0,
            configs.length, numConfigs, 0);

        int[] attribute_list = {
            EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
            EGL14.EGL_NONE
        };

        eglContext = EGL14.eglCreateContext(eglDisplay, configs[0], EGL14.EGL_NO_CONTEXT,
            attribute_list, 0);

        int[] surfaceAttributes = {
            EGL14.EGL_WIDTH, 1,
            EGL14.EGL_HEIGHT, 1,
            EGL14.EGL_NONE
        };
        eglSurface = EGL14.eglCreatePbufferSurface(eglDisplay, configs[0], surfaceAttributes, 0);
        EGL14.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext);
    }

    private PAGImage createPAGImage() {
//        eglSetup();

        AssetManager assetManager = getAssets();
        InputStream stream = null;
        try {
            stream = assetManager.open("test.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        if (bitmap == null) {
            return null;
        }

        return PAGImage.FromBitmap(bitmap);

//        int[] textures = new int[1];
//        GLES20.glGenTextures(1, textures, 0);
//        int textureID = textures[0];
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID);
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
//            GLES20.GL_LINEAR);
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
//            GLES20.GL_LINEAR);
//        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
//            GLES20.GL_CLAMP_TO_EDGE);
//        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
//            GLES20.GL_CLAMP_TO_EDGE);
//        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, bitmap.getWidth(),
//            bitmap.getHeight(), 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, null);
//        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
//
//        return PAGImage.FromTexture(textureID, GLES20.GL_TEXTURE_2D,
//            bitmap.getWidth(), bitmap.getHeight());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        RelativeLayout backgroundView = findViewById(R.id.background_view);
        final PAGView pagView = new PAGView(this, eglContext);
        pagView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        backgroundView.addView(pagView);
        PAGFile pagFile = PAGFile.Load(getAssets(), "replacement.pag");

        testReplaceImage(pagFile, pagView);

        testEditText(pagFile, pagView);

        pagView.setRepeatCount(-1);
        pagView.setFile(pagFile);
        pagView.play();
    }

    /**
     * Test replace image.
     */
    void testReplaceImage(PAGFile pagFile, PAGView pagView) {
        if (pagFile == null || pagView == null || pagFile.numImages() <= 0) return;
        pagView.replaceImage(0, createPAGImage());
    }

    /**
     *
     * Test edit text.
     */
    void testEditText(PAGFile pagFile, PAGView pagView) {
        if (pagFile == null || pagView == null || pagFile.numTexts() <= 0) return;
        PAGText textData = pagFile.getTextData(0);
        textData.text = "haha哈哈大😆";
        textData.fontSize = 13;
        pagView.setTextData(0, textData);
    }
}
