/*  a Soups instance holds
    a collection of TriSoup instances,
    one for each texture
*/

import java.util.ArrayList;

public class Soups{

  private TriSoup[] soups;

  // accumulate triangles here
  private ArrayList<Triangle> triangles;

  // build a "soups" object with
  // given number of separate images,
  // create empty soup's for each, 
  // and create empty list of triangles
  public Soups( int numTextures ){
    soups = new TriSoup[ numTextures ];
    for( int k=0; k<soups.length; k++ ){
      soups[k] = new TriSoup();
    }

    triangles = new ArrayList<Triangle>();
  }

  // go through list of bodies and add
  // the triangles for each body to 
  // cumulative list of triangles
  public void add( ArrayList<Block> bodies ){

    // accumulate all the triangles for the bodies
    for( int k=0; k<bodies.size(); k++ ){
      bodies.get(k).draw( triangles );     
    }

    // System.out.println("this soups (" + this + ") has " + triangles.size() + " triangles");
  }

  // add the triangles for given body to 
  // cumulative list of triangles
  public void add( Block body ){
    body.draw( triangles );
  }

  // go through list of Assemblies and add
  // the triangles for each block in each body
  // public void addAll( ArrayList<Assembly> list ) {
  //   for( int k=0; k<list.size(); k++ ) {
  //     add( list.get(k).getBlocks() ); 
  //   }
  // }

  // go through list of triangles and add
  // them to the soups
  public void addTris( ArrayList<Triangle> list ) {
    for( int k=0; k<list.size(); k++ ) {
      triangles.add( list.get(k) );
    }
  }

  // sort triangles into individual soup's
  // for each image
  public void sortByTexture(){
    for( int k=0; k<triangles.size(); k++ ){
      Triangle tri = triangles.get(k);
      soups[ tri.getTexture() ].add( tri );
    }
  }

  // draw all the TriSoup's
  public void draw(){

    // System.out.println("draw the soups " + this );
    // actually draw each soup
    for( int k=0; k<soups.length; k++ ){
      OpenGL.selectTexture( Pic.get(k) );
      // System.out.println("soup for texture # " + k );
      soups[ k ].draw();
    }

  }
  
  // release all the TriSoup's in this soups
  public void cleanup(){
    for( int k=0; k<soups.length; k++ ){
      soups[k].cleanup();
    }
  }

}
