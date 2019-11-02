package io.github.yuizho.designpattern.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Processor {
    protected Logger logger = LoggerFactory.getLogger(Processor.class);

    private final Action action;

    public Processor (Action action) {
        this.action = action;
    }

    public final void execute() {
        Throwable throwable = null;

        startLog();

        try {
            action.execute();
        } catch (RuntimeException e) {
            throwable = e;
            throw e;
        } catch (Error e) {
            throwable = e;
            throw e;
        } finally {
            endLog(throwable);
        }
    }

    private void startLog() {
        if (logger.isDebugEnabled()) {
            logger.debug("Start: "
                    + getClass().getSimpleName()
                    + "#execute()"
            );
        }
    }

    private void endLog(Throwable throwable) {
        if (logger.isDebugEnabled()) {
            logger.debug("End  : "
                    + getClass().getSimpleName()
                    + "#execute()"
                    , throwable
            );
        }
    }
}
