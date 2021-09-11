package com.eliasjuniornino.recyclermasterkotlin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.NORMAL
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.util.isNotEmpty
import androidx.recyclerview.widget.RecyclerView
import com.eliasjuniornino.recyclermasterkotlin.model.Email
import com.eliasjuniornino.recyclermasterkotlin.model.email

class EmailAdapter(val emails: MutableList<Email>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {
    val selectedItems = SparseBooleanArray()
    private var currentSelectedPos: Int = -1

    inner class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(email: Email) {
            with(email) {
                itemView.findViewById<TextView>(R.id.txt_icon).let {
                    it.text = user.first().toString()
                    val hash = user.hashCode()
                    it.background = itemView.oval(Color.rgb(hash, hash / 2, 0))
                }
                itemView.findViewById<TextView>(R.id.txt_user)?.let {
                    it.text = user
                    it.setTypeface(Typeface.DEFAULT, if (unread) BOLD else NORMAL)
                }
                itemView.findViewById<TextView>(R.id.txt_subject)?.let {
                    it.text = subject
                    it.setTypeface(Typeface.DEFAULT, if (unread) BOLD else NORMAL)
                }
                itemView.findViewById<TextView>(R.id.txt_preview).text = preview
                itemView.findViewById<TextView>(R.id.txt_date)?.let {
                    it.text = date
                    it.setTypeface(Typeface.DEFAULT, if (unread) BOLD else NORMAL)
                }
                itemView.findViewById<TextView>(R.id.txt_date).text = date
                itemView.findViewById<ImageView>(R.id.img_star)?.setImageResource(
                    if (stared) R.drawable.ic_baseline_star_24
                    else R.drawable.ic_baseline_star_border_24
                )
                itemView.findViewById<TextView>(R.id.txt_icon)?.let {
                    if (email.selected) {
                        it.background = it.oval(
                            Color.rgb(26, 115, 233)
                        )
                        itemView.background = GradientDrawable().apply {
                            shape = GradientDrawable.RECTANGLE
                            cornerRadius = 32f
                            setColor(Color.rgb(232, 240, 253))
                        }
                    } else {
                        itemView.background = GradientDrawable().apply {
                            shape = GradientDrawable.RECTANGLE
                            cornerRadius = 32f
                            setColor(Color.WHITE)
                        }
                    }
                    if (selectedItems.isNotEmpty())
                        animate(it, email)
                }
            }
        }
    }

    private fun animate(view: TextView, email: Email) {
        val oa1: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f)
        val oa2: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)

        oa1.interpolator = DecelerateInterpolator()
        oa2.interpolator = AccelerateDecelerateInterpolator()

        oa1.duration = 200
        oa2.duration = 200

        oa1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if (email.selected)
                    view.text = "\u2713"
                oa2.start()
            }
        })
        oa1.start()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.email_item, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position])
        holder.itemView.setOnClickListener {
            if (selectedItems.isNotEmpty())
                onItemClick?.invoke(position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(position)
            return@setOnLongClickListener true
        }
        if (currentSelectedPos == position) currentSelectedPos = -1
    }

    override fun getItemCount(): Int = emails.size

    fun toggleSelection(position: Int) {
        currentSelectedPos = position
        if (selectedItems[position, false]) {
            selectedItems.delete(position)
            emails[position].selected = false
        } else {
            selectedItems.put(position, true)
            emails[position].selected = true
        }
        notifyItemChanged(position)
    }

    fun deleteEmails() {
        emails.removeAll(
            emails.filter { it.selected }
        )
        notifyDataSetChanged()
        currentSelectedPos = -1
    }

    var onItemClick: ((Int) -> Unit)? = null
    var onItemLongClick: ((Int) -> Unit)? = null
}

fun View.oval(@ColorInt color: Int): ShapeDrawable {
    val oval = ShapeDrawable(OvalShape())
    with(oval) {
        intrinsicHeight = height
        intrinsicWidth = width
        paint.color = color
    }
    return oval
}