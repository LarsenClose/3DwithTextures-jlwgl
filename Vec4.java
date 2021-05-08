import java.nio.FloatBuffer;
import java.util.Scanner;

public class Vec4 {

   public double[] data;

   public Vec4() {
      data = new double[4];
   }

   public Vec4( double a, double b, double c ) {
      data = new double[4];
      data[0] = a;  data[1] = b; data[2] = c; 
      data[3] = 1;
   }

   public Vec4( Scanner input ) {
      for (int k=0; k<4; k++) {
         data[k] = input.nextDouble();
      }
      input.nextLine();
   }

   public Vec4 perspDiv() {
      Vec4 p = new Vec4();
      for (int k=0; k<4; k++) {
         p.data[k] = data[k] / data[3];
      }
      return p;
   }

   // send the x,y,z part of buffer
   public void sendData( FloatBuffer buffer ) {
      buffer.put( (float) data[0] );
      buffer.put( (float) data[1] );
      buffer.put( (float) data[2] );
   }

   public String toString() {
      String s = "[";
      for (int k=0; k<4; k++) {
         s += String.format("%12.5f", data[k] );
      }
      s += "]";
      return s;
   }

 // -----------------------

/*

   public void sendData( FloatBuffer buff ) {
      buff.put( (float) x );
      buff.put( (float) y );
      buff.put( (float) z );
   }

   // get perspective view transformation of this point in space
   // from the camera and send it
   public void sendData( FloatBuffer buff, Camera camera ) {

      Vec4 r = camera.viewTransform( this );

      buff.put( (float) r.x );
      buff.put( (float) r.y );
      buff.put( (float) r.z );
   }

   // return this triple minus the other
   public Vec4 minus( Vec4 other ) {
      return new Vec4( x - other.x, y - other.y, z - other.z );
   }

   // compute dot product of this triple and
   // the given other triple
   public double dot( Vec4 other ) {
      return x * other.x + y * other.y + z * other.z;
   }

   // compute cross product of this triple and
   // the given other triple
   public Vec4 cross( Vec4 other ) {
      return new Vec4( y*other.z - z*other.y,
                         z*other.x - x*other.z,
                         x*other.y - y*other.x );
   }

   // produce a normalized copy of this triple
   public Vec4 normalize() {
      double length = Math.sqrt( this.dot( this ) );
      return new Vec4( x / length, y / length, z / length );
   }

   public String toString() {
      return String.format("[%10.4f %10.4f %10.4f]", x, y, z );
   }

   public final static Vec4 up = new Vec4( 0, 0, 1 );

   public static void main(String[] args) {
      Vec4 e = new Vec4( 18, 20, 7 ), 
             a = new Vec4( 9, 8, 7 ),
             b = new Vec4( 1, 14, 12 ),
             c = new Vec4( 13, 5, 17 ),
             p1 = new Vec4( 8, 3, 9 ),
             p2 = new Vec4( 4, 2, 6 ),
             p3 = new Vec4( 3, 6, 10 );

      Vec4 eMinusA = e.minus(a),
             bMinusA = b.minus(a),
             cMinusA = c.minus(a),
             p1MinusE = p1.minus(e),
             p2MinusE = p2.minus(e),
             p3MinusE = p3.minus(e);

      System.out.println( eMinusA + " " + bMinusA + " " + cMinusA +
                          p1MinusE + " " + p2MinusE + " " + p3MinusE + "\n" );

      System.out.printf("%10.4f %10.4f %10.4f %10.4f %10.4f %10.4f\n", eMinusA.dot(eMinusA), eMinusA.dot(bMinusA), eMinusA.dot(cMinusA), eMinusA.dot(p1MinusE), eMinusA.dot(p2MinusE), eMinusA.dot(p3MinusE) );
      System.out.printf("%10.4f %10.4f %10.4f %10.4f %10.4f %10.4f\n", bMinusA.dot(eMinusA), bMinusA.dot(bMinusA), bMinusA.dot(cMinusA), bMinusA.dot(p1MinusE), bMinusA.dot(p2MinusE), bMinusA.dot(p3MinusE) );
      System.out.printf("%10.4f %10.4f %10.4f %10.4f %10.4f %10.4f\n", cMinusA.dot(eMinusA), cMinusA.dot(bMinusA), cMinusA.dot(cMinusA), cMinusA.dot(p1MinusE), cMinusA.dot(p2MinusE), cMinusA.dot(p3MinusE) );

      double lambda, beta, gamma;

      lambda = - eMinusA.dot( eMinusA ) / eMinusA.dot( p1MinusE );
      beta = lambda * bMinusA.dot( p1MinusE ) / bMinusA.dot( bMinusA );
      gamma = lambda * cMinusA.dot( p1MinusE ) / cMinusA.dot( cMinusA );
      System.out.printf("%10.4f %10.4f %10.4f\n", beta, gamma, lambda );

      lambda = - eMinusA.dot( eMinusA ) / eMinusA.dot( p2MinusE );
      beta = lambda * bMinusA.dot( p2MinusE ) / bMinusA.dot( bMinusA );
      gamma = lambda * cMinusA.dot( p2MinusE ) / cMinusA.dot( cMinusA );
      System.out.printf("%10.4f %10.4f %10.4f\n", beta, gamma, lambda );

      lambda = - eMinusA.dot( eMinusA ) / eMinusA.dot( p3MinusE );
      beta = lambda * bMinusA.dot( p3MinusE ) / bMinusA.dot( bMinusA );
      gamma = lambda * cMinusA.dot( p3MinusE ) / cMinusA.dot( cMinusA );
      System.out.printf("%10.4f %10.4f %10.4f\n", beta, gamma, lambda );

   }

*/

}
