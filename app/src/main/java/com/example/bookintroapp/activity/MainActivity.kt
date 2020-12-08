package com.example.bookintroapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.model.SigninModel
import com.example.bookintroapp.view.dialog.SimpleAlertDiralog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

// class サインイン
class MainActivity : AppCompatActivity(),
                        NavigationView.OnNavigationItemSelectedListener{

    // メニュバー
    private lateinit var appBarConfiguration: AppBarConfiguration

    // ナビゲーションコントローラー
    private lateinit var navController: NavController

    init{
        // TODO 初期化
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO 起動
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ツールバー
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        // 右下ボタン
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            /*
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
             */
        }

        // レイアウト設定
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_main_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_main_fragment)

        // ナビゲーション設定
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_bookmain, R.id.nav_bookmy, R.id.nav_bookmark), drawerLayout)
        // アクションバーにナビゲーションバインド
        setupActionBarWithNavController(navController, appBarConfiguration)
        // サイドバーリスナー設定
        navView.setNavigationItemSelectedListener(this)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 画面遷移時の各部品の表示調整
            when(destination.id){
                R.id.nav_bookadd ->{
                    // 書籍追加画面の時は非表示
                    fab.hide()
                }
                else -> {
                    // それ以外は表示
                    fab.show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // TODO 右上メニューの生成
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.book_main_h_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        // TODO バックボタンが押下された時の対応
        /*if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            // 戻るボタン押された場合の処理
            onBackPressedDispatcher.onBackPressed()
            return true
        }*/
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // TODO サイドバーメニュー選択
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // TODO メニューのイベントリスナー
        super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.header_signout -> {
                // サインアウト
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun setBookListener(frag: Fragment){
        // TODO 右下ボタン押下
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            ActivityHelper.nextFragment(frag, R.id.action_bookmain_to_bookadd)
        }
    }



}