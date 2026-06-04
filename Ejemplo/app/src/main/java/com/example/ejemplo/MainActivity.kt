package com.example.ejemplo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.example.ejemplo.service.SpotifakePlayer
import androidx.compose.ui.platform.ComposeView
import com.example.ejemplo.ui.MiniPlayer
import com.example.ejemplo.ui.FullPlayerScreen
import com.example.ejemplo.ui.SpotifakeTheme
import androidx.compose.runtime.*

class MainActivity : AppCompatActivity() {

    override fun onDestroy() {
        super.onDestroy()
        SpotifakePlayer.release()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SpotifakeMainActivity", "onCreate iniciado")
        com.example.ejemplo.client.ApiClient.init(this)
        SpotifakePlayer.initialize(this)

        setContentView(R.layout.activity_main)

        val miniPlayerView = findViewById<ComposeView>(R.id.miniPlayerCompose)
        miniPlayerView.setContent {
            SpotifakeTheme {
                val showFullPlayer by com.example.ejemplo.ui.PlayerState.showFullPlayer.collectAsState()
                val trackTitle by SpotifakePlayer.currentTrackTitle.collectAsState()
                
                Log.d("SpotifakeMainActivity", "Recomponiendo UI. trackTitle: $trackTitle, showFullPlayer: $showFullPlayer")

                if (showFullPlayer) {
                    FullPlayerScreen(onDismiss = { com.example.ejemplo.ui.PlayerState.setShowFullPlayer(false) })
                } else {
                    if (trackTitle != null) {
                        MiniPlayer(onExpand = { com.example.ejemplo.ui.PlayerState.setShowFullPlayer(true) })
                    }
                }
            }
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout : DrawerLayout = findViewById(R.id.main)
        val navView: NavigationView = findViewById(R.id.nav_view)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        loadFragment(HomeFragment())

        navView.setNavigationItemSelectedListener { item ->
            com.example.ejemplo.ui.PlayerState.setShowFullPlayer(false)
            when (item.itemId) {
                R.id.menu_home -> loadFragment(HomeFragment())
                R.id.menu_buscar -> loadFragment(SearchFragment())
                R.id.menu_biblioteca -> loadFragment(LibraryFragment())
                R.id.menu_language -> toggleLanguage()
                R.id.menu_salir -> logout()
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun toggleLanguage() {
        val currentLocale = androidx.appcompat.app.AppCompatDelegate.getApplicationLocales().toLanguageTags()
        val newLocale = if (currentLocale.contains("en")) "es" else "en"
        androidx.appcompat.app.AppCompatDelegate.setApplicationLocales(
            androidx.core.os.LocaleListCompat.forLanguageTags(newLocale)
        )
    }

    private fun logout(){
        SpotifakePlayer.release()
        com.example.ejemplo.ui.PlayerState.setShowFullPlayer(false)
        com.example.ejemplo.util.TokenManager.clearToken(this)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val navView: NavigationView = findViewById(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        // Usar nav_header_main o el ID correcto del TextView
        val tvUsername = headerView.findViewById<android.widget.TextView>(R.id.tvUsername)
        val username = com.example.ejemplo.util.TokenManager.getUser(this)
        tvUsername?.text = username ?: "Usuario"
    }

    fun openArtistProfile(artistId: Long) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainLayout, ArtistProfileFragment.newInstance(artistId))
            .addToBackStack(null)
            .commit()
    }

    fun openAlbumDetails(albumId: Long) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainLayout, AlbumDetailsFragment.newInstance(albumId))
            .addToBackStack(null)
            .commit()
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainLayout, fragment)
            .commit()
    }
}
