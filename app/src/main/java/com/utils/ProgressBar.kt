package com.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.unacademyprogress.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class ProgressBar : View {
    private val TAG = "ProgressBar"
    private var layoutHeight = 0
    private var layoutWidth = 0

    private val MAX_ANGEL = 360.0
    private val DEFAULT_SWEEP_ANGEL = -90.0

    private var rimColor = Color.LTGRAY
    private var progressColor = Color.GREEN
    private var headColor = Color.RED

    private var rimWidth = 12.0f
    private var progressWidth = 12.0f
    private var headWidth = 12.0f

    private var showHead = false
    private var animationDuration = 1000 // in milliseconds
    private var percentage = 0.0f // 0-100

    private var paddingBottom = 10f
    private var paddingTop = 10f
    private var paddingRight = 10f
    private var paddingLeft = 10f

    private var rimBounds = RectF()
    private var progressBounds = RectF()

    private val rimPaint = Paint()
    private val progressPaint = Paint()
    private val headPaint = Paint()

    private var headX: Float = 0.0f
    private var headY: Float = 0.0f

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar, defStyle, 0)
        rimColor = a.getColor(R.styleable.ProgressBar_rimColor, rimColor)
        progressColor = a.getColor(R.styleable.ProgressBar_progressColor, progressColor)
        headColor = a.getColor(R.styleable.ProgressBar_headColor, headColor)
        rimWidth = a.getDimension(R.styleable.ProgressBar_rimWidth, rimWidth)
        progressWidth = a.getDimension(R.styleable.ProgressBar_progressWidth, progressWidth)
        headWidth = a.getDimension(R.styleable.ProgressBar_headWidth, headWidth)

        showHead = a.getBoolean(R.styleable.ProgressBar_showHead, showHead)
        animationDuration = a.getInt(
            R.styleable.ProgressBar_animationDuration,
            animationDuration
        )
        percentage = a.getFloat(R.styleable.ProgressBar_percentage, percentage)
        a.recycle()
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        layoutWidth = w
        layoutHeight = h
        setupBounds()
        setupPaints()
        invalidate()
    }


    private fun setupPaints() {
        rimPaint.color = rimColor
        rimPaint.isAntiAlias = true
        rimPaint.style = Paint.Style.STROKE
        rimPaint.strokeWidth = rimWidth

        progressPaint.color = progressColor
        progressPaint.isAntiAlias = true
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeWidth = progressWidth
        progressPaint.strokeCap = Paint.Cap.ROUND

        headPaint.color = headColor
        headPaint.isAntiAlias = true
        headPaint.style = Paint.Style.FILL
        headPaint.strokeWidth = headWidth
    }

    private fun setupBounds() {
        val minValue = min(layoutWidth, layoutHeight)
        val yOffset = layoutHeight - minValue
        val xOffset = layoutWidth - minValue

        paddingTop = this.getPaddingTop().toFloat() + yOffset / 2
        paddingBottom = this.getPaddingBottom().toFloat() + yOffset / 2
        paddingLeft = this.getPaddingLeft().toFloat() + xOffset / 2
        paddingRight = this.getPaddingRight().toFloat() + xOffset / 2

        rimBounds = RectF(
            paddingLeft + rimWidth,
            paddingTop + rimWidth,
            width.toFloat() - paddingRight - rimWidth,
            height.toFloat() - paddingBottom - rimWidth
        )

        progressBounds = RectF(
            paddingLeft + rimWidth,
            paddingTop + rimWidth,
            width.toFloat() - paddingRight - rimWidth,
            height.toFloat() - paddingBottom - rimWidth
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val endPointRadian = MAX_ANGEL * percentage / 100.0f
        headX = (cos(Math.toRadians(DEFAULT_SWEEP_ANGEL + endPointRadian)) * progressBounds.width() / 2 + (width / 2)).toFloat()
        headY = (sin(Math.toRadians(DEFAULT_SWEEP_ANGEL + endPointRadian)) * progressBounds.width() / 2 + (height / 2)).toFloat()
        canvas.drawArc(rimBounds, 0f, MAX_ANGEL.toFloat(), false, rimPaint)
        canvas.drawArc(progressBounds, DEFAULT_SWEEP_ANGEL.toFloat(), endPointRadian.toFloat(), false, progressPaint)
        canvas.drawCircle(headX, headY, headWidth, headPaint)
    }

    private fun startAnimation(percent: Float) {
        val diff = percent - percentage
        val valueAnimator = ValueAnimator.ofFloat(percentage, percentage + diff).setDuration(animationDuration.toLong())
        valueAnimator.addUpdateListener { animation ->
            percentage = animation.animatedValue as Float
            invalidate()
        }
        valueAnimator.start()
    }


    fun setProgressColor(color: Int) {
        progressColor = color
        invalidate()
    }

    fun setPercentage(percent: Float) {
        startAnimation(percent)
    }
}
