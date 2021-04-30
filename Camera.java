/*
   camera to set frustum and lookAt
   matrices
*/

import java.util.Scanner;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.*;

public class Camera {

   private Triple e, c, eN; 
   private double azi, aziN;  // rotation of camera in x-y plane
   private double alt, altN;  // rotation of camera from x-y plane
   private double near, nearN;  // distance from e to c

   private Mat4 frustum, lookAt;
   private int frustumLoc, lookAtLoc;  // handles to uniform variables
   private FloatBuffer frustumBuffer, lookAtBuffer;

   public Camera( Triple eye, double azimuth, double altitude, double dist ) {
      createBuffers();
      compute( eye, azimuth, altitude, dist );
   }

   public Camera( Scanner input ) {
      createBuffers();

      Triple eye = new Triple( input );
      double azimuth = input.nextDouble();
      double altitude = input.nextDouble();
      double dist = input.nextDouble();
      input.nextLine();
      input.nextLine();

      compute( eye, azimuth, altitude, dist );
   }

   private void createBuffers() {
      frustumBuffer = Util.createFloatBuffer( 16 );
      lookAtBuffer = Util.createFloatBuffer( 16 );
   }

   private void compute( Triple eye, double azimuth, double altitude, double dist ) {
      e = eye;         // "e" for lookAt matrix
      azi = azimuth;   // use to compute c
      alt = altitude;
      near = dist;        // n for frustum matrix

      double alpha = Math.toRadians( azi );
      double beta = Math.toRadians( alt );

      double cosBeta = Math.cos( beta );
      double sinBeta = Math.sin( beta );
      double cosAlpha = Math.cos( alpha );
      double sinAlpha = Math.sin( alpha );

       
      // "c" for lookAt matrix
      c = new Triple( e.x + cosBeta * near * cosAlpha, 
                      e.y + cosBeta * near * sinAlpha,
                      e.z + near * sinBeta 
                    );

   }

   public void turn( double dazi ) {
      azi += dazi;
      compute( e, azi, alt, near );
   }

   public void tilt( double dalt ) {
      alt += dalt;
      compute( e, azi, alt, near );
   }

   public void zoom( double v ) {
      near += v;
      compute( e, azi, alt, near );
   }

   public void move( double dx, double dy, double dz ) {
      e = new Triple( e.x + dx, e.y + dy, e.z + dz );
      compute( e, azi, alt, near );
   }

   public void mapUpdate( int hp1 ) {
      // eN = new Triple( 50, 50, 250 );
      // aziN = 90;
      // altN = -89;
      // nearN = 1;

      // compute( eN, aziN, altN, nearN );
      // Camera.update( hp1);
      // gluLookAt(0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

            // set up handles to uniform variables
            frustumLoc = GL20.glGetUniformLocation( hp1, "frustum" );
            lookAtLoc = GL20.glGetUniformLocation( hp1, "lookAt" );
      
            // create the matrices
            frustum = Mat4.frustum( -1, 1, -1, 1, near, 1000*near );
            lookAt = Mat4.lookAt( e, c, Triple.up );
      
            // create buffer versions of the matrices
            frustum.toBuffer( frustumBuffer );
            lookAt.toBuffer( lookAtBuffer );
      
            // sends data for uniforms to GPU
            GL20.glUniformMatrix4fv( frustumLoc, true, frustumBuffer );
            GL20.glUniformMatrix4fv( lookAtLoc, true, lookAtBuffer );
   }

   public void info(  ) {
       System.out.println("e is " + e + "azi is " + azi + "alt is " + alt + "near is " + near); 
   }


   // given camera data azi, alt, d, e, c
   // update frustum and lookAt and send uniform
   // variable data to GPU
   public void update( int hp1 ) {

      // set up handles to uniform variables
      frustumLoc = GL20.glGetUniformLocation( hp1, "frustum" );
      lookAtLoc = GL20.glGetUniformLocation( hp1, "lookAt" );

      // create the matrices
      frustum = Mat4.frustum( -1, 1, -1, 1, near, 1000*near );
      lookAt = Mat4.lookAt( e, c, Triple.up );

      // create buffer versions of the matrices
      frustum.toBuffer( frustumBuffer );
      lookAt.toBuffer( lookAtBuffer );

      // sends data for uniforms to GPU
      GL20.glUniformMatrix4fv( frustumLoc, true, frustumBuffer );
      GL20.glUniformMatrix4fv( lookAtLoc, true, lookAtBuffer );

   }

}
