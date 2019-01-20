/**
 * ****************************************************************************
 *************** All Rights Reserved Exclusively To John M Lien ***************
 ****************************************************************************
 */
package org.jml.satellite.viewer;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.errors.OrekitException;
import org.orekit.orbits.KeplerianOrbit;
import org.orekit.propagation.SpacecraftState;
import org.orekit.propagation.analytical.KeplerianPropagator;
import org.orekit.time.AbsoluteDate;

/**
 *
 * @author John M. Lien
 */
public class Propagator {

    public Propagator() {
        File orekitData = new File("data/orekit-data");
        DataProvidersManager manager = DataProvidersManager.getInstance();
        try {
            manager.addProvider(new DirectoryCrawler(orekitData));
        } catch (OrekitException ex) {
            Logger.getLogger(Propagator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<SpacecraftState> Propogate(KeplerianOrbit aOrbit,
            AbsoluteDate aStartDate,
            AbsoluteDate aFinalDate,
            double timeStep) throws OrekitException {

        ArrayList<SpacecraftState> tStates = new ArrayList();
        KeplerianPropagator kepler = new KeplerianPropagator(aOrbit);
        kepler.setSlaveMode();

        SpacecraftState state = null;
        for (AbsoluteDate extrapDate = aStartDate;
                extrapDate.compareTo(aFinalDate) <= 0;
                extrapDate = extrapDate.shiftedBy(timeStep)) {
            state = kepler.propagate(extrapDate);
            tStates.add(state);
        }

        return tStates;
    }
}

/**
 * ****************************************************************************
 *************** All Rights Reserved Exclusively To John M Lien ***************
 ****************************************************************************
 */
