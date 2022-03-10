package com.rafalropel.mvvmfavdishes.view.activities

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rafalropel.mvvmfavdishes.R
import com.rafalropel.mvvmfavdishes.databinding.ActivityAddUpdateDishBinding
import com.rafalropel.mvvmfavdishes.databinding.DialogChooseImageBinding

class AddUpdateDishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUpdateDishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        binding.ivAddDishImage.setOnClickListener {
            dialogImageChooser()

        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarAddDishActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back)

        }
        binding.toolbarAddDishActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun dialogImageChooser() {
        val dialog = Dialog(this)
        val binding: DialogChooseImageBinding = DialogChooseImageBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.tvCamera.setOnClickListener {
            Toast.makeText(this, "Aparat", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        binding.tvGallery.setOnClickListener {
            Toast.makeText(this, "Galeria", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
}