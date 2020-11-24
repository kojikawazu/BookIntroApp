package com.example.bookintroapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bookintroapp.R
import com.example.bookintroapp.model.SigninModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

// class サインイン
class MainActivity : AppCompatActivity(){

    // メニュバー
    private lateinit var appBarConfiguration: AppBarConfiguration

    init{
        // TODO 初期化
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO 起動
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ツールバー
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // 右下ボタン
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // レイアウト設定
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        //val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_signin), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            onBackPressedDispatcher.onBackPressed()
        }
        return true
    }


}