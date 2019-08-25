package net.maiatoday.hello8ball

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val model = ViewModelProviders.of(this)[MyViewModel::class.java]
        model.getAnswer().observe(this, Observer<String> { newAnswer ->
            answer.text = newAnswer
        })
        model.getIsLoading().observe(this, Observer<Boolean>{ isLoading ->
            if (isLoading) {
                image8ball.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            } else {
                image8ball.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })

        fab.setOnClickListener { view ->
            if (question.text.isEmpty()) {
                Snackbar.make(view, "Enter a question", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                model.fetchAnswer(question.text.toString())
            }
        }

        MyRepository.setAnswers(resources.getStringArray(R.array.answers))
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
