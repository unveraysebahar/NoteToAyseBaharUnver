package ise308.unver.aysebahar.notetoaysebaharunver

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //private val noteList = ArrayList<Note>()
    //private var tempNote = Note()
    private var mSerializer: JsonSerializer? = null
    private var noteList: ArrayList<Note>? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: NoteAdapter? = null
    private var showDividers: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        /*
        CHAPTER 15 EXAMPLES
        // Declaring and allocating in one step
        val ourArray = IntArray(1000)
        // Let's initialize ourArray using a for loop
        // Because more than a few variables is allot of typing!
        for (i in 0..999) {
            // Put the value into ourArray
            // At the position decided by i.
            ourArray[i] = i * 5
            //Output what is going on
            Log.i("info", "i = $i")
            Log.i("info", "ourArray[i] = ${ ourArray[i]}")
        }

        // Declaring an array
        // Allocate memory for a maximum size of 5 elements
        val ourArray = IntArray(5)
        // Initialize ourArray with values
        // The values are arbitrary, but they must be Int
        // The indexes are not arbitrary. Use 0 through 4 or crash!
        ourArray[0] = 25
        ourArray[1] = 50
        ourArray[2] = 125
        ourArray[3] = 68
        ourArray[4] = 47
        //Output all the stored values
        Log.i("info", "Here is ourArray:")
        Log.i("info", "[0] = " + ourArray[0])
        Log.i("info", "[1] = " + ourArray[1])
        Log.i("info", "[2] = " + ourArray[2])
        Log.i("info", "[3] = " + ourArray[3])
        Log.i("info", "[4] = " + ourArray[4])

        */

        /*
         We can do any calculation with an array element
         provided it is appropriate to the contained type
        Like this:
        */

        /*

        val answer = ourArray[0] +
                ourArray[1] +
                ourArray[2] +
                ourArray[3] +
                ourArray[4]
        Log.i("info", "Answer = $answer")
        */

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val dialog = DialogNewNote()
            dialog.show(supportFragmentManager, "")
        }

        fab.setOnClickListener { view ->
            val dialog = DialogNewNote()
            dialog.show(supportFragmentManager, "")
        }

        mSerializer = JsonSerializer("NoteToSelf.json", applicationContext)


        try {
            noteList = mSerializer!!.load()
        } catch (e: Exception) {
            noteList = ArrayList()
            Log.e("Error loading notes: ", "", e)
        }

        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView

        adapter = NoteAdapter(this, this.noteList!!)

        val layoutManager = LinearLayoutManager(applicationContext)

        recyclerView!!.layoutManager = layoutManager

        recyclerView!!.itemAnimator = DefaultItemAnimator()

        recyclerView!!.adapter = adapter

    }

    fun createNewNote(n: Note) {

        noteList!!.add(n)
        adapter!!.notifyDataSetChanged()

    }

    fun showNote(noteToShow: Int) {
        val dialog = DialogShowNote()
        dialog.sendNoteSelected(noteList!![noteToShow])
        dialog.show(supportFragmentManager, "")
    }

    private fun saveNotes() {
        try {
            mSerializer!!.save(this.noteList!!)
        } catch (e: Exception) {
            Log.e("Error Saving Notes", "", e)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this,
                    SettingsActivity::class.java)

                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPause() {
        super.onPause()

        saveNotes()
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences(
            "Note to self",
            Context.MODE_PRIVATE)
        showDividers = prefs.getBoolean(
            "dividers", true)


        // Add a neat dividing line between list items
        if (showDividers)
            recyclerView!!.addItemDecoration(
                DividerItemDecoration(
                    this, LinearLayoutManager.VERTICAL))
        else {
            // check there are some dividers
            // or the app will crash
            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }

    }

}