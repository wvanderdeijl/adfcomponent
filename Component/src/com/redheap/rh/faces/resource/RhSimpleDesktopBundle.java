package com.redheap.rh.faces.resource;

import java.util.ListResourceBundle;

/**
 * <p>Holds properties used by the components bundled in the jar project.
 * This bundle is part of the trinidad component skin that is configured
 * in the "/META-INF/trinidad-skins.xml" file. Component Renderers
 * will use the <code>RenderingContext</code> to lookup a key by calling
 * the <code>getTranslatedString(key)</code> method.</p>
 */
public class RhSimpleDesktopBundle extends ListResourceBundle {
    /**
      * <p>Returns a two dimensional object array that represents a resource bundle
    . * The first 
      * element of each pair is the key and the second the value.</p>
      * 
      * @return an array of value pairs
      */
     protected Object[][] getContents()
     {
      return new Object[][]
       {
        {"RhMultiSelect_delete","Delete"}
       };
     }
}
