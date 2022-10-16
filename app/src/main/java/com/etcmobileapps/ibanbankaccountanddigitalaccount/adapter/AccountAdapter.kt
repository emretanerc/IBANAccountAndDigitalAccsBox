package com.etcmobileapps.ibanbankaccountanddigitalaccount.adapter

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.provider.Settings.Global.getString
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import com.etcmobileapps.ibanbankaccountanddigitalaccount.R
import com.etcmobileapps.ibanbankaccountanddigitalaccount.model.Account
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates


class AccountAdapter(private val data: List<Account>, val context: Context) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.digital_account_item, p0, false))
    }
    override fun getItemCount(): Int=data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(data[position].application)
        {
            "Netflix" -> {
                Picasso.get().load(R.drawable.netlixlogo).into(holder.tvWebsiteLogo)
            }
            "Instagram" -> {
                Picasso.get().load(R.drawable.instagramlogo).into(holder.tvWebsiteLogo)
            }
            "Facebook" -> {
                Picasso.get().load(R.drawable.facebooklogo).into(holder.tvWebsiteLogo)
            }
            "Spotify" -> {
                Picasso.get().load(R.drawable.spotifylogo).into(holder.tvWebsiteLogo)
            }
            else ->{
                val drawable = TextIconDrawable().apply {
                    text = data[position].application.toString()
                    textColor = Color.BLACK
                }
                holder.tvWebsiteLogo.setImageDrawable(drawable);
            }
        }

        holder.tvWebsiteName?.text = data[position].application
        holder.tvUsername?.text = data[position].username
        holder.tvPassword?.text = data[position].password

        val popupMenu = PopupMenu(context, holder.popupBt)
        popupMenu.menu.add(Menu.NONE, 0, 0, R.string.item_copy);
        popupMenu.menu.add(Menu.NONE, 1, 1, R.string.item_delete);

        popupMenu.setOnMenuItemClickListener { menuItem -> //get id of the clicked item
            val id = menuItem.itemId

            //Get Values from R.string for Localization
            val websiteLocalValue: String =  context.getString(R.string.website_name)
            val usernameLocalValue =          context.getString(R.string.website_username)
            val passwordLocalValue =          context.getString(R.string.website_password)
            var accountBoxAdValue =          context.getString(R.string.copy_ad)

            //Get Values from TextView
            var websiteName = holder.tvWebsiteName.text
            var username = holder.tvUsername.text
            var password = holder.tvPassword.text


            //handle clicks
            if (id == 0) {
                val textToCopy =  "$websiteLocalValue: $websiteName \n$usernameLocalValue: $username \n$passwordLocalValue: $password \n \n $accountBoxAdValue "
                val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", textToCopy)
                clipboardManager.setPrimaryClip(clipData)    }
            else if (id == 1) {

            }
            false
        }


        holder.popupBt.setOnClickListener(View.OnClickListener { popupMenu.show() })
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvWebsiteLogo = itemView.findViewById<ImageView>(R.id.websiteLogo)
        val tvWebsiteName = itemView.findViewById<TextView>(R.id.websiteName)
        val tvUsername = itemView.findViewById<TextView>(R.id.username)
        val tvPassword= itemView.findViewById<TextView>(R.id.password)
        val popupBt= itemView.findViewById<ImageButton>(R.id.popupBt)

    }


    class TextIconDrawable: Drawable() {
        private var textPaint = TextPaint().apply {
            textAlign = Paint.Align.CENTER
            isFakeBoldText = true
        }
        var text by Delegates.observable("") { _, _, _ -> invalidateSelf() }
        var textColor by Delegates.observable(Color.RED) { _, _, _ -> invalidateSelf() }

        private fun fitText(width: Int) {
            textPaint.textSize = 48f
            val widthAt48 = textPaint.measureText(text)
            textPaint.textSize = 30f / widthAt48 * width.toFloat()
        }

        override fun draw(canvas: Canvas) {
            val width = bounds.width()
            val height = bounds.height()

            fitText(width)
            textPaint.color = ColorUtils.setAlphaComponent(textColor, alpha)
            canvas.drawText(text, width / 2f, height / 2f, textPaint)
        }



        override fun setAlpha(alpha: Int) {
            this.alpha = 255
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            textPaint.colorFilter = colorFilter
        }

        override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    }


}