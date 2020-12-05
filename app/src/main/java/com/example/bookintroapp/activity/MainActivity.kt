package com.example.bookintroapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import android.view.MenuItem
import android.view.View
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
import com.example.bookintroapp.model.SigninModel
import com.example.bookintroapp.view.dialog.SimpleAlertDiralog
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

        val fragManager: FragmentManager = this.supportFragmentManager
        val frag : Fragment? = fragManager.findFragmentById(R.id.nav_signin)
        if(frag != null){

        }

        // レイアウト設定
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_main_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_main_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_signin, R.id.nav_bookmain,
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.visibility = View.GONE
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 画面遷移時の各部品の表示調整
            when(destination.id){
                R.id.nav_signin ->{
                    navView.visibility = View.GONE
                }
                R.id.nav_bookmain -> {
                    fab.show()
                    navView.visibility = View.VISIBLE
                }
                else -> {
                    fab.hide()
                    navView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        /*if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            // 戻るボタン押された場合の処理
            onBackPressedDispatcher.onBackPressed()
            return true
        }*/
        val navController = findNavController(R.id.nav_host_main_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        /*
        var fragment: Fragment? = null
        when (item.itemId) {
            nav_item_category_1 ->
                fragment = CategoryOne()
            nav_item_category_2 ->
                fragment = CategoryTwo()
            nav_sub_item_1 ->
                fragment = SubCategoryOne()
        }
        // Replace the fragment.
        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.frame_contents, fragment)
            ft.commit()
        }
        // Close the Navigation Drawer.
        drawer_layout.closeDrawer(GravityCompat.START)
        */
        return true
    }

    fun setBookListener(frag: Fragment){
        // 右下ボタン
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            ActivityHelper.nextFragment(frag, R.id.action_bookmain_to_bookadd)
        }
    }



}