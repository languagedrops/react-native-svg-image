package com.languagedrops.rnsvg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import android.util.Log;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import java.io.IOException;

public class SvgImageManager extends SimpleViewManager<SvgImageViewComponent> {
  public static final String REACT_CLASS = "SvgImageView";

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public SvgImageViewComponent createViewInstance(ThemedReactContext context) {
    return new SvgImageViewComponent(context);
  }

  @ReactProp(name = "src")
  public void setSrc(SvgImageViewComponent view, @Nullable String src) {
    view.setSrc(src);
  }

  @ReactProp(name = "tintColor")
  public void setTint(SvgImageViewComponent view, @Nullable String tint) {
    view.setTint(tint);
  }

  @Override
  protected void onAfterUpdateTransaction(SvgImageViewComponent view) {
    super.onAfterUpdateTransaction(view);
    view.maybeUpdateView();
  }

}
