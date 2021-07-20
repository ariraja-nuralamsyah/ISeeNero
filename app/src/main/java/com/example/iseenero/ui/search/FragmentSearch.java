package com.example.iseenero.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iseenero.R;
import com.example.iseenero.database.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FragmentSearch extends Fragment {

    ListView listView;
    DatabaseReference db;
    SungaiAdapter sungaiAdapter;
    FirebaseHelper helper;
    private ArrayList<ArrayList<Sungai>> arrayList;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        //SET UP FIREBASE DB
        db= FirebaseDatabase.getInstance("https://iseenero-b4cf2-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        helper=new FirebaseHelper(db);

        ArrayList<Sungai> listSungai = helper.retrieve();
        arrayList = new ArrayList<>();
        for (int i=0 ; i< listSungai.size(); i++){
            arrayList.add(listSungai);
        }

        //DISPLAY LISTVIEW
        listView = (ListView) view.findViewById(R.id.listView);
        sungaiAdapter = new SungaiAdapter( getContext(), helper.retrieve());
        listView.setAdapter(sungaiAdapter);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //sungaiAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }
}
