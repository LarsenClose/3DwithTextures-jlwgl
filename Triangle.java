import java.nio.FloatBuffer;
import java.util.Scanner;

public class Triangle {

   private Vertex a, b, c;
   private int textureNumber;

   public Triangle(Vertex aIn, Vertex bIn, Vertex cIn, int texIn) {
      a = aIn;
      b = bIn;
      c = cIn;
      textureNumber = texIn;
   }

   // public Triangle(Scanner input) {
   //    a = new Vertex(input);
   //    b = new Vertex(input);
   //    c = new Vertex(input);v.sendData(positionBuffer);

   public void toBuffers2( FloatBuffer pb, FloatBuffer cb ) {
      a.posToBuffer(pb);
      a.texToBuffer(cb);
      b.posToBuffer(pb);
      b.texToBuffer(cb);
      c.posToBuffer(pb);
      c.texToBuffer(cb);
   }

   public int getTexture() {
      return textureNumber;
   }

   public String toString() {
      return a + " " + b + " " + c;
   }

}
