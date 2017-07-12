package com.example.wangchang.wda1150_xwk1151;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wangchang.wda1150_xwk1151.Been.AccountBeen;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by dell-pc on 2017/7/10.
 */

public class AddAccount_forMonthActivity extends AppCompatActivity {

    EditText timepicker;
    EditText moneyedt;
    EditText introedt;
    MaterialSpinner name_spinner;
    RadioGroup typegroup;

    String name;
    String type;
    String month;
    double money;
    String enter_month;

    AccountBeen new_account;

    Button add;

    Long id;


    private ArrayAdapter<String> adapter;
    private static final String[] ITEMS = {"一般消费","投资","医疗","旅游","工资","生活费","其他"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addaccount);

        enter_month = getIntent().getExtras().getString("month");

        name_spinner = (MaterialSpinner) findViewById(R.id.name_spinner);
        timepicker = (EditText) findViewById(R.id.accounttime);
        moneyedt = (EditText) findViewById(R.id.accountmoney);
        introedt = (EditText) findViewById(R.id.accountintroduce);

        add = (Button) findViewById(R.id.addaccount_btn);


        //初始化数据库
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(),"account-db",null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        final AccountBeenDao accountBeenDao = daoSession.getAccountBeenDao();

        setPricePoint(moneyedt);
        moneyedt.setText("0.00");




        final Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new Date());

        SimpleDateFormat sdf2=new SimpleDateFormat("MM");
        month=sdf2.format(new Date());

        timepicker.setText(date);
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(AddAccount_forMonthActivity.this,new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year,monthOfYear,dayOfMonth);
                        timepicker.setText(DateFormat.format("yyyy-MM-dd", c));
                        month = (String) DateFormat.format("MM",c);
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                Snackbar.make(v,month+"月",Snackbar.LENGTH_LONG).show();
            }
        });


        typegroup = (RadioGroup) findViewById(R.id.type_radiogroup);
        typegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //获取变更后的选中项的ID
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获得radiobutton的实例
                RadioButton rb = (RadioButton) findViewById(radioButtonId);
                type = (String) rb.getText();
                System.out.println(type);
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        name_spinner = (MaterialSpinner) findViewById(R.id.name_spinner);
        name_spinner.setAdapter(adapter);
        name_spinner.setPaddingSafe(0, 0, 0, 0);

        name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name = parent.getItemAtPosition(position).toString();
                Snackbar.make(parent,name,Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Snackbar.make(parent,"selected Nothing",Snackbar.LENGTH_LONG).show();

            }
        });


        AccountBeen search = accountBeenDao.queryBuilder().orderDesc(AccountBeenDao.Properties.Id).limit(1).unique();
        if (search != null){
            id = search.getId()+1;
        }else
        {
            id = 1L;
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int error = 1;
                if ((name.equals("请选择类型"))&&(error==1)){
                    Snackbar.make(v,"请选择账单类型",Snackbar.LENGTH_LONG).show();
                    error = 0;

                }
                if ((moneyedt.getText().toString().equals(""))&&(error==1)){
                    Snackbar.make(v,"请填写金额",Snackbar.LENGTH_LONG).show();
                    error = 0;

                }
                if ((type == null)&&(error==1)){
                    Snackbar.make(v,"请选择收支类型",Snackbar.LENGTH_LONG).show();
                    error = 0;

                }
                if (error == 1) {


                    money = Float.parseFloat(moneyedt.getText().toString());
                    Snackbar.make(v,id+"",Snackbar.LENGTH_LONG).show();

                    new_account = new AccountBeen(id, name, type, money, month, timepicker.getText().toString(), introedt.getText().toString());
                    accountBeenDao.insert(new_account);

                    Intent intent = new Intent(AddAccount_forMonthActivity.this,MonthAccountActivity.class);
                    intent.putExtra("month",enter_month);
                    startActivity(intent);
                }

            }


        });


    }

    //设置金额只能输入2位小数
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });

    }
}
