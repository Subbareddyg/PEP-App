package com.belk.pep.logger;

import java.io.Serializable;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * The Class BelkLogger.
 *
 * @author AFUSOS3
 */
public class BelkLogger implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7543060329220552420L;

	/** The logger. */
	private Logger theLogger = null;

	/**
	 * Instantiates a new belk logger.
	 *
	 * @param paramClass
	 *            the param class
	 */
	public BelkLogger(Class paramClass) {
		this.theLogger = Logger.getLogger(paramClass);
	}

	/**
	 * Instantiates a new belk logger.
	 *
	 * @param paramString
	 *            the param string
	 */
	public BelkLogger(String paramString) {
		this.theLogger = Logger.getLogger(paramString);
	}

	/**
	 * Debug.
	 *
	 * @param paramObject
	 *            the param object
	 */
	public void debug(Object paramObject) {
		this.theLogger.debug(paramObject);
	}

	/**
	 * Debug.
	 *
	 * @param paramObject
	 *            the param object
	 * @param paramThrowable
	 *            the param throwable
	 */
	public void debug(Object paramObject, Throwable paramThrowable) {
		this.theLogger.debug(paramObject, paramThrowable);
	}

	/**
	 * Error.
	 *
	 * @param paramObject
	 *            the param object
	 */
	public void error(Object paramObject) {
		this.theLogger.error(paramObject);
	}

	/**
	 * Error.
	 *
	 * @param paramObject
	 *            the param object
	 * @param paramThrowable
	 *            the param throwable
	 */
	public void error(Object paramObject, Throwable paramThrowable) {
		this.theLogger.error(paramObject, paramThrowable);
	}

	/**
	 * Fatal.
	 *
	 * @param paramObject
	 *            the param object
	 */
	public void fatal(Object paramObject) {
		this.theLogger.fatal(paramObject);
	}

	/**
	 * Fatal.
	 *
	 * @param paramObject
	 *            the param object
	 * @param paramThrowable
	 *            the param throwable
	 */
	public void fatal(Object paramObject, Throwable paramThrowable) {
		this.theLogger.fatal(paramObject, paramThrowable);
	}

	/**
	 * Info.
	 *
	 * @param paramObject
	 *            the param object
	 */
	public void info(Object paramObject) {
		this.theLogger.info(paramObject);
	}

	/**
	 * Info.
	 *
	 * @param paramObject
	 *            the param object
	 * @param paramThrowable
	 *            the param throwable
	 */
	public void info(Object paramObject, Throwable paramThrowable) {
		this.theLogger.info(paramObject, paramThrowable);
	}

	/**
	 * Checks if is debug enabled.
	 *
	 * @return true, if is debug enabled
	 */
	public boolean isDebugEnabled() {
		return this.theLogger.isDebugEnabled();
	}

	/**
	 * Checks if is error enabled.
	 *
	 * @return true, if is error enabled
	 */
	public boolean isErrorEnabled() {
		return this.theLogger.isEnabledFor(Level.ERROR);
	}

	/**
	 * Checks if is fatal enabled.
	 *
	 * @return true, if is fatal enabled
	 */
	public boolean isFatalEnabled() {
		return this.theLogger.isEnabledFor(Level.FATAL);
	}

	/**
	 * Checks if is info enabled.
	 *
	 * @return true, if is info enabled
	 */
	public boolean isInfoEnabled() {
		return this.theLogger.isInfoEnabled();
	}

	/**
	 * Checks if is trace enabled.
	 *
	 * @return true, if is trace enabled
	 */
	public boolean isTraceEnabled() {
		return this.theLogger.isInfoEnabled();
	}

	/**
	 * Checks if is warn enabled.
	 *
	 * @return true, if is warn enabled
	 */
	public boolean isWarnEnabled() {
		return this.theLogger.isEnabledFor(Level.WARN);
	}

	/**
	 * Sets the configurator.
	 */
	protected void setConfigurator() {
	}

	/**
	 * Trace.
	 *
	 * @param paramObject
	 *            the param object
	 */
	public void trace(Object paramObject) {
		this.theLogger.trace(paramObject);
	}

	/**
	 * Trace.
	 *
	 * @param paramObject
	 *            the param object
	 * @param paramThrowable
	 *            the param throwable
	 */
	public void trace(Object paramObject, Throwable paramThrowable) {
		this.theLogger.trace(paramObject, paramThrowable);
	}

	/**
	 * Warn.
	 *
	 * @param paramObject
	 *            the param object
	 */
	public void warn(Object paramObject) {
		this.theLogger.warn(paramObject);
	}

	/**
	 * Warn.
	 *
	 * @param paramObject
	 *            the param object
	 * @param paramThrowable
	 *            the param throwable
	 */
	public void warn(Object paramObject, Throwable paramThrowable) {
		this.theLogger.warn(paramObject, paramThrowable);
	}

}
