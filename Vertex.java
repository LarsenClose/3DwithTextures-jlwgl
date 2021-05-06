import java.nio.FloatBuffer;
import java.util.Scanner;

public class Vertex {

   // private Triple position;
   // private Triple color;
   private double x, y, z; // position
   private double s, t; // texture coordinates

   public Vertex(double xin, double yin, double zin, double sIn, double tIn) {
      x = xin;
      y = yin;
      z = zin;
      s = sIn;
      t = tIn;
   }

   public Vertex(Triple p, double sIn, double tIn) {
      x = p.x;
      y = p.y;
      z = p.z;
      s = sIn;
      t = tIn;
   }

   // send position data to Util.appDataBuffer
   public void positionToBuffer() {
      Util.bufferPut(x);
      Util.bufferPut(y);
      Util.bufferPut(z);
   }

   // send texCoords data to Util.appDataBuffer
   public void texCoordsToBuffer() {
      Util.bufferPut(s);
      Util.bufferPut(t);
   }

   public String toString() {
      return "[" + x + " " + y + " " + z + "]";
   }

   // public Vertex(Triple p, Triple c) {
   //    position = p;
   //    color = c;
   // }

   // public Vertex(Scanner input) {
   //    position = new Triple(input);
   //    color = new Triple(input);
   // }

   // public void sendData(FloatBuffer pb, FloatBuffer cb) {
   //    position.sendData(pb);
   //    color.sendData(cb);
   // }

}