package joseahernandez.rxjavasample.sample08;

import rx.Observable;
import rx.observables.ConnectableObservable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Sample08 {
    public static void main(String []args) throws Exception {
        // Hot Observable, emits events althought no one is subscribed
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ConnectableObservable<Long> connectableObservable = Observable.interval(1, TimeUnit.SECONDS).publish();

        // Start emiting events
        connectableObservable.connect();

        Thread.sleep(4000);

        connectableObservable
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
