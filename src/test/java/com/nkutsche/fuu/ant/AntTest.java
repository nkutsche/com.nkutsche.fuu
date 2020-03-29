package com.nkutsche.fuu.ant;

import org.apache.tools.ant.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

public class AntTest {

    private String base = "src/test/resources/com/nkutsche/fuu/ant/";

    @Test
    public void testMakeUrl(){

        File basedir = new File(base + "makeUrl").getAbsoluteFile();

        File build = new File(basedir,"build.xml");

        String relative = "relative/path";

        Project p = runProject(build, "test.folder", relative);

        checkProperty(p, "test.url", new File(basedir, relative).toURI().toString());

    }

    @Test
    public void testMakeLoc(){

        File basedir = new File(base + "makeLoc").getAbsoluteFile();

        File build = new File(basedir,"build.xml");

        String relative = "relative/path";



        Project p = runProject(build, "test.uri", new File(basedir, relative).toURI().toString());

        checkProperty(p, "test.loc", new File(basedir, relative).getAbsolutePath());

    }

    private void checkProperty(Project project, String prop, String expected){
        Assert.assertEquals(expected, project.getProperty(prop));
    }

    private Project runProject(File buildXml, String prop, String value){
        Properties props = new Properties();
        props.setProperty(prop, value);
        return runProject(buildXml, props);
    }
    private Project runProject(File buildXml, Properties props){
        Project p = new Project();

        p.addBuildListener(new SystemLogger());


        for (Object key:
            props.keySet()) {
            p.setProperty(key.toString(), props.getProperty(key.toString()));
        }

        ProjectHelper.configureProject(p, buildXml);


        p.executeTarget(p.getDefaultTarget());

        return p;
    }
}
