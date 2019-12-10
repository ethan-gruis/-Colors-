package com.example.colors;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
        int r = (int) redValue;
        int g = (int) greenValue;
        int b = (int) blueValue;
        ImageView color1 = (ImageView) rootView.findViewById(R.id.color);
        ImageView color2 = (ImageView) rootView.findViewById(R.id.color2);
        ImageView color3 = (ImageView) rootView.findViewById(R.id.color3);
        ImageView color4 = (ImageView) rootView.findViewById(R.id.color4);
        ImageView baseImage = (ImageView) rootView.findViewById(R.id.colorBase);
        TextView baseHex = (TextView) rootView.findViewById(R.id.baseHex);
        String baseHexText = String.format("#%02X%02X%02X", r, g, b);
        baseHex.setText(baseHexText);
        String color1HexText = "";
        String color2HexText = "";
        String color3HexText = "";
        String color4HexText = "";
        doColorMath(rootView, r, g, b);
        return rootView;
    }
//        float[] hsv = new float[3];
//        int[] rgb = {0, 0, 0, 0};
//        Color.RGBToHSV(r, g, b, hsv);
//        baseImage.setBackgroundColor(Color.HSVToColor(hsv));
//        boolean[] setTextWhite = {false, false, false, false};
//
//        //hsv[0] == hue, hsv[1] == saturation, hsv[2] == value
//        // case 1
//        if(hsv[1] <= .70 && hsv[2] <= .50) {
//            // color 1
//            hsv[2] += .30;
//            color1.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[0] = true;
//            }
//            color1HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            hsv[2] -= .30;
//
//            // color 2
//            hsv[1] += .30;
//            color2.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[1] = true;
//            }
//            color2HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//
//            hsv[1] -= .30;
//            // color 3
//            hsv[2] += .50;
//            color3.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[2] = true;
//            }
//            color3HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//
//            // color 4
//            hsv[1] += .30;
//            color4.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[3] = true;
//            }
//            color4HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            setTextViews(rootView, color1HexText, color2HexText, color3HexText, color4HexText, setTextWhite);
//        } else if(hsv[1] > .70 && hsv[2] > .50) {
//            // color 1
//            hsv[2] -= .30f;
//            color1.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[0] = true;
//            }
//            color1HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            hsv[2] -= .30;
//            hsv[2] += .30f;
//            // color 2
//            hsv[1] -= .30f;
//            color2.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[1] = true;
//            }
//            color2HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            hsv[1] += .30f;
//            // color 3
//            hsv[2] -= .50f;
//            color3.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[2] = true;
//            }
//            color3HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            // color 4
//            hsv[1] -= .30;
//            color4.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[3] = true;
//            }
//            color4HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            setTextViews(rootView, color1HexText, color2HexText, color3HexText, color4HexText, setTextWhite);
//        } else if(hsv[1] <= .70 && hsv[2] > .50) {
//            // color 1
//            hsv[2] -= .30;
//            color1.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[0] = true;
//            }
//            color1HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            hsv[2] += .30;
//            // color 2
//            hsv[1] += .30;
//            color2.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[1] = true;
//            }
//            color2HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            hsv[1] -= .30;
//            // color 3
//            hsv[2] -= .50;
//            color3.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[2] = true;
//            }
//            color3HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            // color 4
//            hsv[1] += .30;
//            color4.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[3] = true;
//            }
//            color4HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            setTextViews(rootView, color1HexText, color2HexText, color3HexText, color4HexText, setTextWhite);
//
//        } else {
//            // color 1
//            hsv[2] += .30;
//            color1.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[0] = true;
//            }
//            color1HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            hsv[2] -= .30;
//            // color 2
//            hsv[1] -= .30;
//            color2.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[1] = true;
//            }
//            color2HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            hsv[1] += .30;
//            // color 3
//            hsv[2] += .50;
//            color3.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[2] = true;
//            }
//            color3HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            // color 4
//            hsv[1] -= .30;
//            hsv[2] += .50;
//            color4.setBackgroundColor(Color.HSVToColor(hsv));
//            rgb = getRGB(hsv);
//            if(rgb[3] == 1) {
//                setTextWhite[3] = true;
//            }
//            color4HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
//            setTextViews(rootView, color1HexText, color2HexText, color3HexText, color4HexText, setTextWhite);
//        }
//
//        return rootView;
//    }

    public void doColorMath(View rootView, int r, int g, int b) {
        ImageView baseImage = (ImageView) rootView.findViewById(R.id.colorBase);
        TextView baseHex = (TextView) rootView.findViewById(R.id.baseHex);
        String baseHexText = String.format("#%02X%02X%02X", r, g, b);
        float[] hsv = new float[3];
        Color.RGBToHSV(r, g, b, hsv);
        int white = setToWhite(r, g, b, hsv);
        if(white == 1) {
            baseHex.setTextColor(Color.WHITE);
        }
        baseHex.setText(baseHexText);
        int[] rgb = {0, 0, 0, 0};
        baseImage.setBackgroundColor(Color.HSVToColor(hsv));
        boolean[] setTextWhite = {false, false, false, false};
        int option = 0;

        if(hsv[1] <= .70 && hsv[2] <= .50) {
            option = 1;
        } else if(hsv[1] > .70 && hsv[2] > .50) {
            option = 2;
        } else if(hsv[1] <= .70 && hsv[2] > .50) {
            option = 3;
        } else {
            option = 4;
        }
        alterImages(rootView, option, hsv);

    }

    public void alterImages(View rootView, int option, float[] hsv) {
        ImageView color1 = (ImageView) rootView.findViewById(R.id.color);
        ImageView color2 = (ImageView) rootView.findViewById(R.id.color2);
        ImageView color3 = (ImageView) rootView.findViewById(R.id.color3);
        ImageView color4 = (ImageView) rootView.findViewById(R.id.color4);
        String color1HexText = "";
        String color2HexText = "";
        String color3HexText = "";
        String color4HexText = "";


        int[] rgb = new int[4];
        boolean[] setTextWhite = {false, false, false, false};

        switch(option){
            case 1:
                // color 1
                hsv[2] += .30;
                color1.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[0] = true;
                }
                color1HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                hsv[2] -= .30;

                // color 2
                hsv[1] += .30;
                color2.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[1] = true;
                }
                color2HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);

                hsv[1] -= .30;
                // color 3
                hsv[2] += .50;
                color3.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[2] = true;
                }
                color3HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);

                // color 4
                hsv[1] += .30;
                color4.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[3] = true;
                }
                color4HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                setTextViews(rootView, color1HexText, color2HexText, color3HexText, color4HexText, setTextWhite);
            case 2:
                // color 1
                hsv[2] -= .30f;
                color1.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[0] = true;
                }
                color1HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                hsv[2] -= .30;
                hsv[2] += .30f;
                // color 2
                hsv[1] -= .30f;
                color2.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[1] = true;
                }
                color2HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                hsv[1] += .30f;
                // color 3
                hsv[2] -= .50f;
                color3.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[2] = true;
                }
                color3HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                // color 4
                hsv[1] -= .30;
                color4.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[3] = true;
                }
                color4HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                setTextViews(rootView, color1HexText, color2HexText, color3HexText, color4HexText, setTextWhite);
            case 3:
                // color 1
                hsv[2] -= .30;
                color1.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[0] = true;
                }
                color1HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                hsv[2] += .30;
                // color 2
                hsv[1] += .30;
                color2.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[1] = true;
                }
                color2HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                hsv[1] -= .30;
                // color 3
                hsv[2] -= .50;
                color3.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[2] = true;
                }
                color3HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                // color 4
                hsv[1] += .30;
                color4.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[3] = true;
                }
                color4HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                setTextViews(rootView, color1HexText, color2HexText, color3HexText, color4HexText, setTextWhite);
            case 4:
                // color 1
                hsv[2] += .30;
                color1.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[0] = true;
                }
                color1HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                hsv[2] -= .30;
                // color 2
                hsv[1] -= .30;
                color2.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[1] = true;
                }
                color2HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                hsv[1] += .30;
                // color 3
                hsv[2] += .50;
                color3.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[2] = true;
                }
                color3HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                // color 4
                hsv[1] -= .30;
                hsv[2] += .50;
                color4.setBackgroundColor(Color.HSVToColor(hsv));
                rgb = getRGB(hsv);
                if(rgb[3] == 1) {
                    setTextWhite[3] = true;
                }
                color4HexText = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                setTextViews(rootView, color1HexText, color2HexText, color3HexText, color4HexText, setTextWhite);
        }
    }

    public int setToWhite(int r, int g, int b, float[] hsv) {
        if(Color.luminance(Color.HSVToColor(hsv)) < 0.7) {
            return 1;
        } else {
            return 0;
        }
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


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public int[] getRGB(float[] hsv) {
        int[] rgb = new int[4];
        rgb[0] = Color.red(Color.HSVToColor(hsv));
        rgb[1] = Color.green(Color.HSVToColor(hsv));
        rgb[2] = Color.blue(Color.HSVToColor(hsv));
        rgb[3] = setToWhite(rgb[0], rgb[1], rgb[2], hsv);
        return rgb;
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