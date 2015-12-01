package joseahernandez.rxjavasample.sample04;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

public class Sample04 {
    public static void main(String []args) throws Exception {
        System.out.println("Start process");
        CountDownLatch countDown = new CountDownLatch(1);

        readFile()
            .subscribeOn(Schedulers.computation())
            .subscribe(
                result -> System.out.println("File read"),
                error -> System.out.println("Error reading file: " + error.getMessage()),
                () -> countDown.countDown()
            );
        System.out.println("End process");

        countDown.await();
    }

    private static Observable<String> readFile() {
        return Observable.create(subscriber -> {
            BufferedInputStream bis = new BufferedInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("file.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(bis));
            StringBuffer sb = new StringBuffer();

            subscriber.onStart();
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    Thread.sleep(1);
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }

            subscriber.onNext(sb.toString());
            subscriber.onCompleted();
        });
    }
}
