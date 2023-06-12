package com.example.shopgistic

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class GoodsInput : AppCompatActivity() {

    private lateinit var editTextProduct: EditText
    private lateinit var editTextWeight: EditText
    private lateinit var editTextPrice: EditText
    private lateinit var buttonTambahProduk: Button
    private lateinit var imageView: ImageView

    private var selectedImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted)
                openImagePicker()
        }

    private val getContentLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                imageView.setImageURI(it)
                Log.d("GoodsInput", "Selected Image URI: $selectedImageUri")
            }
        }

    //untuk input goods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inputtiap2produk)

        //init view2 yang diperlukan
        editTextProduct = findViewById(R.id.editTextName)
        editTextWeight = findViewById(R.id.editTextWeight)
        editTextPrice = findViewById(R.id.editTextPrice)
        buttonTambahProduk = findViewById(R.id.button3)
        imageView = findViewById(R.id.editImageView)

        //click listener untuk button doang
        buttonTambahProduk.setOnClickListener {
            val title = editTextProduct.text.toString()
            val weight = editTextWeight.text.toString().toFloatOrNull() ?: 0f
            val price = editTextPrice.text.toString().toFloatOrNull() ?: 0f
            val categoryId = 1
            performActionWithUserInput(title, weight, price, categoryId)
        }

        //click listener untuk input gambar
        imageView.setOnClickListener {
            requestImagePickerPermission()
        }
    }

    private fun performActionWithUserInput(title: String, weight: Float, price: Float, categoryId: Int) {
        val pictureUriString = selectedImageUri?.toString()

        // Insert the product into the database
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.ProductTable.COLUMN_PRODUCT_NAME, title)
            put(DatabaseContract.ProductTable.COLUMN_PRODUCT_WEIGHT, weight)
            put(DatabaseContract.ProductTable.COLUMN_PRODUCT_PRICE, price)
            put(DatabaseContract.ProductTable.COLUMN_PRODUCT_LOGO, pictureUriString)
            put(DatabaseContract.ProductTable.COLUMN_CATEGORY_ID, categoryId)
        }

        val newRowId = db.insert(DatabaseContract.ProductTable.TABLE_NAME, null, values)

        db.close()

        val resultIntent = Intent()
        resultIntent.putExtra("userInput", title)
        resultIntent.putExtra("pictureUri", pictureUriString)
        resultIntent.putExtra("weight", weight)
        resultIntent.putExtra("price", price)

        setResult(RESULT_OK, resultIntent)
        finish()
    }


    private fun requestImagePickerPermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            openImagePicker()
        } else {
            requestPermissionLauncher.launch(permission)
        }
    }

    private fun openImagePicker() {
        getContentLauncher.launch("image/*")
    }

    private fun getImageResourceFromUri(uri: Uri): Uri {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontTransform()

        Glide.with(this)
            .load(uri)
            .apply(requestOptions)
            .into(imageView)

        return uri
    }

}
