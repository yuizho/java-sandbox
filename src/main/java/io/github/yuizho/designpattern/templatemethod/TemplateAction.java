package io.github.yuizho.designpattern.templatemethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TemplateAction {
    protected Logger logger = LoggerFactory.getLogger(TemplateAction.class);

    public final void execute() {
        Throwable throwable = null;

        startLog();

        try {
            doExecute();
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

    protected abstract void doExecute();

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
