package pt.ulisboa.tecnico.cmov.airdesk_g10.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;


public class FileActivity extends ActionBarActivity {

    public static final int OPERATION_READ = 0;
    public static final int OPERATION_EDIT = 1;
    public static final int OPERATION_CREATE = 2;

    private AirDesk context;

    private int fID;
    private int operation;

    private int wsID;
    private boolean isOwned;

    private EditText fileTitle;
    private EditText fileContent;

    private Button backBtn;
    private Button homeBtn;
    private Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);

        context = (AirDesk) getApplicationContext();

        Intent intent = getIntent();

        operation = intent.getIntExtra("OP", OPERATION_READ);
        fID = intent.getIntExtra("F_ID", 0);
        isOwned = intent.getBooleanExtra("OWNED", true);
        wsID = intent.getIntExtra("WS_ID", 0);

        this.backBtn = (Button) findViewById(R.id.back_btn);
        this.homeBtn = (Button) findViewById(R.id.home_btn);
        this.editBtn = (Button) findViewById(R.id.edit_btn);

        this.fileTitle = (EditText) findViewById(R.id.fileTitle_txt);
        this.fileContent = (EditText) findViewById(R.id.fileContent_txt);

        if(operation == OPERATION_READ || operation == OPERATION_EDIT){

            try {
                context.getmDBHelper().getFile(fID);
            } catch (AirDeskException u){
                Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        if (operation == OPERATION_READ){
            this.editBtn.setVisibility(View.INVISIBLE);
            this.fileTitle.setKeyListener(null);
            this.fileContent.setKeyListener(null);

        } else if (operation == OPERATION_EDIT){
            editBtn.setText("EDIT", TextView.BufferType.EDITABLE);

        }else if (operation == OPERATION_CREATE){
            editBtn.setText("CREATE", TextView.BufferType.EDITABLE);

        }

        this.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FileActivity.this, FileListActivity.class);
                intent.putExtra("OWNED", isOwned);
                intent.putExtra("WS_ID", wsID);
                startActivity(intent);
            }
        });

        this.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FileActivity.this, FileListActivity.class);
                intent.putExtra("OWNED", isOwned);
                intent.putExtra("WS_ID", wsID);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_file, menu);
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
