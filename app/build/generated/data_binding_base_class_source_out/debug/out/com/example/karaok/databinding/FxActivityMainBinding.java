// Generated by data binding compiler. Do not edit!
package com.example.karaok.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.example.karaok.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FxActivityMainBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appBar;

  @NonNull
  public final RecyclerView effectListView;

  @NonNull
  public final FloatingActionButton floatingAddButton;

  @NonNull
  public final Toolbar toolbar;

  protected FxActivityMainBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppBarLayout appBar, RecyclerView effectListView, FloatingActionButton floatingAddButton,
      Toolbar toolbar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appBar = appBar;
    this.effectListView = effectListView;
    this.floatingAddButton = floatingAddButton;
    this.toolbar = toolbar;
  }

  @NonNull
  public static FxActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fx_activity_main, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FxActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FxActivityMainBinding>inflateInternal(inflater, R.layout.fx_activity_main, root, attachToRoot, component);
  }

  @NonNull
  public static FxActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fx_activity_main, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FxActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FxActivityMainBinding>inflateInternal(inflater, R.layout.fx_activity_main, null, false, component);
  }

  public static FxActivityMainBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static FxActivityMainBinding bind(@NonNull View view, @Nullable Object component) {
    return (FxActivityMainBinding)bind(component, view, R.layout.fx_activity_main);
  }
}
