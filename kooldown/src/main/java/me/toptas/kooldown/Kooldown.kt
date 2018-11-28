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
    var duration = 3000L
    var maxAngle = 360
    var inactiveColor = Color.LTGRAY
    var activeColor = Color.RED
    var startAngle = 0f
    var listener: OnProgressListener? = null

    //private var started = false
    private var current = 0.0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = activeColor
        strokeWidth = this@Kooldown.strokeWidth
        style = Paint.Style.STROKE
    }
    private val oval = RectF()
    private var firstDraw = 0L
    private val bitmapPaint = Paint()
    private lateinit var bitmap: Bitmap
    private lateinit var bitmapCanvas: Canvas

    init {
        setBackgroundColor(Color.WHITE)
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
                maxAngle = typedArray.getInt(R.styleable.Kooldown_kd_max_angle, 360)
                duration = typedArray.getInt(R.styleable.Kooldown_kd_duration, 3000).toLong()
                startAngle = typedArray.getFloat(R.styleable.Kooldown_kd_startAngle, 0f)
            } finally {
                typedArray.recycle()
            }
        }
        paint.strokeWidth = strokeWidth
    }

    fun start() {
        firstDraw = System.currentTimeMillis()
        postInvalidate()
    }

    fun stop() {
        current = 0.0
        firstDraw = 0L
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
            if (firstDraw == 0L) firstDraw = System.currentTimeMillis()
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

            if (current < maxAngle /*&& started*/) {
                val diff = System.currentTimeMillis() - firstDraw
                current = ((maxAngle * (diff / duration.toDouble())))
                postInvalidate()
            } else /*if (started)*/ {
                current = maxAngle.toDouble()
                //started = false
                listener?.onComplete()
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