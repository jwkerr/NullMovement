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
            if (slowDown)
                movementForward *= slowDownFactor;
        }

        if (left && right) {
            movementSideways = getMultiplier(lastSideways);
            if (slowDown)
                movementSideways *= slowDownFactor;
        }
    }

    // This will run if both keys on an axis are now being held, if the last held key prior to both being held was positive, it will return negative and vice-versa
    @Unique
    public float getMultiplier(boolean lastPressedWasPositive) {
        return lastPressedWasPositive ? -1.0f : 1.0f;
    }
}
