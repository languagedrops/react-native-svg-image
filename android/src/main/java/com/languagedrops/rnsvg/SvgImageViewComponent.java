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

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff;
import java.io.InputStream;

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
        if (mTintColor == null) {
          Uri uri = Uri.parse(mSrc);
          setImageURI(uri);
        } else {
          drawSVGWithColor();
        }

      } catch (Exception e) {
        Log.e(REACT_CLASS, "Exception", e);
      }
    } else {
      setImageDrawable(getVectorDrawable(getContext(), mSrc));
      if (mTintColor != null) {
        setColorFilter(Color.parseColor(mTintColor));
      }
    }
    mIsDirty = false;
  }

  private void drawSVGWithColor() {
    Bitmap newBM = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
    Canvas bmcanvas = new Canvas(newBM);

    // Render our document onto our canvas
    try {
      Uri uri = Uri.parse(mSrc);
      InputStream is = getContext().getContentResolver().openInputStream(uri);
      SVG svg = SVG.getFromInputStream(is);
      svg.renderToCanvas(bmcanvas);
      Paint paint = new Paint();
      paint.setColorFilter(new PorterDuffColorFilter(Color.parseColor(mTintColor), PorterDuff.Mode.SRC_IN));
      Bitmap bitmapResult = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmapResult);
      canvas.drawBitmap(newBM, 0, 0, paint);
      setImageBitmap(bitmapResult);
    } catch (Exception e) {
      Log.e(REACT_CLASS, "Exception", e);
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
