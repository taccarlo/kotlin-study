package com.taccarlo.kotlinrequestapi.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taccarlo.kotlinrequestapi.R

/**
 * <i>MainActivity</i> is the Activity where the project starts.
 * @author Carlo Tacchella
 * @version 0.0.1
 * @since 2021-07-06
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
