package com.technowave.ilabanktest.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.technowave.ilabanktest.R
import com.technowave.ilabanktest.adapters.ViewPagerAdapter
import com.technowave.ilabanktest.databinding.ActivityMainBinding
import com.technowave.ilabanktest.models.GetDataResponse
import com.technowave.ilabanktest.viewModels.MainViewModel
import com.technowave.marksandspencer.adapters.ItemListAdapter
import com.technowave.marksandspencer.utils.DataResource
import com.technowave.marksandspencer.utils.WaitDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TextWatcher {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var waitDialog: WaitDialog
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var itemListAdapter: ItemListAdapter
    private var tempList = ArrayList<GetDataResponse.Product>()
    private var itemList = ArrayList<GetDataResponse.Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActivity()
    }

    private fun setupActivity() {
        waitDialog = WaitDialog(this)
        itemListAdapter = ItemListAdapter(this, itemList)
        binding.itemListView.adapter = itemListAdapter
        binding.itemListView.layoutManager = LinearLayoutManager(this)
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)
        binding.searchQuery.addTextChangedListener(this)
        getData()
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            addDots(position)
        }
    }

    private fun setupCarousal() {
        pagerAdapter = ViewPagerAdapter(this, tempList)
        binding.viewPager.adapter = pagerAdapter
        repeat(tempList.size) { dots.add(RadioButton(this)) }
        Handler(Looper.getMainLooper()).postDelayed({
            addDots(0)
        }, 500)
    }

    private var dots = ArrayList<TextView>()
    private fun addDots(position: Int) {
        binding.layoutDot.removeAllViews()
        repeat(tempList.size) {
            dots[it] = TextView(this)
            dots[it].text = getString(R.string.bulletin)
            dots[it].textSize = 30f
            dots[it].setTextColor(Color.GRAY)
            binding.layoutDot.addView(dots[it])
        }
        if (tempList.isNotEmpty()) {
            dots[position].text = getString(R.string.bulletin)
            dots[position].setTextColor(Color.GREEN)
        }
    }

    private fun getData() {
        viewModel.getHomePage()
            .observe(this, { response ->
                when (response) {
                    is DataResource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            if (it.status) {
                                itemList.addAll(it.products)
                                tempList.addAll(it.products)
                                setupCarousal()
                                itemListAdapter.notifyDataSetChanged()
                            } else showMessage(it.msg)

                        }
                    }
                    is DataResource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            showMessage(message)
                        }
                    }
                    is DataResource.Loading -> {
                        showProgressBar()
                    }
                }
            })
    }


    private fun showProgressBar() {
        waitDialog.showDialog()
    }

    private fun hideProgressBar() {
        waitDialog.dismiss()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(query: Editable?) {
        val result = ArrayList<GetDataResponse.Product>()
        tempList.forEach {
            if (it.title.lowercase(Locale.getDefault()).contains(
                    query.toString().lowercase(Locale.getDefault())
                )
            ) result.add(it)
        }
        itemList.clear()
        itemList.addAll(result)
        itemListAdapter.notifyDataSetChanged()
    }
}