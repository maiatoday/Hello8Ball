package net.maiatoday.hello8ball.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import net.maiatoday.hello8ball.R
import net.maiatoday.hello8ball.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CopyHandler {

    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        viewModel.copyHandler = this
        viewModel.answer.observe(this, Observer<String> { newAnswer ->
            binding.content.answer.text = newAnswer
        })
        viewModel.isloading.observe(this, Observer<Boolean> { isLoading ->
            binding.content.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.content.image8ball.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        })

        binding.content.image8ball.setOnClickListener {
            viewModel.fetchAnswer( binding.content.question.text.toString())
        }

        binding.fab.setOnClickListener {
            viewModel.fetchAnswer( binding.content.question.text.toString())
        }

        binding.content.question.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    viewModel.fetchAnswer(binding.content.question.text.toString())
                    false
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun copy(item: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Source Text", item)
        clipboardManager.setPrimaryClip(clipData)
        Snackbar.make(
            binding.content.contentMain,
            getString(R.string.copy_snack, item),
            Snackbar.LENGTH_SHORT
        ).show()

    }

    @Suppress("UNUSED_PARAMETER")
    fun copyQuestion(view : View) {
        viewModel.onCopy()
    }

}
