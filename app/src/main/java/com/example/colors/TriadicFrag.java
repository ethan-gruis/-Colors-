package com.example.colors;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TriadicFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * * create an instance of this fragment.
 */
public class TriadicFrag extends Fragment {
    private OnFragmentInteractionListener mListener;

    private float redValue;
    private float greenValue;
    private float blueValue;

    public TriadicFrag(float redValue, float greenValue, float blueValue) {
        // Required empty public constructor
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_palette, container, false);

        ImageView baseImage = (ImageView) rootView.findViewById(R.id.colorBase);
        ImageView color1 = (ImageView) rootView.findViewById(R.id.color);
        ImageView color2 = (ImageView) rootView.findViewById(R.id.color2);
        ImageView color3 = (ImageView) rootView.findViewById(R.id.color3);
        ImageView color4 = (ImageView) rootView.findViewById(R.id.color4);

        int r = (int)redValue;
        int g = (int)greenValue;
        int b = (int)blueValue;
        float[] hsv = new float[3];
        Color.RGBToHSV(r, g, b, hsv);
        baseImage.setBackgroundColor(Color.HSVToColor(hsv));
//        hsv[0] += 120;
//        color1.setBackgroundColor(Color.HSVToColor(hsv));
//        hsv[0] += 120;
//        color2.setBackgroundColor(Color.HSVToColor(hsv));
        hsv[0] += 72;
        color1.setBackgroundColor(Color.HSVToColor(hsv));
        hsv[0] += 72;
        color2.setBackgroundColor(Color.HSVToColor(hsv));
        hsv[0] += 72;
        color3.setBackgroundColor(Color.HSVToColor(hsv));
        hsv[0] += 72;
        color4.setBackgroundColor(Color.HSVToColor(hsv));


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
