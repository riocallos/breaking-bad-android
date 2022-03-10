package com.riocallos.breakingbad.binding

import android.widget.*
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.riocallos.breakingbad.R

/**
 * Data Binding adapters specific to the app.
 */

@BindingAdapter("img")
fun bindImg(imageView: ImageView, img: String?) {

    if(img != null && img.isNotEmpty()) {

        Glide.with(imageView.context)
            .load(img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .load(img).into(imageView)

    }

}

@BindingAdapter("occupation")
fun bindOccupation(textView: TextView, occupations: Array<String>) {

    var first = true
    var text = ""
    for(occupation in occupations) {
        if(!first) {
            text += "\n"
        }
        text += occupation
        first = false
    }
    textView.text = text

}

@BindingAdapter("seasons")
fun bindSeasons(textView: TextView, seasons: Array<Int>) {

    var first = true
    var text = ""
    for(season in seasons) {
        if(!first) {
            text += ", "
        }
        text += "$season"
        first = false
    }
    textView.text = text

}
