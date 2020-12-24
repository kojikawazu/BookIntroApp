package com.example.bookintroapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.FirebaseHelpler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

// class サインイン
class MainActivity : AppCompatActivity(),
                        NavigationView.OnNavigationItemSelectedListener {

    // メニュバー
    private lateinit var appBarConfiguration: AppBarConfiguration

    // ナビゲーションコントローラー
    private lateinit var navController: NavController

    // サインイン中のメールアドレス
    private var signinEmail: String

    // 選択中書籍
    private var targetBookId: String

    // 選択中フォロワーユーザID
    private var targetFollowerId: String

    init{
        // TODO 初期化
        signinEmail = ""
        targetBookId = ""
        targetFollowerId = ""
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
        }

        // レイアウト設定
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_main_layout)
        navController = findNavController(R.id.nav_host_main_fragment)

        // ナビゲーション設定
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.bookMainFragment, R.id.bookMypageFragment, R.id.bookMarkFragment), drawerLayout)
        // アクションバーにナビゲーションバインド
        setupActionBarWithNavController(navController, appBarConfiguration)

        // サイドバーリスナー設定
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 画面遷移時の各部品の表示調整
            when(destination.id){
                R.id.bookAddFragment ->{
                    // 書籍追加画面の時は非表示
                    fab.hide()
                }
                else -> {
                    // それ以外は表示
                    fab.show()
                }
            }
        }

        // サインイン中のメールアドレス保存
        saveSigninMail()
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
                signout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun setBookListener(frag: Fragment){
        // TODO 右下ボタン押下
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            // 書籍追加へ移動
            ActivityHelper.nextFragment(frag, R.id.action_bookmain_to_bookadd)
        }
    }

    fun getSigninMail(): String{
        // TODO サインイン中のメールアドレス取得
        return signinEmail
    }

    fun getTargetBookId(): String{
        // TODO 選択中の書籍IDの取得
        return targetBookId
    }

    fun getFollowerId(): String{
        // TODO 選択中のフォロワーIDの取得
        return targetFollowerId
    }

    fun saveSigninMail(){
        // TODO サインイン中のメールアドレス保存
        val auth: FirebaseAuth =  FirebaseHelpler.getAuth()
        val user = auth.currentUser
        user?.let {
            signinEmail = user?.email.toString()
        }
    }

    fun saveTargetBookId(id: String){
        // TODO 対象の書籍IDの保存
        targetBookId = id
    }

    fun saveTargetFollowerId(id: String){
        // TODO 対象のフォロワーIDの取得
        targetFollowerId = id
    }

    fun signout(){
        // TODO サインアウト処理
        signinEmail = ""
        FirebaseHelpler.authSignout()
        finish()
    }
}