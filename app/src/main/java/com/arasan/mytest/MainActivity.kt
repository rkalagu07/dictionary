package com.arasan.mytest

import android.app.ProgressDialog
import android.graphics.Typeface
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.arasan.mytest.adapters.MeaningAdapter
import com.arasan.mytest.adapters.PhoneticsAdapter
import com.arasan.mytest.databinding.ActivityMainBinding
import com.arasan.mytest.listener.ItemClickListener
import com.arasan.mytest.models.Phonetic
import com.arasan.mytest.models.ResDictionary
import com.arasan.mytest.viewmodel.DictionaryViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClickListener {
    val myList: ArrayList<Phonetic> = ArrayList()
    lateinit var mediaPlayer: MediaPlayer
    private lateinit var binding: ActivityMainBinding
    private val viewModel: DictionaryViewModel by viewModels()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        mediaPlayer = MediaPlayer()
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle the search query submission
                if (!query.isNullOrBlank()) {
                    myList.clear()
                    performSearch(query)
                    progressDialog.show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes in the search query
                return true
            }
        })
        LoadApi()
    }

    private fun performSearch(query: String) {
        if (query.length > 3) {
            viewModel.getAllTVShows(query.trim())
        } else {
            Toast.makeText(this, "Enter At least 4 Characters", Toast.LENGTH_LONG).show()
        }
    }

    private fun LoadApi() {
        viewModel.responseTvShow.observe(this) { getData ->
            Log.e("LoadApi: ", getData)
            val str = getData.startsWith("[")
            progressDialog.dismiss()
            if (str) {
                val mResData = Gson().fromJson(getData, ResDictionary::class.java)
                binding.content.visibility = View.VISIBLE
                val text = "Searched Word :  " + mResData[0].word
                val spannableString = SpannableString(text)
                spannableString.setSpan(
                    StyleSpan(Typeface.ITALIC),
                    0,
                    text.indexOf(":"),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableString.setSpan(
                    StyleSpan(Typeface.NORMAL),
                    text.indexOf(":") + 1,
                    text.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.Word.text = spannableString
                if (!mResData[0].phonetics.isNullOrEmpty()) {
                    for (item in mResData[0].phonetics) {
                        if (!item.audio.isNullOrEmpty() && !item.text.isNullOrEmpty()) {
                            myList.add(Phonetic(item.audio, item.text))
                        }
                    }
                    binding.phonicRV.adapter = PhoneticsAdapter(myList, this)
                }
                if (!mResData[0].meanings.isNullOrEmpty()) {
                    binding.meaningRV.adapter = MeaningAdapter(mResData[0].meanings)
                }
            } else {
                Toast.makeText(this, "Enter Correct Word", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onItemClick(position: Int) {
        Log.e("onItemClick: ", myList[position].audio)
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )

            try {
                setDataSource(myList[position].audio)
                prepareAsync()

                setOnPreparedListener {
                    // Start playback when prepared
                    start()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            mediaPlayer.release()
        }


    }
}
