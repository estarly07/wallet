package com.estarly.wallet.utils

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
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
/**Expanded animation for background floating buttons*/
fun View.animExpand() {
    animAppear()
    val centerX = width
    val centerY = height

    val scaleAnimation = ScaleAnimation(0f, 1f, 0f, 1f, centerX.toFloat(), centerY.toFloat())
    scaleAnimation.duration = 400 // Duración de la animación en milisegundos

    val translateAnimation = TranslateAnimation(
        centerX.toFloat(), 0f,
        centerY.toFloat(), 0f
    )
    translateAnimation.duration = 400 // Duración de la animación en milisegundos
    val animationSet = AnimationSet(true)
    animationSet.addAnimation(scaleAnimation)
    animationSet.addAnimation(translateAnimation)

    startAnimation(animationSet)
}
/**
 * Función para mostrar vistas de diseño en cascada.
 *
 * Esta función se utiliza para mostrar cada vista de diseño en un patrón de cascada, donde las
 * vistas se superponen o se muestran una detrás de la otra.
 */
fun LinearLayout.animateCascadeLayout(){
    // Inicializa el retraso
    var animationDelay = 0L
    for (i in 0 until childCount) {
        val animation = AnimationUtils.loadAnimation(this.context, R.anim.fade_in_down)
        val child = getChildAt(i)
        animationDelay += 100L
        animation.startOffset = animationDelay
        child.startAnimation(animation)
    }
}
fun View.transitionLeft(){
    val originalX = translationX
    val targetX = width.toFloat() / 2 // Desplazamiento hacia la izquierda
    val animator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, targetX, originalX).apply {
        duration = 500 // Duración de la animación en milisegundos
        interpolator = android.view.animation.AccelerateDecelerateInterpolator() // Interpolador para una transición suave
    }
    animator.start()
}
