package com.rafalropel.mvvmfavdishes.view.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.rafalropel.mvvmfavdishes.R
import com.rafalropel.mvvmfavdishes.databinding.ActivityAddUpdateDishBinding
import com.rafalropel.mvvmfavdishes.databinding.DialogChooseImageBinding
import com.rafalropel.mvvmfavdishes.databinding.DialogSelectListBinding
import com.rafalropel.mvvmfavdishes.util.Constants
import com.rafalropel.mvvmfavdishes.view.adapter.SelectListAdapter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AddUpdateDishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUpdateDishBinding
    private var mImagePath: String = ""
    private lateinit var mSelectDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        binding.ivAddDishImage.setOnClickListener {
            dialogImageChooser()

        }

        binding.etType.setOnClickListener {
            selectListDialog(
                getString(R.string.lbl_type),
                Constants.dishTypes(),
                Constants.DISH_TYPE
            )

        }

        binding.etCategory.setOnClickListener {
            selectListDialog(
                getString(R.string.lbl_category),
                Constants.dishCategory(),
                Constants.DISH_CATEGORY
            )
        }

        binding.etCookingTime.setOnClickListener {
            selectListDialog(
                getString(R.string.lbl_cooking_time_in_minutes),
                Constants.dishCookTime(),
                Constants.DISH_COOKING_TIME
            )
        }

        binding.btnAddDish.setOnClickListener {
            val title = binding.etTitle.text.toString().trim { it <= ' ' }
            val type = binding.etType.text.toString().trim { it <= ' ' }
            val category = binding.etCategory.text.toString().trim { it <= ' ' }
            val ingredients = binding.etIngredients.text.toString().trim { it <= ' ' }
            val cookingTime = binding.etCookingTime.text.toString().trim { it <= ' ' }
            val cookingDirection = binding.etDirectionToCook.text.toString().trim { it <= ' ' }

            when {
                TextUtils.isEmpty(mImagePath) -> {
                    Toast.makeText(
                        this,
                        getString(R.string.please_select_image),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(title) -> {
                    Toast.makeText(
                        this,
                        getString(R.string.please_select_title),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(type) -> {
                    Toast.makeText(this, getString(R.string.please_select_type), Toast.LENGTH_SHORT)
                        .show()
                }

                TextUtils.isEmpty(category) -> {
                    Toast.makeText(
                        this,
                        getString(R.string.please_select_category),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(ingredients) -> {
                    Toast.makeText(
                        this,
                        getString(R.string.please_select_ingredients),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(cookingTime) -> {
                    Toast.makeText(
                        this,
                        getString(R.string.please_select_cooking_time),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(cookingDirection) -> {
                    Toast.makeText(
                        this,
                        getString(R.string.please_select_cooking_recipe),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(this, "Wszystkie dane prawidłowe", Toast.LENGTH_SHORT).show()
                }
            }
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
            Dexter.withContext(this).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()) {
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                startActivityForResult(intent, CAMERA)
                            }
                        }

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        showRationalePermissionsDialog()

                    }
                }
            ).onSameThread().check()
            dialog.dismiss()
        }

        binding.tvGallery.setOnClickListener {
            Dexter.withContext(this).withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE

            ).withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, GALLERY)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(
                        this@AddUpdateDishActivity,
                        getString(R.string.permissions_denied),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    showRationalePermissionsDialog()
                }

            }).onSameThread().check()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showRationalePermissionsDialog() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.permissions_denied))
            .setPositiveButton(getString(R.string.go_to_settings)) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }.setNegativeButton(getString(R.string.Cancel)) { dialogInterface, _ ->
                dialogInterface.dismiss()

            }.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {
                data?.extras?.let {
                    val thumbnail: Bitmap = data.extras!!.get("data") as Bitmap

                    Glide.with(this)
                        .load(thumbnail)
                        .centerCrop()
                        .into(binding.ivDishImage)

                    mImagePath = saveImageToStorage(thumbnail)

                    binding.ivAddDishImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_edit
                        )
                    )
                }

            } else if (requestCode == GALLERY) {
                data?.let {
                    val selectedPhotoURI = data.data

                    Glide.with(this)
                        .load(selectedPhotoURI)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                resource?.let {
                                    val bitmap: Bitmap = resource.toBitmap()
                                    mImagePath = saveImageToStorage(bitmap)

                                }
                                return false
                            }

                        })
                        .into(binding.ivDishImage)



                    binding.ivAddDishImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_edit
                        )
                    )

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("Anulowano", "Użytkownik anulował wybór zdjęcia")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun saveImageToStorage(bitmap: Bitmap): String {
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file.absolutePath
    }

    private fun selectListDialog(title: String, itemsList: List<String>, selection: String) {
        mSelectDialog = Dialog(this)
        val binding: DialogSelectListBinding = DialogSelectListBinding.inflate(layoutInflater)
        mSelectDialog.setContentView(binding.root)
        binding.tvSelectTitle.text = title
        binding.rvSelectList.layoutManager = LinearLayoutManager(this)

        val adapter = SelectListAdapter(this, itemsList, selection)
        binding.rvSelectList.adapter = adapter
        mSelectDialog.show()


    }

    fun selectedListItem(item: String, selection: String) {
        when (selection) {
            Constants.DISH_TYPE -> {
                mSelectDialog.dismiss()
                binding.etType.setText(item)
            }

            Constants.DISH_CATEGORY -> {
                mSelectDialog.dismiss()
                binding.etCategory.setText(item)
            }
            else -> {
                mSelectDialog.dismiss()
                binding.etCookingTime.setText(item)
            }
        }
    }

    companion object {
        private const val CAMERA = 1
        private const val GALLERY = 2
        private const val IMAGE_DIRECTORY = "DishesImages"
    }
}