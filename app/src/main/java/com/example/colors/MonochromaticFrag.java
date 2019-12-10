package com.example.colors;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonochromaticFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MonochromaticFrag extends Fragment {
    private float redValue;
    private float greenValue;
    private float blueValue;

    private OnFragmentInteractionListener mListener;

    public MonochromaticFrag(float redValue, float greenValue, float blueValue) {
        // Required empty public constructor
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters


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
//        float[] hsv = new float[3];

       // Toast.makeText(getContext(), baseHSV[0] + "," + baseHSV[1] + "," + baseHSV[2], Toast.LENGTH_LONG).show();
         //hsv[0] == hue, hsv[1] == saturation, hsv[2] == value
        // case 1
        if(hsv[1] <= .70 && hsv[2] <= .50) {
            //Toast.makeText(getContext(), "Case 1: " + hsv[0] + "," + hsv[1] + "," + hsv[2], Toast.LENGTH_LONG).show();

            // color 1
            hsv[2] += .30;
            color1.setBackgroundColor(Color.HSVToColor(hsv));
            Toast.makeText(getContext(), "Color 1: " + r + "," + g + "," + b, Toast.LENGTH_LONG).show();

            hsv[2] -= .30;
            // color 2
            hsv[1] += .30;
            color2.setBackgroundColor(Color.HSVToColor(hsv));
            hsv[1] -= .30;
            // color 3
            hsv[2] += .50;
            color3.setBackgroundColor(Color.HSVToColor(hsv));
            // color 4
            hsv[1] += .30;
            color4.setBackgroundColor(Color.HSVToColor(hsv));

        } else if(hsv[1] > .70 && hsv[2] > .50) {
            //Toast.makeText(getContext(), "Case 2: " + hsv[0] + "," + hsv[1] + "," + hsv[2], Toast.LENGTH_LONG).show();
            // color 1
            hsv[2] -= .30f;
            color1.setBackgroundColor(Color.HSVToColor(hsv));
            //Toast.makeText(getContext(), "Color 1: " + r + "," + g + "," + b, Toast.LENGTH_LONG).show();
            hsv[2] += .30f;
            // color 2
            hsv[1] -= .30f;
            color2.setBackgroundColor(Color.HSVToColor(hsv));
            hsv[1] += .30f;
            // color 3
            hsv[2] -= .50f;
            color3.setBackgroundColor(Color.HSVToColor(hsv));
            // color 4
            hsv[1] -= .30;
            color4.setBackgroundColor(Color.HSVToColor(hsv));

        } else if(hsv[1] <= .70 && hsv[2] > .50) {
            Toast.makeText(getContext(), "Case 3: " + hsv[0] + "," + hsv[1] + "," + hsv[2], Toast.LENGTH_LONG).show();

            // color 1
            hsv[2] -= .30;
            color1.setBackgroundColor(Color.HSVToColor(Color.rgb(r, g, b), hsv));
            hsv[2] += .30;
            // color 2
            hsv[1] += .30;
            color2.setBackgroundColor(Color.HSVToColor(Color.rgb(r, g, b), hsv));
            hsv[1] -= .30;
            // color 3
            hsv[2] -= .50;
            color3.setBackgroundColor(Color.HSVToColor(Color.rgb(r, g, b), hsv));
            // color 4
            hsv[1] += .30;
            color4.setBackgroundColor(Color.HSVToColor(Color.rgb(r, g, b), hsv));
        } else if(hsv[1] > .70 && hsv[2] <= .50) {
            Toast.makeText(getContext(), "Case 4: " + hsv[0] + "," + hsv[1] + "," + hsv[2], Toast.LENGTH_SHORT).show();
            // color 1
            hsv[2] += .30;
            color1.setBackgroundColor(Color.HSVToColor(Color.rgb(r, g, b), hsv));
            hsv[2] -= .30;
            // color 2
            hsv[1] -= .30;
            color2.setBackgroundColor(Color.HSVToColor(Color.rgb(r, g, b), hsv));
            hsv[1] += .30;
            // color 3
            hsv[2] += .50;
            color3.setBackgroundColor(Color.HSVToColor(Color.rgb(r, g, b), hsv));
            // color 4
            hsv[1] -= .30;
            hsv[2] += .50;
            color4.setBackgroundColor(Color.HSVToColor(Color.rgb(r, g, b), hsv));
        }
        return rootView;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }


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