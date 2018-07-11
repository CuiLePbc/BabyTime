package com.cuile.cuile.babytime

import android.support.transition.Fade
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.WindowManager
import com.cuile.cuile.babytime.vp.body.add.BodyAddFragment
import com.cuile.cuile.babytime.vp.eat.add.EatAddFragment
import com.cuile.cuile.babytime.vp.excretion.add.ExcretionAddFragment
import com.cuile.cuile.babytime.vp.sleep.add.SleepAddFragment
import com.cuile.cuile.babytime.utils.DetailsTransition
import com.cuile.cuile.babytime.vp.main.ShowMainFragment
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.uiThread


class MainActivity : BaseActivity() {
    private val showMainFragment by lazy {
        ShowMainFragment.getInstance(fabMenuItemClickListener =  { changeFragmentById(it) })
    }
    private val bodydataAddFragment by lazy { BodyAddFragment.getInstance() }
    private val eatAddFragment by lazy { EatAddFragment.getInstance() }
    private val excretionAddFragment by lazy { ExcretionAddFragment.getInstance() }
    private val sleepAddFragment by lazy { SleepAddFragment.getInstance() }

    private lateinit var currentFragment: Fragment

    override fun getContentViewLayout(): Int = R.layout.activity_main

    override fun initViews() {

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        currentFragment = showMainFragment
        supportFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, showMainFragment, showMainFragment.javaClass.name)
                .commit()



    }

    override fun onBackPressed() {
        if (currentFragment != showMainFragment) {
            changeToFragment(showMainFragment)
        }
        else super.onBackPressed()
    }

    private fun changeFragmentById(id: Int) {
        doAsync {
            Thread.sleep(200)
            uiThread {
                when(id) {
                    R.id.fabMenuToBodyData -> {
                        changeToFragment(bodydataAddFragment)
                    }
                    R.id.fabMenuToEatData -> {
                        changeToFragment(eatAddFragment)
                    }
                    R.id.fabMenuToExcretionData -> {
                        changeToFragment(excretionAddFragment)
                    }
                    R.id.fabMenuToSleepData -> {
                        changeToFragment(sleepAddFragment)
                    }
                }
            }
        }
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
            is BodyAddFragment -> {
                transaction.addSharedElement(currentFragment.find(R.id.bodydataFab), getString(R.string.fab_shared_name))
            }
            is EatAddFragment -> {
                transaction.addSharedElement(currentFragment.find(R.id.eatDataFab), getString(R.string.fab_shared_name))
            }
            is ExcretionAddFragment -> {
                transaction.addSharedElement(currentFragment.find(R.id.excretionFab), getString(R.string.fab_shared_name))
            }
            is SleepAddFragment -> {
                transaction.addSharedElement(currentFragment.find(R.id.sleepFab), getString(R.string.fab_shared_name))
            }
        }
    }

    private fun addTransitions(fragment: Fragment) {
        fragment.sharedElementEnterTransition = DetailsTransition()
        fragment.sharedElementReturnTransition = DetailsTransition()
        fragment.enterTransition = Fade()
        fragment.exitTransition = Fade()
    }
}
