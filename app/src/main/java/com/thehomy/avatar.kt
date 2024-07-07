package com.thehomy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.GridLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thehomy.databinding.ActivityAvatarBinding

class avatar : AppCompatActivity() {
    private lateinit var binding: ActivityAvatarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val avatarList = listOf(
            R.drawable.avatar1,
            R.drawable.avatar2,
            R.drawable.avatar3,
            R.drawable.avatar4,
            R.drawable.avatar5,
            R.drawable.avatar6
        )

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        val avatarSizeDp = resources.getDimensionPixelSize(R.dimen.avatar_size)
        val avatarMarginDp = resources.getDimensionPixelSize(R.dimen.avatar_margin)

        val numColumns = (screenWidth - avatarMarginDp) / (avatarSizeDp + avatarMarginDp)

        binding.avatarContainer.columnCount = numColumns

        var rowIndex = 0
        var columnIndex = 0

        for (avatarResId in avatarList) {
            if (columnIndex >= numColumns) {
                rowIndex++
                columnIndex = 0
            }

            val imageView = ImageView(this)
            val params = GridLayout.LayoutParams().apply {
                width = avatarSizeDp
                height = avatarSizeDp
                setMargins(avatarMarginDp, avatarMarginDp, avatarMarginDp, avatarMarginDp)
            }
            imageView.layoutParams = params
            imageView.setImageResource(avatarResId)
            imageView.setOnClickListener {
                returnSelectedAvatar(avatarResId)
            }
            binding.avatarContainer.addView(imageView)

            val gridLayoutParams = imageView.layoutParams as GridLayout.LayoutParams
            gridLayoutParams.rowSpec = GridLayout.spec(rowIndex)
            gridLayoutParams.columnSpec = GridLayout.spec(columnIndex)

            columnIndex++
        }
    }

    private fun returnSelectedAvatar(avatarResId: Int) {
        val resultIntent = Intent()
        resultIntent.putExtra("selectedAvatar", avatarResId)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()

    }
}