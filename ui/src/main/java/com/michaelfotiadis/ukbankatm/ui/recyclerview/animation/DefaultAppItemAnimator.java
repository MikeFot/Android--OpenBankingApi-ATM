package com.michaelfotiadis.ukbankatm.ui.recyclerview.animation;

public class DefaultAppItemAnimator extends CustomItemAnimator {

    public DefaultAppItemAnimator() {
        this.setItemRemoveCustomizer(PredefinedAnimations.SHRINK);
        this.setItemAddCustomizer(PredefinedAnimations.GROW);
        this.setAddDuration(200L);
        this.setRemoveDuration(200L);
    }
}
