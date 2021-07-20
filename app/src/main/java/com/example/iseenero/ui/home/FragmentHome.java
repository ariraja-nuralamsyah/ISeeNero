package com.example.iseenero.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.iseenero.InformasiDuaActivity;
import com.example.iseenero.InformasiSatuActivity;
import com.example.iseenero.R;

public class FragmentHome extends Fragment {

    private CardView informasi1, informasi2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        informasi1 = view.findViewById(R.id.cardViewInformasi1);
        informasi1.setOnClickListener(View ->{
            Intent intent = new Intent(getContext(), InformasiSatuActivity.class);
            startActivityForResult(intent, 1);
        });

        informasi2 = view.findViewById(R.id.cardViewInformasi2);
        informasi2.setOnClickListener(View ->{
            Intent intent = new Intent(getContext(), InformasiDuaActivity.class);
            startActivityForResult(intent, 1);
        });


        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}


