package com.example.bookintroapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.ActivityHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

// ユーザアクティビティ
class UserActivity : AppCompatActivity() {

    // メニュバー
    private lateinit var appBarConfiguration: AppBarConfiguration

    init{
        // TODO 初期化
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO 起動
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        // ツールバー
        val toolbar: Toolbar = findViewById(R.id.toolbar_user)
        setSupportActionBar(toolbar)

        // レイアウト設定
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_user_layout)
        val navController = findNavController(R.id.nav_host_user_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_signin), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 画面遷移時の各部品の表示調整

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // TODO ナビゲーションバーのイベント
        if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            // 戻るボタン押された場合の処理
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return true
    }

    fun changeMainActivity(){
        // TODO メイン画面へ遷移
        val intent = Intent(application, MainActivity::class.java)
        startActivity(intent)
    }

}