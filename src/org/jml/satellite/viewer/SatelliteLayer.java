/******************************************************************************
 *************** All Rights Reserved Exclusively To John M Lien ***************
 *****************************************************************************/

package org.jml.satellite.viewer;

import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Vec4;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.orekit.errors.OrekitException;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.orbits.KeplerianOrbit;
import org.orekit.orbits.PositionAngle;
import org.orekit.propagation.SpacecraftState;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;

/**
 *
 * @author John M. Lien
 */
public class SatelliteLayer extends SpaceLayer {

    Propagator m_propagator = new Propagator();
    Frame m_InertialFrame = FramesFactory.getEME2000();
    AbsoluteDate m_UniverseTime;
    Satellite m_deathstar;

    public SatelliteLayer(WorldWindowGLCanvas aCanvas) {
        super(aCanvas);

        setUniverseTime();
        addDeathStar();
    }

    private void setUniverseTime() {
        try {
            m_UniverseTime = new AbsoluteDate(
                2019, 01, 01, 23, 30, 00.000, TimeScalesFactory.getUTC());
        } catch (OrekitException ex) {
            Logger.getLogger(SatelliteLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addDeathStar() {
        double a = 6731000;                      // semi major axis in meters
        double e = 0.0004560;                    // eccentricity
        double i = Math.toRadians(51.6398);      // inclination
        double omega = Math.toRadians(288.3162); // perigee argument
        double raan = Math.toRadians(12.8462);   // right ascension of ascending node
        double mean = 71.7494;                   // mean anomaly

        m_deathstar = new Satellite();
        m_deathstar.setKeplerianElements(a, e, i, omega, raan, mean);

        this.addRenderable(m_deathstar);
    }

    @Override
    public void update() {
        Thread t1 = new Thread(() -> {
            double timeStep = 1;
            AbsoluteDate finalDate = m_UniverseTime.shiftedBy(86400);

            KeplerianOrbit orbit = new KeplerianOrbit(
                m_deathstar.a, m_deathstar.e, m_deathstar.i,  m_deathstar.omega,
                m_deathstar.raan, m_deathstar.mean, PositionAngle.MEAN,
                m_InertialFrame, m_UniverseTime, 3.986004415e+14);
            try {
                ArrayList<SpacecraftState> states =
                    m_propagator.Propogate(orbit, m_UniverseTime, finalDate, timeStep);

                for (SpacecraftState s : states) {
                    Vector3D vec3d = s.getPVCoordinates().getPosition();
                    Position pos =
                        m_canvas.getModel().getGlobe().computePositionFromPoint(
                            new Vec4(vec3d.getX(), vec3d.getY(), vec3d.getZ(), 1));
                    m_deathstar.setCenterPosition(pos);
                    
                    
                    /* Add a sleep and redraw as a temporary work around for 
                    * performing an updated render on the the seperate thread. */
                    m_canvas.redraw();
                    Thread.sleep(250);
                }
            } catch (OrekitException | InterruptedException ex) {
                Logger.getLogger(SatelliteLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        t1.start();
    }
}

/******************************************************************************
 *************** All Rights Reserved Exclusively To John M Lien ***************
 *****************************************************************************/