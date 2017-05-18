package com.denis.learning.puzzle;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;

public class MemoryPuzzleWithLambdas {
	static class A {
	    //http://stackoverflow.com/questions/5054360/do-anonymous-classes-always-maintain-a-reference-to-their-enclosing-instance
        //instances of anonymous inner classes hold on to a reference to their enclosing instances
        public Runnable get() {
			return new Runnable() {
			    //final A this$0;
				public void run() {
					System.out.println("Hi from A");
				}
			};
		}

        protected void finalize() throws Throwable {
            //never called since runnable holds reference to outer class
            System.out.println("A finalized");
        }
	}

    static class B {
        public Runnable get() {
            //lambdas that do not capture members from the enclosing instance do not hold a reference to it
            return () -> System.out.println("Hi from B");
        }

        protected void finalize() throws Throwable {
            //called once
            System.out.println("B finalized");
        }
    }

    static class C {
        private int count = 0;

        public Runnable get() {
            return () -> System.out.println("Hi from C #" + ++count);
        }

        protected void finalize() throws Throwable {
            System.out.println("C finalized");
        }
    }

    static class D {
        private static int count = 0;

        public Runnable get() {
            return () -> System.out.println("Hi from D #" + ++count);
        }

        protected void finalize() throws Throwable {
            System.out.println("D finalized");
        }
    }

    static class E {
       // private int count = ThreadLocalRandom.current().nextInt();
        private int count = new Random().nextInt();

        public Runnable get() {
            int count = this.count;
            return () -> System.out.println("Hi from E " + count);
        }

        protected void finalize() throws Throwable {
            System.out.println("E finalized");
        }
    }

    static class F {
        public Runnable get() {
            return this::friendly;
        }

        public void friendly() {
            System.out.println("Hi from F");
        }

        protected void finalize() throws Throwable {
            System.out.println("F finalized");
        }
    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        //timer.scheduleAtFixedRate(new A().get(), 1, 1, SECONDS);
        //timer.scheduleAtFixedRate(new B().get(), 1, 1, SECONDS);
        //timer.scheduleAtFixedRate(new C().get(), 1, 1, SECONDS);
        //timer.scheduleAtFixedRate(new D().get(), 1, 1, SECONDS);
        //timer.scheduleAtFixedRate(new E().get(), 1, 1, SECONDS);
        timer.scheduleAtFixedRate(new F().get(), 1, 1, SECONDS);

        timer.scheduleAtFixedRate(System::gc, 1, 1, SECONDS);
    }

}
