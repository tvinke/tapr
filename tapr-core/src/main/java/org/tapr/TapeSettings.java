package org.tapr;

import java.io.Serializable;

public interface TapeSettings extends Serializable {

    /**
     * Enables real-time logging of method invocations on this tape. Can be used
     * during test debugging in order to find wrong interactions with this mock.
     * <p>
     * Invocations are logged as they happen to the standard output stream.
     * <p>
     * Calling this method multiple times makes no difference.
     * <p>
     * Example:
     * <pre class="code"><code class="java">
     * SomeObject tapeWithLogger = tape(SomeObject.class, withSettings().verboseLogging());
     * </code></pre>
     *
     * @return this settings instance
     */
	TapeSettings verboseLogging();
}
