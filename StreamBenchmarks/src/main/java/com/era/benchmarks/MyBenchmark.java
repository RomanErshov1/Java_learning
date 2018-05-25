package com.era.benchmarks;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class MyBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState{
        public static final int SEED = 42;
        public static final int ARRAY_LENGTH = 1_000_000;

        volatile List<Person> list;

        @Setup
        public void initState(){
            Random random = new Random(SEED);
            Person[] array = new Person[ARRAY_LENGTH];
            for (int i = 0; i < array.length; i++){
                Person person = new Person();
                person.setName("N" + random.nextInt());
                person.setAge(random.nextInt(200));
                person.setChildren(random.nextInt(100));
                array[i] = person;
            }
            this.list = Arrays.asList(array);
        }
    }

    @Benchmark
    public List<Person> streamApi(BenchmarkState state){
        return state.list.stream()
                .filter(person -> person.getAge() > 18)
                .filter(person -> person.getChildren() > 50)
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Person> streamApiOneFilter(BenchmarkState state){
        return  state.list.stream()
                .filter(person -> person.getAge() > 18 && person.getChildren() > 50)
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Person> streamApiParallel(BenchmarkState state){
        return state.list.stream()
                .parallel()
                .filter(person -> person.getAge() > 18)
                .filter(person -> person.getChildren() > 50)
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Person> streamApiOneFilterParallel(BenchmarkState state){
        return  state.list.stream()
                .parallel()
                .filter(person -> person.getAge() > 18 && person.getChildren() > 50)
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Person> guava(BenchmarkState state){
        List<Person> list = state.list;
        Iterable<Person> result = Iterables.filter(list, new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() > 18;
            }
        });

        result = Iterables.filter(result, new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getChildren() > 50;
            }
        });
        return Lists.newArrayList(result);
    }
}
