package com.cuile.cuile.babytime

import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class MainActivity : BaseActivity() {
    override fun getContentViewLayout(): Int = R.layout.activity_main

    override fun initViews() {
        mainBottomAppBar.inflateMenu(R.menu.main_menu)
        mainBottomAppBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.main_menu_setting) {
                Snackbar.make(mainBottomAppBar, "Setting", Snackbar.LENGTH_SHORT).show()
            }
            true
        }

        mainFab.onClick {
            Snackbar.make(mainBottomAppBar, "Fab Clicked", Snackbar.LENGTH_LONG).show()
        }
    }
}
