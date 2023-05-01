package com.example.karaok.datatype;

import java.lang.System;

/**
 * Class which represents an audio effect
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u000eH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001c"}, d2 = {"Lcom/example/karaok/datatype/Effect;", "", "effectDescription", "Lcom/example/karaok/datatype/EffectDescription;", "(Lcom/example/karaok/datatype/EffectDescription;)V", "getEffectDescription", "()Lcom/example/karaok/datatype/EffectDescription;", "enable", "", "getEnable", "()Z", "setEnable", "(Z)V", "name", "", "getName", "()Ljava/lang/String;", "paramValues", "", "getParamValues", "()[F", "component1", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class Effect {
    @org.jetbrains.annotations.NotNull()
    private final com.example.karaok.datatype.EffectDescription effectDescription = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final float[] paramValues = null;
    private boolean enable = true;
    
    /**
     * Class which represents an audio effect
     */
    @org.jetbrains.annotations.NotNull()
    public final com.example.karaok.datatype.Effect copy(@org.jetbrains.annotations.NotNull()
    com.example.karaok.datatype.EffectDescription effectDescription) {
        return null;
    }
    
    /**
     * Class which represents an audio effect
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * Class which represents an audio effect
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * Class which represents an audio effect
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public Effect(@org.jetbrains.annotations.NotNull()
    com.example.karaok.datatype.EffectDescription effectDescription) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.karaok.datatype.EffectDescription component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.karaok.datatype.EffectDescription getEffectDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final float[] getParamValues() {
        return null;
    }
    
    public final boolean getEnable() {
        return false;
    }
    
    public final void setEnable(boolean p0) {
    }
}