/*

*/

package fractals;

import renderer.scene.*;
import renderer.scene.primitives.*;

/**
   Create a wireframe model of a Pythagoras tree.
   <p>
   See <a href="https://en.wikipedia.org/wiki/Pythagoras_tree_(fractal)" target="_top">
                https://en.wikipedia.org/wiki/Pythagoras_tree_(fractal)</a>
*/
public class PythagorasTree extends Model
{
   /**
      Create a Pythagoras tree in the xy-plane with {@code n}
      branches and with its base on the line segment from
      {@code (-0.25, -1.0, 0.0)} to {@code (0.25, -1.0, 0.0)}.

      @param alpha  determines the location of the peek
      @param beta   determines the location of the peek
      @param n      number of branches in this Pythagoras tree
   */
   public PythagorasTree(final double alpha,
                         final double beta,
                         final int n)
   {
      this(new Vertex(-0.25, -1.0, 0.0),
           new Vertex( 0.25, -1.0, 0.0),
           alpha, beta,
           n);
   }


   /**
      Create a Pythagoras tree in the xy-plane with {@code n}
      branches and with its base on the line segment from
      (-0.25, -1.0, 0.0) to (0.25, -1.0, 0.0). Each branch
      will be topped with an isosceles triangle with the
      given angle at its peek.

      @param angle  determines the angle at the peek of the isosceles triangle
      @param n       number of branches in this Pythagoras tree
   */
   public PythagorasTree(final double angle,
                         final int n)
   {
      this(new Vertex(-0.25, -1.0, 0.0),
           new Vertex( 0.25, -1.0, 0.0),
           0.5,
           0.5/Math.tan(0.5*angle*Math.PI/180),
           n);
   }


   /**
      Create a Pythagoras tree with {@code n} branches and
      with its base on the line segment from {@link Vertex}
      {@code v0} to {@link Vertex} {@code v1}.
      <p>
      The vertices {@code v0} and {@code v1} should be in the same z-plane.

      @param v0     left {@link Vertex} of the base
      @param v1     right {@link Vertex} of the base
      @param alpha  determines the horizontal location of the peek
      @param beta   determines the vertical location of the peek
      @param n      number of branches in this Pythagoras tree
   */
   public PythagorasTree(final Vertex v0, final Vertex v1,
                         final double alpha, final double beta,
                         final int n)
   {
      name = "Pythagoras Tree (n = " + n + ")";

      addVertex(v0, v1);

      pythagoras(0, 1, alpha, beta, n);
   }


   /**
      Given two points, {@code p0 = (x0, y0)} and {@code p1 = (x1, y1)},
      compute two new points, {@code p2 = (x2, y2)} and {@code p3 = (x3, y3)},
      that determine a square whose base is the line segment from
      {@code p0} to {@code p1}. Then build a triangle with one side being
      the side of the square opposite to the base. The third point of
      the triangle is the point {@code p5 = (x5, y5)}. The point
      {@code p5} is above the point {@code p4 = (x4, y4)}.
      <p>
      Let {@code length} equal the length of each side of the square. Let
      <pre>{@code
         the distance between p4 and p2 = alpha * length,
         the distance between p5 and p4 = beta * length.
      }</pre>
      <pre>{@code
                      p5
                      +
                    /    \
                   /       \
                  /           \
                 /              \
                /                 \
               /                     \
           p2 +-------+----------------+ p3
              |       p4               |
              |                        |
              |                        |
              |                        |
              |                        |
              |                        |
              |                        |
              |                        |
              +------------------------+
             p0                        p1
      }</pre>
      <p>
      Let {@code v0} be the vector from {@code p0} to {@code p1}.
      <pre>{@code
         v0 = (x1 - x0, y1 - y0)
      }</pre>
      <p>
      Let {@code v1} be the vector along the side of the square
      from {@code p0} to {@code p2}. So {@code v1} is
      perpendicular to {@code v0}.
      <pre>{@code
         v1 = (y0 - y1, x1 - x0)
      }</pre>
      <p>
      You should check that the dot product of {@code v0} with {@code v1} is {@code 0}.

      <p>
      The point {@code p2} has these coordinates.
      <pre>{@code
         p2 = (x2, y2) = (x0, y0) + v1
                       = (x0 + y0 - y1, y0 + x1 - x0)
      }</pre>
      <p>
      The point {@code p3} has these coordinates.
      <pre>{@code
         p3 = (x3, y3) = (x1, y1) + v1
                       = (x1 + y0 - y1, y1 + x1 - x0)
      }</pre>
      <p>
      The point {@code p3} should also have these coordinates.
      <pre>{@code
         p3 = (x3, y3) = (x2, y2) + v0
                       = (x2 + x1 - x0, y2 + y1 - y0)
                       = ( (x0 + y0 - y1) + x1 - x0,
                           (y0 + x1 - x0) + y1 - y0 )
                       = ( x1 + y0 - y1,
                           y1 + x1 - x0 )
      }</pre>
      <p>
      The point {@code p4} has these coordinates.
      <pre>{@code
         p4 = (1-alpha)*p2 + alpha*p3
            = (1-alpha)*((x0 + y0 - y1, y0 + x1 - x0) + alpha*(x1 + y0 - y1, y1 + x1 - x0)
            = ( (1-alpha)*x0 + alpha*x1 + y0 - y1,
                (1-alpha)*y0 + alpha*y1 + x1 - x0 )
      }</pre>
      <p>
      The point {@code p5} has these coordinates.
      <pre>{@code
         p5 = p4 + beta * v1
            = ( (1-alpha)*x0 + alpha*x1 + y0 - y1,
                (1-alpha)*y0 + alpha*y1 + x1 - x0 ) + beta*(y0 - y1, x1 - x0)
            = ( (1-alpha)*x0 + alpha*x1 + y0 - y1 + beta*(y0 - y1),
                (1-alpha)*y0 + alpha*y1 + x1 - x0 + beta*(x1 - x0) )
            = ( (1-alpha)*x0 + alpha*x1 + (1+beta)*(y0 - y1),
                (1-alpha)*y0 + alpha*y1 + (1+beta)*(x1 - x0) )
      }</pre>

      @param vIndex0  index of the left {@link Vertex} of the base
      @param vIndex1  index of the right {@link Vertex} of the base
      @param alpha    determines the horizontal location of the peek
      @param beta     determines the vertical location of the peek
      @param n        number of branches in this Pythagoras tree
   */
   private void pythagoras(final int vIndex0, final int vIndex1,
                           final double alpha, final double beta,
                           final int n)
   {



   }
}//PythagorasTree
