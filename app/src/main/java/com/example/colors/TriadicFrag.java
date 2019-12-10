package com.example.colors;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


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

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
//set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                        .setTitle("Are you sure to Exit")
//set message
                        .setMessage("Exiting will bring you back to home")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getActivity(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
            }
        });

        ImageView baseImage = (ImageView) rootView.findViewById(R.id.colorBase);
        ImageView color1 = (ImageView) rootView.findViewById(R.id.color);
        ImageView color2 = (ImageView) rootView.findViewById(R.id.color2);
        ImageView color3 = (ImageView) rootView.findViewById(R.id.color3);
        ImageView color4 = (ImageView) rootView.findViewById(R.id.color4);
        TextView baseHex = (TextView) rootView.findViewById(R.id.baseHex);


        int r = (int)redValue;
        int g = (int)greenValue;
        int b = (int)blueValue;
        String baseHexText = String.format("#%02X%02X%02X", r, g, b);
        baseHex.setText(baseHexText);
        float[] hsv = new float[3];
        int[] rgb = new int[4];
        boolean[] setTextWhite = {false, false, false, false};
        Color.RGBToHSV(r, g, b, hsv);
        baseImage.setBackgroundColor(Color.HSVToColor(hsv));

        hsv[0] += 72;
        rgb = getRGB(hsv);
        if(rgb[3] == 1) {
            setTextWhite[0] = true;
        }
        String color1HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
        color1.setBackgroundColor(Color.HSVToColor(hsv));
        hsv[0] += 72;
        rgb = getRGB(hsv);
        if(rgb[3] == 1) {
            setTextWhite[1] = true;
        }
        String color2HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
        color2.setBackgroundColor(Color.HSVToColor(hsv));
        hsv[0] += 72;
        rgb = getRGB(hsv);
        if(rgb[3] == 1) {
            setTextWhite[2] = true;
        }
        String color3HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
        color3.setBackgroundColor(Color.HSVToColor(hsv));
        hsv[0] += 72;
        rgb = getRGB(hsv);
        if(rgb[3] == 1) {
            setTextWhite[2] = true;
        }
        String color4HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
        color4.setBackgroundColor(Color.HSVToColor(hsv));
        setTextViews(rootView, color1HexText, color2HexText, color3HexText, color4HexText, setTextWhite);
        return rootView;
    }

    public void setTextViews(View rootView, String one, String two, String three, String four, boolean[] setTextWhite) {
        TextView color1Hex = (TextView) rootView.findViewById(R.id.color1Hex);
        TextView color2Hex = (TextView) rootView.findViewById(R.id.color2Hex);
        TextView color3Hex = (TextView) rootView.findViewById(R.id.color3Hex);
        TextView color4Hex = (TextView) rootView.findViewById(R.id.color4Hex);
        if(setTextWhite[0]) {
            color1Hex.setTextColor(Color.WHITE);
        }
        if(setTextWhite[1]) {
            color2Hex.setTextColor(Color.WHITE);
        }
        if(setTextWhite[2]) {
            color3Hex.setTextColor(Color.WHITE);
        }
        if(setTextWhite[3]) {
            color4Hex.setTextColor(Color.WHITE);
        }
        color1Hex.setText(one);
        color2Hex.setText(two);
        color3Hex.setText(three);
        color4Hex.setText(four);
    }

    public int setToWhite(int r, int g, int b) {
        if(Color.luminance(Color.rgb(r,g,b)) < 0.7) {
            return 1;
        } else {
            return 0;
        }
    }

    public int[] getRGB(float[] hsv) {
        int[] rgb = new int[4];
        rgb[0] = Color.red(Color.HSVToColor(hsv));
        rgb[1] = Color.green(Color.HSVToColor(hsv));
        rgb[2] = Color.blue(Color.HSVToColor(hsv));
        rgb[3] = setToWhite(rgb[0], rgb[1], rgb[2]);
        return rgb;
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
