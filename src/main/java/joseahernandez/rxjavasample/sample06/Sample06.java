package joseahernandez.rxjavasample.sample06;

import rx.Observable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Sample06 {
    public static void main(String []args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Observable.interval(1, TimeUnit.SECONDS)
            .subscribe(timer -> {
                System.out.println("Tick " + timer);
                if (timer == 5) {
                    countDownLatch.countDown();
                }
            });

        System.out.println("End");
        countDownLatch.await();
    }
}
