package com.sbrf.reboot.concurrency;

import com.sbrf.reboot.service.concurrency.Task;
import com.sbrf.reboot.service.concurrency.TaskExecutorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class TaskExecutorServiceTest {

    @Test
    public void successRunMultithreading() throws InterruptedException {

        Task task = Mockito.mock(Task.class);
        CountDownLatch latch = new CountDownLatch(2);

        TaskExecutorService taskExecutorService = new TaskExecutorService(2, 0);

        doAnswer((e -> {
            latch.countDown();
            return null;
        })).when(task).run();

        taskExecutorService.execute(task);

        latch.await();

        assertEquals(0, latch.getCount());
        verify(task, times(2)).run();

        taskExecutorService.shutdown();
    }

    @Test
    public void successRunNoMultithreading() throws InterruptedException {

        Task task = Mockito.mock(Task.class);
        Task taskIO = Mockito.mock(Task.class);

        CountDownLatch latch = new CountDownLatch(0);
        CountDownLatch latchIO = new CountDownLatch(0);

        TaskExecutorService taskExecutorService = new TaskExecutorService(-3, 0);

        doAnswer((e -> {
            latch.countDown();
            return null;
        })).when(task).run();

        doAnswer((e -> {
            latchIO.countDown();
            return null;
        })).when(taskIO).run();

        taskExecutorService.execute(task);
        taskExecutorService.executeIO(taskIO);

        latch.await();
        latchIO.await();

        assertEquals(0, latch.getCount());
        verify(task, times(0)).run();

        assertEquals(0, latchIO.getCount());
        verify(taskIO, times(0)).run();

        taskExecutorService.shutdown();
    }

    @Test
    public void successRunBothMultithreading() throws InterruptedException {

        Task task = Mockito.mock(Task.class);
        Task taskIO = Mockito.mock(Task.class);

        CountDownLatch latch = new CountDownLatch(4);
        CountDownLatch latchIO = new CountDownLatch(3);

        TaskExecutorService taskExecutorService = new TaskExecutorService(4, 3);

        doAnswer((e -> {
            latch.countDown();
            return null;
        })).when(task).run();

        doAnswer((e -> {
            latchIO.countDown();
            return null;
        })).when(taskIO).run();

        taskExecutorService.execute(task);
        taskExecutorService.executeIO(taskIO);

        latch.await();
        latchIO.await();

        assertEquals(0, latch.getCount());
        verify(task, times(4)).run();

        assertEquals(0, latchIO.getCount());
        verify(taskIO, times(3)).run();

        taskExecutorService.shutdown();
    }
}