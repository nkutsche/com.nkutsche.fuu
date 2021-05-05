package com.nkutsche.fuu.saxon;

import com.nkutsche.fuu.core.Converter;
import net.sf.saxon.event.SequenceCollector;
import net.sf.saxon.event.SequenceWriter;
import net.sf.saxon.expr.StaticProperty;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.s9api.ItemType;
import net.sf.saxon.s9api.XdmMap;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.AnyURIValue;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

public class PathInfo extends ExtensionFunctionDefinition {


    private StructuredQName funcname = new StructuredQName("fuu", "http://nkutsche.com/fuu", "path-info");

    @Override
    public StructuredQName getFunctionQName() {
        return funcname;
    }

    @Override
    public SequenceType[] getArgumentTypes() {
        return new SequenceType[]{
                SequenceType.SINGLE_STRING,
                SequenceType.OPTIONAL_STRING
        };
    }

    @Override
    public int getMinimumNumberOfArguments() {
        return 1;
    }

    @Override
    public int getMaximumNumberOfArguments() {
        return getArgumentTypes().length;
    }

    @Override
    public SequenceType getResultType(SequenceType[] sequenceTypes) {
        return new SequenceType(ItemType.ANY_MAP.getUnderlyingItemType(), StaticProperty.EXACTLY_ONE);
    }

    @Override
    public ExtensionFunctionCall makeCallExpression() {
        return new ExtensionFunctionCall() {
            @Override
            public Sequence call(XPathContext xPathContext, Sequence[] sequences) throws XPathException {
                HashMap<String, Object> properties = Converter.getInfo(sequences[0].head().getStringValue());
                SequenceCollector outputter = xPathContext.getController().allocateSequenceOutputter(50);
                outputter.write(XdmMap.makeMap(convertMapForSaxon(properties)).getUnderlyingValue());

                return outputter.getSequence();
            }
        };
    }

    private HashMap<String, Object> convertMapForSaxon(HashMap<String, Object> map){
        HashMap<String, Object> resultMap = new HashMap<>();
        for (String key:
             map.keySet()) {
            Object value = map.get(key);
            if(value instanceof File){
                resultMap.put(key, new StringValue(value.toString()));
            } else if(value instanceof URI){
                resultMap.put(key, new AnyURIValue(value.toString()));
            } else if(value instanceof URL){
                resultMap.put(key, new AnyURIValue(value.toString()));
            } else {
                resultMap.put(key, new StringValue(value.toString()));
            }
        }
        return resultMap;
    }
}
