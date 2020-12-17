package top.desq.executor;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import top.desq.executor.model.Script;
import top.desq.executor.repository.InMemoryScriptRepository;
import top.desq.executor.repository.ScriptRepository;
import top.desq.executor.service.ScriptExecutor;

import java.util.Arrays;
import java.util.Collections;

public class MainTest {

    private final ScriptRepository repository = new InMemoryScriptRepository();
    private static final int INITIAL_SCRIPT_ID = 1;
    private static final Integer[] EXPECTED_ORDER_ARRAY = {4, 5, 2, 6, 3, 1};

    /**
     * Script dependencies structure:
     *           1
     *          / \
     *         2   3
     *        / \ / \
     *       4   5   6
     */
    @Test
    public void executionOrderTest() {
        fillDependencies();
        ScriptExecutor scriptExecutor = new ScriptExecutor(repository);
        scriptExecutor.execute(INITIAL_SCRIPT_ID); // execute script with ID = 1 and all its dependencies
        System.out.println("Cache: " + scriptExecutor.getCache());
        Assert.assertArrayEquals(EXPECTED_ORDER_ARRAY, scriptExecutor.getCache().toArray());
    }

    /**
     * Script dependencies structure:
     *           1 <----\
     *          / \      \
     *         2   3 <---/
     *        / \ / \   /
     *       4   5   6 /
     */
    @Test(expected = StackOverflowError.class)
    public void circularDependenciesTest() {
        fillCircularDependencies();
        ScriptExecutor scriptExecutor = new ScriptExecutor(repository);
        scriptExecutor.execute(INITIAL_SCRIPT_ID); // execute script with ID = 1 and all its dependencies
    }

    @After
    public void clear() {
        repository.clear();
    }

    private void fillDependencies() {
        repository.add(1, new Script(1, Arrays.asList(2, 3)));
        repository.add(2, new Script(2, Arrays.asList(4, 5)));
        repository.add(3, new Script(3, Arrays.asList(5, 6)));
        repository.add(4, new Script(4, Collections.emptyList()));
        repository.add(5, new Script(5, Collections.emptyList()));
        repository.add(6, new Script(6, Collections.emptyList()));
    }

    private void fillCircularDependencies() {
        fillDependencies();
        repository.add(6, new Script(6, Arrays.asList(1, 3)));
    }

}