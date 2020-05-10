package com.example.metronome.databinding;
import com.example.metronome.R;
import com.example.metronome.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentPlayerBindingImpl extends FragmentPlayerBinding implements com.example.metronome.generated.callback.OnTextChanged.Listener, com.example.metronome.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.player_name, 3);
        sViewsWithIds.put(R.id.recyclerview_player, 4);
        sViewsWithIds.put(R.id.button_previous_playlist, 5);
        sViewsWithIds.put(R.id.button_next_playlist, 6);
    }
    // views
    // variables
    @Nullable
    private final androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged mCallback1;
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentPlayerBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private FragmentPlayerBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.Button) bindings[6]
            , (android.widget.Button) bindings[5]
            , (android.widget.TextView) bindings[3]
            , (android.widget.EditText) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[4]
            , (android.widget.ToggleButton) bindings[2]
            );
        this.ConstraintLayout.setTag(null);
        this.playlistTempo.setTag(null);
        this.startButtonPlaylist.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new com.example.metronome.generated.callback.OnTextChanged(this, 1);
        mCallback2 = new com.example.metronome.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.pViewModel == variableId) {
            setPViewModel((com.example.metronome.viewModel.PlayerViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPViewModel(@Nullable com.example.metronome.viewModel.PlayerViewModel PViewModel) {
        this.mPViewModel = PViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.pViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.example.metronome.viewModel.PlayerViewModel pViewModel = mPViewModel;
        // batch finished
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.playlistTempo, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, mCallback1, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, (androidx.databinding.InverseBindingListener)null);
            this.startButtonPlaylist.setOnClickListener(mCallback2);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnTextChanged(int sourceId , java.lang.CharSequence callbackArg_0, int callbackArg_1, int callbackArg_2, int callbackArg_3) {
        // localize variables for thread safety
        // pViewModel
        com.example.metronome.viewModel.PlayerViewModel pViewModel = mPViewModel;
        // pViewModel != null
        boolean pViewModelJavaLangObjectNull = false;



        pViewModelJavaLangObjectNull = (pViewModel) != (null);
        if (pViewModelJavaLangObjectNull) {



            pViewModel.textChangeBpm(callbackArg_0);
        }
    }
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // pViewModel
        com.example.metronome.viewModel.PlayerViewModel pViewModel = mPViewModel;
        // pViewModel != null
        boolean pViewModelJavaLangObjectNull = false;



        pViewModelJavaLangObjectNull = (pViewModel) != (null);
        if (pViewModelJavaLangObjectNull) {


            pViewModel.playButtonClick();
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): pViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}