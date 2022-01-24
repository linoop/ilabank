package com.technowave.marksandspencer.adapters

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technowave.ilabanktest.R

class ItemListDecorator(private val context: Context, private val orientation: Int) :
    RecyclerView.ItemDecoration() {
    private var divider: Drawable? = getDrawable(context, R.drawable.recyclerview_divider)

    init {
        require(orientation == LinearLayoutManager.VERTICAL)
        { "This Item Decoration can be used only with a RecyclerView that uses a LinearLayoutManager with vertical orientation" }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDivider(c, parent, state)
        }
    }

    private fun drawHorizontalDivider(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left: Int
        var top: Int
        val right: Int
        var bottom: Int
        left = parent.paddingLeft
        right = parent.width - parent.paddingRight
        val count = parent.childCount
        for (i in 0 until count) {
                val current = parent.getChildAt(i)
                val params = current.layoutParams as RecyclerView.LayoutParams
                top = current.top - params.topMargin
                bottom = top + (divider?.getIntrinsicHeight() ?: 0)
                divider?.setBounds(left, top, right, bottom)
                divider?.draw(c)
        }
    }
}