package com.example.karaok;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0082 J\u000e\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\t\u0010\u0011\u001a\u00020\nH\u0086 J\t\u0010\u0012\u001a\u00020\nH\u0086 J\u000e\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\fJ\u0019\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0082 J\u0011\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014H\u0082 J\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u001bH\u0082 \u00a2\u0006\u0002\u0010\u001cJ!\u0010\u001d\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0082 J\u000e\u0010 \u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\fJ\u0011\u0010!\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\fH\u0082 J\u0016\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020\fJ\u0019\u0010%\u001a\u00020\n2\u0006\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020\fH\u0082 J\u0016\u0010&\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\fR\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\'"}, d2 = {"Lcom/example/karaok/NativeInterface;", "", "()V", "effectDescriptionMap", "", "", "Lcom/example/karaok/datatype/EffectDescription;", "getEffectDescriptionMap", "()Ljava/util/Map;", "addDefaultEffectNative", "", "id", "", "addEffect", "effect", "Lcom/example/karaok/datatype/Effect;", "convertEffectToId", "createAudioEngine", "destroyAudioEngine", "enable", "", "enableEffectAt", "turnOn", "index", "enableEffectNative", "enablePassthroughNative", "getEffects", "", "()[Lcom/example/karaok/datatype/EffectDescription;", "modifyEffectNative", "params", "", "removeEffectAt", "removeEffectNative", "rotateEffectAt", "from", "to", "rotateEffectNative", "updateParamsAt", "app_debug"})
public final class NativeInterface {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.karaok.NativeInterface INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, com.example.karaok.datatype.EffectDescription> effectDescriptionMap = null;
    
    private NativeInterface() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, com.example.karaok.datatype.EffectDescription> getEffectDescriptionMap() {
        return null;
    }
    
    public final void addEffect(@org.jetbrains.annotations.NotNull()
    com.example.karaok.datatype.Effect effect) {
    }
    
    public final void enableEffectAt(boolean turnOn, int index) {
    }
    
    public final void updateParamsAt(@org.jetbrains.annotations.NotNull()
    com.example.karaok.datatype.Effect effect, int index) {
    }
    
    public final void removeEffectAt(int index) {
    }
    
    public final void rotateEffectAt(int from, int to) {
    }
    
    public final void enable(boolean enable) {
    }
    
    public final native void createAudioEngine() {
    }
    
    public final native void destroyAudioEngine() {
    }
    
    private final native com.example.karaok.datatype.EffectDescription[] getEffects() {
        return null;
    }
    
    private final native void addDefaultEffectNative(int id) {
    }
    
    private final native void removeEffectNative(int index) {
    }
    
    private final native void rotateEffectNative(int from, int to) {
    }
    
    private final native void modifyEffectNative(int id, int index, float[] params) {
    }
    
    private final native void enableEffectNative(int index, boolean enable) {
    }
    
    private final native void enablePassthroughNative(boolean enable) {
    }
    
    private final int convertEffectToId(com.example.karaok.datatype.Effect effect) {
        return 0;
    }
}