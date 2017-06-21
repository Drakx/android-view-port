/*
 * Kai Windle Copyright (c) 19/06/17 14:39
 * CodeOverlay.kt
 * Boxoverlay
 */

package com.kaiwindle.boxoverlay.overlays

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast

import com.kaiwindle.boxoverlay.R
import com.kaiwindle.boxoverlay.model.DPSize

/**
 * Created by Kai on 15/06/2017.
 * Boxoverlay
 */
class CodeOverlay : LinearLayout {

    private var bitmap: Bitmap? = null
    private var paint: Paint? = null

    private var centreX = 0F
    private var centreY = 0F

    private var boxWidth = 0F
    private var boxHeight = 0F

    private var point: DPSize? = null

    constructor(context: Context?, point: DPSize) : super(context) {
        this.point = point

        orientation = LinearLayout.VERTICAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        // Box surrounding the image view/image
        paint = Paint()
        paint?.strokeWidth = 10F
        paint?.color = Color.YELLOW
        paint?.alpha = 75
        paint?.style = Paint.Style.STROKE

        val p = point
        calculateCutout(p as DPSize)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        if (bitmap == null) {
            createWindowFrame()
        }
        canvas?.drawBitmap(bitmap, 0F, 0F, null)

        val drawable: Drawable?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.resources.getDrawable(R.drawable.vector_view_port_head, null)
        } else {
            drawable = context.resources.getDrawable(R.drawable.vector_view_port_head)
        }

        // Lets set the size of the image shall we so we can see it
        drawable.setBounds(centreX.toInt(), centreY.toInt(), (centreX + boxWidth).toInt(),
                (centreY + boxHeight).toInt())
        drawable.draw(canvas)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Draw the square
        canvas?.drawRect(centreX, centreY, centreX + boxWidth, centreY + boxHeight, paint)
    }

    /**
     * Try and calculate the center of the screen using DP
     * @param point Contains screen width, height and density
     */
    private fun calculateCutout(point: DPSize) {
        boxWidth = ((point.width / 3) * 2) * point.density
        boxHeight = boxWidth

        centreX = (((point.width * point.density) - boxWidth) / 2)
        centreY = ((point.height - boxHeight) / 2)
    }

    /**
     * Creates a window the size of the screen then sets the colour
     * and alpha which fill's until it's final size which
     * is done within {@link #calculateCutout}
     */
    private fun createWindowFrame() {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val osCanvas = Canvas(bitmap)

        val outerRectangle = RectF(0F, 0F, width.toFloat(), height.toFloat())

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK

        paint.alpha = 150
        osCanvas.drawRect(outerRectangle, paint)

        paint.color = Color.TRANSPARENT
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)

        val finalRect = RectF(centreX, centreY, centreX + boxWidth, centreY + boxHeight)

        osCanvas.drawRect(finalRect, paint)
        // One thing to note is that this works only in portrait mode
        // as far as I know since I haven't tested it with landscape

    }
}



