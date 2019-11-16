package com.example.smartparking.Adaptere;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.*;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> List = new ArrayList<>();
    private final List<String> Titlu=new ArrayList<>();

   public ViewPagerAdapter(FragmentManager fm)
   {
       super(fm);
   }
    public Fragment getItem(int pozitie)
    {
        return List.get(pozitie);
    }

    public int getCount()
    {
        return Titlu.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Titlu.get(position);
    }
    public void Adauga (Fragment fragment,String Titlul)
    {
        List.add(fragment);
        Titlu.add(Titlul);
    }
}
