package com.oneandonly.inventationblock.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.core.view.GravityCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.afterUpdate
import com.oneandonly.inventationblock.databinding.ActivityMainBinding
import com.oneandonly.inventationblock.databinding.NavHeaderMainBinding
import com.oneandonly.inventationblock.datasource.model.data.*
import com.oneandonly.inventationblock.datasource.model.repository.RecipeRepository
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.datasource.model.repository.UserRepository
import com.oneandonly.inventationblock.makeToast
import com.oneandonly.inventationblock.ui.adapter.SearchDropDownAdapter
import com.oneandonly.inventationblock.ui.adapter.StockAdapter
import com.oneandonly.inventationblock.viewmodel.*
import com.oneandonly.inventationblock.viewmodel.RecipeViewModel.Companion.searchMenuList
import com.oneandonly.inventationblock.viewmodel.StockViewModel.Companion.searchStockList
import com.oneandonly.inventationblock.viewmodel.factory.RecipeFactory
import com.oneandonly.inventationblock.viewmodel.factory.StockFactory
import com.oneandonly.inventationblock.viewmodel.factory.UserFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), StockAdapter.OnClick {

    private lateinit var binding: ActivityMainBinding

    private lateinit var userViewModel: UserViewModel
    private lateinit var stockViewModel: StockViewModel
    private lateinit var recipeViewModel: RecipeViewModel

    private lateinit var stockAdapter: StockAdapter

    private var searchState = false
    // true: ?????? ???/ false: ?????? ??? ?????? ???

    private var clickTime: Long = 0

    private val searchList: ArrayList<Search> = ArrayList()

    companion object {
        val stockList: ArrayList<Search> = ArrayList()
        val menuList: ArrayList<Search> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        binding.lifecycleOwner = this@MainActivity

        setViewModel()

        Log.d("Token Main",tokens.toString())

        val navBind: NavHeaderMainBinding = NavHeaderMainBinding.bind(binding.mainNavView.getHeaderView(0))
        navBind.user = userViewModel //????????? ????????? ?????? //TODO(?????? ??????)
        binding.user = userViewModel
        binding.mainToolBar.user = userViewModel

        //UI
        uiSetting()

        //Observer
        stockListObserver()
        errorObserver()
        observeSearchList()

    }

    override fun onStart() {
        super.onStart()
        userViewModel.getInformation()
    }

    override fun onResume() {
        super.onResume()
        resetList()
    }

    override fun onBackPressed() {

        if (binding.mainSearchEdit.hasFocus()) {
            binding.mainSearchEdit.clearFocus()
        } else {
            //?????? ?????? ??????
            val current = System.currentTimeMillis()
            if (current - clickTime >= 2500) {
                clickTime = current
                makeToast("?????? ??? ?????? ??? ???????????????.")
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun observeSearchList() {
        searchStockList.observe(this@MainActivity, Observer {
            stockList.clear()
            for ( i in it) {
                stockList.add(i)
            }
            if (stockList.isNotEmpty()) {
                dropdownSetting()
                Log.d("SearchList","StockList ?????? $stockList")
            }
        })
        searchMenuList.observe(this@MainActivity, Observer {
            menuList.clear()
            for ( i in it) {
                menuList.add(i)
            }
            if (menuList.isNotEmpty()) {
                dropdownSetting()
                Log.d("SearchList","menuList ?????? $menuList")
            }
        })

    }

    private fun setViewModel() {
        val repository = UserRepository()
        val stockRepo = StockRepository()
        val recipeRepo = RecipeRepository()

        val viewModelFactory = UserFactory(repository,this@MainActivity)
        userViewModel = ViewModelProvider(this@MainActivity,viewModelFactory)[UserViewModel::class.java]

        val stockViewModelFactory = StockFactory(stockRepo)
        stockViewModel = ViewModelProvider(this@MainActivity,stockViewModelFactory)[StockViewModel::class.java]

        val recipeViewModelFactory = RecipeFactory(recipeRepo)
        recipeViewModel = ViewModelProvider(this@MainActivity,recipeViewModelFactory)[RecipeViewModel::class.java]
    }

    private fun onClickLogout() {
        val autoLoginViewModel = AutoLoginViewModel()
        val tokenViewModel = TokenViewModel()

        tokenViewModel.updateToken("null")

        autoLoginViewModel.updateAutoLogin(false)
        moveToLogin()
    }

    private fun moveToLogin() {
        Log.d("Main_Activity","moveToLogin")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun moveToMenu() {
        Log.d("Main_Activity","moveToMenu")
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    private fun moveToMenuAdd() {
        Log.d("Main_Activity","moveToMenuAdd")
        val intent = Intent(this, StockAddActivity::class.java)
        startActivity(intent)
    }

    private fun uiSetting() {
        drawerSetting()
        toolBarSetting()
        stockListSetting(stockViewModel)
        searchEditSetting()
        fabSetting()
        spinnerSetting()
    }

    private fun drawerSetting() {
        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)  //??? ?????? ????????? ??????, ?????? ?????? ????????? ??????
        binding.mainNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_myPage -> {
                    afterUpdate()
                }
                R.id.menu_logout -> {
                    onClickLogout()
                }
                R.id.menu_stockSetting -> {
                    moveToMenu()
                }
                R.id.menu_stockReport -> {
                    afterUpdate()
                }
            }
            binding.mainDrawer.closeDrawers()
            return@setNavigationItemSelectedListener false
        }
    }

    private fun spinnerSetting() {
        binding.mainListAlign.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                CoroutineScope(Dispatchers.Main).launch {
                    stockViewModel.getStockList(position)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }

        }
    }

    private fun toolBarSetting() {
        binding.mainToolBar.toolBarDrawerBtn.setOnClickListener {
            binding.mainDrawer.openDrawer(GravityCompat.START)
        }

        binding.mainToolBar.toolBarAlarmBtn.setOnClickListener {
            afterUpdate()
            //TODO(?????? ????????? ??? ??????)
        }

        binding.mainToolBar.toolBarMyPageBtn.setOnClickListener {
            afterUpdate()
            //TODO(?????????????????? ??????)
        }

        binding.mainToolBar.toolBarTitle.setOnClickListener {
            //TODO(?????????????????? ????????? ?????????)
        }
    }

    private fun stockListSetting(stockViewModel: StockViewModel) {
        binding.stockList.layoutManager = LinearLayoutManager(this)
        stockAdapter = StockAdapter(stockViewModel.stockList, stockViewModel, this)
        binding.stockList.adapter = stockAdapter

        CoroutineScope(Dispatchers.Main).launch {
            stockViewModel.getStockList(0) //TODO(?????? ?????? ?????? ??????, ????????? ????????? ????????? ????????????)
        }
    }

    private fun searchEditSetting() {
        Log.d("menuSearchTest","TEST")

        binding.mainSearchEdit.setOnItemClickListener { adapterView, _, i, _ ->
            val selected = adapterView.adapter.getItem(i) as Search
            searchState = true
            Log.d("menuSearchTest","elementToStock ${selected.type}")
            binding.mainSearchEdit.setText(selected.name)
            if(selected.type == "stock") {
                search()
            } else {
                searchByMenu(selected.id)
            }
            changeStateSearch(false)
        }

        binding.mainSearchEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                changeStateSearch(true)
            }
        }

        binding.mainSearchEdit.setOnEditorActionListener { _, i, _ ->
            if ( i == EditorInfo.IME_ACTION_SEARCH) {
                search()
                return@setOnEditorActionListener true
            } else {
                return@setOnEditorActionListener false
            }
        }

        binding.mainIcon.setOnClickListener {
            changeStateSearch(false)
        }

        binding.mainSearchBtn.setOnClickListener {
            search()
        }

        binding.mainToolBar.toolBarTitle.setOnClickListener {
            if (searchState) {
                changeStateSearch(false)
                stockViewModel.getStockList(0) //TODO
                searchState = false
                binding.mainListAlign.isInvisible = searchState
                binding.mainSearchEdit.text.clear()
            } else {
                changeStateSearch(false)
            }
        }
    }

    private fun fabSetting() {
        var clickedFab = false
        binding.mainFab.setOnClickListener {
            clickedFab = !clickedFab
            clickFab(clickedFab)
        }

        binding.fab1.setOnClickListener {
            moveToMenuAdd()
        }

        binding.fab2.setOnClickListener {
            afterUpdate()
        }

        binding.fab3.setOnClickListener {
            afterUpdate()
        }

        binding.sticker.setOnClickListener {
            clickedFab = !clickedFab
            clickFab(clickedFab)
        }
    }

    private fun clickFab(clickedFab: Boolean) {
        val rotateOpen = AnimationUtils.loadAnimation(applicationContext,R.anim.rotate_open_anim)
        val rotateClose = AnimationUtils.loadAnimation(applicationContext,R.anim.rotate_close_anim)
        val toBottom = AnimationUtils.loadAnimation(applicationContext,R.anim.to_bottom_anim)
        val fromBottom = AnimationUtils.loadAnimation(applicationContext,R.anim.from_bottom_anim)

        binding.let {
            it.fabLayout1.isVisible = clickedFab
            it.fabLayout2.isVisible = clickedFab
            it.fabLayout3.isVisible = clickedFab

            if(clickedFab) {
                it.mainFab.startAnimation(rotateOpen)
                it.fabLayout1.startAnimation(fromBottom)
                it.fabLayout2.startAnimation(fromBottom)
                it.fabLayout3.startAnimation(fromBottom)
            } else {
                it.mainFab.startAnimation(rotateClose)
                it.fabLayout1.startAnimation(toBottom)
                it.fabLayout2.startAnimation(toBottom)
                it.fabLayout3.startAnimation(toBottom)
            }

            it.fab1.isClickable = clickedFab
            it.fab2.isClickable = clickedFab
            it.fab3.isClickable = clickedFab

            it.sticker.visibility = if(clickedFab) View.VISIBLE else View.GONE
        } //T
    }

    private fun dropdownSetting() {
        searchList.clear()
        searchList.addAll(stockList)
        searchList.addAll(menuList)

        val adapter = SearchDropDownAdapter(this,R.layout.item_dropdown, searchList)

        binding.mainSearchEdit.setAdapter(adapter)
        binding.mainSearchEdit.threshold = 1
    }

    private fun stockListObserver() {
        val stockObserver: Observer<ArrayList<Stock>> = Observer {
                stockAdapter = StockAdapter(stockViewModel.stockList, stockViewModel, this)
                binding.stockList.adapter = stockAdapter
                if (stockViewModel.stockList.value?.size!! <= 0 ) {
                    makeToast("????????? ????????? ????????????")
                }
        }
        stockViewModel.stockList.observe(this,stockObserver)
    }

    private fun errorObserver() {
        val errorObserver: Observer<String> = Observer {
            makeToast(stockViewModel.errorList.value.toString())
        }
        stockViewModel.errorList.observe(this,errorObserver)
    }

    private fun menuSearchListToStockList() {

    }

    private fun changeStateSearch(state: Boolean) { //?????? ?????? ?????? ??? ??????
        Log.d("State_Change","?????? $state")

        binding.let {
            it.mainListAlign.visibility = if (state) View.GONE else View.VISIBLE
            it.mainFab.isInvisible = state
            it.stockList.isInvisible = state
            it.mainIcon.isClickable = state
            it.mainIcon.setImageResource(if (state) R.drawable.ic_back else R.drawable.ic_logo)
            if (!state) {
                it.mainSearchEdit.clearFocus()
                hideKeyBoard()
            }
        }
    }

    private fun search() { //??????
        searchState = true
        stockViewModel.getSearchList(binding.mainSearchEdit.text.toString())
        Log.d("Search","!")
        changeStateSearch(false) //???????????? ?????? ??? ??????
        binding.mainListAlign.isInvisible = true
    }

    private fun searchByMenu(rid: Int) {
        searchState = true
        recipeViewModel.getRecipeInfoReturn(rid)

        val recipeObserver: Observer<ArrayList<RecipeElement>> = Observer {
            stockViewModel.elementToStock(recipeViewModel.recipeList)

            Log.d("menuSearchTest","Observing")
        }
        recipeViewModel.recipeList.observe(this, recipeObserver)

        changeStateSearch(false)
        binding.mainListAlign.isInvisible = true
    }

    private fun hideKeyBoard() {
        try {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.mainSearchEdit.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } //????????? ?????????

    override fun onClick() {
        stockViewModel.ing.value = State.Loading
        stockViewModel.ing.observe(this) {
            when (it) {
                State.Success -> {
                    resetList()
                    Log.d("ToggleClick","Success")
                }
                State.Fail -> {
                    Log.d("ToggleClick",".Fail")
                }
                State.Loading -> {
                    Log.d("ToggleClick","loading")
                }
                State.Null -> {

                }
            }
        }
    }

    private fun resetList() {
        if (searchState) {
            //stockViewModel.getSearchList(binding.mainSearchEdit.text.toString())
        } else {
            stockViewModel.getStockList(0) //TODO(?????? ????????? ??? ????????????)
        }
    }

}