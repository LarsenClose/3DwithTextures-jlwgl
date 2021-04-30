import java.util.*;
import java.nio.FloatBuffer;


public class Block {

   // return total number of vertices in all the triangles
   // in the list of blocks
   public static int getNumVerts(ArrayList<Block> list) {
      int count = 0;
      for (int k = 0; k < list.size(); k++) {
         count += list.get(k).numVerts();
      }
      return count;
   }

   // instance fields
   private String kind;
   private Triple[] verts; // all model vertices of the triangles
   private int[][] tris; // indices into verts of each triangle
   public ArrayList<Mat4> matrices;


   // transformations:
   Mat4 scale, scaleHalf, rotate, translate, translateOne, translateTwo, translateTre, translateFour, translateFive;

   public Block(Scanner input) {

      kind = input.next();
      input.nextLine();

      if (kind.equals("groundBox") || kind.equals("clownBox") || kind.equals("groundBoxed")
            || kind.equals("sierpinskiBox") || kind.equals("pyraBox")) {
         if (kind.equals("pyraBox") || kind.equals("sierpinskiBox")) {
            // build the model vertices
            verts = new Triple[5];
            // x y z <=> 4 2 1
            verts[0] = new Triple(-1, -1, 0);
            verts[1] = new Triple(-1, 1, 0);
            verts[2] = new Triple(1, -1, 0);
            verts[3] = new Triple(1, 1, 0);
            verts[4] = new Triple(0, 0, 1);

            // build the triangles
            tris = new int[][] { { 0, 1, 2 }, { 2, 3, 1 }, // bottom
                  { 0, 1, 4 }, { 0, 2, 4 }, { 1, 3, 4 }, { 3, 2, 4 }, { 2, 3, 4 },

            };
         } // pyramid


         else if (kind.equals("groundBox") || kind.equals("clownBox")) {

            // build the model vertices
            verts = new Triple[8];
            // x y z <=> 4 2 1
            verts[0] = new Triple(-1, -1, -1);
            verts[1] = new Triple(-1, -1, 1);
            verts[2] = new Triple(-1, 1, -1);
            verts[3] = new Triple(-1, 1, 1);
            verts[4] = new Triple(1, -1, -1);
            verts[5] = new Triple(1, -1, 1);
            verts[6] = new Triple(1, 1, -1);
            verts[7] = new Triple(1, 1, 1);

            // build the triangles
            tris = new int[][] { { 0, 4, 5 }, { 0, 5, 1 }, // front face
                  { 4, 6, 7 }, { 4, 7, 5 }, // right
                  { 2, 3, 7 }, { 2, 7, 6 }, // back
                  { 2, 0, 1 }, { 2, 1, 3 }, // left
                  { 2, 6, 4 }, { 2, 4, 0 }, // bottom
                  { 1, 5, 7 }, { 1, 7, 3 } }; // top

         } // box
      }

      else {// unknown
         System.out.println("Unknown kind of block");
         System.exit(1);
      }

      // get transformation data and build matrices
      double sx = input.nextDouble(), sy = input.nextDouble(), sz = input.nextDouble();
      input.nextLine();
      scale = Mat4.scale(sx, sy, sz);

      double theta = input.nextDouble(), ax = input.nextDouble(), ay = input.nextDouble(), az = input.nextDouble();
      input.nextLine();
      rotate = Mat4.rotate(theta, ax, ay, az);

      double tx = input.nextDouble(), ty = input.nextDouble(), tz = input.nextDouble();
      input.nextLine();
      translate = Mat4.translate(tx, ty, tz);
            


   }



   // send the position and color data for all the
   // vertices in all the triangles
   public void sendData(FloatBuffer positionBuffer, FloatBuffer colorBuffer) {
      Mat4 matrix = translate.mult(rotate.mult(scale));
      

      for (int k = 0; k < tris.length; k++) {
         for (int j = 0; j < 3; j++) {
            Vec4 v = matrix.mult(verts[tris[k][j]].toVec4());
            v.sendData(positionBuffer);
            if (kind.equals("clownBox")) {
               Colors.sendData(k, colorBuffer);
            } else if (kind.equals("pyraBox"))  {
               Colors.sendData(k + 12, colorBuffer);
            } else if (kind.equals("sierpinskiBox"))  {
               Colors.sendData(k + 12, colorBuffer);
            } else if (kind.equals("groundBox")) {
               Colors.sendData(19, colorBuffer);
            }
         }
      }
   }
      


   public int numVerts() {
      return tris.length * 3;
   }

}
