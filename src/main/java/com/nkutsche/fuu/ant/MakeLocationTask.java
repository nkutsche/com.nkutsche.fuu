package com.nkutsche.fuu.ant;

import org.apache.tools.ant.BuildException;

import java.io.File;
import java.net.MalformedURLException;

import static com.nkutsche.fuu.core.Converter.*;

public class MakeLocationTask extends org.apache.tools.ant.Task {

    private String url;
    private String property;
    private boolean isUrl;

    public void setUrl(String url){

        this.url = url;
    }

    public void setProperty(String property){

        this.property = property;
    }

    @Override
    public void execute() throws BuildException {
        File uri = (File) getInfo(url).get("file");

        this.getProject().setProperty(property, uri.getAbsolutePath());
    }
}
