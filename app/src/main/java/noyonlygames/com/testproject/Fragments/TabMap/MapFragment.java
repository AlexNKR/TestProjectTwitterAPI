package noyonlygames.com.testproject.Fragments.TabMap;


import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import noyonlygames.com.testproject.Fragments.BaseFragment;
import noyonlygames.com.testproject.Models.TabConstants;
import noyonlygames.com.testproject.R;

public class MapFragment extends BaseFragment {

    private SupportMapFragment mapFragment;
    private final String UNIVERSITY_TITLE = "GUMRF", PARK_TITLE = "Ekateringof";
    private PopupWindow popupWindow = null;
    private GoogleMap map;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, mapFragment).commit();
        }
    }

    @Override
    protected String initializeTitle() {
        return TabConstants.TAB_MAP_TITLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        map = mapFragment.getMap();
        if (map != null)
            setMarkers();

    }

    private void setMarkers() {

        LatLng marker = new LatLng(59.909658, 30.249844);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));

        map.addMarker(new MarkerOptions()
                .title(UNIVERSITY_TITLE)
                .snippet("This is my university")
                .position(marker));

        marker = new LatLng(59.903, 30.26);
        map.addMarker(new MarkerOptions()
                .title(PARK_TITLE)
                .snippet("Park")
                .position(marker));

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                switch (marker.getTitle()) {
                    case UNIVERSITY_TITLE:
                        showPopupWindow();
                        break;

                    case PARK_TITLE:
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://ru.wikipedia.org/wiki/%D0%95%D0%BA%D0%B0%D1%82%D0%B5%D1%80%D0%B8%D0%BD%D0%B3%D0%BE%D1%84")));
                        break;
                }
            }
        });
    }

    private void showPopupWindow() {

        View popupView = getActivity().getLayoutInflater().inflate(R.layout.popup_map_gumrf, null);

        if (popupWindow == null) {
            popupWindow = new PopupWindow(
                    popupView,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

            Button close = (Button) popupView.findViewById(R.id.popup_map_gumrf_close_button);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            });
            popupWindow.showAsDropDown(popupView.getRootView(), convertDpToPixel(16f), convertDpToPixel(96f));
        }

    }

    private int convertDpToPixel(float dp){
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int)px;
    }


}
