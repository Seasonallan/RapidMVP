package com.season.rapiddevelopment;

import com.season.example.model.ModelFactory;
import com.season.rapiddevelopment.tools.Console;

import junit.framework.TestCase;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends TestCase {
    public ApplicationTest() {
    }

    public void test() throws Exception{

        ModelFactory.net().kuaifang().video().getVideo(Configure.PAGE_SIZE, 1, "0", null);

        Console.logNetMessage("debugModel>> ");
        ModelFactory.local().file().key().setValue("test", "value", new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Console.logNetMessage("setValueonSubscribe>> ");


            }

            @Override
            public void onNext(@NonNull Boolean o) {
                Console.logNetMessage("setValueonNext>> "+o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Console.logNetMessage("setValueonError>> "+e.getMessage());

            }

            @Override
            public void onComplete() {
                Console.logNetMessage("setValueonComplete>> ");

            }
        });
//        ModelFactory.local().file().key().getValue("test", new Observer<Object>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                Console.logNetMessage("onSubscribe>> ");
//
//
//            }
//
//            @Override
//            public void onNext(@NonNull Object o) {
//                Console.logNetMessage("onNext>> "+o);
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Console.logNetMessage("onError>> "+e.getMessage());
//
//            }
//
//            @Override
//            public void onComplete() {
//                Console.logNetMessage("onComplete>> ");
//
//            }
//        });
    }

    public void testEx() throws Exception {
        int input1 = 1;
        int input2 = 2;
        assertEquals(input1, input2);
    }

}