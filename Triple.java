import java.nio.FloatBuffer;
import java.util.Scanner;

public class Triple {

   private static int currentId = 0;

   private int id;
   public double x, y, z;

   public Triple(double a, double b, double c) {
      currentId++;  id = currentId;
      x = a;
      y = b;
      z = c;
   }


   public Triple( Triple other) {
      currentId++;  id = currentId;
      x = other.x;
      y = other.y;
      z = other.z;
   }

   public Triple(Scanner input) {
      currentId++;  id = currentId;
      x = input.nextDouble();
      y = input.nextDouble();
      z = input.nextDouble();
      input.nextLine();
   }


   public Triple vectorTo( Triple other )
   {
     return new Triple( other.x - x, other.y - y, other.z - z );
   }
 
   public Triple scalarProduct( double s )
   {
     return new Triple( s*x, s*y, s*z );
   }
 
   public double dotProduct( Triple other )
   {
     return x*other.x + y*other.y + z*other.z;
   }
   // return this triple minus the other
   public double dot( Triple other )
   {
     return x*other.x + y*other.y + z*other.z;
   }
   public Triple minus(Triple other) {
      return new Triple(x - other.x, y - other.y, z - other.z);
   }

   // compute dot product of this triple and
   // the given other triple
 
   // compute cross product of this triple and
   // the given other triple
   public Triple cross(Triple other) {
      return new Triple(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
   }

   public Triple crossProduct( Triple other )
   {
     return new Triple( y*other.z - z*other.y,
                        z*other.x - x*other.z,
                        x*other.y - y*other.x );
   }

   // produce a normalized copy of this triple
   public Triple normalize() {
      double length = Math.sqrt(this.dot(this));
      return new Triple(x / length, y / length, z / length);
   }

     // compute the point on the line from this point s of the way
  // along the vector d
  public Triple pointOnLine( double lambda, Triple d )
  {
    return new Triple( x + lambda*d.x, y + lambda*d.y, z + lambda*d.z );
  }


    // compute point lambda of the way from this point to q
    public Triple ofTheWay( double lambda, Triple q )
    {
      return new Triple( x + lambda*(q.x-x),
                         y + lambda*(q.y-y),
                         z + lambda*(q.z-z) );
    }


    public double norm()
    {
      return Math.sqrt( x*x + y*y + z*z );
    }

    public Triple normalized()
    {
      double len = norm();
      return new Triple( x/len, y/len, z/len );
    }

  // make a new triple that is this triple plus v
  public Triple add( Triple v )
  {
    return new Triple( x+v.x, y+v.y, z+v.z );
  }

  // make a new triple that is this triple minus v
  public Triple subtract( Triple v )
  {
    return new Triple( x-v.x, y-v.y, z-v.z );
  }

  // scale this triple 
  public Triple scale( double sx, double sy, double sz ){
    return new Triple( sx*x, sy*y, sz*z );
  }

  public String toString()
  {
    return "<" + x + " " + y + " " + z + ">";
  }

  public static Triple linearComb( double alpha, Triple a,
                                   double beta, Triple b )
  {
    return new Triple( alpha*a.x + beta*b.x, 
                       alpha*a.y + beta*b.y,
                       alpha*a.z + beta*b.z );
  }

  public static Triple linearComb( double alpha, Triple a,
                                   double beta, Triple b,
                                   double gamma, Triple c )
  {
    return new Triple( alpha*a.x + beta*b.x + gamma*c.x, 
                       alpha*a.y + beta*b.y + gamma*c.y,
                       alpha*a.z + beta*b.z + gamma*c.z );
  }

  public static Triple linearComb( double alpha, Triple a,
                                   double beta, Triple b,
                                   double gamma, Triple c,
                                   double delta, Triple d )
  {
    return new Triple( alpha*a.x + beta*b.x + gamma*c.x + delta*d.x, 
                       alpha*a.y + beta*b.y + gamma*c.y + delta*d.y,
                       alpha*a.z + beta*b.z + gamma*c.z + delta*d.z );
  }

  public static double zCoordCrossProduct( Triple a, Triple b )
  {
    return a.x * b.y  - b.x * a.y;
  }

  // since Triple is immutable, makes sense to have "constants"
  // ("final" probably doesn't do anything, since no method can change)
  public final static Triple zero = new Triple(0,0,0);
  public final static Triple xAxis = new Triple(1,0,0);
  public final static Triple yAxis = new Triple(0,1,0);
  public final static Triple zAxis = new Triple(0,0,1);


   // produce homogeneous coords version of this triple
   public Vec4 toVec4() {
      return new Vec4(x, y, z);
   }

   // public String toString() {
   //    return String.format("[%10.4f %10.4f %10.4f]", x, y, z);
   // }

   public final static Triple up = new Triple(0, 0, 1);


   public void sendData(FloatBuffer buff) {
      buff.put((float) x);
      buff.put((float) y);
      buff.put((float) z);
   }
   public static void main(String[] args) {
      Triple e = new Triple(18, 20, 7), a = new Triple(9, 8, 7), b = new Triple(1, 14, 12), c = new Triple(13, 5, 17),
            p1 = new Triple(8, 3, 9), p2 = new Triple(4, 2, 6), p3 = new Triple(3, 6, 10);

      Triple eMinusA = e.minus(a), bMinusA = b.minus(a), cMinusA = c.minus(a), p1MinusE = p1.minus(e),
            p2MinusE = p2.minus(e), p3MinusE = p3.minus(e);

      System.out.println(eMinusA + " " + bMinusA + " " + cMinusA + p1MinusE + " " + p2MinusE + " " + p3MinusE + "\n");

      System.out.printf("%10.4f %10.4f %10.4f %10.4f %10.4f %10.4f\n", eMinusA.dot(eMinusA), eMinusA.dot(bMinusA),
            eMinusA.dot(cMinusA), eMinusA.dot(p1MinusE), eMinusA.dot(p2MinusE), eMinusA.dot(p3MinusE));
      System.out.printf("%10.4f %10.4f %10.4f %10.4f %10.4f %10.4f\n", bMinusA.dot(eMinusA), bMinusA.dot(bMinusA),
            bMinusA.dot(cMinusA), bMinusA.dot(p1MinusE), bMinusA.dot(p2MinusE), bMinusA.dot(p3MinusE));
      System.out.printf("%10.4f %10.4f %10.4f %10.4f %10.4f %10.4f\n", cMinusA.dot(eMinusA), cMinusA.dot(bMinusA),
            cMinusA.dot(cMinusA), cMinusA.dot(p1MinusE), cMinusA.dot(p2MinusE), cMinusA.dot(p3MinusE));

      double lambda, beta, gamma;

      lambda = -eMinusA.dot(eMinusA) / eMinusA.dot(p1MinusE);
      beta = lambda * bMinusA.dot(p1MinusE) / bMinusA.dot(bMinusA);
      gamma = lambda * cMinusA.dot(p1MinusE) / cMinusA.dot(cMinusA);
      System.out.printf("%10.4f %10.4f %10.4f\n", beta, gamma, lambda);

      lambda = -eMinusA.dot(eMinusA) / eMinusA.dot(p2MinusE);
      beta = lambda * bMinusA.dot(p2MinusE) / bMinusA.dot(bMinusA);
      gamma = lambda * cMinusA.dot(p2MinusE) / cMinusA.dot(cMinusA);
      System.out.printf("%10.4f %10.4f %10.4f\n", beta, gamma, lambda);

      lambda = -eMinusA.dot(eMinusA) / eMinusA.dot(p3MinusE);
      beta = lambda * bMinusA.dot(p3MinusE) / bMinusA.dot(bMinusA);
      gamma = lambda * cMinusA.dot(p3MinusE) / cMinusA.dot(cMinusA);
      System.out.printf("%10.4f %10.4f %10.4f\n", beta, gamma, lambda);

   }

}
