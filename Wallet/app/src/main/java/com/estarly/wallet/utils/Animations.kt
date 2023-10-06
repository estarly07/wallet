package com.estarly.wallet.utils

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AnimationUtils
import com.estarly.wallet.R

fun View.animationTranslateUp(translate: Boolean = false, hasAnimationDone :()->Unit ={}){
    val moveY = if (translate) 0  else this.height
    val animator = this.animate().translationY(moveY.toFloat()).setDuration(300).setStartDelay(100)
    animator.setListener(object : AnimatorListener {
        override  fun onAnimationStart(animation: Animator) {}
        override  fun onAnimationEnd(animation: Animator) = if(translate) hasAnimationDone() else this@animationTranslateUp.animVanish()
        override  fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })
    animator.start()
}
/**its function is appear an any view*/
fun View.animAppear() {
    val animation = AnimationUtils.loadAnimation(this.context, R.anim.anim_appear)
    this.visibility = View.VISIBLE
    this.animation = animation
}

/**its function is vanish an any view*/
fun View.animVanish() {
    val animation   = AnimationUtils.loadAnimation(this.context, R.anim.anim_disappear)
    this.visibility = View.GONE
    this.animation  = animation
}
fun View.animRotate(rotate : Boolean = false) {
    val angle = if (rotate) 0f else 180f
    val animator = ObjectAnimator.ofFloat(this, View.ROTATION, angle)
    animator.duration = 600
    animator.startDelay = 100
    animator.start()
}