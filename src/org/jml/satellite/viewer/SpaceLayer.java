/******************************************************************************
 *************** All Rights Reserved Exclusively To John M Lien ***************
 *****************************************************************************/

package org.jml.satellite.viewer;

import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.layers.RenderableLayer;

/**
 *
 * @author John M. Lien
 */
public abstract class SpaceLayer extends RenderableLayer{
    WorldWindowGLCanvas m_canvas;

    public SpaceLayer(WorldWindowGLCanvas aCanvas) {
        super();
        m_canvas = aCanvas;
    }

    public abstract void update();
}

/******************************************************************************
 *************** All Rights Reserved Exclusively To John M Lien ***************
 *****************************************************************************/