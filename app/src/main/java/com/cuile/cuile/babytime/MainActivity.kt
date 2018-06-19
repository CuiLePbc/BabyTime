package com.cuile.cuile.babytime

import android.support.transition.Explode
import android.support.transition.Fade
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.Menu
import android.view.MenuItem
import com.cuile.cuile.babytime.add.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.support.v4.find


class MainActivity : BaseActivity() {
    private val showMainFragment by lazy { ShowMainFragment.getInstance() }
    private val bodydataAddFragment by lazy { BodydataAddFragment.getInstance() }
    private val eatAddFragment by lazy { EatAddFragment.getInstance() }
    private val excretionAddFragment by lazy { ExcretionAddFragment.getInstance() }
    private val medicineAddFragment by lazy { MedicineAddFragment.getInstance() }
    private val sleepAddFragment by lazy { SleepAddFragment.getInstance() }

    private lateinit var currentFragment: Fragment

    override fun getContentViewLayout(): Int = R.layout.activity_main

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.main_menu_bodydata -> changeToFragment(bodydataAddFragment)
            R.id.main_menu_eat -> changeToFragment(eatAddFragment)
            R.id.main_menu_excretion -> changeToFragment(excretionAddFragment)
            R.id.main_menu_medicine -> changeToFragment(medicineAddFragment)
            R.id.main_menu_sleep -> changeToFragment(sleepAddFragment)
        }
        mainToolbar.title = item?.title
        return super.onOptionsItemSelected(item)
    }

    override fun initViews() {
        setSupportActionBar(mainToolbar)
        mainToolbar.title = getString(R.string.show_main)

        currentFragment = showMainFragment
        supportFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, showMainFragment, showMainFragment.javaClass.name)
                .commit()
    }

    override fun onBackPressed() {
        if (currentFragment != showMainFragment) {
            changeToFragment(showMainFragment)
            mainToolbar.title = getString(R.string.show_main)
        }
        else super.onBackPressed()
    }

    private fun changeToFragment(toFragment: Fragment) {

        if (currentFragment == toFragment) return

        addTransitions(toFragment)

        val transaction = supportFragmentManager.beginTransaction()

        addSharedElement(transaction)

        transaction.replace(R.id.mainFragmentContainer, toFragment).commit()

        currentFragment = toFragment
    }

    private fun addSharedElement(transaction: FragmentTransaction) {
        when(currentFragment) {
            is ShowMainFragment -> {
                transaction.addSharedElement(currentFragment.find(R.id.mainshowFab), getString(R.string.fab_shared_name))
            }
            is BodydataAddFragment -> {
                transaction.addSharedElement(currentFragment.find(R.id.bodydataFab), getString(R.string.fab_shared_name))
            }
            is EatAddFragment -> {
                transaction.addSharedElement(currentFragment.find(R.id.eatFab_stop_or_submit), getString(R.string.fab_shared_name))
            }
            is ExcretionAddFragment -> {
                transaction.addSharedElement(currentFragment.find(R.id.excretionFab), getString(R.string.fab_shared_name))
            }
            is MedicineAddFragment -> {
                transaction.addSharedElement(currentFragment.find(R.id.medicineFab), getString(R.string.fab_shared_name))
            }
            is SleepAddFragment -> {
                transaction.addSharedElement(currentFragment.find(R.id.sleepFab), getString(R.string.fab_shared_name))
            }
        }
    }

    private fun addTransitions(fragment: Fragment) {
        fragment.sharedElementEnterTransition = DetailsTransition()
        fragment.sharedElementReturnTransition = DetailsTransition()
        fragment.enterTransition = Explode()
        fragment.exitTransition = Explode()
    }
}
