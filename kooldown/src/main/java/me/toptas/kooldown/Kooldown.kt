package me.toptas.kooldown

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.RelativeLayout

class Kooldown @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    var strokeWidth = 5f
        set(value) {
            paint.strokeWidth = value
            field = value
        }
    var duration = 3000L
    var progress = 360
    var inactiveColor = Color.LTGRAY
    var activeColor = Color.RED
    var startAngle = 0f
    var listener: OnProgressListener? = null
    var autoStart = false
    val isAnimationRunning: Boolean
        get() = startAnimating

    private var current = 0.0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = activeColor
        style = Paint.Style.STROKE
    }
    private val oval = RectF()
    private var firstDraw = 0L
    private var pauseTime = 0L
    private val bitmapPaint = Paint()
    private lateinit var bitmap: Bitmap
    private lateinit var bitmapCanvas: Canvas
    private var startAnimating = false

    init {
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.Kooldown, 0, 0)
            try {
                inactiveColor = typedArray.getColor(
                    R.styleable.Kooldown_kd_inactiveColor,
                    Color.LTGRAY
                )
                activeColor = typedArray.getColor(
                    R.styleable.Kooldown_kd_activeColor,
                    Color.RED
                )
                strokeWidth = typedArray.getFloat(R.styleable.Kooldown_kd_strokeWidth, 5f)
                progress = typedArray.getInt(R.styleable.Kooldown_kd_progress, 360)
                duration = typedArray.getInt(R.styleable.Kooldown_kd_duration, 3000).toLong()
                startAngle = typedArray.getFloat(R.styleable.Kooldown_kd_startAngle, 0f)
                autoStart = typedArray.getBoolean(R.styleable.Kooldown_kd_autoStart, false)
            } finally {
                typedArray.recycle()
            }
        }
        paint.strokeWidth = strokeWidth
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun start() {
        startAnimating = true
        if (pauseTime > 0) {
            firstDraw = System.currentTimeMillis() - pauseTime
        } else {
            current = 0.0
            firstDraw = System.currentTimeMillis()
        }

        postInvalidate()
    }

    fun reset() {
        current = 0.0
        startAnimating = false
        pauseTime = 0
    }

    fun pause() {
        if (isAnimationRunning) {
            startAnimating = false
            pauseTime = System.currentTimeMillis() - firstDraw
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!::bitmap.isInitialized) {
            bitmap = Bitmap.createBitmap(
                width,
                height,
                Bitmap.Config.ARGB_8888
            )
            bitmapCanvas = Canvas(bitmap)
        }

        bitmapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        drawCircle(bitmapCanvas)

        canvas.drawBitmap(bitmap, 0f, 0f, bitmapPaint)
    }

    private fun drawCircle(canvas: Canvas) {
        if (width > 0 && height > 0) {
            if (firstDraw == 0L) {
                firstDraw = System.currentTimeMillis()
                if (autoStart) start()
            }
            val shortSide = if (width > height) height else width

            val centerX = width / 2
            val centerY = height / 2

            val radius = shortSide / 2f
            oval.set(
                centerX - radius + strokeWidth,
                centerY - radius + strokeWidth,
                centerX + radius - strokeWidth,
                centerY + radius - strokeWidth
            )

            if (startAnimating) {
                if (current < progress) {
                    val diff = System.currentTimeMillis() - firstDraw
                    current = ((progress * (diff / duration.toDouble())))
                    postInvalidate()
                } else {
                    current = progress.toDouble()
                    listener?.onComplete()
                    startAnimating = false
                    pauseTime = 0
                }
            }
            drawArc(canvas, current.toFloat())

        }
    }

    private fun drawArc(canvas: Canvas, angle: Float) {
        paint.color = activeColor
        canvas.drawArc(oval, startAngle, angle, false, paint)
        paint.color = inactiveColor
        canvas.drawArc(oval, angle + startAngle, 360f - angle, false, paint)

    }
}

interface OnProgressListener {
    fun onComplete()
}