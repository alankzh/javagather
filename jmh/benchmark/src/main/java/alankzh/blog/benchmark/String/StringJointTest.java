package alankzh.blog.benchmark.String;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class StringJointTest {

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(StringJointTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(options).run();
    }


    static CustomerRes customerRes = new CustomerRes()
            .setCustomerId("10086")
            .setName("诸葛亮")
            .setIdentityNumber("3334333433343334");

    @Benchmark
    public void toStringJoinWithPlus(){
        customerRes.toStringJoinWithPlus();
    }

    @Benchmark
    public void toStringJoinWithBuilder(){
        customerRes.toStringJoinWithBuilder();
    }

    @Benchmark
    public void toStringJoinWithPlusMask(){
        customerRes.toStringJoinWithPlusMask();
    }

    @Benchmark
    public void toStringJoinWithBuilderMask(){
        customerRes.toStringJoinWithBuilderMask();
    }


}
