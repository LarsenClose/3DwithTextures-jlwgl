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

   public void positionToBuffer() {
      a.positionToBuffer();
      b.positionToBuffer();
      c.positionToBuffer();
   }

   // send texCoords data for triangle to Util.appDataBuffer
   public void texCoordsToBuffer() {
      a.texCoordsToBuffer();
      b.texCoordsToBuffer();
      // c.texCoordsToBuffer();
   }

   public int getTexture() {
      return textureNumber;
   }

   public String toString() {
      return a + " " + b + " " + c;
   }

}
