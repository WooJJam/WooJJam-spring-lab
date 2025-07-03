package co.kr.woojjam.asynchronous;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExecutionOrder {

    public static void main(String[] args) {
        System.out.println("▶ main 시작");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            System.out.println("📦 1. supplyAsync 실행됨");
            return "Hello";
        });

        future.thenApply(result -> {
            System.out.println("📦 2. thenApply 실행됨: " + result);
            return result + " World";
        }).thenAccept(result -> {
            System.out.println("📦 3. thenAccept 실행됨: " + result);
        });

        System.out.println("▶ main 종료 (콜백 실행은 비동기)");
        sleep(3000); // main 쓰레드 종료 방지
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
