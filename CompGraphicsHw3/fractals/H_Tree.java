/*

*/

package fractals;

import renderer.scene.*;
import renderer.scene.primitives.*;

/**
   Create a wireframe model of an H-tree.
   <p>
   See <a href="https://en.wikipedia.org/wiki/H_tree" target="_top">
                https://en.wikipedia.org/wiki/H_tree</a>
*/
public class H_Tree extends Model
{
   /**
      Create an H-tree in the xy-plane with {@code n}
      branches and with its base on the line segment from
      {@code (-0.5, 0.0, 0.0)} to {@code (0.5, 0.0, 0.0)}.

      @param n  number of branches in this H-tree
   */
   public H_Tree(final int n)
   {
      this(new Vertex(-0.5, 0.0, 0.0),
           new Vertex( 0.5, 0.0, 0.0),
           n);
   }


   /**
      Create an H-tree with {@code n} branches and with
      its base on the line segment from {@link Vertex}
      {@code v0} to {@link Vertex} {@code v1}.
      <p>
      The vertices {@code v0} and {@code v1} should be in the same z-plane.

      @param v0  left {@link Vertex} of the base
      @param v1  right {@link Vertex} of the base
      @param n   number of branches in this H-tree
   */
   public H_Tree(final Vertex v0, final Vertex v1,
                 final int n)
   {
      name = "H Tree (n = " + n + ")";

      addVertex(v0, v1);

      hTree(0, 1, n);
   }


   /**
      If {@code n > 0}, draw the line segment whose vertices
      are indexed by {@code vIndex0} and {@code vIndex1} and
      then recursively draw two perpendicular H-trees on
      either end of the line segment.
      <p>
      If {@code n == 0}, just draw the line segment whose vertices
      are indexed by {@code vIndex0} and {@code vIndex1}.
      <p>
      Given two points, {@code p0 = (x0, y0)} and {@code p1 = (x1, y1)},
      compute the new points {@code p2 = (x2, y2)}, {@code p3 = (x3, y3)},
      {@code p4 = (x4, y4)}, and {@code p5 = (x5, y5)}, that determine
      two line segments perpendicular to the given line segment and with
      length {@code 1/sqrt(2)} times the length of the given line segment.
      <pre>{@code
              p2                    p4
              +                     +
              |                     |
              |                     |
           p0 +---------------------+ p1
              |                     |
              |                     |
              +                     +
              p3                    p5
      }</pre>
      <p>
      Let {@code length} equal the length of the given line segment.
      <p>
      Let {@code v0} be the vector from {@code p0} to {@code p1}.
      <pre>{@code
         v0 = (x1 - x0, y1 - y0)
      }</pre>
      <p>
      Let {@code v1} be the vector perpendicular to {@code v0} and whose length is
      equal to {@code 0.5*length/sqrt(2)}.
      <pre>{@code
         v1 = ( 0.5*(y0 - y1)/sqrt(2), 0.5*(x1 - x0)/sqrt(2) )
      }</pre>
      <p>
      Notice that
      <pre>{@code
         p2 = p0 + v1
         p3 = p0 - v1
         p4 = p1 + v1
         p5 = p1 - v1
      }</pre>
      <p>
      So
      <pre>{@code
         p2 = (x2, y2) = (x0 + 0.5*(y0 - y1)/sqrt(2), y0 + 0.5*(x1 - x0)/sqrt(2))
         p3 = (x3, y3) = (x0 - 0.5*(y0 - y1)/sqrt(2), y0 - 0.5*(x1 - x0)/sqrt(2))
         p4 = (x4, y4) = (x1 + 0.5*(y0 - y1)/sqrt(2), y1 + 0.5*(x1 - x0)/sqrt(2))
         p5 = (x5, y5) = (x1 - 0.5*(y0 - y1)/sqrt(2), y1 - 0.5*(x1 - x0)/sqrt(2))
      }</pre>

      @param vIndex0  index of the left {@link Vertex} of the base
      @param vIndex1  index of the right {@link Vertex} of the base
      @param n        number of branches in this H tree
   */
   private void hTree(final int vIndex0, final int vIndex1,
                      final int n)
   {
      addPrimitive(new LineSegment(vIndex0,  vIndex1));

      if (n > 0)
      {
         final Vertex v0 = vertexList.get(vIndex0);
         final Vertex v1 = vertexList.get(vIndex1);
         final int index = vertexList.size();

         final double x0 = v0.x;
         final double y0 = v0.y;
         final double z0 = v0.z;
         final double x1 = v1.x;
         final double y1 = v1.y;
         final double z1 = v1.z; // should have z1 == z0

         // Add two vertices to the model.
         double distance = Math.sqrt((y1 - y0) * (y1 - y0) + (x1 - x0) * (x1 - x0));
         final double length = distance * (1/Math.sqrt(2));
         if( y0 == y1 ){
            addVertex(new Vertex(x0, (y0 + length/2), z0),
                     new Vertex(x0, (y0 - length/2), z0),
                     new Vertex(x1, (y1 + length/2), z0),
                     new Vertex(x1, (y1 - length/2), z0));
         }
         else if( x0 == x1 ){
            addVertex(new Vertex((x0 + length/2), y0, z0),
                     new Vertex((x0 - length/2), y0, z0),
                     new Vertex((x1 + length/2), y1, z0),
                     new Vertex((x1 - length/2), y1, z0));
         }

         // Give a name to the index of each of the four new vertices.
         final int vIndex2 = index + 0;
         final int vIndex3 = index + 1;
         final int vIndex4 = index + 2;
         final int vIndex5 = index + 3;

         // Recursively branch the two new intervals.
         hTree(vIndex2, vIndex3, n-1);
         hTree(vIndex4, vIndex5, n-1);
      }
   }
}//H_Tree
