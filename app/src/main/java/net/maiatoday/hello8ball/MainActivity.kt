package net.maiatoday.hello8ball

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val myViewModel: MyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        myViewModel.answer.observe(this, Observer<String> { newAnswer ->
            answer.text = newAnswer
        })
        myViewModel.isloading.observe(this, Observer<Boolean> { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            image8ball.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        })

        image8ball.setOnClickListener {
            myViewModel.fetchAnswer(question.text.toString())
        }

        fab.setOnClickListener {
            myViewModel.fetchAnswer(question.text.toString())
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
