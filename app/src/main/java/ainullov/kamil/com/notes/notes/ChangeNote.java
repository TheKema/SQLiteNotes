package ainullov.kamil.com.notes.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ainullov.kamil.com.notes.R;

public class ChangeNote extends AppCompatActivity implements View.OnClickListener {

    EditText etName;
    EditText etDesc;
    Button btnChange;
    Button btnBack;
    int id;
    String strName;
    String strDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_note);


        etDesc = (EditText) findViewById(R.id.etDesc);
        etName = (EditText) findViewById(R.id.etName);
        btnChange = (Button) findViewById(R.id.btnChange);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnChange.setOnClickListener(this);

        Intent intentGet = getIntent();
        strName = intentGet.getStringExtra("strNameChange");
        strDesc = intentGet.getStringExtra("strDescChange");
        id = intentGet.getIntExtra("id", id);
        etName.setText(strName);
        etDesc.setText(strDesc);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                setResult(RESULT_CANCELED);
                break;
            case R.id.btnChange:
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                String strId = String.valueOf(id);
                Intent intent = new Intent();
                intent.putExtra("strNameUpdate", name);
                intent.putExtra("strDescUpdate", desc);
                intent.putExtra("id", strId);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
