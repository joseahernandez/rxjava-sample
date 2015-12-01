package joseahernandez.rxjavasample.sample03;

import rx.Observable;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Sample03 {
    public static void main(String []args) throws Exception {
        System.out.println("Start process");
        readFile()
            .subscribe(
                result -> System.out.println("File read"),
                error -> System.out.println("Error reading file: " + error.getMessage())
            );
        System.out.println("End process");
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
