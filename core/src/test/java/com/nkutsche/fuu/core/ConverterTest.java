package com.nkutsche.fuu.core;

import org.junit.Test;

import java.io.File;
import java.net.URI;

import static com.nkutsche.fuu.core.Converter.FUU;
import static com.nkutsche.fuu.core.Converter.uriFromFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConverterTest {

    String basePath = "src/test/resources/core/";

    @Test
    public void testUriFromFile(){
        File file = new File(basePath + "resource.xml");
        URI uri = uriFromFile(file.getAbsoluteFile());
        assertEquals("file", uri.getScheme());
        assertTrue(uri.toString().endsWith("src/test/resources/core/resource.xml"));
    }

    @Test
    public void testUriFromFile_dir(){
        File file = new File(basePath);
        URI uri = uriFromFile(file.getAbsoluteFile());
        assertEquals("file", uri.getScheme());
        assertTrue(uri.toString().endsWith("src/test/resources/core"));
    }

//    C:\absolut\path -> windows file
//    /C:/absolut/path -> unix file
//    file:/C:/absolute/path -> uri
//    relative\path -> windows file
//    relative/path -> unix file
    @Test
    public void testPathDetection(){
        assertEquals(FUU.File, Converter.detectFromString("C:\\absolut\\path"));
        assertEquals(FUU.URI, Converter.detectFromString("/C:/absolut/path"));
        assertEquals(FUU.URL, Converter.detectFromString("file:/C:/absolute/path"));
        assertEquals(FUU.File, Converter.detectFromString("relative\\path"));
        assertEquals(FUU.File, Converter.detectFromString("relative/path with whitespace"));
        assertEquals(FUU.URI, Converter.detectFromString("relative/path%20with%20whitespace"));

    }
}
