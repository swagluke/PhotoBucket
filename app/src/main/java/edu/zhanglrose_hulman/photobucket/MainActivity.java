package edu.zhanglrose_hulman.photobucket;



import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements PhotoListFragment.Callback{

    public PhotoAdapter mPhotoAdapter;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        PhotoListFragment photoListFragment = new PhotoListFragment();
        ft.add(R.id.fragment_container, photoListFragment);
        ft.commit();
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

    @Override
    public void onDisplay(Photo weatherpic){

        showImageFromUrl(weatherpic);
    }

    private void showImageFromUrl(Photo photo) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        PhotoDetailFragment fragment = PhotoDetailFragment.newInstance(photo);
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack("detail");
        ft.commit();
    }

    public FloatingActionButton getFab(){
        return fab;
    }
}
