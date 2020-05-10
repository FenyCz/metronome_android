package com.example.metronome.databinding;
import com.example.metronome.R;
import com.example.metronome.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentMetronomeBindingImpl extends FragmentMetronomeBinding implements com.example.metronome.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.con_layout, 6);
        sViewsWithIds.put(R.id.accent_spinner, 7);
        sViewsWithIds.put(R.id.spinner_text_view, 8);
        sViewsWithIds.put(R.id.text_view_bar, 9);
        sViewsWithIds.put(R.id.croller, 10);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback6;
    @Nullable
    private final android.view.View.OnClickListener mCallback4;
    @Nullable
    private final android.view.View.OnClickListener mCallback5;
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    // values
    // listeners
    private OnTextChangedImpl mMViewModelTextChangeBpmAndroidxDatabindingAdaptersTextViewBindingAdapterOnTextChanged;
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener labelandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of ("") + (mViewModel.data.bpm)
            //         is mViewModel.data.setBpm((int) androidx.databinding.ViewDataBinding.parse(callbackArg_0, mViewModel.data.bpm))
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(label);
            // localize variables for thread safety
            // mViewModel.data != null
            boolean mViewModelDataJavaLangObjectNull = false;
            // (int) androidx.databinding.ViewDataBinding.parse(callbackArg_0, mViewModel.data.bpm)
            int intAndroidxDatabindingViewDataBindingParseCallbackArg0MViewModelDataBpm = 0;
            // mViewModel.data.bpm
            int mViewModelDataBpm = 0;
            // mViewModel.data
            com.example.metronome.model.Model mViewModelData = null;
            // ("") + (mViewModel.data.bpm)
            java.lang.String javaLangStringMViewModelDataBpm = null;
            // mViewModel != null
            boolean mViewModelJavaLangObjectNull = false;
            // mViewModel
            com.example.metronome.viewModel.MetronomeViewModel mViewModel = mMViewModel;
            // androidx.databinding.ViewDataBinding.parse(callbackArg_0, mViewModel.data.bpm)
            int androidxDatabindingViewDataBindingParseCallbackArg0MViewModelDataBpm = 0;



            mViewModelJavaLangObjectNull = (mViewModel) != (null);
            if (mViewModelJavaLangObjectNull) {


                mViewModelData = mViewModel.data;

                mViewModelDataJavaLangObjectNull = (mViewModelData) != (null);
                if (mViewModelDataJavaLangObjectNull) {






                    mViewModelDataBpm = mViewModelData.getBpm();

                    androidxDatabindingViewDataBindingParseCallbackArg0MViewModelDataBpm = androidx.databinding.ViewDataBinding.parse(callbackArg_0, mViewModelDataBpm);

                    intAndroidxDatabindingViewDataBindingParseCallbackArg0MViewModelDataBpm = ((int) (androidxDatabindingViewDataBindingParseCallbackArg0MViewModelDataBpm));

                    mViewModelData.setBpm(intAndroidxDatabindingViewDataBindingParseCallbackArg0MViewModelDataBpm);
                }
            }
        }
    };

    public FragmentMetronomeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private FragmentMetronomeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.Spinner) bindings[7]
            , (android.widget.Button) bindings[4]
            , (android.widget.Button) bindings[5]
            , (android.widget.Button) bindings[2]
            , (android.widget.LinearLayout) bindings[6]
            , (com.sdsmdg.harjot.crollerTest.Croller) bindings[10]
            , (android.widget.EditText) bindings[1]
            , (android.widget.TextView) bindings[8]
            , (android.widget.ToggleButton) bindings[3]
            , (android.widget.TextView) bindings[9]
            );
        this.buttonMinus.setTag(null);
        this.buttonPlus.setTag(null);
        this.buttonRepeat.setTag(null);
        this.label.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.startButton.setTag(null);
        setRootTag(root);
        // listeners
        mCallback6 = new com.example.metronome.generated.callback.OnClickListener(this, 4);
        mCallback4 = new com.example.metronome.generated.callback.OnClickListener(this, 2);
        mCallback5 = new com.example.metronome.generated.callback.OnClickListener(this, 3);
        mCallback3 = new com.example.metronome.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
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
        if (BR.mViewModel == variableId) {
            setMViewModel((com.example.metronome.viewModel.MetronomeViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMViewModel(@Nullable com.example.metronome.viewModel.MetronomeViewModel MViewModel) {
        this.mMViewModel = MViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.mViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeMViewModelData((com.example.metronome.model.Model) object, fieldId);
        }
        return false;
    }
    private boolean onChangeMViewModelData(com.example.metronome.model.Model MViewModelData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.bpm) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
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
        int mViewModelDataBpm = 0;
        com.example.metronome.model.Model mViewModelData = null;
        androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged mViewModelTextChangeBpmAndroidxDatabindingAdaptersTextViewBindingAdapterOnTextChanged = null;
        java.lang.String javaLangStringMViewModelDataBpm = null;
        com.example.metronome.viewModel.MetronomeViewModel mViewModel = mMViewModel;

        if ((dirtyFlags & 0xfL) != 0) {



                if (mViewModel != null) {
                    // read mViewModel.data
                    mViewModelData = mViewModel.data;
                }
                updateRegistration(0, mViewModelData);


                if (mViewModelData != null) {
                    // read mViewModel.data.bpm
                    mViewModelDataBpm = mViewModelData.getBpm();
                }


                // read ("") + (mViewModel.data.bpm)
                javaLangStringMViewModelDataBpm = ("") + (mViewModelDataBpm);
            if ((dirtyFlags & 0xaL) != 0) {

                    if (mViewModel != null) {
                        // read mViewModel::textChangeBpm
                        mViewModelTextChangeBpmAndroidxDatabindingAdaptersTextViewBindingAdapterOnTextChanged = (((mMViewModelTextChangeBpmAndroidxDatabindingAdaptersTextViewBindingAdapterOnTextChanged == null) ? (mMViewModelTextChangeBpmAndroidxDatabindingAdaptersTextViewBindingAdapterOnTextChanged = new OnTextChangedImpl()) : mMViewModelTextChangeBpmAndroidxDatabindingAdaptersTextViewBindingAdapterOnTextChanged).setValue(mViewModel));
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.buttonMinus.setOnClickListener(mCallback5);
            this.buttonPlus.setOnClickListener(mCallback6);
            this.buttonRepeat.setOnClickListener(mCallback3);
            this.startButton.setOnClickListener(mCallback4);
        }
        if ((dirtyFlags & 0xfL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.label, javaLangStringMViewModelDataBpm);
        }
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.label, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)mViewModelTextChangeBpmAndroidxDatabindingAdaptersTextViewBindingAdapterOnTextChanged, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, labelandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    public static class OnTextChangedImpl implements androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged{
        private com.example.metronome.viewModel.MetronomeViewModel value;
        public OnTextChangedImpl setValue(com.example.metronome.viewModel.MetronomeViewModel value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onTextChanged(java.lang.CharSequence arg0, int arg1, int arg2, int arg3) {
            this.value.textChangeBpm(arg0, arg1, arg2, arg3); 
        }
    }
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 4: {
                // localize variables for thread safety
                // mViewModel != null
                boolean mViewModelJavaLangObjectNull = false;
                // mViewModel
                com.example.metronome.viewModel.MetronomeViewModel mViewModel = mMViewModel;



                mViewModelJavaLangObjectNull = (mViewModel) != (null);
                if (mViewModelJavaLangObjectNull) {




                    mViewModel.buttonChangeBpm(+5);
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // mViewModel != null
                boolean mViewModelJavaLangObjectNull = false;
                // mViewModel
                com.example.metronome.viewModel.MetronomeViewModel mViewModel = mMViewModel;



                mViewModelJavaLangObjectNull = (mViewModel) != (null);
                if (mViewModelJavaLangObjectNull) {


                    mViewModel.playButtonClick();
                }
                break;
            }
            case 3: {
                // localize variables for thread safety
                // mViewModel != null
                boolean mViewModelJavaLangObjectNull = false;
                // mViewModel
                com.example.metronome.viewModel.MetronomeViewModel mViewModel = mMViewModel;



                mViewModelJavaLangObjectNull = (mViewModel) != (null);
                if (mViewModelJavaLangObjectNull) {




                    mViewModel.buttonChangeBpm(-5);
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // mViewModel != null
                boolean mViewModelJavaLangObjectNull = false;
                // mViewModel
                com.example.metronome.viewModel.MetronomeViewModel mViewModel = mMViewModel;



                mViewModelJavaLangObjectNull = (mViewModel) != (null);
                if (mViewModelJavaLangObjectNull) {


                    mViewModel.buttonTapBpm();
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): mViewModel.data
        flag 1 (0x2L): mViewModel
        flag 2 (0x3L): mViewModel.data.bpm
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}