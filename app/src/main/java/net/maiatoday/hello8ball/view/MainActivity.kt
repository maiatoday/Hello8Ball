package net.maiatoday.hello8ball.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import net.maiatoday.hello8ball.R
import net.maiatoday.hello8ball.question.QuestionRepository
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CopyHandler {

    @Inject lateinit var repository: QuestionRepository
    private val viewModel: MyViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel.copyHandler = this
        viewModel.answer.observe(this, Observer<String> { newAnswer ->
            answer.text = newAnswer
        })
        viewModel.isloading.observe(this, Observer<Boolean> { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            image8ball.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        })

        image8ball.setOnClickListener {
            viewModel.fetchAnswer(question.text.toString())
        }

        fab.setOnClickListener {
            viewModel.fetchAnswer(question.text.toString())
        }

        question.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    viewModel.fetchAnswer(question.text.toString())
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
        clipboardManager.primaryClip = clipData
        Snackbar.make(
            content_main,
            getString(R.string.copy_snack, item),
            Snackbar.LENGTH_SHORT
        ).show()

    }

    @Suppress("UNUSED_PARAMETER")
    fun copyQuestion(view : View) {
        viewModel.onCopy()
    }

}
