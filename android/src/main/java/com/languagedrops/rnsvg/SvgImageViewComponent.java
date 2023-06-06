package com.languagedrops.rnsvg;

import javax.annotation.Nullable;
import androidx.annotation.NonNull;
import android.content.Context;
import android.util.Log;
import java.io.IOException;
import android.graphics.Color;
import android.net.Uri;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVG;

public class SvgImageViewComponent extends SVGImageView {
  public static final String REACT_CLASS = "SvgImageViewComponent";

  private @Nullable String mTintColor;
  private @Nullable String mSrc;
  private boolean mIsDirty;

  public SvgImageViewComponent(Context context) {
    super(context);
  }

  public void setSrc(@Nullable String src) {
    // check if src contains documents in it
    mSrc = src;
    mIsDirty = true;
  }

  public void setTint(@Nullable String tint) {
    mTintColor = tint;
    mIsDirty = true;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (w > 0 && h > 0 && (w != oldw || h != oldh)) {
      maybeUpdateView();
    }
  }

  public void maybeUpdateView() {
    if (!mIsDirty) {
      return;
    }
    if (getWidth() <= 0 || getHeight() <= 0) {
      // If need a resize and the size is not yet set, wait until the layout pass
      // provides one
      return;
    }

    if (mSrc != null && mSrc.contains("file://")) {
      try {
        Uri uri = Uri.parse(mSrc);
        setImageURI(uri);
      } catch (Exception e) {
        Log.e(REACT_CLASS, "Exception", e);
      }
    } else {
      setImageDrawable(getVectorDrawable(getContext(), mSrc));
    }
    mIsDirty = false;
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
