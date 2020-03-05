package edu.cnm.deepdive.notes.controller;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.cnm.deepdive.notes.R;
import edu.cnm.deepdive.notes.model.entity.Note;
import edu.cnm.deepdive.notes.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

  private ListView notesList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    notesList = findViewById(R.id.notes_list);
    notesList.setOnItemClickListener((parent, view, position, id) -> {
      long noteId = ((Note) notesList.getItemAtPosition(position)).getId();
      showDetails(noteId);
    });
    notesList.setOnItemLongClickListener((parent, view, position, id) -> {
      // TODO Pop up a context menu, to allow removal of a Note instance.
      return true;
    });

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener((view) -> showDetails(0));

    MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    viewModel.getAll().observe(this, (notes) -> {
      ArrayAdapter<Note> adapter =
          new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
      notesList.setAdapter(adapter);
    });
  }

  private void showDetails(long noteId) {
    DetailFragment fragment = DetailFragment.newInstance(noteId);
    fragment.show(getSupportFragmentManager(), fragment.getClass().getName());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}

