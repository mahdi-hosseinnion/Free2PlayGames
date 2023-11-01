package com.ssmmhh.free2playgames.featureGame.presentation.util

import android.animation.LayoutTransition
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import com.ssmmhh.free2playgames.featureGame.presentation.util.TextViewCollapsingAnimation.CollapseAnimationState.*

/**
 * A class that add collapse and expand animation to textview with long text
 * InspiredBy: https://medium.com/@yuriyskul/expandable-textview-with-layouttransition-part-1-b506681e78e7
 */

class TextViewCollapsingAnimation(
    private val containerView: ViewGroup,
    private val textView: TextView,
    isCollapsedAtInit: Boolean,
    initialText: String? = null,
    private val animationDuration: Long = 1000,
    private val collapsedTextViewMaxLine: Int = 3
) : LayoutTransition.TransitionListener {

    private val TAG = "TextViewCollapsingAnime"

    private var mCurrentAnimationState: CollapseAnimationState = IDLE

    /** prevent the first initial to have animation*/
    private var isTransitionAnimationInitialized = false

    init {

        // set initial text before setup animation
        initialText?.let { updateWithNewText(it) }

        textView.maxLines = if (isCollapsedAtInit) {
            collapsedTextViewMaxLine
        } else {
            Integer.MAX_VALUE
        }
    }

    /**
     * change textView collapse state
     */
    fun onCollapseStateChanged(isNewStateCollapse: Boolean) {
        Log.d(TAG, "onCollapseStateChanged: isNewStateCollapse: $isNewStateCollapse")
        if (isAnimationRunning()) {
            // recall setLayoutTransition to cancel ongoing animation
            containerView.layoutTransition = containerView.layoutTransition
        }
        if (textView.maxLines == collapsedTextViewMaxLine && isNewStateCollapse) {
            // don't do anything if new state is collapse and text is trimmed already
            return
        }
        textView.maxLines = if (isNewStateCollapse) {
            mCurrentAnimationState = COLLAPSING
            collapsedTextViewMaxLine
        } else {
            mCurrentAnimationState = EXPANDING
            Int.MAX_VALUE
        }
        if (isNewStateCollapse) {
            textView.post {
                // workaround #1: prevent text trimming at start collapsing
                textView.maxLines = Integer.MAX_VALUE
            }
        }
    }

    /**
     * Make sures that text is long enough to enable collapsing animation
     * If [textView]'s lines (after setting the text), is less then [collapsedTextViewMaxLine] then
     * it will disable [textView]'s isClickable and ellipsize
     */
    fun updateWithNewText(text: String) {
        textView.text = text
        /**
         * textView still has previous instance of Layout(StaticLayout.java) from old text so
         * to use new Layout values for computation we have to wait until text is going to be
         * “relayouted”. It can be done with the help of OnGlobalLayoutListener. Ad listener and
         * remove it form ViewTreeObserver after computation is finished.
         * Computation logic is:
         * Detect whether new textView with new text is limited with maxLines and if
         * it is not — compare its lines count with limit value, and if text is short enough to
         * fit the limit lines size — disable clicking, otherwise — enable;
         * if text is limited (getMaxLines() != Integer.MAX_VALUE) — check whether it is trimmed
         * up to limit lines count or it is just short enough to fit the limited textview size
         */
        textView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (isTextViewMaxLineUnlimited()) {
                    if (canBeCollapsed()) {
                        setTextViewTypeToFix()
                    } else {
                        setTextViewTypeToCollapsable()
                    }
                } else {
                    if (isTrimmedWithLimitLines()) {
                        setTextViewTypeToFix()
                    } else {
                        setTextViewTypeToCollapsable()
                    }
                }
                if (!isTransitionAnimationInitialized) {
                    setTransitionAnimationToContainer()
                }
                textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun setTransitionAnimationToContainer() {
        containerView.layoutTransition = LayoutTransition().apply {
            setDuration(animationDuration)
            enableTransitionType(LayoutTransition.CHANGING)
            addTransitionListener(this@TextViewCollapsingAnimation)
        }
        isTransitionAnimationInitialized = true
    }

    override fun startTransition(
        transition: LayoutTransition?,
        container: ViewGroup?,
        view: View?,
        transitionType: Int
    ) {
    }

    override fun endTransition(
        transition: LayoutTransition?,
        container: ViewGroup?,
        view: View?,
        transitionType: Int
    ) {
        if (COLLAPSING == mCurrentAnimationState) {
            Log.d(TAG, "endTransition: IF CALLED")
            // workaround #2: prevent textview expanding again
            textView.maxLines = collapsedTextViewMaxLine
        }
        // idle the state when animation is finished
        mCurrentAnimationState = IDLE
    }

    private fun setTextViewTypeToCollapsable() {
        textView.isClickable = true
        textView.ellipsize = TextUtils.TruncateAt.END
    }

    private fun setTextViewTypeToFix() {
        textView.isClickable = false
        textView.ellipsize = null
    }

    private fun isAnimationIdle(): Boolean = mCurrentAnimationState == IDLE

    private fun isAnimationRunning(): Boolean = !isAnimationIdle()

    private fun isTextViewMaxLineUnlimited(): Boolean = textView.maxLines == Int.MAX_VALUE

    private fun canBeCollapsed(): Boolean = textView.lineCount <= collapsedTextViewMaxLine

    private fun isTrimmedWithLimitLines(): Boolean = textView.lineCount <= textView.maxLines

    private enum class CollapseAnimationState {
        IDLE,
        COLLAPSING,
        EXPANDING
    }
}
