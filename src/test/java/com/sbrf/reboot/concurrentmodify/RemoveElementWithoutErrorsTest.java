package com.sbrf.reboot.concurrentmodify;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveElementWithoutErrorsTest {

    @Test
    public void successConcurrentModificationException() {

        List<Integer> list = new ArrayList() {{
            add(1);
            add(2);
            add(3);
        }};

        assertThrows(ConcurrentModificationException.class, () -> {
            
            for (Integer integer : list) {
                list.remove(1);
            }
            
        });

    }

    @Test
    public void successRemoveElementWithoutErrorsVar1() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.removeIf(num -> num == 3);

        assertEquals(Arrays.asList(1, 2, 4), list);
    }

    @Test
    public void successRemoveElementWithoutErrorsVar2() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list = list.stream()
                .filter(num -> num != 3)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(1, 2, 4), list);
    }

    @Test
    public void successRemoveElementWithoutErrorsVar3() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> excludedList = new ArrayList<>();

        for(Integer num : list) {
            if (num == 3) {
                excludedList.add(num);
            }
        }
        list.removeAll(excludedList);

        assertEquals(Arrays.asList(1, 2, 4), list);
    }

}
