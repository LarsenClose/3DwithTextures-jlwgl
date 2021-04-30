/*
   holds a bunch of colors
   for convenience
*/

import java.util.ArrayList;
import java.nio.FloatBuffer;

public class Colors {

   private static ArrayList<Triple> standardColors = init();

   // private static ArrayList<Triple> pyraColors = initPyro();

   private static ArrayList<Triple> init() {
      ArrayList<Triple> colors = new ArrayList<Triple>();

      colors.add(new Triple(1, 0, 0)); // 0 = red
      colors.add(new Triple(0, 1, 0)); // 1 = green
      colors.add(new Triple(0, 0, 1)); // 2 = blue
      colors.add(new Triple(1, 1, 0)); // 3 = yellow
      colors.add(new Triple(1, 0, 1)); // 4 = magenta
      colors.add(new Triple(0, 1, 1)); // 5 = cyan
      colors.add(new Triple(1, 0.5, 0)); // 6 = orange
      colors.add(new Triple(1, 0, 0.5)); // 7
      colors.add(new Triple(0, 1, 0.5)); // 8
      colors.add(new Triple(0.5, 1, 0)); // 9
      colors.add(new Triple(0.5, 0, 1)); // 10
      colors.add(new Triple(0, 0.5, 1)); // 11
      colors.add(new Triple(0, 1, .247)); // pryabase
      colors.add(new Triple(0, 1, .247)); // pyrabase
      colors.add(new Triple(1, .278, .612)); // pyraface
      colors.add(new Triple(.208, .710, 1)); // pyraface
      colors.add(new Triple(.558, 1, 1)); // pyrafaca
      colors.add(new Triple(.735, 0, 1)); // pyraface
      colors.add(new Triple(.51, 1.0, 0.92)); // 18 = pale green
      colors.add(new Triple(0,.444,0)); // 19 = black
      return colors;
   }

   // private static ArrayList<Triple> initPyro() {
   // ArrayList<Triple> colors = new ArrayList<Triple>();
   // colors.add( new Triple( .451,0.02,0.09) );
   // colors.add( new Triple( .451,0.02,0.09) );
   // colors.add( new Triple( .957,.271,.376 ) );
   // colors.add( new Triple( .267,.82,.875 ) );
   // colors.add( new Triple( .196,.643,.655 ) );
   // colors.add( new Triple( .118,.439,.412 ) );

   // return colors;
   // }

   public static void sendData(int k, FloatBuffer buffer) {
      standardColors.get(k).sendData(buffer);
   }

   // public static void sendDataPyra( int k, FloatBuffer buffer ) {
   // pyraColors.get(k).sendData( buffer );
   // }

}
