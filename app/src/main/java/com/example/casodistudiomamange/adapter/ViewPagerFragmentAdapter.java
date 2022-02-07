package com.example.casodistudiomamange.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.casodistudiomamange.fragment.GuestFragment;
import com.example.casodistudiomamange.fragment.LoginFragment;
import com.example.casodistudiomamange.fragment.SignUpFragment;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
    private  String[] titles={"Guest", "Login", "Signup"};

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return  new GuestFragment();
            case 1:
                return  new LoginFragment();
            case 2:
                return new SignUpFragment();
        }
        return  new GuestFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
