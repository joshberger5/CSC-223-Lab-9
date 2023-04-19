package preprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.Triangle;
import geometry_objects.angle.Angle;

public class TriangleIdentifier
{
	protected Set<Triangle>         _triangles;
	protected Map<Segment, Segment> _segments; // The set of ALL segments for this figure.

	public TriangleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested;
	 * memorize results for subsequent calls.
	 */
	public Set<Triangle> getTriangles()
	{
		if (_triangles != null) return _triangles;

		_triangles = new HashSet<Triangle>();

		computeTriangles();

		return _triangles;
	}

	private void computeTriangles()
	{
		Segment[] segments = (Segment[]) _segments.keySet().toArray();
		
		for(int i=0; i<segments.length-2; i++) {
			for(int j=i+1; j<segments.length-1; j++) {
				for (int k=j+1; k<segments.length; k++) {
					try {
						List<Segment> segs = new ArrayList<Segment>();
						segs.add(segments[i]);
						segs.add(segments[j]);
						segs.add(segments[k]);
						Triangle triangle = new Triangle(segs);
						_triangles.add(triangle);
					}
					catch (FactException e) {}	
				}
			}
		}
	}
}
