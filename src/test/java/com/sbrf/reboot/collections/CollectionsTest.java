package com.sbrf.reboot.collections;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionsTest {


    /*
     * Задача.
     * Имеется список лучших студентов вуза.
     *
     * 1. Иванов
     * 2. Петров
     * 3. Сидоров
     *
     * В новом семестре по результатам подсчетов оценок в рейтинг на 1 место добавился новый студент - Козлов.
     * Теперь в рейтинге участвуют 4 студента.
     * (Предполагаем что в рейтинг можно попасть только получив достаточное количество балов, что бы занять 1 место).
     *
     * Вопрос.
     * Какую коллекцию из реализаций интерфейса Collection вы предпочтете для текущего хранения и использования списка студентов?
     *
     * Проинициализируйте students, добавьте в нее 4 фамилии студентов что бы тест завершился успешно.
     */
    @Test
    public void addStudentToRating() {

        List<String> students = new LinkedList<>(Arrays.asList("Иванов", "Петров", "Сидоров"));

        students.add(0, "Козлов");

        assertEquals(4, students.size());
    }

    /*
     * Задача.
     * Вы коллекционируете уникальные монеты.
     * У вас имеется специальный бокс с секциями, куда вы складываете монеты в хаотичном порядке.
     *
     * Вопрос.
     * Какую коллекцию из реализаций интерфейса Collection вы предпочтете использовать для хранения монет в боксе.
     *
     * Проинициализируйте moneyBox, добавьте в нее 10 монет что бы тест завершился успешно.
     */
    @Test
    public void addMoneyToBox() {

        Set<Integer> moneyBox = new HashSet<>(Arrays.asList(1, 2, 3, 5, 10, 15, 30, 20, 50, 100));

        assertEquals(10, moneyBox.size());
    }

    /*
     * Задача.
     * Имеется книжная полка.
     * Периодически вы берете книгу для чтения, затем кладете ее на свое место.
     *
     * Вопрос.
     * Какую коллекцию из реализаций интерфейса Collection вы предпочтете использовать для хранения книг.
     *
     * Проинициализируйте bookshelf, добавьте в нее 3 книги что бы тест завершился успешно.
     */
    @Test
    public void addBookToShelf() {
        class Book {
        }

        List<Book> bookshelf = new ArrayList<>();
        bookshelf.add(new Book());
        bookshelf.add(new Book());
        bookshelf.add(new Book());

        assertEquals(3, bookshelf.size());
    }

    /*
     * Задача
     * Имеется список с идентификаторами заказчиков, совершившими заказы в течение дня
     * Количество записей равно числу всех заказов
     * При помощи коллекций узнать сколько заказчиков (уникально) было в этот день
     */
    @Test
    public void getCustomersUnique() {
        List<Integer> orders = new ArrayList<>(Arrays.asList(1, 6, 3, 7, 4, 5, 8, 2, 6, 1, 7, 5, 1, 9));

        Set<Integer> customersId = new HashSet<>(orders);

        assertEquals(9, customersId.size());
    }

    /*
     * Задача
     * Игра в проверку на память
     * Составляется набор вещей, которые игрок будет запоминать
     * Затем случайные элементы могут удаляться или переставляться
     * относительно других, а также в случайных местах могут появляться новые
     */
    @Test
    public void getPositionByGrowth() {
        List<String> things = new LinkedList<>(Arrays.asList("Шарик", "Ножницы" , "Бусы", "Кубик", "Карандаш"));
        things.remove("Ножницы");
        things.remove("Кубик");
        things.add(2, "Часы");

        assertEquals(new LinkedList<>(Arrays.asList("Шарик", "Бусы", "Часы", "Карандаш")), things);
    }
}
