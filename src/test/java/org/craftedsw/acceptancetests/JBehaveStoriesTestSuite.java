package org.craftedsw.acceptancetests;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.craftedsw.acceptancetests.steps.StatementPrintingSteps;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JBehaveParameterized.class)
public class JBehaveStoriesTestSuite extends ConfigurableEmbedder {
	
	private static final String SINGLE_JBEHAVE_TEST_PARAMETER = "story";
	private final String storyPath;
	
	@JBehaveParameterized.JBehaveStoryPaths
	public static Collection<String> storyPaths() {
		return new StoryFinder().findPaths(
					codeLocationFromClass(JBehaveStoriesTestSuite.class), 
					storyPattern(), 
					"**/excluded*.story");
	}
	
	private static String storyPattern() {
		String testName = System.getProperty(SINGLE_JBEHAVE_TEST_PARAMETER);
		return (testName != null) 
					? "**/" + testName + ".story"
					: "**/*.story";
	}

	public JBehaveStoriesTestSuite(final String jbehaveStoryPath) {
		this.storyPath = jbehaveStoryPath;
	}
	
	@Before
	public void before() {
		configuredEmbedder().embedderControls()
							.doGenerateViewAfterStories(true)
							.doIgnoreFailureInStories(false)
							.doIgnoreFailureInView(true)
							.useStoryTimeoutInSecs(60);
	}
	
	@Override
	public Configuration configuration() {
		Class<? extends Embeddable> embeddableClass = this.getClass();
		// Start from default ParameterConverters instance
		ParameterConverters parameterConverters = new ParameterConverters();
		// factory to allow parameter conversion and loading from external
		// resources (used by StoryParser too)
		ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(
				new LocalizedKeywords(), new LoadFromClasspath(embeddableClass), parameterConverters);
		// and add custom converters
		parameterConverters.addConverters(
				new ParameterConverters.DateConverter(new SimpleDateFormat("dd-MM-yyyy")),
				new ParameterConverters.ExamplesTableConverter(examplesTableFactory),
				new AmountConverter());
	
		return new MostUsefulConfiguration()
						.useStoryLoader(new LoadFromClasspath(embeddableClass))
						.useStoryParser(new RegexStoryParser(examplesTableFactory))
						.useStoryReporterBuilder(
									new StoryReporterBuilder()
										.withCodeLocation(codeLocationFromClass(embeddableClass))
										.withDefaultFormats()
										.withFormats(CONSOLE, TXT, HTML, XML))
						.useParameterConverters(parameterConverters);
	}

	@Override
	public List<CandidateSteps> candidateSteps() {
		return new InstanceStepsFactory(
					configuration(), 
					new StatementPrintingSteps()).createCandidateSteps();
	}

	@Test
	// Run all stories as separate tests
	public void run() throws Throwable {
		Embedder embedder = configuredEmbedder();
		try {
			embedder.runStoriesAsPaths(Arrays.asList(storyPath));
		} finally {
			embedder.generateCrossReference();
		}
	}

}
