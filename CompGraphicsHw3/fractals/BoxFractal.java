/*

*/

package fractals;

import renderer.scene.*;
import renderer.scene.primitives.*;

/**
   Create a wireframe model of a Box Fractal.
   <p>
   See <a href="https://en.wikipedia.org/wiki/Vicsek_fractal" target="_top">
                https://en.wikipedia.org/wiki/Vicsek_fractal</a>
*/
public class BoxFractal extends Model
{
   /**
      Create a box fractal in the xy-plane with {@code n}
      subdivisions and with corners at {@code (�1, �1, 0)}.

      @param n  number of subdivisions for this rectangle
   */
   public BoxFractal(final int n)
   {
      this(new Vertex(-1,  1, 0),
           new Vertex( 1,  1, 0),
           new Vertex( 1, -1, 0),
           new Vertex(-1, -1, 0),
           n);
   }


   /**
      Create a Sierpinski triangle with {@code n}
      subdivisions and its corners at the given
      vertices.
      <p>
      The vertices {@code v0}, {@code v1}, {@code v2},
      and {@code v3} should all be in the same z-plane.

      @param v0  1st {@link Vertex} of the rectangle
      @param v1  2nd {@link Vertex} of the rectangle
      @param v2  3rd {@link Vertex} of the rectangle
      @param v3  4th {@link Vertex} of the rectangle
      @param n   number of subdivisions for this rectangle
   */
   public BoxFractal(final Vertex v0,
                     final Vertex v1,
                     final Vertex v2,
                     final Vertex v3,
                     final int n)
   {
      name = "Box Fractal (n = " + n + ")";

      addVertex(v0, v1, v2, v3);

      box(0, 1, 2, 3, n);
   }


   /**
      If {@code n > 0}, recursively subdivide the rectangle whose
      vertices are indexed by {@code vIndex0}, {@code vIndex1},
      {@code vIndex2}, and {@code vIndex3}.
      <p>
      If {@code n == 0}, draw the rectangle whose vertices are
      indexed by {@code vIndex0}, {@code vIndex1}, {@code vIndex2},
      and {@code vIndex3}.
      <p>
      Given four points that are the corners of a rectangle,
      {@code p0 = (x0, y0)}, {@code p1 = (x1, y0)},
      {@code p2 = (x1, y1)}, {@code p3 = (x0, y1)}, compute 12
      new points that divide the rectangle up into nine
      sub rectangles of equal size.
      <p>
      The following five of the nine subrectangles will then be
      recursively subdivided.

      <pre>{@code
            p0                                     p1
            +------------+------------+------------+
            |            |............|            |
            |            |............|            |
            |            |............|            |
            |            |............|            |
            |            |............|            |
         y6 +------------+------------+------------+
            |............|............|............|
            |............|............|............|
            |............|............|............|
            |............|............|............|
            |............|............|............|
         y7 +------------+------------+------------+
            |            |............|            |
            |            |............|            |
            |            |............|            |
            |            |............|            |
            |            |............|            |
            +------------+------------+------------+
            p3          x4            x5           p2
      }</pre>
      <p>
      The coordinates {@code x4} and {@code x5} divide the horizontal sides
      of the rectangle into thirds. Similarly for coordinates {@code y6}
      and {@code y7} and the vertical sides. So
      <pre>{@code
         x4 = (2/3) * x0 + (1/3) * x1
         x5 = (1/3) * x0 + (2/3) * x1
         y6 = (2/3) * y0 + (1/3) * y1
         y7 = (1/3) * y0 + (2/3) * y1
      }</pre>
      <p>
      The 12 new vertices have the following coordinates.
      <pre>{@code
         (x4, y0)
         (x5, y0)
         (x0, y6)
         (x4, y6)
         (x5, y6)
         (x1, y6)
         (x0, y7)
         (x4, y7)
         (x5, y7)
         (x1, y7)
         (x4, y3)
         (x5, y3)
      }</pre>

      @param vIndex0  index of a {link Vertex} of a rectangle
      @param vIndex1  index of a {link Vertex} of a rectangle
      @param vIndex2  index of a {link Vertex} of a rectangle
      @param vIndex3  index of a {link Vertex} of a rectangle
      @param n        number of subdivisions for this rectangle
   */
   private void box(final int vIndex0,
                    final int vIndex1,
                    final int vIndex2,
                    final int vIndex3,
                    final int n)
   {
      if( n <= 1){
         addPrimitive(new LineSegment(vIndex0,  vIndex1));
         addPrimitive(new LineSegment(vIndex1,  vIndex2));
         addPrimitive(new LineSegment(vIndex2,  vIndex3));
         addPrimitive(new LineSegment(vIndex3,  vIndex0));
      }

      if (n > 0)
      {
         final Vertex v0 = vertexList.get(vIndex0);
         final Vertex v1 = vertexList.get(vIndex1);
         final Vertex v2 = vertexList.get(vIndex2);
         final Vertex v3 = vertexList.get(vIndex3);
         final int index = vertexList.size();

         final double x0 = v0.x;
         final double y0 = v0.y;
         final double z0 = v0.z;
         final double x1 = v1.x;
         final double y1 = v1.y;
         final double z1 = v1.z; // should have z1 == z0
         final double x2 = v2.x;
         final double y2 = v2.y;
         final double z2 = v2.z;
         final double x3 = v3.x;
         final double y3 = v3.y;
         final double z3 = v3.z;

         // Add two vertices to the model.
         double xSplit = (x0 + x2)/2;
         double ySplit = (y0 + y2)/2;

         double x4 = (2/3.0) * x0 + (1/3.0) * x1;
         double x5 = (1/3.0) * x0 + (2/3.0) * x1;
         double y6 = (2/3.0) * y0 + (1/3.0) * y2;
         double y7 = (1/3.0) * y0 + (2/3.0) * y2;
         
         addVertex(  new Vertex(x4, y0, 0.0),
                     new Vertex(x5, y0, 0.0),
                     
                     new Vertex(x0, y6, 0.0),
                     new Vertex(x4, y6, 0.0),
                     new Vertex(x5, y6, 0.0),
                     new Vertex(x1, y6, 0.0),

                     new Vertex(x0, y7, 0.0),
                     new Vertex(x4, y7, 0.0),
                     new Vertex(x5, y7, 0.0),
                     new Vertex(x1, y7, 0.0),

                     new Vertex(x4, y2, 0.0),
                     new Vertex(x5, y2, 0.0));

         // Give a name to the index of each of the four new vertices.
         final int vIndex4 = index + 0;
         final int vIndex5 = index + 1;

         final int vIndex6 = index + 2;
         final int vIndex7 = index + 3;
         final int vIndex8 = index + 4;
         final int vIndex9 = index + 5;

         final int vIndex10 = index + 6;
         final int vIndex11 = index + 7;
         final int vIndex12 = index + 8;
         final int vIndex13 = index + 9;

         final int vIndex14 = index + 10;
         final int vIndex15 = index + 11;

         // Recursively branch the two new intervals.
         box(vIndex4, vIndex5, vIndex8, vIndex7, n-1);
         box(vIndex6, vIndex7, vIndex11, vIndex10, n-1);
         box(vIndex7, vIndex8, vIndex12, vIndex11, n-1);
         box(vIndex8, vIndex9, vIndex13, vIndex12, n-1);
         box(vIndex11, vIndex12, vIndex15, vIndex14, n-1);
      }
   }
}//BoxFractal
