package com.nkutsche.fuu.saxon;

import net.sf.saxon.TransformerFactoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PathInfoTest {

    Transformer transformer;

    public PathInfoTest() throws TransformerConfigurationException {
        TransformerFactoryImpl tf = (TransformerFactoryImpl) TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", null);
        tf.getConfiguration().registerExtensionFunction(new PathInfo());
        transformer = tf.newTransformer(new StreamSource(PathInfo.class.getResourceAsStream("test.xsl")));
    }

    @Test
    public void testExtensionClass() {
        try {
            Result result = new DOMResult();

            transformer.transform(new StreamSource(PathInfo.class.getResourceAsStream("in.xml")), result);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
