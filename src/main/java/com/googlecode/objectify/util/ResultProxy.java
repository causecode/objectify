package com.googlecode.objectify.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.googlecode.objectify.Result;


/**
 * A dynamic proxy that wraps a Result<?> value.  For example, if you had a Result<List<String>>, the
 * proxy would implement List<String> and call through to the inner object.
 */
public class ResultProxy<T> implements InvocationHandler
{
	/**
	 * Create a ResultProxy for the given interface.
	 */
	@SuppressWarnings("unchecked")
	public static <S> S create(Class<? super S> interf, Result<S> result) {
		return (S)Proxy.newProxyInstance(result.getClass().getClassLoader(), new Class[] { interf }, new ResultProxy<S>(result));
	}

	Result<T> result;

	private ResultProxy(Result<T> result) {
		this.result = result;
	}

	@Override
	public Object invoke(Object obj, Method meth, Object[] params) throws Throwable {
		return meth.invoke(result.now(), params);
	}
}
