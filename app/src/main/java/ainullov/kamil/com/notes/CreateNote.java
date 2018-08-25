package ainullov.kamil.com.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateNote extends AppCompatActivity implements View.OnClickListener {

    EditText etName;
    EditText etDesc;
    Button btnCreate;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        etDesc = (EditText) findViewById(R.id.etDesc);
        etName = (EditText) findViewById(R.id.etName);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnCreate:
                String strName = etName.getText().toString();
                String strDesc = etDesc.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("strName",strName);
                intent.putExtra("strDesc",strDesc);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
