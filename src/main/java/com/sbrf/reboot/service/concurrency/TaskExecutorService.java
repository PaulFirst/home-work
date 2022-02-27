package com.sbrf.reboot.service.concurrency;

import java.util.concurrent.*;

public class TaskExecutorService {

    private final int numberOfThreads;
    private final int numberOfIOThreads;

    private final ExecutorService service;
    private final ExecutorService serviceIO;

    public TaskExecutorService(int numberOfThreads, int numberOfIOThreads) {
        //пулы создаются напрямую через ThreadPoolExecutor,чтобы была возможность задать 0 значение,
        //если не нужны потоки в пуле на старте
        //service - пул потоков вычисления
        //serviceIO - пул потоков I/O

        this.numberOfThreads = Math.max(numberOfThreads, 0);
        this.service = new ThreadPoolExecutor(this.numberOfThreads, Math.max(this.numberOfThreads, 1),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        this.numberOfIOThreads = Math.max(numberOfIOThreads, 0);
        this.serviceIO = new ThreadPoolExecutor(this.numberOfIOThreads, Math.max(this.numberOfIOThreads, 1),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

    }

    public void execute(Task task) {
        for (int i = 0; i < numberOfThreads; i++)
        {
            service.execute(task);
        }
    }

    public void executeIO(Task task) {
        for (int i = 0; i < numberOfIOThreads; i++) {
            serviceIO.execute(task);
        }
    }

    public void shutdown() {
        service.shutdown();
        serviceIO.shutdown();
    }

}
