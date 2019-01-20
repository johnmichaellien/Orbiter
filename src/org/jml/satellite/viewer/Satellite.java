/******************************************************************************
 *************** All Rights Reserved Exclusively To John M Lien ***************
 *****************************************************************************/

package org.jml.satellite.viewer;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Ellipsoid;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;

/**
 *
 * @author John M. Lien
 */
public class Satellite extends Ellipsoid {
    double a;      // semi major axis in meters
    double e;      // eccentricity
    double i;      // inclination
    double omega;  // perigee argument
    double raan;   // right ascension of ascending node
    double mean;   // mean anomaly
    
    public Satellite() {
        super(Position.fromDegrees(0, 0, 0), 100000, 100000, 100000);

        ShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setInteriorMaterial(Material.WHITE);
        attrs.setInteriorOpacity(0.9);
        attrs.setEnableLighting(true);
        attrs.setOutlineMaterial(Material.WHITE);
        attrs.setOutlineWidth(2d);
        attrs.setDrawInterior(true);
        attrs.setDrawOutline(false);

        this.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        this.setImageSources("data/deathstar.png");
        this.setAttributes(attrs);
        this.setVisible(true);
    }

    public void setKeplerianElements(double a, double e, double i,
                                     double omega, double raan, double mean) {
        this.a = a;
        this.e = e;
        this.i = i;
        this.omega = omega;
        this.raan = raan;
        this.mean = mean;
    }
}
