package com.nkutsche.fuu.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

public class Converter {

    public enum FUU {
        File, URI, URL
}
    protected static FUU detectFromString(String path){
        try {
            URL url = new URL(path);
            return FUU.URL;
        } catch (MalformedURLException e) {
            try {
                URI uri = new URI(path);
                return FUU.URI;
            } catch (URISyntaxException e2) {
                return FUU.File;
            }
        }
    }

    public static HashMap<String, Object> getInfo(String path){
        HashMap<String, Object> map = new HashMap<>();
        File file = file(path);
        map.put("file", file);
        map.put("filename", file.getName());
        map.put("extension", file.getName().replaceAll(".*\\.([^\\.]+)$", "$1"));
        map.put("url", url(path));
        map.put("uri", uri(path));
        return map;
    }

    public static File file(String path){
        try {
            switch (detectFromString(path)){
                case URI:
                    return fileFromURI(new URI(path));
                case URL:
                    return fileFromURL(new URL(path));
                case File:
                default:
                    return new File(path);
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    public static URL url(String path){
        try {
            switch (detectFromString(path)){
                case URI:
                    return urlFromURI(new URI(path));
                case URL:
                    return new URL(path);
                case File:
                default:
                    return urlFromFile(new File(path));
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static URI uri(String path){
        try {
            switch (detectFromString(path)){
                case URI:
                    return new URI(path);
                case URL:
                    return uriFromURL(new URL(path));
                case File:
                default:
                    return uriFromFile(new File(path));
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static File fileFromURI(URI uri){

        return new File(absoluteUri(uri));
    }


    public static File fileFromURL(URL url) throws URISyntaxException {
        return fileFromURI(uriFromURL(url));
    }

    public static URI uriFromURL(URL url) throws URISyntaxException {
        return url.toURI();
    }

    public static URI uriFromFile(File file){
        return file.toURI();
    }

    public static URL urlFromURI(URI uri) throws MalformedURLException {
        return absoluteUri(uri).toURL();
    }

    public static URL urlFromFile(File file) throws MalformedURLException {
        return urlFromURI(uriFromFile(file));
    }

    private static URI absoluteUri(URI uri){
        if(!uri.isAbsolute()){
            uri = new File(".").getAbsoluteFile().toURI().resolve(uri);
        }
        return uri;
    }
}
