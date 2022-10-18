/*
 * Renderer 2. The MIT License.
 * Copyright (c) 2022 rlkraft@pnw.edu
 * See LICENSE for details.
*/

import fractals.*;
import renderer.scene.*;
import renderer.scene.util.PointCloud;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

/**
   You do not need to modify this file.
*/
public class InteractiveModelsAll implements KeyListener, ComponentListener
{
   private final FrameBufferPanel fbp; // The event handlers need
   private final Scene scene;          // access to these fields.
   private final List<Model> modelArray = new ArrayList<>();
   private int currentModel = 0;
   private double xPosition = 0.0;
   private double yPosition = 0.0;
   private double zPosition = -1.0;
   private boolean displayTransformations = false;
   private Model savedModel = null; // used to hold a PointCloud model
   private int pointSize = 0;       // used by the point clouds

   private boolean takeScreenshot = false;
   private int screenshotNumber = 0;

   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
      Then this constructor instantiates the GUI.
   */
   public InteractiveModelsAll()
   {
      // Create the Scene object that we shall render
      scene = new Scene();

      modelArray.add(new Canopy(30, 0));
      modelArray.add(new Canopy(31, 1));
      modelArray.add(new Canopy(32, 2));
      modelArray.add(new Canopy(33, 3));
      modelArray.add(new Canopy(34, 4));
      modelArray.add(new Canopy(35, 5));
      modelArray.add(new Canopy(36, 6));
      modelArray.add(new Canopy(37, 7));
      modelArray.add(new Canopy(38, 8));
      modelArray.add(new Canopy(39, 9));
      modelArray.add(new Canopy(40, 10));
      modelArray.add(new Canopy(41, 11));
      modelArray.add(new Canopy(42, 12));
      modelArray.add(new Canopy(43, 13));
      modelArray.add(new Canopy(44, 14));
      modelArray.add(new Canopy(45, 15));
      modelArray.add(new Canopy(46, 16));

      modelArray.add(new KochCurve(0));
      modelArray.add(new KochCurve(1));
      modelArray.add(new KochCurve(2));
      modelArray.add(new KochCurve(3));
      modelArray.add(new KochCurve(4));
      modelArray.add(new KochCurve(5));
      modelArray.add(new KochCurve(6));
      modelArray.add(new KochCurve(7));

      modelArray.add(new H_Tree(0));
      modelArray.add(new H_Tree(1));
      modelArray.add(new H_Tree(2));
      modelArray.add(new H_Tree(3));
      modelArray.add(new H_Tree(4));
      modelArray.add(new H_Tree(5));
      modelArray.add(new H_Tree(6));
      modelArray.add(new H_Tree(7));
      modelArray.add(new H_Tree(8));
      modelArray.add(new H_Tree(9));
      modelArray.add(new H_Tree(10));
      modelArray.add(new H_Tree(11));
      modelArray.add(new H_Tree(12));
      modelArray.add(new H_Tree(13));
      modelArray.add(new H_Tree(14));
      modelArray.add(new H_Tree(15));
      modelArray.add(new H_Tree(16));

      modelArray.add(new SierpinskiTriangle(0));
      modelArray.add(new SierpinskiTriangle(1));
      modelArray.add(new SierpinskiTriangle(2));
      modelArray.add(new SierpinskiTriangle(3));
      modelArray.add(new SierpinskiTriangle(4));
      modelArray.add(new SierpinskiTriangle(5));
      modelArray.add(new SierpinskiTriangle(6));
      modelArray.add(new SierpinskiTriangle(7));
      modelArray.add(new SierpinskiTriangle(8));
      modelArray.add(new SierpinskiTriangle(9));
      modelArray.add(new SierpinskiTriangle(10));

      modelArray.add(new BoxFractal(0));
      modelArray.add(new BoxFractal(1));
      modelArray.add(new BoxFractal(2));
      modelArray.add(new BoxFractal(3));
      modelArray.add(new BoxFractal(4));
      modelArray.add(new BoxFractal(5));
      modelArray.add(new BoxFractal(6));
      modelArray.add(new BoxFractal(7));
      modelArray.add(new BoxFractal(8));

      modelArray.add(new C_Curve(0));
      modelArray.add(new C_Curve(1));
      modelArray.add(new C_Curve(2));
      modelArray.add(new C_Curve(3));
      modelArray.add(new C_Curve(4));
      modelArray.add(new C_Curve(5));
      modelArray.add(new C_Curve(6));
      modelArray.add(new C_Curve(7));
      modelArray.add(new C_Curve(8));
      modelArray.add(new C_Curve(9));
      modelArray.add(new C_Curve(10));
      modelArray.add(new C_Curve(11));
      modelArray.add(new C_Curve(12));
      modelArray.add(new C_Curve(13));
      modelArray.add(new C_Curve(14));
      modelArray.add(new C_Curve(15));
      modelArray.add(new C_Curve(16));
      modelArray.add(new C_Curve(17));
      modelArray.add(new C_Curve(18));
      modelArray.add(new C_Curve(19));
      modelArray.add(new C_Curve(20));

      modelArray.add(new PythagorasTree(0.4, 0.45, 0));
      modelArray.add(new PythagorasTree(0.4, 0.45, 1));
      modelArray.add(new PythagorasTree(0.4, 0.45, 2));
      modelArray.add(new PythagorasTree(0.4, 0.45, 3));
      modelArray.add(new PythagorasTree(0.4, 0.45, 4));
      modelArray.add(new PythagorasTree(0.4, 0.45, 5));
      modelArray.add(new PythagorasTree(0.4, 0.45, 6));
      modelArray.add(new PythagorasTree(0.4, 0.45, 7));
      modelArray.add(new PythagorasTree(0.4, 0.45, 8));
      modelArray.add(new PythagorasTree(0.4, 0.45, 9));
      modelArray.add(new PythagorasTree(0.4, 0.45, 10));
      modelArray.add(new PythagorasTree(0.4, 0.45, 11));
      modelArray.add(new PythagorasTree(0.4, 0.45, 12));
      modelArray.add(new PythagorasTree(0.4, 0.45, 13));
      modelArray.add(new PythagorasTree(0.4, 0.45, 14));
      modelArray.add(new PythagorasTree(0.4, 0.45, 15));
/*
      modelArray.add(new PythagorasTree(60, 0));
      modelArray.add(new PythagorasTree(60, 1));
      modelArray.add(new PythagorasTree(60, 2));
      modelArray.add(new PythagorasTree(60, 3));
      modelArray.add(new PythagorasTree(60, 4));
      modelArray.add(new PythagorasTree(60, 5));
      modelArray.add(new PythagorasTree(60, 6));
      modelArray.add(new PythagorasTree(60, 7));
      modelArray.add(new PythagorasTree(60, 8));
      modelArray.add(new PythagorasTree(60, 9));
      modelArray.add(new PythagorasTree(60, 10));
      modelArray.add(new PythagorasTree(60, 11));
      modelArray.add(new PythagorasTree(60, 12));
      modelArray.add(new PythagorasTree(60, 13));
      modelArray.add(new PythagorasTree(60, 14));
      modelArray.add(new PythagorasTree(60, 15));
*/
/*
      Vertex v0 = new Vertex(-1, -1, 0);
      Vertex v1 = new Vertex( 1, -1, 0);
      Vertex v2 = new Vertex(-1,  1, 0);
      modelArray.add(new SierpinskiTriangle(v0, v1, v2, 0));
      modelArray.add(new SierpinskiTriangle(v0, v1, v2, 1));
      modelArray.add(new SierpinskiTriangle(v0, v1, v2, 2));
      modelArray.add(new SierpinskiTriangle(v0, v1, v2, 3));
      modelArray.add(new SierpinskiTriangle(v0, v1, v2, 4));
      modelArray.add(new SierpinskiTriangle(v0, v1, v2, 5));
      modelArray.add(new SierpinskiTriangle(v0, v1, v2, 6));
      modelArray.add(new SierpinskiTriangle(v0, v1, v2, 7));
      modelArray.add(new SierpinskiTriangle(v0, v1, v2, 8));
*/
      // Add a model to the Scene.
      scene.addPosition(new Position(modelArray.get(currentModel)));

      // Push the models away from where the camera is.
      scene.getPosition(0).translation(xPosition,
                                       yPosition,
                                       zPosition);

      Rasterize.doClipping = true;


      // Define initial dimensions for a FrameBuffer.
      final int width  = 1024;
      final int height = 1024;

      // Create a FrameBufferPanel that holds a FrameBuffer.
      fbp = new FrameBufferPanel(width, height);

      // Create a JFrame that will hold the FrameBufferPanel.
      final JFrame jf = new JFrame("Fractals - Renderer 2");
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jf.getContentPane().add(fbp, BorderLayout.CENTER);
      jf.pack();
      jf.setLocationRelativeTo(null);
      jf.setVisible(true);

      // Register this object as the event listener for JFrame events.
      jf.addKeyListener(this);
      jf.addComponentListener(this);

      print_help_message();
   }


   // Implement the KeyListener interface.
   @Override public void keyPressed(KeyEvent e){}
   @Override public void keyReleased(KeyEvent e){}
   @Override public void keyTyped(KeyEvent e)
   {
      //System.out.println( e );

      final char c = e.getKeyChar();
      if ('h' == c)
      {
         print_help_message();
         return;
      }
      else if ('d' == c && e.isAltDown())
      {
         System.out.println();
         System.out.println(scene.getPosition(0).getModel());
      }
      else if ('d' == c)
      {
         scene.debug = ! scene.debug;
      }
      else if ('D' == c)
      {
         Rasterize.debug = ! Rasterize.debug;
      }
      else if ('i' == c)
      {
         final int verts = scene.getPosition(0).getModel().vertexList.size();
         final int lines = scene.getPosition(0).getModel().primitiveList.size();
         System.out.print("The current Model has " + verts + " vertices and ");
         System.out.println(lines + " line segments.");
      }
      else if ('c' == c)
      {
         Rasterize.doClipping = ! Rasterize.doClipping;
         System.out.print("Clipping is turned ");
         System.out.println(Rasterize.doClipping ? "On" : "Off");
      }
      else if ('/' == c)
      {
         currentModel = (currentModel + 1) % modelArray.size();
         scene.getPosition(0).setModel(modelArray.get(currentModel));
         savedModel = null;
         pointSize = 0;
      }
      else if ('?' == c)
      {
         currentModel = (currentModel - 1);
         if (currentModel < 0) currentModel = modelArray.size() - 1;
         scene.getPosition(0).setModel(modelArray.get(currentModel));
         savedModel = null;
         pointSize = 0;
      }
      else if ('p' == c)
      {
         scene.getCamera().perspective = ! scene.getCamera().perspective;
         final String p = scene.getCamera().perspective ? "perspective" : "orthographic";
         System.out.println("Using " + p + " projection");
      }
      else if ('P' == c)
      {
         if (savedModel != null)
         {
            scene.getPosition(0).setModel(savedModel);
            savedModel = null;
            ++pointSize;
         }
         else
         {
            final Model model = scene.getPosition(0).getModel();
            savedModel = model;
            scene.getPosition(0)
                    .setModel(PointCloud.make(model, pointSize));
         }
      }
      else if ('m' == c) //display transformation information
      {
         displayTransformations = ! displayTransformations;
      }
      else if ('=' == c) // Reset the translation vector.
      {
         xPosition =  0;
         yPosition =  0;
         zPosition = -1;
      }
      else if ('x' == c) // Translate ALL the models.
      {
         xPosition -= 0.1; // left
      }
      else if ('X' == c)
      {
         xPosition += 0.1; // right
      }
      else if ('y' == c)
      {
         yPosition -= 0.1; // down
      }
      else if ('Y' == c)
      {
         yPosition += 0.1; // up
      }
      else if ('z' == c)
      {
         zPosition -= 0.1; // back
      }
      else if ('Z' == c)
      {
         zPosition += 0.1; // forward
      }
      else if ('+' == c)
      {
         takeScreenshot = true;
      }

      scene.getPosition(0).translation(xPosition,
                                       yPosition,
                                       zPosition);

      if (displayTransformations && (
                     'm'==c||'='==c
                   ||'x'==c||'y'==c||'z'==c
                   ||'X'==c||'Y'==c||'Z'==c))
      {
         System.out.printf("xPosition = %.2f, yPosition = %.2f, " +
                           "zPosition = %.2f\n",
                            xPosition,        yPosition,
                            zPosition);
      }

      // Render again.
      final FrameBuffer fb = fbp.getFrameBuffer();
      fb.clearFB();
      Pipeline.render(scene, fb);
      if (takeScreenshot)
      {
         fb.dumpFB2File(String.format("Screenshot%03d.png", screenshotNumber),
                        "png");
         ++screenshotNumber;
         takeScreenshot = false;
      }
      fbp.update();
   }


   // Implement the ComponentListener interface.
   @Override public void componentMoved(ComponentEvent e){}
   @Override public void componentHidden(ComponentEvent e){}
   @Override public void componentShown(ComponentEvent e){}
   @Override public void componentResized(ComponentEvent e)
   {
      //System.out.println( e );
      /*
      System.out.printf("JFrame [w = %d, h = %d]: " +
                        "FrameBufferPanel [w = %d, h = %d].\n",
                        fbp.getTopLevelAncestor().getWidth(),
                        fbp.getTopLevelAncestor().getHeight(),
                        fbp.getWidth(), fbp.getHeight());
      */
      // Get the new size of the FrameBufferPanel.
      final int w = fbp.getWidth();
      final int h = fbp.getHeight();

      // Create a new FrameBuffer that fits the FrameBufferPanel.
      final FrameBuffer fb = new FrameBuffer(w, h);
      fbp.setFrameBuffer(fb);
      Pipeline.render(scene, fb);
      fbp.update();
   }


   private static void print_help_message()
   {
      System.out.println("Use the 'd/D' keys to toggle debugging information on and off for the current model.");
      System.out.println("Use the '/' and '?' keys to cycle through the models.");
      System.out.println("Use the 'i' key to get information about the current model.");
      System.out.println("Use the 'p' key to toggle between parallel and orthographic projection.");
      System.out.println("Use the x/X, y/Y, z/Z, keys to translate the models along the x, y, z axes.");
      System.out.println("Use the 'm' key to toggle the display of transformation information.");
      System.out.println("Use the '=' key to reset the model translation.");
      System.out.println("Use the 'c' key to toggle line clipping on and off.");
      System.out.println("Use the 'P' key to convert the current model to a point cloud.");
      System.out.println("Use the '+' key to save a \"screenshot\" of the framebuffer.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }


   /**
      Create an instance of this class which has
      the affect of creating the GUI application.
   */
   public static void main(String[] args)
   {
      // We need to call the program's constructor in the
      // Java GUI Event Dispatch Thread, otherwise we get a
      // race condition between the constructor (running in
      // the main() thread) and the very first ComponentEvent
      // (running in the EDT).
      javax.swing.SwingUtilities.invokeLater(
         () -> new InteractiveModelsAll()
      );
   }
}
