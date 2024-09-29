package edu.jgsilveira.challenge.kg.presentation.view.error

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import edu.jgsilveira.challenge.kg.R
import edu.jgsilveira.challenge.kg.databinding.ErrorHintLayoutBinding

internal class ErrorHintView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding by lazy {
        ErrorHintLayoutBinding.inflate(
            LayoutInflater.from(context),
            this
        )
    }

    init {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        orientation = VERTICAL
        context.withStyledAttributes(
            attrs,
            R.styleable.ErrorHintView,
            defStyleAttr
        ) {
            val iconDrawable = getDrawable(
                R.styleable.ErrorHintView_error_hint_icon
            )
            val title = getString(
                R.styleable.ErrorHintView_error_hint_title
            )
            val primaryButtonText = getString(
                R.styleable.ErrorHintView_error_hint_primary_button_text
            )
            setIconDrawable(iconDrawable)
            setTitle(title)
            setPrimaryButtonText(primaryButtonText)
        }
    }

    fun setIconDrawable(drawable: Drawable?) {
        with(binding.errorHintIcon) {
            setImageDrawable(drawable)
            isVisible = drawable != null
        }
    }

    fun setIconResource(@DrawableRes iconResId: Int) {
        val drawable = ResourcesCompat.getDrawable(
            resources,
            iconResId,
            context.theme
        )
        setIconDrawable(drawable)
    }

    fun setTitle(title: String?) {
        with(binding.errorHintTitle) {
            text = title
            isVisible = !title.isNullOrEmpty()
        }
    }

    fun setPrimaryButtonText(text: String?) {
        with(binding.errorHintPrimaryButton) {
            setText(text)
            isVisible = !text.isNullOrEmpty()
        }
    }

    fun setOnPrimaryButtonClickListener(listener: OnClickListener?) {
        binding.errorHintPrimaryButton.setOnClickListener(listener)
    }
}