import java.util.Scanner;
import java.nio.FloatBuffer;

public class Mat4 {
   private double[][] a;

   private double[][] data;

   public Mat4(double a11, double a12, double a13, double a14, double a21, double a22, double a23, double a24,
         double a31, double a32, double a33, double a34, double a41, double a42, double a43, double a44) {
      a = new double[4][4];
      a[0][0] = a11;
      a[0][1] = a12;
      a[0][2] = a13;
      a[0][3] = a14;
      a[1][0] = a21;
      a[1][1] = a22;
      a[1][2] = a23;
      a[1][3] = a24;
      a[2][0] = a31;
      a[2][1] = a32;
      a[2][2] = a33;
      a[2][3] = a34;
      a[3][0] = a41;
      a[3][1] = a42;
      a[3][2] = a43;
      a[3][3] = a44;
   }

   public  Mat4(double[][] temp) {
      a = new double[4][4];
      for (int r = 0; r < 4; r++)
         for (int c = 0; c < 4; c++) {
            a[r][c] = temp[r][c];
         }
   }

   public  Mat4(Scanner input) {
      data = new double[4][4];
      for (int r = 0; r < 4; r++)
         for (int c = 0; c < 4; c++) {
            data[r][c] = input.nextDouble();
         }
   }

   public static Mat4 identity() {
      return new Mat4(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
   }

   public static Mat4 translate(double a, double b, double c) {
      return new Mat4(1, 0, 0, a, 0, 1, 0, b, 0, 0, 1, c, 0, 0, 0, 1);
   }

   public static Mat4 scale(double a, double b, double c) {
      return new Mat4(a, 0, 0, 0, 0, b, 0, 0, 0, 0, c, 0, 0, 0, 0, 1);
   }

   public static Mat4 rotate(double theta, double x, double y, double z) {
      // normalize [x,y,z]
      double len = Math.sqrt(x * x + y * y + z * z);
      x /= len;
      y /= len;
      z /= len;

      double c = Math.cos(Math.toRadians(theta));
      double s = Math.sin(Math.toRadians(theta));

      return new Mat4(x * x * (1 - c) + c, x * y * (1 - c) - z * s, x * z * (1 - c) + y * s, 0, y * x * (1 - c) + z * s,
            y * y * (1 - c) + c, y * z * (1 - c) - x * s, 0, z * x * (1 - c) - y * s, z * y * (1 - c) + x * s,
            z * z * (1 - c) + c, 0, 0, 0, 0, 1);
   }

   public static Mat4 frustum(double l, double r, double b, double t, double n, double f) {
      return new Mat4((2 * n) / (r - l), 0, (r + l) / (r - l), 0, 0, (2 * n) / (t - b), (t + b) / (t - b), 0, 0, 0,
            -(f + n) / (f - n), -(2 * f * n) / (f - n), 0, 0, -1, 0);
   }

   public static Mat4 parralelProj(double l, double r, double b, double t, double n, double f) {
      return new Mat4(2 / (r - l), 0, 0, -(r + l) / (r - l), 0, 2 / (t - b), 0, -(t + b) / (t - b), 0, 0, -2 / (f - n),
            -(f + n) / (f - n), 0, 0, 0, 1);
   }

   public static Mat4 ortho(double l, double r, double b, double t, double n, double f) {
      return new Mat4(2 / (r - l), 0, 0, -(r + l) / (r - l), 0, 2 / (t - b), 0, -(t + b) / (t - b), 0, 0, -2 / (f - n),
            -(f + n) / (f - n), 0, 0, 0, 1);
   }

   public static Mat4 lookAt(Triple e, Triple c, Triple up) {
      Triple n = c.minus(e);
      n = n.normalize();
      Triple r = n.cross(up);
      r = r.normalize();
      Triple w = r.cross(n);
      w = w.normalize();

      Mat4 trans = Mat4.translate(-e.x, -e.y, -e.z);
      Mat4 rotate = new Mat4(r.x, r.y, r.z, 0, w.x, w.y, w.z, 0, -n.x, -n.y, -n.z, 0, 0, 0, 0, 1);

      Mat4 lookAt = rotate.mult(trans);

      return lookAt;
   }

   public static Mat4 lookAt(double eyex, double eyey, double eyez, double cx, double cy, double cz, double ux,
         double uy, double uz) {
      Triple e = new Triple(eyex, eyey, eyez);
      Triple c = new Triple(cx, cy, cz);
      Triple u = new Triple(ux, uy, uz);

      Triple n = c.subtract(e);
      Triple r = n.crossProduct(u);
      Triple w = r.crossProduct(n);
      n = n.normalized();
      r = r.normalized();
      w = w.normalized();

      Mat4 translate = new Mat4(1, 0, 0, -e.x, 0, 1, 0, -e.y, 0, 0, 1, -e.z, 0, 0, 0, 1);
      Mat4 rotate = new Mat4(r.x, r.y, r.z, 0, w.x, w.y, w.z, 0, -n.x, -n.y, -n.z, 0, 0, 0, 0, 1);
      Mat4 lookAt = rotate.mult(translate);
      return lookAt;
   }

   public Mat4 mult( Mat4 m ){
      double[][] temp = new double[4][4];
      for( int r=0; r<4; r++ )
        for( int c=0; c<4; c++ ){
          // form temp[r][c]
          temp[r][c] = 0;
          for( int k=0; k<4; k++ )
            temp[r][c] += a[r][k] * m.a[k][c];
        }
      return new Mat4( temp );    
    }
    


   public Vec4 mult(Vec4 p) {
      Vec4 q = new Vec4();
      for (int r = 0; r < 4; r++) {
         q.data[r] = 0;
         for (int c = 0; c < 4; c++) {
            q.data[r] += data[r][c] * p.data[c];
         }
      }
      return q;
   }

     // multiply 3 vector v
  // by upper 3 by 3 portion of this matrix
  // (only works for some matrices, including
  //  rotation matrices)
  public Triple mult( Triple v ) {
   return new Triple( a[0][0]*v.x + a[0][1]*v.y + a[0][2]*v.z,
                      a[1][0]*v.x + a[1][1]*v.y + a[1][2]*v.z,
                      a[2][0]*v.x + a[2][1]*v.y + a[2][2]*v.z );
}

// multiply 3 vector v as [x,y,z]
// by upper 3 by 3 portion of this matrix
// (only works for some matrices, including
//  rotation matrices)
public Triple mult( double x, double y, double z ) {
   return new Triple( a[0][0]*x + a[0][1]*y + a[0][2]*z,
                      a[1][0]*x + a[1][1]*y + a[1][2]*z,
                      a[2][0]*x + a[2][1]*y + a[2][2]*z );
}


   // fill buffer with components of this matrix
   // in ROW major order (row by row, rather than
   // column by column), then transpose when
   // do glUniform
   public void toBuffer(FloatBuffer buffer) {
      buffer.rewind();
      for (int r = 0; r < 4; r++) {
         for (int c = 0; c < 4; c++) {
            buffer.put((float) data[r][c]);
         }
      }
      buffer.rewind();
   }



   public String toString(){
      String s = "\n";
      for( int r=0; r<4; r++ ){
        for( int c=0; c<4; c++ ){
          s += Util.nice( a[r][c], 12, 5 );
        }
        s += "\n";
      }
      return s;
    }



     // convert this matrix a into handy column major
  // float[] and then make it a FloatBuffer
  public FloatBuffer toBuffer(){
   float[] fa = new float[16];

   int index = 0;

   for( int c=0; c<4; c++ )
     for( int r=0; r<4; r++ )
     {
       fa[index] = (float) a[r][c];
       index++;
     }

   return Util.arrayToBuffer( fa );
 }

   // public String toString() {
   //    String s = "";
   //    for (int r = 0; r < 4; r++) {
   //       for (int c = 0; c < 4; c++) {
   //          s += String.format("%12.5f", data[r][c]);
   //       }
   //       s += "\n";
   //    }
   //    return s;
   // }

   public static void main(String[] args) {
      Mat4 persp = Mat4.frustum(-3, 3, -2, 2, 1, 100);
      System.out.println(persp);

      Vec4 lbn = new Vec4(-3, -2, -1), rtf = new Vec4(300, 200, -100);
      Vec4 p = persp.mult(lbn);
      System.out.println("frustum * lbn = " + p);

      p = persp.mult(rtf);
      System.out.println("frustum * rtf = " + p);
      p = p.perspDiv();
      System.out.println("after persp div = " + p);

   }

}
