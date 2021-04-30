import java.nio.FloatBuffer;
import java.util.Scanner;

public class Triangle {

   private Vertex a, b, c;

   public Triangle( Vertex aIn, Vertex bIn, Vertex cIn ) {
      a = aIn;  b = bIn;  c = cIn;
   }

   public Triangle( Scanner input ) {
      a = new Vertex( input );
      b = new Vertex( input );
      c = new Vertex( input );
   }

   // get the data for this triangle from camera
   // and put it to pb and cb
   public void sendData( FloatBuffer pb, FloatBuffer cb ) {
      a.sendData( pb, cb );
      b.sendData( pb, cb );
      c.sendData( pb, cb );
   }

}
