package com.nkutsche.fuu.core;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentConverter {

    private String[] args;

    private static class Function{
        private String func;
        private String content;

        Function(String func, String content){
            this.func = func;
            this.content = content;
        }
        private String convert() {
            if("url".equals(func.toLowerCase())){
                return Converter.url(content).toString();
            } else if("uri".equals(func)){
                return Converter.uri(content).toString();
            } else if("file".equals(func)){
                return Converter.file(content).toString();
            } else {
                return content;
            }
        }

//        private URI uri(){
//            File f = new File(content);
//            boolean shouldEndSlash = content.matches(".*(/|\\\\)");
//            URI uri = f.toURI();
//            boolean endSlash = uri.toString().matches(".*/");
//            if(shouldEndSlash && !endSlash){
//                try {
//                    uri = new URI(uri.toString() + "/");
//                } catch (URISyntaxException e) {
//                    System.err.println(e.getMessage());
//                }
//            } else if(!shouldEndSlash && endSlash){
//                try {
//                    uri = new URI(uri.toString().substring(0, uri.toString().length() - 1));
//                } catch (URISyntaxException e) {
//                    System.err.println(e.getMessage());
//                }
//            }
//
//            return uri;
//        }
    }

    public ArgumentConverter(String[] args){
        this.args = args;
    }

    public String[] convertArgs() {
        String[] resArgs = new String[args.length];
        int i = 0;
        for (String arg: this.args) {
            resArgs[i++] = convertArg(arg);
        }
        return resArgs;
    }

    private String convertArg(String arg) {
        Function function = findFunction(arg);
        if(function != null){
            return function.convert();
        } else if (arg.contains("=")){
            String before = arg.substring(0, arg.indexOf('=') + 1);
            String after = arg.substring(arg.indexOf('=') + 1);
            return before + convertArg(after);
        } else {
            return arg;
        }

    }

    private Function findFunction(String arg){
        Pattern p = Pattern.compile("^(url|uri|file)\\((.*)\\)$");
        Matcher m = p.matcher(arg);
        if (m.find()){
            return new Function(m.group(1), m.group(2));
        }
        return null;
    }
}