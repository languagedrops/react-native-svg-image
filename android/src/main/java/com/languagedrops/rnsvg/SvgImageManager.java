package com.languagedrops.rnsvg;

import android.content.Context;
import android.graphics.drawable.VectorDrawable;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class SvgImageManager extends SimpleViewManager<ImageView> {
  public static final String REACT_CLASS = "SvgImageView";

  @Override public String getName() {
    return REACT_CLASS;
  }

  @Override public ImageView createViewInstance(ThemedReactContext context) {
    return new ImageView(context);
  }

  @ReactProp(name = "src") public void setSrc(ImageView view, @Nullable String src) {
    view.setImageDrawable(getVectorDrawable(view.getContext(), src));
  }

  @ReactProp(name = "tintColor") public void setTint(ImageView view, @Nullable String tint) {
     view.setColorFilter(tint == null ? null : Color.parseColor(tint));
  }

  @Nullable
  private VectorDrawable getVectorDrawable(@NonNull Context context,
      @Nullable final String fileName) {
    if (fileName == null) {
      return null;
    }
    int res = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
    if (res != 0) {
      try {
        return (VectorDrawable) context.getDrawable(res);
      } catch (Exception e) {
        Log.e(REACT_CLASS, "Exception", e);
        return null;
      }
    }
    Log.e(REACT_CLASS, "No drawable with name " + fileName + " found.");
    return null;
  }
}
