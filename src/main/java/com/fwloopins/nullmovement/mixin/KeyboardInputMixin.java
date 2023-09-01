package com.fwloopins.nullmovement.mixin;

import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends Input {
    @Unique private boolean lastForward;
    @Unique private boolean lastSideways;

    @Inject(method = "tick", at = @At("TAIL"))
    private void afterTick(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
        boolean forward = pressingForward;
        boolean backward = pressingBack;
        boolean left = pressingLeft;
        boolean right = pressingRight;

        if (forward && !backward) {
            lastForward = true;
        } else if (!forward && backward) {
            lastForward = false;
        }

        if (left && !right) {
            lastSideways = true;
        } else if (!left && right) {
            lastSideways = false;
        }

        if (forward && backward) {
            movementForward = getMultiplier(lastForward);
        }

        if (left && right) {
            movementSideways = getMultiplier(lastSideways);
        }

        if (slowDown) {
            movementForward *= slowDownFactor;
            movementSideways *= slowDownFactor;
        }
    }

    @Unique
    public float getMultiplier(boolean lastPressed) {
        if (!lastPressed) {
            return 1.0f;
        } else {
            return -1.0f;
        }
    }
}
