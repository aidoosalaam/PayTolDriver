package com.andela.buildsdgs.rtrc;

import android.os.Bundle;

import com.andela.buildsdgs.rtrc.ui.main.fragments.AccountFragment;
import com.andela.buildsdgs.rtrc.ui.main.fragments.TransactionFragment;
import com.andela.buildsdgs.rtrc.ui.main.fragments.VehicleFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.andela.buildsdgs.rtrc.ui.main.adaptors.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize objects
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //add method for Setting up viewpager
        setupViewPager(viewPager);

    }


    private  void setupViewPager(ViewPager viewPager){
        sectionsPagerAdapter  = new SectionsPagerAdapter( getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(new VehicleFragment(),"Vehicles");
        sectionsPagerAdapter.addFragment(new TransactionFragment(),"Transactions");
        sectionsPagerAdapter.addFragment(new AccountFragment(),"Account");
        viewPager.setAdapter(sectionsPagerAdapter);
    }
}