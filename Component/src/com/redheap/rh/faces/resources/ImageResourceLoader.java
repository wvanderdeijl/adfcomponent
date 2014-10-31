package com.redheap.rh.faces.resources;

import java.io.IOException;

import java.net.URL;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.resource.ClassLoaderResourceLoader;
import org.apache.myfaces.trinidad.resource.RegexResourceLoader;

/**
 * This resource loader is registered in META-INF/servlets/resources/rh.resources
 * Each ADF application includes the org.apache.myfaces.trinidad.webapp.ResourceServlet
 * in the web.xml. It should also have a servlet-mapping for url-pattern "/rh/*"
 * The path (rh) in this servlet-mapping causes org.apache.myfaces.trinidad.webapp.ResourceServlet
 * to look for META-INF/servlets/resources/rh.resources in the component library which
 * should be a text file containing the name of this java class.
 */
public class ImageResourceLoader extends RegexResourceLoader {

    private static ADFLogger logger = ADFLogger.createADFLogger(ImageResourceLoader.class);

    public ImageResourceLoader() {
        // this resourceloader can load URL's like context-root/rh/images/example.png
        // and delegate this to an internal resource loader that looks
        // at META-INF/rh/images/example.png om the classpath
        register("(/.*\\.(jpg|gif|png|jpeg))", new ClassLoaderResourceLoader("META-INF"));
    }

    @Override
    protected URL findResource(String path) throws IOException {
        logger.fine("ImageResourceLoader.findResource for {0}", path);
        return super.findResource(path);
    }

}
