package joseahernandez.rxjavasample.sample07;

import rx.Observable;
import rx.subjects.PublishSubject;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Sample07 {
    public static void main(String []args) throws Exception {
        // Hot Observable, emits events althought no one is subscribed
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS);

        PublishSubject<Long> publishSubject = PublishSubject.create();
        observable.subscribe(publishSubject);

        Thread.sleep(4000);

        publishSubject
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
