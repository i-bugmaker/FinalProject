package com.example.finalproject.ui.info

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.databinding.FragmentInfoBinding
import com.example.finalproject.db.PetDatabaseHelper
import com.example.finalproject.db.PublicDatabaseHelper
import com.example.finalproject.ui.home.HomeViewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val takePhoto = 1
lateinit var imageUri: Uri
lateinit var outputImage: File

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val infoViewModel =
            ViewModelProvider(this).get(InfoViewModel::class.java)

        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textInfo
//        infoViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val capture = binding.capture
        capture.setOnClickListener {
            Toast.makeText(requireContext(), "开始拍照", Toast.LENGTH_SHORT).show()
            outputImage = File(requireContext().externalCacheDir, "output_image.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.finalproject.fileprovider",
                    outputImage
                )
            } else {
                Uri.fromFile(outputImage)
            }
// 启动相机程序
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            );

            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, takePhoto)
        }
        //发布按钮事件
        binding.publish.setOnClickListener {
            val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
            val nickname = binding.nickname.text.toString()
            val breed = binding.breed.text.toString()
            val age = binding.age.text.toString()
            val sex =
                if (binding.rbFemale.isChecked) binding.rbFemale.text.toString() else binding.rbMale.text.toString()
            val description = binding.description.text.toString()
//            val capture = binding.capture
            val image = binding.image

            val contact = binding.contact.text.toString()
            val phone = binding.phone.text.toString()


            val petDbHelper = PetDatabaseHelper(requireContext(), "pet.db", 2)
            val petDb = petDbHelper.writableDatabase
            val os = ByteArrayOutputStream()
            val bitmap: Bitmap =
                BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(imageUri))
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
            petDb.execSQL(
                "insert into Pet (nickname, breed, age, sex, image) values(?, ?, ?, ?,?)",
                arrayOf(nickname, breed, age, sex, os.toByteArray())
            )
            val cursor = petDb.rawQuery("select * from Pet", null)
            cursor.moveToLast()
            val pet_id = cursor.getInt(cursor.getColumnIndex("id"))
            val publicDbHelper = PublicDatabaseHelper(requireContext(), "public.db", 2)
            val publicDb = publicDbHelper.writableDatabase
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formatted = current.format(formatter)


            publicDb.execSQL(
                "insert into Public (date, description, pet_id, contact,phone) values(?, ?, ?, ?,?)",
                arrayOf(formatted, description, pet_id, contact, phone)
            )
//            val cursor = petDb.rawQuery("select * from Pet", null)
//            cursor.moveToLast()
//            val imageHex = cursor.getBlob(cursor.getColumnIndex("image"))
//            val bit = BitmapFactory.decodeByteArray(imageHex, 0, imageHex.size)
//            CardList.cardList.add(0, Card(nickname, breed, age, sex, bit))
////            val image = binding.image.toString().toInt()
//            val adapter = CardAdapter(CardList.cardList)
//            adapter.notifyItemInserted(0)

            Toast.makeText(requireContext(), "发布成功", Toast.LENGTH_SHORT).show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK) {
// 将拍摄的照片显示出来
                    val bitmap = BitmapFactory.decodeStream(
                        requireContext().contentResolver.openInputStream(imageUri)
                    )
                    val rotatedBitmap = rotateIfRequired(bitmap)
                    binding.image.setImageBitmap(rotatedBitmap)
                }
            }
        }
    }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height,
            matrix, true
        )
        bitmap.recycle() // 将不再需要的Bitmap对象回收
        return rotatedBitmap
    }

}