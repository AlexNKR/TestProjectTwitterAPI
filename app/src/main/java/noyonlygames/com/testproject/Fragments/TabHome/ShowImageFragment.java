package noyonlygames.com.testproject.Fragments.TabHome;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import noyonlygames.com.testproject.Fragments.BaseFragment;
import noyonlygames.com.testproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowImageFragment extends BaseFragment {

    static private final String ARG_URL = "arg_url";
    String url = "";

    public static ShowImageFragment newInstance(String url) {
        ShowImageFragment imageFragment = new ShowImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        imageFragment.setArguments(args);
        return imageFragment;
    }

    @Override
    protected String initializeTitle() {
        return "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        url = getArguments().getString(ARG_URL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;

            default: return true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_show_image, container, false);
        final SubsamplingScaleImageView  image = (SubsamplingScaleImageView )view.findViewById(R.id.show_image_subsampling_scale_image_view);
        image.setMaxScale(2);
        image.setMinScale(0.5f);

        final ImageView imageHolder = new ImageView(getActivity());

        Picasso.with(getActivity())
                .load(url)
                .into(imageHolder, new Callback() {
                    @Override
                    public void onSuccess() {
                        imageHolder.buildDrawingCache();
                        image.setImage(ImageSource.bitmap(((BitmapDrawable)imageHolder.getDrawable()).getBitmap()));
                    }

                    @Override
                    public void onError() {

                    }
                });

        return view;
    }
}
