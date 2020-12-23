package top.desq.executor;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import top.desq.executor.repository.InMemoryScriptRepository;
import top.desq.executor.repository.ScriptRepository;
import top.desq.executor.service.ScriptExecutor;

public class MainTest {

    private final ScriptRepository repository = new InMemoryScriptRepository();

    /**
     * Script dependencies structure:
     *          1
     *         / \
     *        2   3
     *       / \ / \
     *      4   5   6
     */
    @Test
    public void newVersionsTest() {
        // old set of data
        ScriptExecutor scriptExecutor = new ScriptExecutor(repository);
        TestData.fillDependencies(repository);
        scriptExecutor.execute(TestData.INITIAL_SCRIPT_ID);
        // new set of data
        repository.clear();
        TestData.fillDependenciesWithNewVersions(repository);
        scriptExecutor.execute(TestData.INITIAL_SCRIPT_ID);

        Assert.assertEquals(TestData.EXPECTED_ORDER_MAP, scriptExecutor.getCache());
    }

    /**
     * Script dependencies structure:
     *          1
     *         / \
     *        2   3
     *       / \ / \
     *      4   5   6
     */
    @Test
    public void executionOrderTest() {
        TestData.fillDependencies(repository);
        ScriptExecutor scriptExecutor = new ScriptExecutor(repository);
        scriptExecutor.execute(TestData.INITIAL_SCRIPT_ID);
        Assert.assertArrayEquals(TestData.EXPECTED_ORDER_ARRAY, scriptExecutor.getCache().keySet().toArray());
        scriptExecutor.clearCache();
    }

    /**
     * Script dependencies structure:
     *          1 <----\
     *         / \      \
     *        2   3 <---/
     *       / \ / \   /
     *      4   5   6 /
     */
    @Test
    public void circularDependenciesTest() {
        TestData.fillCircularDependencies(repository);
        ScriptExecutor scriptExecutor = new ScriptExecutor(repository);
        scriptExecutor.execute(TestData.INITIAL_SCRIPT_ID);
        scriptExecutor.clearCache();
    }

    /**
     * Script dependencies structure:
     *          1
     *         / \
     *        2   3
     *       / \ / \
     *      4   5  null
     */
    @Test
    public void nonExistentScriptTest() {
        TestData.fillDependenciesWithIncorrectId(repository);
        ScriptExecutor scriptExecutor = new ScriptExecutor(repository);
        scriptExecutor.execute(TestData.INITIAL_SCRIPT_ID);
        scriptExecutor.clearCache();
    }

    @After
    public void clear() {
        repository.clear();
    }

}