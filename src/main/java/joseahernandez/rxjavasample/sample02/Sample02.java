package joseahernandez.rxjavasample.sample02;

import rx.Observable;

public class Sample02 {
    public static void main(String []args) throws Exception {
        String letters[] = {"a", "b", "c", "a", "d", "b", "f", "g", "a", "h", "b", "c", "i", "a", "a", "j"};
        Integer numbers[] = { 1, 4, 5, 6, 7, 8, 1, 9, 0, 1, 2, 6, 3, 1, 4, 5, 0, 9, 4, 8, 5, 8 };
        Observable<String> observableString = Observable.from(letters);
        Observable<Integer> observableInteger = Observable.from(numbers);

        Observable.merge(observableString, observableInteger)
            .subscribe(element -> System.out.println(element));
    }
}
