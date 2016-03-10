package com.belk.pep.logger;

import java.io.Serializable;

/**
 * The Class ProductEnrichmentLogger.
 */
public class ProductEnrichmentLogger implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5825866863920598439L;

	/** The logger. */
	private BelkLogger logger;

	/**
	 * Instantiates a new product enrichment logger.
	 *
	 * @param clazz
	 *            the clazz
	 */
	public ProductEnrichmentLogger(Class<?> clazz) {
		BelkLogger logger = new BelkLogger(clazz);
		this.logger = logger;
	}

	/**
	 * Debug.
	 *
	 * @param debugMessage
	 *            the debug message
	 */
	public void debug(Object debugMessage) {
		// If Statements Must Use Braces - Sonar Fix
		if (logger.isDebugEnabled()) {
			logger.debug(debugMessage);
		}
	}

	/**
	 * Debug.
	 *
	 * @param debugMessage
	 *            the debug message
	 * @param ex
	 *            the ex
	 */
	public void debug(Object debugMessage, Throwable ex) {
		if (logger.isDebugEnabled()) {
			logger.debug(debugMessage, ex);
		}
	}

	/**
	 * Debug.
	 *
	 * @param debugMessage
	 *            the debug message
	 * @param debugValues
	 *            the debug values
	 */
	public void debug(final String debugMessage, final Object... debugValues) {
		if (logger.isDebugEnabled()) {
			String message;
			if (debugValues != null) {
				StringBuilder buffer = new StringBuilder();
				buffer.append(debugMessage);
				// Removed if block b/c debugValues null check was done already
				for (Object object : debugValues) {
					// Fix to Load of known null value - Sonar Fix
					if (object != null) {
						buffer.append(object.toString());
					}
				}
				message = buffer.toString();
			} else {
				message = debugMessage;
			}
			logger.debug(message);
		}
	}

	/**
	 * Error.
	 *
	 * @param errorMessage
	 *            the error message
	 */
	public void error(Object errorMessage) {
		if (logger.isErrorEnabled()) {
			logger.error(errorMessage);
		}
	}

	/**
	 * Error.
	 *
	 * @param errorMessage
	 *            the error message
	 * @param ex
	 *            the ex
	 */
	public void error(Object errorMessage, Throwable ex) {
		if (logger.isErrorEnabled()) {
			logger.error(errorMessage, ex);
		}
	}

	/**
	 * Fatal.
	 *
	 * @param fatalMessage
	 *            the fatal message
	 */
	public void fatal(Object fatalMessage) {
		if (logger.isFatalEnabled()) {
			logger.fatal(fatalMessage);
		}
	}

	/**
	 * Fatal.
	 *
	 * @param fatalMessage
	 *            the fatal message
	 * @param ex
	 *            the ex
	 */
	public void fatal(Object fatalMessage, Throwable ex) {
		if (logger.isFatalEnabled()) {
			logger.fatal(fatalMessage, ex);
		}
	}

	/**
	 * Info.
	 *
	 * @param infoMessage
	 *            the info message
	 */
	public void info(Object infoMessage) {
		if (logger.isInfoEnabled()) {
			logger.info(infoMessage);
		}
	}

	/**
	 * Info.
	 *
	 * @param infoMessage
	 *            the info message
	 * @param ex
	 *            the ex
	 */
	public void info(Object infoMessage, Throwable ex) {
		if (logger.isInfoEnabled()) {
			logger.info(infoMessage, ex);
		}
	}

	/**
	 * Info.
	 *
	 * @param infoMessage
	 *            the info message
	 * @param infoValues
	 *            the info values
	 */
	public void info(final String infoMessage, final Object... infoValues) {
		if (logger.isInfoEnabled()) {
			String message;
			if (infoValues != null) {
				StringBuilder buffer = new StringBuilder();
				buffer.append(infoMessage);
				for (Object object : infoValues) {
					// Fix to Load of known null value - Sonar Fix
					if (object != null) {
						buffer.append(object.toString());
					}
				}
				message = buffer.toString();
			} else {
				message = infoMessage;
			}
			logger.info(message);
		}
	}

	/**
	 * Checks if is debug enabled.
	 *
	 * @return true, if is debug enabled
	 */
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/**
	 * Checks if is error enabled.
	 *
	 * @return true, if is error enabled
	 */
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	/**
	 * Checks if is fatal enabled.
	 *
	 * @return true, if is fatal enabled
	 */
	public boolean isFatalEnabled() {
		return logger.isFatalEnabled();
	}

	/**
	 * Checks if is info enabled.
	 *
	 * @return true, if is info enabled
	 */
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/**
	 * Checks if is trace enabled.
	 *
	 * @return true, if is trace enabled
	 */
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	/**
	 * Checks if is warn enabled.
	 *
	 * @return true, if is warn enabled
	 */
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	/**
	 * Trace.
	 *
	 * @param traceMessage
	 *            the trace message
	 */
	public void trace(Object traceMessage) {
		if (logger.isTraceEnabled()) {
			logger.trace(traceMessage);
		}
	}

	/**
	 * Warn.
	 *
	 * @param warnMessage
	 *            the warn message
	 */
	public void warn(Object warnMessage) {
		if (logger.isWarnEnabled()) {
			logger.warn(warnMessage);
		}
	}

	/**
	 * Warn.
	 *
	 * @param warnMessage
	 *            the warn message
	 * @param ex
	 *            the ex
	 */
	public void warn(Object warnMessage, Throwable ex) {
		if (logger.isWarnEnabled()) {
			logger.warn(warnMessage, ex);
		}
	}

}
