package org.craftedsw.acceptancetests;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class JBehaveParameterized extends Suite {
	
	private final ArrayList<Runner> runners = new ArrayList<Runner>();
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface JBehaveStoryPaths {
	}
	
	private class TestClassRunnerForJBehaveStory extends BlockJUnit4ClassRunner {
		
		private final String jbehaveStoryPath;
		
		TestClassRunnerForJBehaveStory(final Class<?> type, final String jbehaveStoryPath) throws InitializationError {
			super(type);
			this.jbehaveStoryPath = jbehaveStoryPath;
		}
		
		public Object createTest() throws Exception {
			return getTestClass().getOnlyConstructor().newInstance(jbehaveStoryPath);
		}
		
		protected String getName() {
			return String.format("[%s]", FilenameUtils.getName(jbehaveStoryPath));
		}
		
		protected String testName(final FrameworkMethod method) {
			return String.format("[%s]", FilenameUtils.getName(jbehaveStoryPath));
		}
		
		protected void validateConstructor(final List<Throwable> errors) {
			validateOnlyOneConstructor(errors);
		}
		
		protected Statement classBlock(final RunNotifier notifier) {
			return childrenInvoker(notifier);
		}
		
	}

	public JBehaveParameterized(final Class<?> clazz) throws Throwable {
		super(clazz, Collections.<Runner>emptyList());
		Collection<String> parametersList = getParametersList(getTestClass());
		for (String jbehaveStoryPath : parametersList) {
			runners.add(new TestClassRunnerForJBehaveStory(getTestClass().getJavaClass(), jbehaveStoryPath));
		}
	}

	protected List<Runner> getChildren() {
		return runners;
	}
	
	@SuppressWarnings("unchecked")
	private Collection<String> getParametersList(final TestClass clazz) throws Throwable {
		return (Collection<String>) getParametersMethod(clazz).invokeExplosively(null);
	}
	
	private FrameworkMethod getParametersMethod(TestClass testClass) throws Exception {
		List<FrameworkMethod> methods = testClass.getAnnotatedMethods(JBehaveStoryPaths.class);
		for (FrameworkMethod each : methods) {
			int modifiers = each.getMethod().getModifiers();
			if (isStatic(modifiers) && isPublic(modifiers)) {
				return each;
			}
		}
		
		throw new Exception(
				"No public static method which returns collection of JBehave stories paths on class " 
						+ testClass.getName());
	}
}
