import java.nio.FloatBuffer;
import java.util.Scanner;

public class Vertex {

   private Triple position;
   private Triple color;

   public Vertex( Triple p, Triple c ) {
      position = p;
      color = c;
   }

   public Vertex( Scanner input ) {
      position = new Triple( input );
      color = new Triple( input );
   }

   public void sendData( FloatBuffer pb, FloatBuffer cb ) {
      position.sendData( pb );
      color.sendData( cb );
   }

}
