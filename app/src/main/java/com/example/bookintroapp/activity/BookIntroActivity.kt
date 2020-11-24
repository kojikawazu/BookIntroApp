package com.example.bookintroapp.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.bookintroapp.R

class BookIntroActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_main_layout)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        //val fab: FloatingActionButton = findViewById(R.id.fab)


       // val navController = findNavController(R.id.nav_host_fragment)

        // レイアウトのバインド
        Create_layout()
    }

    private fun Create_layout() {
        // TODO レイアウトの設定



    }




}