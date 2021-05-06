import java.util.Scanner;
import java.util.ArrayList;

import java.nio.FloatBuffer;


public class Block {


  private static int nextId = 0;
  private int id;


      // return total number of vertices in all the triangles
   // in the list of blocks
   public static int getNumVerts( ArrayList<Block> list ) {
      int count = 0;
      for (int k=0; k<list.size(); k++) {
         count += list.get(k).numVerts();
      }
      return count;
   }

   public int numVerts() {
      return tris.length * 3;
   }

   // instance fields
   private String kind;
   private Vertex[] verts;  // all model vertices of the triangles
   private int[][] tris;    // indices into verts of each triangle
   public ArrayList<Mat4> matrices;

  // texture info for the 6 faces
  protected int[] textures;  // texture number for each face in standard order
                           // front, right, back, left, top, bottom
  protected double[] texScales;  // each kind of block has its own texture
                               // scaling






protected double cx, cy, cz;  //  current center point of the block
  protected double sx, sy, sz;  // current size of block

  // june25:  remember how oriented, changing so cursor is at reference point
  protected int ori;  // is one of 0 (+x) 90 (+y) 180 (-x) 270 (-y)
  protected double refX, refY, refZ;  // keep reference point updated to match center

  protected Vertex vel;  // current translational velocity for this block
  // other physics stuff will be added later:



   // universal attributes
   protected boolean supported;

   // transformations:
   Mat4 scale, scaleHalf, rotate, translate, translateOne, translateTwo, translateTre, translateFour, translateFive;

   public Block(Scanner input) {

      kind = input.next();
      input.nextLine();



      if (kind.equals("groundBox") || kind.equals("clownBox") || kind.equals("groundBoxed")
            || kind.equals("sierpinskiBox") || kind.equals("pyraBox")) {
         if (kind.equals("pyraBox") || kind.equals("sierpinskiBox")) {
            // build the model vertices
            verts = new Vertex[5];
            // x y z <=> 4 2 1
            verts[0] = new Vertex(-1, -1, 00, 0);
            verts[1] = new Vertex(-1, 1, 0, 0, 0);
            verts[2] = new Vertex(1, -1, 0, 0, 0);
            verts[3] = new Vertex(1, 1, 0, 0, 0);
            verts[4] = new Vertex(0, 0, 1, 0, 0);

            // build the triangles
            tris = new int[][] { { 0, 1, 2 }, { 2, 3, 1 }, // bottom
                  { 0, 1, 4 }, { 0, 2, 4 }, { 1, 3, 4 }, { 3, 2, 4 }, { 2, 3, 4 },

            };
         } // pyramid

         else if (kind.equals("groundBox") || kind.equals("clownBox")) {

            // build the model vertices
            verts = new Vertex[8];
            // x y z <=> 4 2 1
            verts[0] = new Vertex(-1, -1, -1, 0, 0);
            verts[1] = new Vertex(-1, -1, 1, 0, 0);
            verts[2] = new Vertex(-1, 1, -1, 0, 0);
            verts[3] = new Vertex(-1, 1, 1, 0, 0);
            verts[4] = new Vertex(1, -1, -1, 0, 0);
            verts[5] = new Vertex(1, -1, 1, 0, 0);
            verts[6] = new Vertex(1, 1, -1, 0, 0);
            verts[7] = new Vertex(1, 1, 1, 0, 0);

            // build the triangles
            tris = new int[][] { { 0, 4, 5 }, { 0, 5, 1 }, // front face
                  { 4, 6, 7 }, { 4, 7, 5 }, // right
                  { 2, 3, 7 }, { 2, 7, 6 }, // back
                  { 2, 0, 1 }, { 2, 1, 3 }, // left
                  { 2, 6, 4 }, { 2, 4, 0 }, // bottom
                  { 1, 5, 7 }, { 1, 7, 3 } }; // top

         } // clown & ground box
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
   public void sendData(FloatBuffer positionBuffer, FloatBuffer textBuffer) {
      Mat4 matrix = translate.mult(rotate.mult(scale));

      for (int k = 0; k < tris.length; k++) {
         for (int j = 0; j < 3; j++) {
            Vec4 v = matrix.mult(verts[tris[k][j]].toVec4());
            v.positionToBuffer(positionBuffer);
            v.texCoordsToBuffer(textBuffer);
            if (kind.equals("clownBox")) {

               v.positionToBuffer(positionBuffer);
               v.texCoordsToBuffer(textBuffer);
            } else if (kind.equals("pyraBox")) {
               v.positionToBuffer(positionBuffer);
               v.texCoordsToBuffer(textBuffer);
 
            } else if (kind.equals("sierpinskiBox")) {

               v.positionToBuffer(positionBuffer);
               v.texCoordsToBuffer(textBuffer);
            } else if (kind.equals("groundBox")) {
               v.positionToBuffer(positionBuffer);
               v.texCoordsToBuffer(textBuffer);
            }
         }
      }
   }

   // public void draw(ArrayList<Triangle> list) {

   //    Vertex v1, v2, v3; // convenience

   //    // front face (index 0) --------------------

   //    v1 = new Vertex(cx - sx, cy - sy, cz - sz, 0, 0);
   //    v2 = new Vertex(cx + sx, cy - sy, cz - sz, 2 * sx / texScales[0], 0);
   //    v3 = new Vertex(cx + sx, cy - sy, cz + sz, 2 * sx / texScales[0], 2 * sz / texScales[0]);
   //    list.add(new Triangle(v1, v2, v3, textures[0]));

   //    v1 = new Vertex(cx - sx, cy - sy, cz - sz, 0, 0);
   //    v2 = new Vertex(cx + sx, cy - sy, cz + sz, 2 * sx / texScales[0], 2 * sz / texScales[0]);
   //    v3 = new Vertex(cx - sx, cy - sy, cz + sz, 0, 2 * sz / texScales[0]);
   //    list.add(new Triangle(v1, v2, v3, textures[0]));

   //    // right face (index 1) --------------------

   //    v1 = new Vertex(cx + sx, cy - sy, cz - sz, 0, 0);
   //    v2 = new Vertex(cx + sx, cy + sy, cz - sz, 2 * sy / texScales[1], 0);
   //    v3 = new Vertex(cx + sx, cy + sy, cz + sz, 2 * sy / texScales[1], 2 * sz / texScales[1]);
   //    list.add(new Triangle(v1, v2, v3, textures[1]));

   //    v1 = new Vertex(cx + sx, cy - sy, cz - sz, 0, 0);
   //    v2 = new Vertex(cx + sx, cy + sy, cz + sz, 2 * sy / texScales[1], 2 * sz / texScales[1]);
   //    v3 = new Vertex(cx + sx, cy - sy, cz + sz, 0, 2 * sz / texScales[1]);
   //    list.add(new Triangle(v1, v2, v3, textures[1]));

   //    // back face (index 2) --------------------

   //    v1 = new Vertex(cx + sx, cy + sy, cz - sz, 0, 0);
   //    v2 = new Vertex(cx - sx, cy + sy, cz - sz, 2 * sx / texScales[2], 0);
   //    v3 = new Vertex(cx - sx, cy + sy, cz + sz, 2 * sx / texScales[2], 2 * sz / texScales[2]);
   //    list.add(new Triangle(v1, v2, v3, textures[2]));

   //    v1 = new Vertex(cx + sx, cy + sy, cz - sz, 0, 0);
   //    v2 = new Vertex(cx - sx, cy + sy, cz + sz, 2 * sx / texScales[2], 2 * sz / texScales[2]);
   //    v3 = new Vertex(cx + sx, cy + sy, cz + sz, 0, 2 * sz / texScales[2]);
   //    list.add(new Triangle(v1, v2, v3, textures[2]));

   //    // left face (index 3) --------------------

   //    v1 = new Vertex(cx - sx, cy + sy, cz - sz, 0, 0);
   //    v2 = new Vertex(cx - sx, cy - sy, cz - sz, 2 * sy / texScales[3], 0);
   //    v3 = new Vertex(cx - sx, cy - sy   public int numVerts() {
   //       return tris.length * 3;
   //    }
   //    // top face (index 4) --------------------

   //    v1 = new Vertex(cx - sx, cy - sy, cz + sz, 0, 0);
   //    v2 = new Vertex(cx + sx, cy - sy, cz + sz, 2 * sx / texScales[4], 0);
   //    v3 = new Vertex(cx + sx, cy + sy, cz + sz, 2 * sx / texScales[4], 2 * sy / texScales[4]);
   //    list.add(new Triangle(v1, v2, v3, textures[4]));

   //    v1 = new Vertex(cx - sx, cy - sy, cz + sz, 0, 0);
   //    v2 = new Vertex(cx + sx, cy + sy, cz + sz, 2 * sx / texScales[4], 2 * sy / texScales[4]);
   //    v3 = new Vertex(cx - sx, cy + sy, cz + sz, 0, 2 * sy / texScales[4]);
   //    list.add(new Triangle(v1, v2, v3, textures[4]));

   //    // // bottom face (index 5) --------------------

   //    // v1 = new Vertex(cx - sx, cy + sy, cz - sz, 0, 0);
   //    // v2 = new Vertex(cx + sx, cy + sy, cz - sz, 2 * sx / texScales[5], 0);
   //    // v3 = new Vertex(cx + sx, cy - sy, cz - sz, 2 * sx / texScales[5], 2 * sy / texScales[5]);
   //    // list.add(new Triangle(v1, v2, v3, textures[5]));

   //    // v1 = new Vertex(cx - sx, cy + sy, cz - sz, 0, 0);
   //    // v2 = new Vertex(cx + sx, cy - sy, cz - sz, 2 * sx / texScales[5], 2 * sy / texScales[5]);
   //    // v3 = new Vertex(cx - sx, cy - sy, cz - sz, 0, 2 * sy / texScales[5]);
   //    // list.add(new Triangle(v1, v2, v3, textures[5]));
   // }

}

