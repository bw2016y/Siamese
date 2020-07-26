package org.apache.spark.daslab.sql.internal;


import java.net.URL;


//todo
import org.apache.spark.util.MutableURLClassLoader;

/**
 * This class loader cannot be closed (its `close` method is a no-op).
 */
public class NonClosableMutableURLClassLoader extends MutableURLClassLoader {

    static {
        ClassLoader.registerAsParallelCapable();
    }

    public NonClosableMutableURLClassLoader(ClassLoader parent) {
        super(new URL[]{}, parent);
    }

    @Override
    public void close() {}
}
