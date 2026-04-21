package com.wanted.android.montage.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavGraph
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WantedDesignSystemDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startDestination = intent.getStringExtra(START_DESTINATION)
            ?.let { MontageDesignDemoNavContract.fromClassName(it) }
            ?: MontageDesignDemoNavContract.DemoList

        setContent {
            DesignSystemTheme {
                MontageDesignDemoNavGraph(
                    navHostController = rememberNavController(),
                    startDestination = startDestination,
                    onClickBack = { finish() }
                )
            }
        }
    }

    companion object {
        private const val START_DESTINATION = "START_DESTINATION"

        fun getIntent(
            context: Context,
            startDestination: MontageDesignDemoNavContract
        ): Intent = Intent(context, WantedDesignSystemDemoActivity::class.java).apply {
            putExtra(START_DESTINATION, startDestination::class.simpleName)
        }
    }
}