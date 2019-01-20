/******************************************************************************
 *************** All Rights Reserved Exclusively To John M Lien ***************
 *****************************************************************************/

package org.jml.satellite.viewer;

import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import java.util.ArrayList;

/**
 *
 * @author John M. Lien
 */
public class LayerManager {
    WorldWindowGLCanvas m_canvas;
    ArrayList<SpaceLayer> m_layers;
 
    public LayerManager(WorldWindowGLCanvas aCanvas) {
        m_canvas = aCanvas;
        m_layers = new ArrayList();

        SatelliteLayer tSatelliteLayer = new SatelliteLayer(aCanvas);
        m_canvas.getModel().getLayers().add(tSatelliteLayer);
        
        m_layers.add(tSatelliteLayer);
    }
    
    public void update(){
        m_layers.stream().forEach(SpaceLayer::update);
    }
}

/******************************************************************************
 *************** All Rights Reserved Exclusively To John M Lien ***************
 *****************************************************************************/