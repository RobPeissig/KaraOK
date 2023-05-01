package com.example.karaok;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001dB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\fH\u0016J\u0018\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0012H\u0016J\u0018\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0012H\u0016R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001e"}, d2 = {"Lcom/example/karaok/EffectsAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/karaok/EffectsAdapter$EffectsHolder;", "()V", "effectList", "Ljava/util/ArrayList;", "Lcom/example/karaok/datatype/Effect;", "getEffectList", "()Ljava/util/ArrayList;", "itemTouchHelper", "Landroidx/recyclerview/widget/ItemTouchHelper;", "mRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getMRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "setMRecyclerView", "(Landroidx/recyclerview/widget/RecyclerView;)V", "getItemCount", "", "onAttachedToRecyclerView", "", "recyclerView", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "EffectsHolder", "app_debug"})
public final class EffectsAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.karaok.EffectsAdapter.EffectsHolder> {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.karaok.EffectsAdapter INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.ArrayList<com.example.karaok.datatype.Effect> effectList = null;
    public static androidx.recyclerview.widget.RecyclerView mRecyclerView;
    private static final androidx.recyclerview.widget.ItemTouchHelper itemTouchHelper = null;
    
    private EffectsAdapter() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.example.karaok.datatype.Effect> getEffectList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.recyclerview.widget.RecyclerView getMRecyclerView() {
        return null;
    }
    
    public final void setMRecyclerView(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.example.karaok.EffectsAdapter.EffectsHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.karaok.EffectsAdapter.EffectsHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    public void onAttachedToRecyclerView(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView recyclerView) {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u000eR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0017"}, d2 = {"Lcom/example/karaok/EffectsAdapter$EffectsHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "parentView", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "effect", "Lcom/example/karaok/datatype/Effect;", "getEffect", "()Lcom/example/karaok/datatype/Effect;", "setEffect", "(Lcom/example/karaok/datatype/Effect;)V", "floatFormat", "", "index", "", "layoutContainer", "Landroid/widget/LinearLayout;", "getParentView", "()Landroid/view/ViewGroup;", "bindEffect", "", "bindedEffect", "position", "app_debug"})
    public static final class EffectsHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.view.ViewGroup parentView = null;
        private final android.widget.LinearLayout layoutContainer = null;
        public com.example.karaok.datatype.Effect effect;
        private final java.lang.String floatFormat = "%4.2f";
        private int index = -1;
        
        public EffectsHolder(@org.jetbrains.annotations.NotNull()
        android.view.ViewGroup parentView) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.view.ViewGroup getParentView() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.karaok.datatype.Effect getEffect() {
            return null;
        }
        
        public final void setEffect(@org.jetbrains.annotations.NotNull()
        com.example.karaok.datatype.Effect p0) {
        }
        
        public final void bindEffect(@org.jetbrains.annotations.NotNull()
        com.example.karaok.datatype.Effect bindedEffect, int position) {
        }
    }
}