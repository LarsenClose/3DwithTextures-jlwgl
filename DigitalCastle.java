import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*; // just for the key constants
import static org.lwjgl.system.MemoryUtil.*;

import java.io.File;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import javax.swing.JFileChooser;

public class DigitalCastle extends Basic {

  private static final int MAX = 1000;

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Usage:  j DigitalCastle <input file name>");
      System.exit(1);
    }

    DigitalCastle app = new DigitalCastle("Chapter 7", 1000, 500, 30, args[0]);
    app.start();
  } // main

  // instance variables

  private Pic pic;
  private Shader v1, f1;
  private int hp1; // handle for the GLSL program
  private int textureId1;

  private int vao, vbo, vaoHandle, vboPositionHandle, vboTexCoordsHandle; // handle to the vertex array object
  private int vaoHandle1, vboPositionHandle1, vboTexCoordsHandle1, textureHandle;
  private int vaoHandle2, vboPositionHandle2, vboTexCoordsHandle2;
  private int colorLoc;
  private int hp2;

  private ArrayList<Block> blocks;

  private int positionHandle, colorHandle, textHandle;
  private FloatBuffer positionBuffer, colorBuffer, textureBuffer;
  private Camera camera, mapView;

  // construct basic application with given title, pixel width and height
  // of drawing area, and frames per second
  public DigitalCastle(String appTitle, int pw, int ph, int fps, String fileName) {
    super(appTitle, pw, ph, (long) ((1.0 / fps) * 1000000000));

    // pic = new Pic("image", "Pictures/" + greenfire);

    // read camera data and triangle data from data file with given name
    try {

      Pic.init();
      Util.init();

      Scanner input = new Scanner(new File(fileName));

      mapView = new Camera(new Triple(50, 50, 100), 0, -90.00001, 2);
      camera = new Camera(input);

      blocks = new ArrayList<Block>();
      int number = input.nextInt();
      input.nextLine();
      input.nextLine();
      for (int k = 0; k < number; k++) {
        blocks.add(new Block(input));
        input.nextLine();
      }
    } catch (Exception e) {
      System.out.println("Failed to open and load from [" + fileName + "]");
      System.exit(1);
    }
  }

  protected void init() {

    OpenGL.init();
    OpenGL.useRegularProgram();

    // activate all the textures
    for (int k = 0; k < Pic.size(); k++) {
      OpenGL.loadTexture(Pic.get(k));
      System.out.println("activated texture number " + k);
    }

    OpenGL.setBackColor(0, 0, 0);

                    String vertexShaderCode = "#version 330 core\n" + "layout (location = 0 ) in vec3 vertexPosition;\n"
                        + "layout (location = 1 ) in vec2 vertexTexCoord;\n" + "out vec2 vertexTexCoord;\n" + "uniform mat4 frustum;\n"
                        + "uniform mat4 lookAt;\n" + "void main(void)\n" 
                        + "{\n" 
                        + "  texCoord = vertexTexCoord;\n"
                        + "  gl_Position = frustum * lookAt * vec4( vertexPosition, 1.0);\n" + "}\n";

                    System.out.println("Vertex shader:\n" + vertexShaderCode + "\n\n");

                    v1 = new Shader("vertex", vertexShaderCode);

                    String fragmentShaderCode = "#version 330 core\n" 
                        + "in vec2 texCoord;\n"
                        + "layout (location = 0 ) out vec4 fragColor;\n" 
                        + "void main(void)\n" 
                        + "{\n"
                        + "  fragColor = texture( texture, texCoord );\n" 
                        + "}\n";

                    System.out.println("Fragment shader:\n" + fragmentShaderCode + "\n\n");

                    f1 = new Shader("fragment", fragmentShaderCode);

                    hp1 = GL20.glCreateProgram();
                    Util.error("after create program");
                    System.out.println("program handle is " + hp1);

                    GL20.glAttachShader(hp1, v1.getHandle());
                    Util.error("after attach vertex shader to program");

                    GL20.glAttachShader(hp1, f1.getHandle());
                    Util.error("after attach fragment shader to program");

                    GL20.glLinkProgram(hp1);
                    Util.error("after link program");

                    // GL20.glUseProgram(hp1);
                    // Util.error("after use program");

                    // create vertex buffer objects and their handles one at a time
                    positionHandle = GL15.glGenBuffers();
                    textureHandle = GL15.glGenBuffers();
                    System.out.println("have position handle " + positionHandle + " and texture handle " + textureHandle);

                    // create the buffers (data doesn't matter so much, just the size)
                    positionBuffer = Util.createFloatBuffer(MAX * 3 * 3);
                    textureBuffer = Util.createFloatBuffer(MAX * 2 * 1);

                    // set the background color
                    GL11.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glClearDepth(1.0f);
                    GL11.glDepthFunc(GL11.GL_LESS);








    for (int k = 0; k < Pic.size(); k++) {
      OpenGL.selectTexture(Pic.get(k));
      System.out.println("selected texture number " + k);
    }

  }

  protected void processInputs() {
    // process all waiting input events
    while (InputInfo.size() > 0) {
      InputInfo info = InputInfo.get();

      if (info.kind == 'k' && (info.action == GLFW_PRESS || info.action == GLFW_REPEAT)) {
        int code = info.code;
        boolean debug = false;

        if (code == GLFW_KEY_A) { // look left
          camera.turn(3);
          if (debug)
            mapView.turn(3);
        } else if (code == GLFW_KEY_D) { // look right
          camera.turn(-3);
          if (debug)
            mapView.turn(-3);
        } else if (code == GLFW_KEY_Q) { // pan vision down
          camera.tilt(-3);
          if (debug)
            mapView.tilt(-3);
        } else if (code == GLFW_KEY_E) { // pan vision up
          camera.tilt(3);
          if (debug)
            mapView.tilt(3);
        } else if (code == GLFW_KEY_LEFT) { // strafe left relative to body orientation
          camera.move(-1, 0, 0);
          if (debug)
            mapView.move(-1, 0, 0);
        } else if (code == GLFW_KEY_RIGHT) { // strafe left relative to body orientation
          camera.move(1, 0, 0);
          if (debug)
            mapView.move(1, 0, 0);
        } else if (code == GLFW_KEY_UP) { // forward
          camera.move(0, 1, 0);
          if (debug)
            mapView.move(0, 1, 0);
        } else if (code == GLFW_KEY_DOWN) { // backward
          camera.move(0, -1, 0);
          if (debug)
            mapView.move(0, -1, 0);
        } else if (code == GLFW_KEY_W) { // up
          camera.move(0, 0, 1);
          if (debug)
            mapView.move(0, 0, 1);
        } else if (code == GLFW_KEY_S) { // down
          camera.move(0, 0, -1);
          if (debug)
            mapView.move(0, 0, -1);
        } else if (code == GLFW_KEY_R) { // down

        }
      } // input event is a key
      else if (info.kind == 'm') { // mouse moved
        // System.out.println( info );
      } else if (info.kind == 'b') { // button action
        System.out.println(info);
        System.out.println(" updates info ");
        camera.update(hp1);
        camera.info();

        mapView.mapUpdate(hp1);
        mapView.info();
      }
    } // loop to process all input events
  }

  // hide retina display issue from ourselves
  private void setViewport(int left, int bottom, int width, int height) {
    // Note: the Util.retinaDisplay constant adjusts for
    // whether have Mac retina display (double pixels, I guess)
    // or not
    GL11.glViewport(Util.retinaDisplay * left, Util.retinaDisplay * bottom, Util.retinaDisplay * width,
        Util.retinaDisplay * height);
  }

  protected void display() {
    super.display(); // just clears the color and depth buffers


    sendData();
    map();
    update();
    // permSoups.draw();
  }

  protected void update() {
    camera.update(hp1); // updates and sends frustum and lookAt

    setViewport(0, 0, 500, 500);
    GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, Block.getNumVerts(blocks));
    Util.error("after draw arrays");

  }

  protected void map() {
    mapView.mapUpdate(hp1);

    setViewport(500, 0, 500, 500);
    GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, Block.getNumVerts(blocks));
    Util.error("after draw arrays");
  }


  private void sendData() {

    // delete previous handle and binding
    // before doing a new one
    if (vaoHandle != -1) {
      GL30.glBindVertexArray(0);
      GL30.glDeleteVertexArrays(vaoHandle);
    }

      // using convenience form that produces one vertex array handle
      vaoHandle = GL30.glGenVertexArrays();
      Util.error("after generate vaoHandle vertex array");
      GL30.glBindVertexArray(vaoHandle);
      Util.error("after bind the vaoHandle");
      // System.out.println("vao is " + vao );

      // connect data to the VBO's
      vboPositionHandle = GL15.glGenBuffers();
      Util.error("after generate vboPositionHandle buffer handle");
      GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vboPositionHandle );
      Util.error("after bind vboPositionHandle");


    positionBuffer.rewind();
    // colorBuffer.rewind();
    textureBuffer.rewind();

    for (int k = 0; k < blocks.size(); k++) {
      blocks.get(k).sendData(positionBuffer, textureBuffer);
    }
    positionBuffer.rewind();
    textureBuffer.rewind();

    // // connect data to the VBO's
    // vboPositionHandle = Util.setupVBO(positionBuffer, 0, 3);
    // vboTexCoordsHandle = Util.setupVBO(textureBuffer, 1, 2);

        // now connect the buffers
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionHandle);
        Util.error("after bind positionHandle");
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
        Util.error("after set position data");
  
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureHandle);
        Util.error("after bind textureHandle");
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureHandle, GL15.GL_STATIC_DRAW);
        Util.error("after set texture data");
  
        // enable the vertex array attributes
        GL20.glEnableVertexAttribArray(0); // position
        Util.error("after enable attrib 0");
        GL20.glEnableVertexAttribArray(1); // color
        Util.error("after enable attrib 1");
  
        // map index 0 to the position buffer index 1 to the color buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionHandle);
        Util.error("after bind position buffer");
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        Util.error("after do position vertex attrib pointer");
  
        // map index 1 to the color buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureHandle);
        Util.error("after bind color buffer");
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
        Util.error("after do color vertex attrib pointer");
  

  }

  // given an array with data in it and an allocated buffer,
  // overwrite buffer contents with array data
  private void sendArrayToBuffer(float[] array, FloatBuffer buffer) {
    buffer.rewind();
    for (int k = 0; k < array.length; k++) {
      buffer.put(array[k]);
    }

  } // sendArrayToBuffer
} // DigitalCastle
