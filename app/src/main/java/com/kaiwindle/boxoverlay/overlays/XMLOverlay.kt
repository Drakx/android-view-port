/*
 * Kai Windle Copyright (c) 19/06/17 14:39
 * XMLOverlay.kt
 * Boxoverlay
 */

package com.kaiwindle.boxoverlay.overlays

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView

import com.kaiwindle.boxoverlay.R

/**
 * Created by Kai on 19/06/2017.
 * Boxoverlay
 * This isn't as good of an effect
 * as the code based version
 */
class XMLOverlay : ConstraintLayout, View.OnClickListener {

    constructor(context: android.content.Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        // Do any other setups needed here
        LayoutInflater.from(context).inflate(R.layout.overlay, this, true)
        val btnClose = findViewById(R.id.btn_close) as Button
        btnClose.setOnClickListener(this)

        val imageView = findViewById(R.id.img_view_port) as ImageView
        val drawable = imageView.drawable as VectorDrawable
//        drawable.alpha = 75
    }

    private var listener: ButtonListener? = null

    interface ButtonListener {
        fun closeView()
    }

    fun setButtonListener(listener: ButtonListener) {
        this.listener = listener
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onClick(v: View?) {
        if (listener != null) {
            listener?.closeView()
        }
    }
}



