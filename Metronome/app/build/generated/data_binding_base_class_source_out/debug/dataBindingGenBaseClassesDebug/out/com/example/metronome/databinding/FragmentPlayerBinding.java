// Generated by data binding compiler. Do not edit!
package com.example.metronome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.example.metronome.R;
import com.example.metronome.viewModel.PlayerViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentPlayerBinding extends ViewDataBinding {
  @NonNull
  public final ConstraintLayout ConstraintLayout;

  @NonNull
  public final Button buttonNextPlaylist;

  @NonNull
  public final Button buttonPreviousPlaylist;

  @NonNull
  public final TextView playerName;

  @NonNull
  public final EditText playlistTempo;

  @NonNull
  public final RecyclerView recyclerviewPlayer;

  @NonNull
  public final ToggleButton startButtonPlaylist;

  @Bindable
  protected PlayerViewModel mPViewModel;

  protected FragmentPlayerBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ConstraintLayout ConstraintLayout, Button buttonNextPlaylist, Button buttonPreviousPlaylist,
      TextView playerName, EditText playlistTempo, RecyclerView recyclerviewPlayer,
      ToggleButton startButtonPlaylist) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ConstraintLayout = ConstraintLayout;
    this.buttonNextPlaylist = buttonNextPlaylist;
    this.buttonPreviousPlaylist = buttonPreviousPlaylist;
    this.playerName = playerName;
    this.playlistTempo = playlistTempo;
    this.recyclerviewPlayer = recyclerviewPlayer;
    this.startButtonPlaylist = startButtonPlaylist;
  }

  public abstract void setPViewModel(@Nullable PlayerViewModel pViewModel);

  @Nullable
  public PlayerViewModel getPViewModel() {
    return mPViewModel;
  }

  @NonNull
  public static FragmentPlayerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_player, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentPlayerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentPlayerBinding>inflateInternal(inflater, R.layout.fragment_player, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentPlayerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_player, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentPlayerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentPlayerBinding>inflateInternal(inflater, R.layout.fragment_player, null, false, component);
  }

  public static FragmentPlayerBinding bind(@NonNull View view) {
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
  public static FragmentPlayerBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentPlayerBinding)bind(component, view, R.layout.fragment_player);
  }
}