import java.nio.FloatBuffer;
import java.util.Scanner;

public class Triple {

   public double x, y, z;

   public Triple( double a, double b, double c ) {
      x = a;  y = b;  z = c;
   }

 

   public Triple( Scanner input ) {
      x = input.nextDouble();  
      y = input.nextDouble();  
      z = input.nextDouble();
      input.nextLine();
   }

   public void sendData( FloatBuffer buff ) {
      buff.put( (float) x );
      buff.put( (float) y );
      buff.put( (float) z );
   }

   // return this triple minus the other
   public Triple minus( Triple other ) {
      return new Triple( x - other.x, y - other.y, z - other.z );
   }

   // compute dot product of this triple and
   // the given other triple
   public double dot( Triple other ) {
      return x * other.x + y * other.y + z * other.z;
   }

   // compute cross product of this triple and
   // the given other triple
   public Triple cross( Triple other ) {
      return new Triple( y*other.z - z*other.y,
                         z*other.x - x*other.z,
                         x*other.y - y*other.x );
   }

   // produce a normalized copy of this triple
   public Triple normalize() {
      double length = Math.sqrt( this.dot( this ) );
      return new Triple( x / length, y / length, z / length );
   }

   // produce homogeneous coords version of this triple
   public Vec4 toVec4() {
      return new Vec4( x, y, z );
   }

   public String toString() {
      return String.format("[%10.4f %10.4f %10.4f]", x, y, z );
   }

   public final static Triple up = new Triple( 0, 0, 1 );


   public static void main(String[] args) {
      Triple e = new Triple( 18, 20, 7 ), 
             a = new Triple( 9, 8, 7 ),
             b = new Triple( 1, 14, 12 ),
             c = new Triple( 13, 5, 17 ),
             p1 = new Triple( 8, 3, 9 ),
             p2 = new Triple( 4, 2, 6 ),
             p3 = new Triple( 3, 6, 10 );

      Triple eMinusA = e.minus(a),
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

}
