package com.example.hong.practice6;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button button;
    ArrayList<restaurant_info> restaurant = new ArrayList<restaurant_info>();
    static final int _RESULT_MSG_CODE = 100;
    //  ArrayList<String> data = new ArrayList<String>();
    restaurant_info_adapter adapter;
    CheckBox cb;
    ArrayList<Integer> ischecked = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        button = (Button) findViewById(R.id.b4);
        cb = (CheckBox) findViewById(R.id.checkBox);


        출처: http://androes.tistory.com/75 [Androes's NotePad]
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        restaurant.add(new restaurant_info("BBQ", "양식", "01011112222", "menu1", "menu2", "menu3", "www.naver.com", "20170413", false));
        restaurant.add(new restaurant_info("mango", "한식", "01033332239", "menu1", "menu2", "menu3", "www.naver.com", "20170413", false));
        restaurant.add(new restaurant_info("banana", "한식", "01020793539", "menu1", "menu2", "menu3", "www.naver.com", "20170413", false));
        restaurant.add(new restaurant_info("apple", "일식", "01066393539", "menu1", "menu2", "menu3", "www.naver.com", "20170413", false));
        restaurant.add(new restaurant_info("july", "중식", "01020733332", "menu1", "menu2", "menu3", "www.naver.com", "20170413", false));
        restaurant.add(new restaurant_info("qubic", "한식", "01009129938", "menu1", "menu2", "menu3", "www.naver.com", "20170413", false));
        setListview();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b1:
                Intent intent = new Intent(this, Main2Activity.class);
                intent.putExtra("restaurant", restaurant);
                startActivityForResult(intent, _RESULT_MSG_CODE);//양방향
                break;
            case R.id.b2://이름 순
                adapter.setNameAscSort();
                break;
            case R.id.b3://종류 순
                adapter.setNameAsc2Sort();
                break;
            case R.id.b4://선택
                if (button.getText().equals("선택")) {
                    button.setText("삭제");
                    checkbox_use(TRUE);
                } else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle("삭제확인");
                    dlg.setIcon(R.mipmap.ic_launcher);
                    dlg.setMessage("선택한 맛집을 삭제하실건가요?");
                    dlg.setNegativeButton("취소", null);
                    dlg.setPositiveButton("삭제",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                  //  SparseBooleanArray booleans = listView.getCheckedItemPositions();
                                    for(int i=0;i<adapter.positions.size();i++){
                                        Toast.makeText(getApplicationContext(),""+adapter.positions.get(i),Toast.LENGTH_SHORT).show();
                                        restaurant.remove(adapter.positions.get(i));
                                    }adapter.notifyDataSetChanged();

                                    //  Toast.makeText(getApplicationContext(), "" + booleans, Toast.LENGTH_SHORT).show();
                             /*      for (int i = 0; i < restaurant.size(); i++) {
                                        if (booleans.get(i)) {
                                            Toast.makeText(getApplicationContext(), "" + restaurant.get(i).name, Toast.LENGTH_SHORT).show();
                                        }
                                    }*/


//anditstory.tistory.com/entry/안드로이드ListViewCHOICEMODESINGLECHOICEMODEMULTIPLE [i티스토리]
//                                    for (int i = 0; i < restaurant.size(); i++) {
//                                        if(cb.isChecked()){
//                                            Toast.makeText(getApplicationContext(), ""+restaurant.get(i).name, Toast.LENGTH_SHORT).show();
//                                          //  restaurant.remove(i);
//                                        }
//                                    }
                                    checkbox_use(FALSE);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                                }
                            });
                    dlg.show();
                    button.setText("선택");
                }
                break;
            case R.id.checkBox:

                Toast.makeText(getApplicationContext(), "!!!", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == _RESULT_MSG_CODE) {
            if (resultCode == RESULT_OK) {
                restaurant = (ArrayList) data.getSerializableExtra("add");
                //Toast.makeText(getApplicationContext(),restaurant.toString(),Toast.LENGTH_SHORT).show();
                setListview();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void setListview() {
        adapter = new restaurant_info_adapter(this, restaurant, FALSE);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                dlg.setTitle("삭제 확인 ");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setMessage("선택한 맛집을 삭제하겠습니까?");
                dlg.setNegativeButton("취소", null);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        restaurant.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                intent.putExtra("restaurant", restaurant);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }

    void checkbox_use(Boolean TF) {
        adapter = null;
        adapter = new restaurant_info_adapter(this, restaurant, TF);
        listView.setAdapter(adapter);
    }
}
