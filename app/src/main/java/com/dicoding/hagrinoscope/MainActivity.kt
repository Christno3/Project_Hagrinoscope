package com.dicoding.hagrinoscope

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.hagrinoscope.artikel.Article_Fragment
import com.dicoding.hagrinoscope.databinding.ActivityMainBinding
import com.dicoding.hagrinoscope.predict.CameraUploadFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menampilkan ArticleFragment saat aplikasi pertama kali dibuka
        showFragment(Article_Fragment())

        binding.navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_camera -> {
                    showFragment(CameraUploadFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_artikel -> {
                    showFragment(Article_Fragment())
                    return@setOnNavigationItemSelectedListener true
                }

                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
