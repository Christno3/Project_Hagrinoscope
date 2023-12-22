package com.dicoding.hagrinoscope.predict

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.dicoding.hagrinoscope.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit

class CameraUploadFragment : Fragment() {

    private lateinit var btnCamera: Button
    private lateinit var btnGallery: Button
    private lateinit var btnUpload: Button
    private lateinit var imgResult1: ImageView
    private lateinit var imgResult2: ImageView
    private lateinit var tvApiResult: TextView

    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera_upload, container, false)

        btnCamera = view.findViewById(R.id.btnCamera)
        btnGallery = view.findViewById(R.id.btnGallery)
        btnUpload = view.findViewById(R.id.btnUpload)
        imgResult1 = view.findViewById(R.id.imgResult1)
        imgResult2 = view.findViewById(R.id.imgResult2)
        tvApiResult = view.findViewById(R.id.tvApiResult)

        btnCamera.setOnClickListener { openCamera() }
        btnGallery.setOnClickListener { openGallery() }
        btnUpload.setOnClickListener { uploadImages() }

        return view
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap ?: run {
                val selectedImage = data?.data
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
            }

            // Display the image in one of the ImageView (imgResult1 or imgResult2)
            if (imgResult1.drawable == null) {
                imgResult1.setImageBitmap(imageBitmap)
            } else {
                imgResult2.setImageBitmap(imageBitmap)
            }
        }
    }

    private fun uploadImages() {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("files", "image1.jpg", getRequestBodyFromImageView(imgResult1))
            .addFormDataPart("files", "image2.jpg", getRequestBodyFromImageView(imgResult2))
            .build()

        // OkHttpClient instance with timeout settings...
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("https://hagrinoscope-f3zafjyukq-uc.a.run.app/predict/")
                    .post(requestBody)
                    .build()

                client.newCall(request).execute().use { response ->
                    // Print response details for debugging
                    val responseBodyString = response.body?.string()
                    println("HTTP Code: ${response.code}")
                    println("Response Body: $responseBodyString")

                    // Check for errors in the response
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected HTTP code: ${response.code}")
                    }

                    // Handle the API response on the UI thread
                    withContext(Dispatchers.Main) {
                        handleApiResponse(responseBodyString)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()

                // Handle the error on the UI thread
                withContext(Dispatchers.Main) {
                    tvApiResult.text = "Error: ${e.message}"
                }
            }
        }
    }

    private fun getRequestBodyFromImageView(imageView: ImageView): RequestBody {
        val byteArray = getByteArrayFromImageView(imageView)
        return byteArray!!.toRequestBody("image/jpeg".toMediaTypeOrNull())
    }

    private fun getByteArrayFromImageView(imageView: ImageView): ByteArray? {
        val bitmap = (imageView.drawable).toBitmap()
        ByteArrayOutputStream().use { byteArrayOutputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        }
    }

    private fun handleApiResponse(responseBody: String?) {
        if (responseBody.isNullOrBlank()) {
            tvApiResult.text = "Error: Empty response body"
        } else {
            // Parse the JSON response and update the UI (tvApiResult)
            val resultJson = JSONObject(responseBody)
            val fruit = resultJson.optString("fruit", "")
            val soil = resultJson.optString("soil", "")
            val fruitRecommendation = resultJson.optString("fruit_recommendation", "")
            val soilRecommendation = resultJson.optString("soil_recommendation", "")

            val resultText =
                "Fruit: $fruit\nSoil: $soil\nFruit Recommendation: $fruitRecommendation\n \n \n Soil Recommendation: $soilRecommendation"
            tvApiResult.text = resultText

            // Log the visibility status of tvApiResult
            Log.d("CameraUploadFragment", "tvApiResult visibility: ${tvApiResult.visibility}")
        }
    }
}
