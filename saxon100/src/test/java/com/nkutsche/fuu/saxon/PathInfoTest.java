package com.nkutsche.fuu.saxon;

import net.sf.saxon.TransformerFactoryImpl;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

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
