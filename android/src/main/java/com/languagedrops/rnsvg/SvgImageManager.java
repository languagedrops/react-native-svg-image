package com.languagedrops.rnsvg;

import android.content.Context;
import android.graphics.drawable.VectorDrawable;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import android.util.Log;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVG;
import java.io.InputStream;
import java.net.URL;
import android.net.Uri;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import java.io.IOException;

public class SvgImageManager extends SimpleViewManager<SVGImageView> {
  public static final String REACT_CLASS = "SvgImageView";
  private final OkHttpClient client = new OkHttpClient();

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public SVGImageView createViewInstance(ThemedReactContext context) {
    return new SVGImageView(context);
  }

  @ReactProp(name = "src")
  public void setSrc(SVGImageView view, @Nullable String src) {
    // check if src contains documents in it
    if (src != null && src.contains("http")) {
      try {
        // new Thread(new Runnable() {
        // @Override
        // public void run() {
        // try {
        // InputStream stream = new URL(src).openStream();
        // SVG svg = SVG.getFromInputStream(stream);
        // view.setSVG(svg);
        // } catch (Exception ex) {
        // ex.printStackTrace();
        // }
        // }
        // }).start();
        Request request = new Request.Builder()
            .url(src)
            .build();

        client.newCall(request).enqueue(new Callback() {
          @Override
          public void onFailure(Call call, IOException e) {
            e.printStackTrace();
          }

          @Override
          public void onResponse(Call call, Response response) throws IOException {
            try (ResponseBody responseBody = response.body()) {
              if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

              try {
                SVG svg = SVG.getFromString(responseBody.string());
                view.setSVG(svg);
              } catch (Exception e) {
                Log.e(REACT_CLASS, "Exception", e);
              }
            }
          }
        });
      } catch (Exception e) {
        Log.e(REACT_CLASS, "Exception", e);
      }
    } else if (src != null && src.contains("file://")) {
      try {
        Uri uri = Uri.parse(src);
        view.setImageURI(uri);
      } catch (Exception e) {
        Log.e(REACT_CLASS, "Exception", e);
      }
    } else {
      view.setImageDrawable(getVectorDrawable(view.getContext(), src));
    }
  }

  @ReactProp(name = "tintColor")
  public void setTint(SVGImageView view, @Nullable String tint) {
    if (tint != null) {
      view.setColorFilter(Color.parseColor(tint));
    }
  }

  @Nullable
  private VectorDrawableCompat getVectorDrawable(@NonNull Context context,
      @Nullable final String fileName) {
    if (fileName == null) {
      return null;
    }
    int res = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
    if (res != 0) {
      try {
        return VectorDrawableCompat.create(context.getResources(), res, null);
      } catch (Exception e) {
        Log.e(REACT_CLASS, "Exception", e);
        return null;
      }
    }
    Log.e(REACT_CLASS, "No drawable with name " + fileName + " found.");
    return null;
  }
}
