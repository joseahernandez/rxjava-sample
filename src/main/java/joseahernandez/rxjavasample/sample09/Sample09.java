package joseahernandez.rxjavasample.sample09;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

public class Sample09 {
    public static void main(String []args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        getResponse("http://google.com")
            .subscribeOn(Schedulers.io())
            .subscribe(
                response -> System.out.println(response),
                error -> System.out.println("Error: " + error.getMessage()),
                () -> countDownLatch.countDown()
            );

        do {
            System.out.println("Doing some work");
            Thread.sleep(500);
        } while (countDownLatch.getCount() == 1);
    }

    private static Observable<String> getResponse(String endpoint) {
        return Observable.create(subscriber -> {
            subscriber.onStart();

            try {
                URL url = new URL(endpoint);
                URLConnection connection = url.openConnection();
                StringBuffer sb = new StringBuffer();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();

                Thread.sleep(1000);

                subscriber.onNext(sb.toString());
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
