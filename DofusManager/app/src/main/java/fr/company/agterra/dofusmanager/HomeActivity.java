package fr.company.agterra.dofusmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_perso:
                    mTextMessage.setText(R.string.title_home);
                    return true;

                case R.id.navigation_encyclopedie:
                    mTextMessage.setText(R.string.title_encyclopedie);
                    return true;

                case R.id.navigation_sorts_cac:
                    mTextMessage.setText(R.string.title_sorts_cac);
                    return true;

            }

            return false;

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        DataRetrieveTask task = new DataRetrieveTask();

        task.execute();

    }

}
