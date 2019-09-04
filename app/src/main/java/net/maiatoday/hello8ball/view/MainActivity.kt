package net.maiatoday.hello8ball.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import net.maiatoday.hello8ball.question.QuestionRepository
import net.maiatoday.hello8ball.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val repository = QuestionRepository()
        val model = ViewModelProviders
            .of(this, MyViewModel.FACTORY(repository))
            .get(MyViewModel::class.java)

        model.answer.observe(this, Observer<String> { newAnswer ->
            answer.text = newAnswer
        })
        model.isloading.observe(this, Observer<Boolean> { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            image8ball.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        })

        image8ball.setOnClickListener {
            model.fetchAnswer(question.text.toString())
        }

        fab.setOnClickListener {
            model.fetchAnswer(question.text.toString())
        }

        question.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    model.fetchAnswer(question.text.toString())
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
}