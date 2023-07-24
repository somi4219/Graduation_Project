package org.techtown.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    List<String> places = Arrays.asList("출발지","도착지","아이템0", "아이템1", "아이템2", "아이템3", "아이템4"); //장소 리스트
    TextView floor_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar 디자인 수정
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 왼쪽 상단 menu button 만들기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //1층 지도가 뜨는 걸 기본으로 설정
        FragmentView(1);
        TextView floor_check = findViewById(R.id.floor_check);
        floor_check.setText("1층");

        // 출발지 선택 부분
        Spinner spinner_start = findViewById(R.id.spinner_start);
        List<String> placesStart = new ArrayList<>();
        for (int i = 0; i < places.size(); i++) {
            if (i != 1) {
                placesStart.add(places.get(i));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, placesStart);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_start.setAdapter(adapter);
        spinner_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 선택되면
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //설정 필요
            }
            // 아무것도 선택되지 않은 상태일 때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //설정 필요
            }
        });

        //도착지 선택 부분
        Spinner spinner_end = findViewById(R.id.spinner_end);
        List<String> placesEnd = new ArrayList<>();
        for (int i = 1; i < places.size(); i++) {
            placesEnd.add(places.get(i));
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, placesEnd);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_end.setAdapter(adapter1);
        spinner_end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 선택되면
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //설정 필요
            }
            // 아무것도 선택되지 않은 상태일 때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //설정 필요
            }
        });
    }

    //왼쪽 상단 menu button 눌렀을 때 드로워 부르기
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 네비게이션 드로어의 메뉴 속 아이템 선택 시 호출
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        TextView floor_check = findViewById(R.id.floor_check);
        if (id == R.id.item_floor1) {
            FragmentView(1);
            floor_check.setText("1층");
        } else if (id == R.id.item_floor2) {
            FragmentView(2);
            floor_check.setText("2층");
        } else if (id == R.id.item_logout) { // 로그인 화면으로 돌아가기
            Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logoutIntent);
        }
        drawerLayout.closeDrawer(GravityCompat.START); // 메뉴 선택 후 드로어 닫기
        return true;
    }


    // 층 선택 시 지도 바뀔 수 있도록 fragment 지정
    private void FragmentView(int fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (fragment){
            case 1: //1층
                FragmentFloor1 fragment_floor1 = new FragmentFloor1();
                transaction.replace(R.id.fragment_container, fragment_floor1);
                transaction.commit();
                break;
            case 2: //2층
                FragmentFloor2 fragment_floor2 = new FragmentFloor2();
                transaction.replace(R.id.fragment_container, fragment_floor2);
                transaction.commit();
                break;

        }
    }

    // Back 버튼 눌렀을 때
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
