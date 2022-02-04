package com.sbrf.reboot.functionalinterface;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sbrf.reboot.utils.JSONUtils;
import com.sbrf.reboot.utils.XMLUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionalInterfaceTest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class SomeObject {
        private String objectName;
    }

    @FunctionalInterface
    interface ObjectToJsonFunction<T> {
        String applyAsJson(T t) throws JsonProcessingException;
    }

    static class ListConverter<T> {
        public List<String> toJsonsList(@NonNull List<T> someObjects, ObjectToJsonFunction<T> objectToJsonFunction)
                throws JsonProcessingException {
            List<String> result = new ArrayList<>();
            if (someObjects.isEmpty())
                throw new IllegalArgumentException("The list is empty");

            //added code
            for (T object : someObjects) {
                result.add(objectToJsonFunction.applyAsJson(object));
            }

            return result;
        }
    }

    @Test
    public void successCallFunctionalInterface() throws JsonProcessingException {
        ListConverter<SomeObject> ListConverter = new ListConverter<>();

        ObjectToJsonFunction<SomeObject> objectToJsonFunction = someObject -> {
            //added code
            return JSONUtils.toJSON(someObject);
        };

        List<String> strings = ListConverter.toJsonsList(
                Arrays.asList(
                        new SomeObject("Object-1"),
                        new SomeObject("Object-2")
                ),
                objectToJsonFunction
        );

        Assertions.assertTrue(strings.contains("{\"objectName\":\"Object-1\"}"));
        Assertions.assertTrue(strings.contains("{\"objectName\":\"Object-2\"}"));
    }


    static class ListTransformer {
        public List<Integer> transformIfPredicate(List<Integer> list,
                                                  Predicate<List<Integer>> predicate,
                                                  Function<Integer, Integer> action) {
            if (predicate.test(list)) {
                list = list.stream().map(action).collect(Collectors.toList());
            }
            return list;
        }
    }

    @Test
    public void correctApplyingActionToList() {
        List<Integer> uniqueEnoughList = new ArrayList<>();
        uniqueEnoughList.add(3);
        uniqueEnoughList.add(7);
        uniqueEnoughList.add(9);
        uniqueEnoughList.add(1);

        List<Integer> uniqueNotEnoughList = new ArrayList<>();
        uniqueNotEnoughList.add(3);
        uniqueNotEnoughList.add(3);
        uniqueNotEnoughList.add(9);
        uniqueNotEnoughList.add(1);


        Predicate<List<Integer>> isUniqueSizeEnough = list -> new HashSet<>(list).size() >= 4;

        Function<Integer, Integer> squareElement = el -> el *= el;

        ListTransformer transformer = new ListTransformer();

        List<Integer> transformedUniqueList =
                transformer.transformIfPredicate(uniqueEnoughList, isUniqueSizeEnough, squareElement);

        List<Integer> transformedNotUniqueList =
                transformer.transformIfPredicate(uniqueNotEnoughList, isUniqueSizeEnough, squareElement);

        Assertions.assertEquals(Arrays.asList(9, 49, 81, 1), transformedUniqueList);
        Assertions.assertEquals(Arrays.asList(3, 3, 9, 1), transformedNotUniqueList);
    }

}
