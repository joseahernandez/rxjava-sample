package joseahernandez.rxjavasample.sample05;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

public class Sample05 {
    public static void main(String []args) throws Exception {
        System.out.println("Start process");
        CountDownLatch countDown = new CountDownLatch(1);

        StringBuffer buffer = new StringBuffer();

        readFile()
                .subscribeOn(Schedulers.computation())
                .subscribe(
                        result -> {
                            buffer.append(result);
                            System.out.println("Line read");
                        },
                        error -> System.out.println("Error reading file: " + error.getMessage()),
                        () -> {
                            System.out.println("File read with content: " + buffer.toString());
                            countDown.countDown();
                        }
                );
        System.out.println("End process");

        countDown.await();
    }

    private static Observable<String> readFile() {
        return Observable.create(subscriber -> {
            BufferedInputStream bis = new BufferedInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("file.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(bis));

            subscriber.onStart();
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    subscriber.onNext(line);
                    Thread.sleep(1);
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }

            subscriber.onCompleted();
        });
    }
}
