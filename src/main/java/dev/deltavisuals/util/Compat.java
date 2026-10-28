package dev.deltavisuals.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec3d;

/**
 * Version-sensitive Minecraft calls that are not plain Screen/DrawContext usage.
 * If a Minecraft/Yarn update breaks compilation, the fix is most likely in this file,
 * in render/Pipelines.java or in render/WorldHook.java.
 */
public final class Compat {
    private Compat() {}

    /** Camera position in world space. (Older Yarn: camera.getPos().) */
    public static Vec3d cameraPos(Camera camera) { return camera.getCameraPos(); }

    /** Render partial tick (0..1). */
    public static float partialTick(MinecraftClient mc) { return mc.getRenderTickCounter().getTickProgress(false); }

    /** True if a vanilla Minecraft control is currently bound to the same physical key / button. */
    public static boolean conflictsWithVanilla(MinecraftClient mc, boolean mouse, int code) {
        try {
            InputUtil.Type t = mouse ? InputUtil.Type.MOUSE : InputUtil.Type.KEYSYM;
            String key = t.createFromCode(code).getTranslationKey();
            for (KeyBinding kb : mc.options.allKeys) {
                if (key.equals(kb.getBoundKeyTranslationKey())) return true;
            }
        } catch (Throwable ignored) { }
        return false;
    }
}
