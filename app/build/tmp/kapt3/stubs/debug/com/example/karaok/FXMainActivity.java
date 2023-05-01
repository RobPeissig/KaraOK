package com.example.karaok;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001&B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0003J\u0012\u0010\u0013\u001a\u00020\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0015J\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0012H\u0014J\u0010\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0012H\u0014J-\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00042\u000e\u0010 \u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0!2\u0006\u0010\"\u001a\u00020#H\u0016\u00a2\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u0012H\u0014R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/example/karaok/FXMainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "MY_PERMISSIONS_RECORD_AUDIO", "", "getMY_PERMISSIONS_RECORD_AUDIO", "()I", "TAG", "", "binding", "Lcom/example/karaok/databinding/FxActivityMainBinding;", "getBinding", "()Lcom/example/karaok/databinding/FxActivityMainBinding;", "setBinding", "(Lcom/example/karaok/databinding/FxActivityMainBinding;)V", "isAudioEnabled", "", "handleMidiDevices", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "onDestroy", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onPause", "onRequestPermissionsResult", "requestCode", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResume", "MyMidiReceiver", "app_debug"})
public final class FXMainActivity extends androidx.appcompat.app.AppCompatActivity {
    private java.lang.String TAG;
    public com.example.karaok.databinding.FxActivityMainBinding binding;
    private boolean isAudioEnabled = false;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 17;
    private java.util.HashMap _$_findViewCache;
    
    public FXMainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.karaok.databinding.FxActivityMainBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull()
    com.example.karaok.databinding.FxActivityMainBinding p0) {
    }
    
    public final int getMY_PERMISSIONS_RECORD_AUDIO() {
        return 0;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.M)
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.M)
    private final void handleMidiDevices() {
    }
    
    @java.lang.Override()
    public boolean onCreateOptionsMenu(@org.jetbrains.annotations.NotNull()
    android.view.Menu menu) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.M)
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J*\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0004\u00a8\u0006\u0013"}, d2 = {"Lcom/example/karaok/FXMainActivity$MyMidiReceiver;", "Landroid/media/midi/MidiReceiver;", "seekBar", "Landroid/widget/SeekBar;", "(Landroid/widget/SeekBar;)V", "TAG", "", "getSeekBar", "()Landroid/widget/SeekBar;", "setSeekBar", "onSend", "", "data", "", "offset", "", "count", "timestamp", "", "app_debug"})
    public static final class MyMidiReceiver extends android.media.midi.MidiReceiver {
        @org.jetbrains.annotations.NotNull()
        private android.widget.SeekBar seekBar;
        private final java.lang.String TAG = "MyMidiReceiver";
        
        public MyMidiReceiver(@org.jetbrains.annotations.NotNull()
        android.widget.SeekBar seekBar) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.SeekBar getSeekBar() {
            return null;
        }
        
        public final void setSeekBar(@org.jetbrains.annotations.NotNull()
        android.widget.SeekBar p0) {
        }
        
        @java.lang.Override()
        public void onSend(@org.jetbrains.annotations.Nullable()
        byte[] data, int offset, int count, long timestamp) {
        }
    }
}