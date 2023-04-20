package preprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;

public class AngleIdentifier
{
	protected AngleEquivalenceClasses _angles;
	protected Map<Segment, Segment> _segments; // The set of ALL segments for this figure

	public AngleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested; memorize results for subsequent calls.
	 */
	public AngleEquivalenceClasses getAngles()
	{
		if (_angles != null) return _angles;

		_angles = new AngleEquivalenceClasses();

		computeAngles();

		return _angles;
	}

	private void computeAngles()
	{
		ArrayList<Segment> segments = new ArrayList<Segment>(_segments.keySet());
		
		for(int i=0; i<segments.size()-1; i++) {
			for(int j=i; j<segments.size(); j++) {
				try {
					Angle angle = new Angle(segments.get(i), segments.get(j));
					_angles.add(angle);
				}
				catch (FactException e) {}	
			}
		}
	}
}

