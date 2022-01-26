package com.sbrf.reboot.equalshashcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class EqualsHashCodeTest {

    class Car {
        String model;
        String color;
        Calendar releaseDate;
        int maxSpeed;

        @Override
        public boolean equals(Object o) {

            //Рефлексивность: объект должен равняться самому себе
            if (o == this)
                return true;

            //сравнение с null: проверка любого не-null объекта при сравнении с null должна возвращать
            // ложное значение
            if (o == null) {
                return false;
            }


            //симметричность: для любой пары объектов отношение равенства
            // выполняется как в одну, так и в другую сторону

            //транзитивность: для любой тройки объектов при равенстве любых двух пар,
            // выполняется равенство третьей (A = B, B = C -> A = C)

            //согласованность: повторные вызовы проверки равенства двух объектов
            // могут дать иной результат по сравнению с исходным только в случае изменения
            // одного или обоих объектов, в противном случае истинность должна сохраняться неизменной
            if (o.getClass() != this.getClass()) {
                return false;
            }
            Car c = (Car) o;
            return maxSpeed == c.maxSpeed
                    && Objects.equals(model, c.model)
                    && Objects.equals(color, c.color)
                    && Objects.equals(releaseDate, c.releaseDate);

        }

        @Override
        public int hashCode() {
            //внутернняя согласованность: повторные вызовы hashCode должны возвращать
            // одно и то же значение хэша при условии неизменности объекта

            //внешняя согласованность: для двух одинаковых объектов вызовы hashCode
            // должны возвращать одинаковое значение хэша

            //коллизии: есть вероятность, что для двух различных объектов вызовы hashCode
            // вернут одинаковые хэши

            // в соответствии с рекомендациями Джошуа Блоха
            // 23 и 19 - случайно выбранные простые числа
            int hashCode = 23;
            hashCode = 19 * hashCode + (model != null ? model.hashCode() : 0);
            hashCode = 19 * hashCode + (color != null ? color.hashCode() : 0);
            hashCode = 19 * hashCode + (releaseDate != null ? releaseDate.hashCode() : 0);
            hashCode = 19 * hashCode + maxSpeed;
            return hashCode;
        }
    }

    @Test
    public void assertTrueEquals() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Mercedes";
        car2.color = "black";
        car2.releaseDate = new GregorianCalendar(2020, 0, 25);
        car2.maxSpeed = 10;


        Assertions.assertTrue(car1.equals(car2));
    }

    @Test
    public void assertFalseEquals() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Audi";
        car2.color = "white";
        car2.releaseDate = new GregorianCalendar(2017, 0, 25);
        car2.maxSpeed = 10;

        Assertions.assertFalse(car1.equals(car2));
    }

    @Test
    public void assertFalseEqualsWhenNull() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        Car car2 = null;

        Assertions.assertFalse(car1.equals(car2));
    }

    @Test
    public void assertTrueEqualsTriple() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Mercedes";
        car2.color = "black";
        car2.releaseDate = new GregorianCalendar(2020, 0, 25);
        car2.maxSpeed = 10;

        Car car3 = new Car();
        car3.model = "Mercedes";
        car3.color = "black";
        car3.releaseDate = new GregorianCalendar(2020, 0, 25);
        car3.maxSpeed = 10;

        Assertions.assertTrue(car1.equals(car2));
        Assertions.assertTrue(car2.equals(car3));
        Assertions.assertTrue((car1.equals(car3)));
    }

    @Test
    public void assertFalseAndTrueEquals() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "BMW";
        car2.color = "black";
        car2.releaseDate = new GregorianCalendar(2020, 0, 25);
        car2.maxSpeed = 10;

        Assertions.assertFalse(car1.equals(car2));

        car2.model = "Mercedes";

        Assertions.assertTrue(car1.equals(car2));
    }

    @Test
    public void successEqualsHashCode() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Mercedes";
        car2.color = "black";
        car2.releaseDate = new GregorianCalendar(2020, 0, 25);
        car2.maxSpeed = 10;

        Assertions.assertEquals(car1.hashCode(), car2.hashCode());

    }

    @Test
    public void failEqualsHashCode() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Audi";
        car2.color = "white";
        car2.releaseDate = new GregorianCalendar(2017, 0, 25);
        car2.maxSpeed = 10;

        Assertions.assertNotEquals(car1.hashCode(), car2.hashCode());

    }

    @Test
    public void successConsistencyHashCode() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        int firstHash = car1.hashCode();

        Assertions.assertEquals(car1.hashCode(), car1.hashCode());

        car1.color = "red";

        Assertions.assertNotEquals(firstHash, car1.hashCode());

        car1.color = "black";

        Assertions.assertEquals(firstHash, car1.hashCode());
    }

}
