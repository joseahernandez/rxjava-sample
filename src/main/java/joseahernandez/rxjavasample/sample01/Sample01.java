package joseahernandez.rxjavasample.sample01;

import rx.Observable;

public class Sample01 {
    public static void main(String []args) {
        String letters[] = {"a", "b", "c", "a", "d", "b", "f", "g", "a", "h", "b", "c", "i", "a", "a", "j"};
        Observable<String> observable = Observable.from(letters);
        observable
                .map(letter -> letter.equals("b") ? "a" : letter)
                .filter(letterChanged -> letterChanged.equals("a"))
                .count()
                .subscribe(count -> System.out.println(count));
    }
}
