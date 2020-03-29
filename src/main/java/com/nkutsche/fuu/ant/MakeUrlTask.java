package com.nkutsche.fuu.ant;

import com.nkutsche.fuu.core.Converter;
import org.apache.tools.ant.BuildException;

import java.io.File;
import java.net.MalformedURLException;

import static com.nkutsche.fuu.core.Converter.*;

public class MakeUrlTask extends org.apache.tools.ant.Task {

    private File path;
    private String property;
    private boolean isUrl;

    public void setPath(File path){

        this.path = path;
    }

    public void setProperty(String property){

        this.property = property;
    }

    public void setUrl(boolean isUrl){

        this.isUrl = isUrl;
    }

    @Override
    public void execute() throws BuildException {
        String uri = "";
        try {
            uri = isUrl ? urlFromFile(path).toString() : uriFromFile(path).toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.getProject().setProperty(property, uri);
    }
}
